package tech.susheelkona.billsearch.model.legislation;

public class Ballot {
    private int id;
    private String name;
    private String vote;
    private String party;

    public Ballot(int id, String name, String vote, String party) {
        this.id = id;
        this.name = name;
        this.vote = vote;
        this.party = party;
    }

    public Ballot() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
