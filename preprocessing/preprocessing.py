import pandas as pd
import geopandas as gpd
import numpy as np
import matplotlib.pyplot as plt
import shapely
import geojson

#Retrieve and store WV state information
states_map = gpd.read_file('us_map.json')
wv = states_map.loc[states_map['NAME'] == 'West Virginia']
wv_gjson = geojson.dumps(wv.iloc[0]['geometry'])
legal_guidelines = "LEGAL GUIDELINES PLACEHOLDER"
state_entry = [0, wv_gjson, legal_guidelines, 'West Virginia']
states = pd.DataFrame([state_entry], columns=['id', 'geography', 'legal guidelines', 'state_name'])
states.to_csv('states.csv')

#Next, load in district information, extract WV, and make district table.
districts = gpd.read_file("districts114.json")
districts = districts.loc[districts['STATENAME'] == "West Virginia"]
districts = districts[["ID", "DISTRICT", "geometry"]]
districts.columns = ["id", "district", "geometry"]
#Capture district id and geography in arrays
district_ids = []
geojsons = []
for index, row in districts.iterrows():
    district_ids.append(row['id'])
    geojsons.append(geojson.dumps(row['geometry']))
district_dict = {'id': district_ids, 'geojson': geojsons}
#Create new dataframe and save.
modified_districts = pd.DataFrame(district_dict)
modified_districts.to_csv("wv_districts.csv", sep='\t', index=False)

#Retrieve and parse 2016 House data.
congress = pd.read_csv("2016-precinct-house.csv", encoding='latin-1')
congress = congress.loc[congress['state'] == "West Virginia"]
congress = congress.loc[congress['office'] == "US House"]
congress = congress.loc[congress['stage'] == "gen"]
congress = congress[["year", "state", "state_postal", "office", "county_name", "county_fips", "jurisdiction", "precinct", "candidate", "candidate_normalized", "district", "party", "mode", "votes"]]
wv_2016_election = congress
#Retrieve and parse 2016 Presidential data.
pres = pd.read_csv("2016-precinct-president.csv", encoding='latin-1')
pres = pres.loc[pres['state'] == "West Virginia"]
pres = pres.loc[pres['office'] == 'US President']
pres = pres.loc[pres['stage'] == 'gen']
pres = pres[["year", "state", "state_postal", "office", "county_name", "county_fips", "jurisdiction", "precinct", "candidate", "candidate_normalized", "district", "party", "mode", "votes"]]
#Merge the dataframes.
election_df = pd.concat([wv_2016_election, pres], axis=0)

#Use the election data to form the precinct table.
pdata = election_df.drop_duplicates(subset=['county_name', 'precinct'])
precinct_ids = []
states = []
districts = []
counties = []
for index, row in pdata.iterrows():
    #Padding is required for the precinct_id to map to other data.
    precinct_ids.append(str(int(row["county_fips"])) + row["precinct"].zfill(6))
    states.append(row["state"])
    districts.append(row["district"])
    counties.append(row["county_name"])
precinct_dict = {'id': precinct_ids, 'state': states, 'district': districts, 'county': counties}
precinct_table = pd.DataFrame(precinct_dict)

#Create the mapping table from district -> precinct (one-to-many relationship).
dist_to_prec = precinct_table[['id', 'district']]
#Convert district number to key of district table.
for index, row in dist_to_prec.iterrows():
    if row['district'] == 1:
        dist_to_prec.at[index, 'district'] = 54113114001
    elif row['district'] == 2:
        dist_to_prec.at[index, 'district'] = 54113114002
    else:
        dist_to_prec.at[index, 'district'] = 54113114003
        
dist_to_prec.to_csv("dist_to_prec.csv", sep='\t', index=False)

#Go through the election data again, filling in voting information.
#This time the data is not cleared for duplicates.
precinct_ids = []
parties = []
votes = []
years = []
offices = []
for index, row in election_df.iterrows():
    precinct_ids.append(str(int(row["county_fips"])) + row["precinct"].zfill(6))
    parties.append(row["party"])
    votes.append(row["votes"])
    years.append(row["year"])
    offices.append(row["office"])
voting_dict = {'id': precinct_ids, 'year': years, 
               'office': offices, 'party': parties, 
               'votes': votes}
voting_table = pd.DataFrame(voting_dict)

