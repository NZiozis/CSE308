import geopandas as gp
import datetime

initial_format = {'init':'epsg:4326'}
cart_format = {'init':'epsg:3395'}
refine_factor = 5

#cuts the given data frame based on given parameters
def cut_range(df, lon_high, lon_low, lat_high, lat_low):
    return df[
        ((df.INTPTLON10.astype('float64') >= lon_low) &
         (df.INTPTLON10.astype('float64') < lon_high)) &
        ((df.INTPTLAT10.astype('float64') >= lat_low) &
         (df.INTPTLAT10.astype('float64') < lat_high))] 


def get_bounds(df):
    max_lat = float('-inf')
    min_lat = float('inf')
    max_lon = float('-inf')
    min_lon = float('inf')
    
    for index, precinct in df.iterrows():   
        lon = float(precinct.INTPTLON10)
        lat = float(precinct.INTPTLAT10)
        if(lat > max_lat):
            max_lat = lat
        if(lat < min_lat):
            min_lat = lat
        if(lon > max_lon):
            max_lon = lon
        if(lon < min_lon):
            min_lon = lon
    
    return(max_lat, min_lat, max_lon, min_lon)
    

def bucket_maker(df, bucket_size, max_lat, min_lat, max_lon, min_lon, lat_step, lon_step, epsilon):

    lat_shift = lat_step * epsilon
    lon_shift = lon_step * epsilon
    
    #now we sort into buckets
    buckets = [[ cut_range(df,
                           (lon_step*(i+1)+min_lon+lon_shift),
                           (lon_step*i+min_lon-lon_shift),
                           (lat_step*(j+1)+min_lat+lat_shift),
                           (lat_step*j+min_lat-lat_shift) )
                 for j in range(bucket_size)] for i in range(bucket_size)]
    #Note, for consistency, we index as follows
    #buckets[LON][LAT]

    return buckets


def determine_bucket(precinct, bucket_size, max_lat, min_lat, max_lon, min_lon, lat_step, lon_step):
    
    bucket_lon = -1
    bucket_lat = -1
    #determine your bucket
    for i in range(bucket_size):
        for j in range(bucket_size):
            high_lat = (j+1)*lat_step + min_lat
            high_lon = (i+1)*lon_step + min_lon
            
            my_lat = float(precinct.INTPTLAT10)
            my_lon = float(precinct.INTPTLON10)
            if(my_lat < high_lat and my_lon < high_lon):
                return (i,j)
    

#we use refine bounds to create a expand our grid a bit
def refine_bounds(bucket_size, max_lat, min_lat, max_lon, min_lon, factor):
    lat_step = ((max_lat - min_lat) / bucket_size)
    lon_step = ((max_lon - min_lon) / bucket_size)
    
    #add a small epsilon to the min and max
    max_lat += (lat_step / factor)
    min_lat -= (lat_step / factor)
    max_lon += (lon_step / factor)
    min_lon -= (lon_step / factor)
    
    #now redefinne the step based on our new bounds
    lat_step = ((max_lat - min_lat) / bucket_size)
    lon_step = ((max_lon - min_lon) / bucket_size)
    
    return (max_lat, min_lat, max_lon, min_lon, lat_step, lon_step)



#find_neighbors('wrk/fl_round/fl_round.shp', 'fl_round_neighs.shp', 4, .25, 100)

def find_neighbors(src, dest, bucket_size, overlap, border_len):  
    latlon_df = gp.read_file(src) # open file
    latlon_df.crs = initial_format
    #cart_df = latlon_df.to_crs(epsg=3395) #so we can use meters
    cart_df = latlon_df.to_crs(cart_format) #so we can use meters

    cart_df["NEIGHBORS"] = None  # add NEIGHBORS column

    (max_lat, min_lat, max_lon, min_lon) = get_bounds(cart_df)
    (max_lat, min_lat, max_lon, min_lon, lat_step, lon_step) = refine_bounds(bucket_size, max_lat, min_lat, max_lon, min_lon, refine_factor)
    
    #now we break into little blocks,
    buckets = bucket_maker(cart_df, bucket_size, max_lat, min_lat, max_lon, min_lon, lat_step, lon_step, overlap)
 
    #create a dataframe to hold all the neighbors
    neighbors_df = pd.DataFrame(columns=['geoid1', 'geoid2'])
    
    for index, precinct in cart_df.iterrows():        
        (bucket_lat, bucket_lon) = determine_bucket(precinct, bucket_size, max_lat, min_lat, max_lon, min_lon, lat_step, lon_step)
        
        cur_df = buckets[bucket_lat][bucket_lon]
                
        # get 'not disjoint' precincts
        poss_neighs = cur_df[~cur_df.disjoint(precinct.geometry) == True]
            
        neighs = poss_neighs.intersection(precinct.geometry).length > border_len
        neighs = poss_neighs[neighs].GEOID10.tolist()
        # remove own name from the list
        neighs = [geoid for geoid in neighs if precinct.GEOID10 != geoid]
        #append to neighbors_df
        for n in neighs:
            dict1 = {'geoid1': precinct['GEOID10'], 'geoid2': n}
            neighbors_df = neighbors_df.append(dict1, ignore_index=True)

    #switch back
    final_df = cart_df.to_crs(initial_format)
    final_df.to_file(dest)
    return neighbors_df

#Compute the neighbors
neighbors_df = find_neighbors("west_virginia_precincts.json", "dest.shp", 4, .25, 100)
#Add the necessary padding to the geoids.
for index, row in neighbors_df.iterrows():
    a = neighbors_df.iloc[index]['geoid1'][0:5]
    b = neighbors_df.iloc[index]['geoid1'][5:].zfill(6)
    neighbors_df.at[index, 'geoid1'] = a + b
    
    a = neighbors_df.iloc[index]['geoid2'][0:5]
    b = neighbors_df.iloc[index]['geoid2'][5:].zfill(6)
    neighbors_df.at[index, 'geoid2'] = a + b
neighbors_df.to_csv("neighbors.csv", sep='\t', index=False)