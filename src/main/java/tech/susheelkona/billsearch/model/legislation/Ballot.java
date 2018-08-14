package tech.susheelkona.billsearch.model.legislation;

public class Ballot {
    private int id;
    private String name;
    private String vote;

    public Ballot(int id, String name, String vote) {
        this.id = id;
        this.name = name;
        this.vote = vote;
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
}
