package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class CurrentAccount extends Account{
    private double overdraft;
    private ArrayList<CheckBook> checkBooks;

    public CurrentAccount() {
    }

    public CurrentAccount(String number, double residue, LocalDate dateCreation, double overdraft) {
        super(number, residue, dateCreation);
        this.overdraft = overdraft;
    }

    // * *
    public boolean findCheckBook(String id){
        for (int i = 0; i < checkBooks.size(); i++) {
            if (checkBooks.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean addCheckBook(String id, String numberFrom, String numberTo){
        if (findCheckBook(id) == false){
            checkBooks.add(new CheckBook(id, numberFrom, numberTo));
            return true;
        }
        return false;
    }

    public ArrayList<CheckBook> getCheckBooks(){
        return (ArrayList<CheckBook>) checkBooks.clone();
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public void deposity(double valueDepo) {
        double balanceDeposit = getResidue() + valueDepo;
        setResidue(balanceDeposit);
    }

    @Override
    public boolean retirement(double valueR) {
        double res = getResidue() - (valueR + getOverdraft());
        if (res < 0){
            return false;
        }else{
            setResidue(res);
            return true;
        }
    }
}
