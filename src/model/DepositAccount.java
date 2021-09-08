package model;

import java.time.LocalDate;

public class DepositAccount extends Account{
    private double minResidue;
    private final float INTEREST_RATE = (float) 0.05;

    public DepositAccount(String number, double residue, LocalDate dateCreation, double minResidue, double INTEREST_RATE) {
        super(number, residue, dateCreation);
        this.minResidue = minResidue;
    }

    public DepositAccount() {
    }

    // ***
    public double calculateInterest(){
        return this.residue * INTEREST_RATE;
    }

    public double getMinResidue() {
        return minResidue;
    }

    public void setMinResidue(double minResidue) {
        this.minResidue = minResidue;
    }
}
