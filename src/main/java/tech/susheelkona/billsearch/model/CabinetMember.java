package tech.susheelkona.billsearch.model;

public class CabinetMember extends Person {
    private int orderOfPrecedence;
    private String position;

    public int getOrderOfPrecedence() {
        return orderOfPrecedence;
    }

    public void setOrderOfPrecedence(int orderOfPrecedence) {
        this.orderOfPrecedence = orderOfPrecedence;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return super.getName();
    }


}