#The office and year fields should be combined together into an int, easily accessable by an enum.
new_voting_table = voting_table[['id', 'office', 'party', 'votes']]
count = 0
for index, row in new_voting_table.iterrows():
    if row['office'] == 'US House':
        new_voting_table.at[index, 'office'] = 0
    elif row['office'] == 'US President':
        new_voting_table.at[index, 'office'] = 1
    if row['party'] == 'democratic':
        new_voting_table.at[index, 'party'] = 0
    elif row['party'] == 'republican':
        new_voting_table.at[index, 'party'] = 1
    else:
        new_voting_table.at[index, 'party'] = 2
    new_voting_table.at[index, 'id'] = count
    count = count + 1
new_voting_table.to_csv("voting.csv", sep='\t', index=False)

#Now get and parse the demographic data.
df = pd.read_csv("wv_dem_complete.csv")
df = df[['GEO.id', 'GEO.id2', 'GEO.display-label', 'VD01', 'VD02', 'VD03', 'VD04', 'VD05', 'VD06', 'VD07', 'VD08']]
wv_demographics = df
ids = []
totals = []
wh = []
bl = []
ind = []
asi = []
pac = []
other = []
for index, row in wv_demographics.iterrows():
    ids.append(row["GEO.id2"])
    totals.append(row["VD02"])
    wh.append(row["VD03"])
    bl.append(row["VD04"])
    ind.append(row["VD05"])
    asi.append(row["VD06"])
    pac.append(row["VD07"])
    other.append(row["VD08"])
dem_dict = {'id': ids, 'total': totals, 'white': wh,
            'african-american': bl, 'american-indian': ind,
            'asian': asi, 'pacific islander': pac, 'other': other}
demographic_table = pd.DataFrame(dem_dict)
new_demographic_table = demographic_table.copy()
count = 0
for index, row in new_demographic_table.iterrows():
    new_demographic_table.at[index, 'id'] = count
    count = count + 1

new_demographic_table.to_csv("demographics_table.csv", sep='\t', index=False)

#Create the mapping table between precincts and demographics.
precinct_to_dem = demographic_table[['id']]
counts = []
for i in range(0, len(precinct_to_dem.index)):
    counts.append(i)
precinct_to_dem['dem_context'] = counts
precinct_to_dem.to_csv("precinct_to_dem.csv", sep='\t', index=False)

#Lastly, include geographic data.
precincts = gpd.read_file("west_virginia_precincts.json")
precincts = precincts[["STATEFP10", "COUNTYFP10", "VTDST10", "GEOID10", "geometry"]]
#The key of the DB will be GEOID10, with VTDST10 padded to 6 digits.
padded_geoid = []
geometries = []
for index, row in precincts.iterrows():
    padded_geoid.append(row["STATEFP10"] + row["COUNTYFP10"] + row["VTDST10"].zfill(6))
    geometries.append(row["geometry"])
geo_dict = {'geoid': padded_geoid, 'geometry': geometries}
#Construct the table of unique geometric IDs mapping to geographies.
geography_table = pd.DataFrame(geo_dict)

#The precinct table should contain corresponding geographic information.
new_precinct_table = precinct_table.copy()
new_precinct_table = new_precinct_table[['id', 'county']]
new_precinct_table.columns = ['geoid', 'county']
#Perform a join to obtain this data.
precinct_geometries = pd.merge(new_precinct_table, geography_table, on='geoid')

#The polygon shape in the GeoDataFrame needs to be converted to a MultiPolygon in GeoJSON.
new_precinct_ids = []
new_precinct_counties = []
new_precinct_strs = []
for index, row in precinct_geometries.iterrows():
    geojson_str = geojson.dumps(precinct_geometries.iloc[index]['geometry'])
    if 'MultiPolygon' in geojson_str:
        geojson_str = "{\"type\": \"GeometryCollection\", \"geometries\": [" + geojson_str + "]}"
    else:
        geojson_str = geojson_str.replace('Polygon', 'MultiPolygon')
        geojson_str = geojson_str.replace('[[[', '[[[[')
        geojson_str = geojson_str.replace(']]]', ']]]]')
        geojson_str = "{\"type\": \"GeometryCollection\", \"geometries\": [" + geojson_str + "]}"
    new_precinct_ids.append(row['geoid'])
    new_precinct_counties.append(row['county'])
    new_precinct_strs.append(geojson_str)
new_precinct_dict = {"geoid": new_precinct_ids, "county": new_precinct_counties, "geography": new_precinct_strs}
final_precinct_table = pd.DataFrame(new_precinct_dict)
final_precinct_table.to_csv("precincts.csv", quoting=csv.QUOTE_ALL)