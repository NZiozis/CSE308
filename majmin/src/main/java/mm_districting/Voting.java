package mm_districting;

import util.Election;
import util.Party;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VOTING")
public class Voting {

    private int      dataId;
    private int      votes;
    private Party    party;
    private Election election;

    public Voting() {}

    public Voting(int votes, Party party, Election election) {
        this.votes = votes;
        this.party = party;
        this.election = election;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DATA_ID")
    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    @Column(name = "ELECTION")
    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    @Column(name = "VOTES")
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Column(name = "PARTY")
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public static Set<Voting> combineVotingSets(Set<Voting> setOne, Set<Voting> setTwo) {
        Set<Voting> combinedData = new HashSet<>();
        for (Voting v : setOne) {
            Voting combined = new Voting();
            Voting matching = getMatchingVoting(v, setTwo);
            combined.votes = v.votes + matching.votes;
            combined.party = v.party;
            combined.election = v.election;
            combinedData.add(combined);
        }
        return combinedData;
    }

    @Transient
    private static Voting getMatchingVoting(Voting voting, Set<Voting> set) {
        for (Voting v : set) {
            if (v.getParty() == voting.getParty() && v.getElection() == voting.getElection()) {
                return v;
            }
        }
        return null;
    }
}


