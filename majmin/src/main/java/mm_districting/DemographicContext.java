package mm_districting;

import util.Race;

import javax.persistence.*;

/**
 * Holds demographic data in a given geographic boundry such as a state, district, or precinct.
 *
 * @author Patrick Wamsley
 * @author Brett Weinger
 * @author nikoziozis
 */
@Entity
@Table(name = "DEMOGRAPHIC_CONTEXT")
public class DemographicContext {

    private int contextId;
    private int total;
    private int white;
    private int africanAmerican;
    private int americanIndian;
    private int asian;
    private int pacificIslander;
    private int other;

    public DemographicContext() {}

    public DemographicContext(int total, int white, int africanAmerican, int americanIndian, int asian,
                              int pacificIslander, int other) {
        this.total = total;
        this.white = white;
        this.africanAmerican = africanAmerican;
        this.americanIndian = americanIndian;
        this.asian = asian;
        this.pacificIslander = pacificIslander;
        this.other = other;
    }

    public int getByRace(Race race) {
        switch (race) {
            case WHITE:
                return getWhite();
            case BLACK:
                return getAfricanAmerican();
            case ASIAN:
                return getAsian();
            case PACIFIC_ISLANDER:
                return getPacificIslander();
            case NATIVE_AMERICAN:
                return getAmericanIndian();
            case OTHER:
                return getOther();
        }
        return -1;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CONTEXT_ID")
    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }

    @Column(name = "TOTAL")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void addTotal(int value) {
        this.total += value;
    }

    @Column(name = "WHITE")
    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public void addWhite(int value) {
        this.white += value;
    }

    @Column(name = "AFRICAN_AMERICAN")
    public int getAfricanAmerican() {
        return africanAmerican;
    }

    public void setAfricanAmerican(int africanAmerican) {
        this.africanAmerican = africanAmerican;
    }

    public void addAfricanAmerican(int value) {
        this.africanAmerican += value;
    }

    @Column(name = "AMERICAN_INDIAN")
    public int getAmericanIndian() {
        return americanIndian;
    }

    public void setAmericanIndian(int americanIndian) {
        this.americanIndian = americanIndian;
    }

    public void addAmericanIndian(int value) {
        this.americanIndian += value;
    }

    @Column(name = "ASIAN")
    public int getAsian() {
        return asian;
    }

    public void setAsian(int asian) {
        this.asian = asian;
    }

    public void addAsian(int value) {
        this.asian += value;
    }

    @Column(name = "PACIFIC_ISLANDER")
    public int getPacificIslander() {
        return pacificIslander;
    }

    public void setPacificIslander(int pacificIslander) {
        this.pacificIslander = pacificIslander;
    }

    public void addPacificIslander(int value) {
        this.pacificIslander += value;
    }

    @Column(name = "OTHER")
    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public void addOther(int value) {
        this.other += value;
    }

    public static DemographicContext combine(DemographicContext one, DemographicContext two) {
        DemographicContext context = new DemographicContext();
        context.total = one.total + two.total;
        context.white = one.white + two.white;
        context.africanAmerican = one.africanAmerican + two.africanAmerican;
        context.asian = one.asian + two.asian;
        context.americanIndian = one.americanIndian + two.americanIndian;
        context.pacificIslander = one.pacificIslander + two.pacificIslander;
        context.other = one.other + two.other;
        return context;
    }

}
