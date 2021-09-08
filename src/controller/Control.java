package controller;

import exceptions.ValueException;
import model.*;
import view.View;

import javax.swing.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;

public class Control {
    private View view;
    private ManagementAccount managementAccount;
    private Validations val;
    private int contAcc;

    public Control() {
        view = new View();
        managementAccount = new ManagementAccount();
        val = new Validations();
        contAcc = 0;
    }

    public void initialMenu() {
        boolean check = true;
        int option = 0;
        while ( check ) {
            try {
                option = view.optionMenu();
            } catch (Exception e) {
                view.showMessageErr("Acción inválida");
            }
            switch ( option ) {
                case 1:
                    handlingAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    retirement();
                    break;
                case 4:
                    break;
                case 5:
                    showReports();
                    break;
                case 6:
                    check = false;
                    break;
            }
        }
    }

    private void handlingAccount() {
        String options = "[Yes] Agregar cuenta\n[No] Eliminar cuenta\n[Cancel] Consultar cuenta";
        switch ( view.confirmDialog( options ) ) {
            case 0:
                createAccount();
                break;
            case 1:
                break;
            case 2:
                consultAccount();
                break;
        }
    }

    private void createAccount() {
        String [] dataAccount = new String[2];
        try {
            double cant = view.readDouble("Ingrese saldo para crear la cuenta");
            val.valCant( cant );
            dataAccount[1] = new Double( cant ).toString();
            contAcc++;
            dataAccount[0] = new Integer( contAcc ).toString();

            managementAccount.addAccount( chooseET(), dataAccount );
        } catch (ValueException valueException) {
            view.showMessageErr(valueException.getMessage());
            handlingAccount();
        } catch (Exception e) {
            handlingAccount();
        }

        managementAccount.getAccounts().forEach( accs -> System.out.println(accs));
    }

    private ETypeAccount chooseET() {
        ETypeAccount typeAccount = null;
        String[] typesAccount = {"Cuenta corriente", "Cuenta de ahorros"};
        try {
            switch (view.selection("Seleccione tipo de cuenta", typesAccount)) {
                case "Cuenta corriente":
                    typeAccount = ETypeAccount.CURRENT;
                    break;
                case "Cuenta de ahorros":
                    typeAccount = ETypeAccount.DEPOSIT;
                    break;
            }
            return typeAccount;
        } catch (Exception e) {
        }
        return typeAccount;
    }

    private void consultAccount() {
        try {
            Account account = managementAccount.findAccount(view.readString("Digite el numero de cuenta"));
            String typeAcc = account.getClass().equals(CurrentAccount.class) ? "Cuenta Corriente" : "Cuenta de Ahorro";
            String dataAcc = "";
            if ( dataAcc.equals("Cuenta Corriente") ) {
                dataAcc = "N° de cuenta: " + ((CurrentAccount)account).getNumber() + "\nTipo de cuenta: " + typeAcc
                        + "\nSaldo: $" + ((CurrentAccount)account).getResidue() + "\nNumero de cheques" + ((CurrentAccount)account).getCheckBooks().size();
            } else  {
                dataAcc = "N° de cuenta: " + ((DepositAccount)account).getNumber() + "\nTipo de cuenta: " + typeAcc
                        + "\nSaldo: $" + ((DepositAccount)account).getResidue() + "\nInteres: $" + ((DepositAccount)account).calculateInterest();
            }
            view.showMessage(dataAcc);
        } catch (Exception e){
            view.showMessageErr("Cuenta no registrada");
            handlingAccount();
        }
    }

    private Account searchAccount() {
        return managementAccount.findAccount(view.readString("Digite en numero de cuenta"));
    }

    private void deleteAccount() {
        try {
            managementAccount.deleteAccount(view.readString("Digite en numero de cuenta"));
            view.showMessage("Cuenta eliminada");
        } catch (Exception e) {
            view.showMessageErr("Cuenta no registrada");
            handlingAccount();
        }
    }

    private void deposit() {
        try {
            String number = view.readString("Digite en numero de cuenta");
            double deposit = view.readDouble("Digite la cantidad $");
            managementAccount.deposity(number, deposit);
            System.out.println("Nuevo saldo: $" + managementAccount.findAccount(number).getResidue());
            String report = "Deposito | " + number + " | " + deposit + " | " + LocalDate.now() + " | " + LocalTime.now();
            System.out.println( report );
            managementAccount.addReport( report );
        } catch (NullPointerException nullEx) {
            view.showMessageErr("Cuenta no registrada");
        } catch (ValueException vEx) {
            view.showMessageErr( vEx.getMessage() );
        }
    }

    private void retirement() {
        try {
            String number = view.readString("Digite en numero de cuenta");
            double valueR = view.readDouble("Digite la cantidad $");
            managementAccount.retirement(number, valueR);
            System.out.println("Nuevo saldo: $" + managementAccount.findAccount(number).getResidue());
            String report = "Retiro | " + number + " | " + valueR + " | " + LocalDate.now() + " | " + LocalTime.now();
            System.out.println( report );
            managementAccount.addReport( report );
        } catch (NullPointerException nullEx) {
            view.showMessageErr("Cuenta no registrada");
        } catch (ValueException vEx) {
            view.showMessageErr( vEx.getMessage() );
        }
    }

    void showReports() {
        try {
            String in = "| Reporte | N° | Cantidad | Fecha | Hora |";
            String re= "";
            for ( String arr : managementAccount.getReports() ){
                re += arr.toString() + "\n";
            }
            String result = in + "\n" + re;
            view.showMessage(result);

        } catch (Exception e ) {
            view.showMessageErr(e.getMessage());
        }
    }

}
