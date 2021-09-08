package model;

import exceptions.ValueException;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManagementAccount {
    private ArrayList<Account> accounts;
    private ArrayList<String> reports;

    public ManagementAccount() {
        accounts = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public double deposity( String number, double deposit) throws ValueException {
        if ( deposit < 5000 ) {
            throw new ValueException("El valor debe ser igual o mayor a $5000");
        } else {
            Account account = findAccount(number);
            account.deposity(deposit);
            return account.getResidue();
        }
    }

    public boolean retirement( String number, double valueR ) throws ValueException {
        Account account = findAccount( number );
        boolean checkR = account.retirement(valueR);
        if ( !checkR ) {
            throw new ValueException("No es posible retirar, saldo insuficiente");
        } else if ( valueR < 10_000 ) {
            throw new ValueException("El valor de los retiros deben ser en valores de 10000");
        } else {
            return checkR;
        }
    }

    public Account findAccount( String number ) {
        for (Account accs : accounts) {
            if ( accs.getNumber().equals( number ) ) {
                return accs;
            }
        }
        return null;
    }

    public boolean addAccount(ETypeAccount typeAccount, String ... dataAccount) throws ValueException {
        String number = dataAccount[0];
        double residue = Double.parseDouble( dataAccount[1] );
        Account checkAc = typeAccount.equals(ETypeAccount.CURRENT) ?
                new CurrentAccount(number, residue, LocalDate.now(), 4_500)
                : new DepositAccount(number, residue, LocalDate.now(), 20_000);
        if (accounts.isEmpty()) {
            accounts.add(checkAc);
            return true;
        } else {
            if (findAccount(number) == null) {
                accounts.add(checkAc);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Account> getAccounts() {
        return (ArrayList<Account>) accounts.clone();
    }

    public boolean addCheckBook(String number, String numberFrom, String numberTo) {
        return false;
    }

    public double getAverageAccounts() {
        double av = 0;
        for (Account accs : accounts) {
            av += accs.getResidue();
        }
        return ( av / accounts.size() );
    }

    public boolean deleteAccount(String number) {
        return accounts.remove( findAccount( number ) );
    }

    public boolean addReport( String report ) {
        return reports.add(report);
    }

    public ArrayList<String> getReports() {
        return (ArrayList<String>) reports.clone();
    }
}
