package model;

import java.time.LocalDate;

public abstract class Account implements ActionsAccount{
    protected String number;
    protected double residue;
    protected LocalDate dateCreation;

    public Account(){

    }

    public Account(String number, double residue, LocalDate dateCreation) {
        this.number = number;
        this.residue = residue;
        this.dateCreation = dateCreation;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getResidue() {
        return residue;
    }

    public void setResidue(double residue) {
        this.residue = residue;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }


}
