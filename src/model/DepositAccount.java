package model;

import java.time.LocalDate;

public class DepositAccount extends Account{
    private double minResidue;
    private static float INTEREST_RATE = 0.05f;

    public DepositAccount() {

    }

    public DepositAccount(String number, double residue, LocalDate dateCreation, double minResidue) {
        super(number, residue, dateCreation);
        this.minResidue = minResidue;
    }

    public double getMinResidue() {

        return minResidue;
    }

    public void setMinResidue(double minResidue) {

        this.minResidue = minResidue;
    }

    public double calculateInterest() {

        return (this.getResidue() * INTEREST_RATE);
    }

    @Override
    public String toString() {
        return "DepositAccount{" +
                "number='" + number + '\'' +
                ", residue=" + residue +
                ", dateCreation=" + dateCreation +
                ", minResidue=" + minResidue +
                '}';
    }

    @Override
    public void deposity(double deposit) {
        double balanceDeposit = getResidue() + deposit;
        setResidue(balanceDeposit);
    }

    @Override
    public boolean retirement(double valueR) {
        if ( valueR <= (getResidue() - getMinResidue()) ) {
            setResidue( (getResidue() - valueR) );
            return true;
        }
        return false;
    }
}
