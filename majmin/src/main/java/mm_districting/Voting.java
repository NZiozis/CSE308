package mm_districting;

import util.Election;
import util.Party;

import javax.persistence.*;

@Entity
@Table(name = "VOTING")
public class Voting {

    private int      dataId;
    private int      votes;
    private Party    party;
    private Election election;

    public Voting() {}

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
}


