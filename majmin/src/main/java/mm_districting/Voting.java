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

    @Column(name = "ELECTION")
    public Election getElection() {
        return election;
    }

    @Column(name = "VOTES")
    public int getVotes() {
        return votes;
    }

    @Column(name = "PARTY")
    public Party getParty() {
        return party;
    }
}


