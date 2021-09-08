package model;

public class CheckBook {
    private String id;
    private String numberFrom;
    private String numberTo;

    public CheckBook(String id, String numberFrom, String numberTo) {
        this.id = id;
        this.numberFrom = numberFrom;
        this.numberTo = numberTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberFrom() {
        return numberFrom;
    }

    public void setNumberFrom(String numberFrom) {
        this.numberFrom = numberFrom;
    }

    public String getNumberTo() {
        return numberTo;
    }

    public void setNumberTo(String numberTo) {
        this.numberTo = numberTo;
    }
}
