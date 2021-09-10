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
    private CurrentAccount currentAccount;
    private Validations val;
    private int contAcc;

    public Control() {
        view = new View();
        managementAccount = new ManagementAccount();
        currentAccount = new CurrentAccount();
        val = new Validations();
        contAcc = 0;
    }

    public void initialMenu() {
        boolean check = true;
        int option = 0;
        int optionCheck = 0;
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
                    try {
                        optionCheck = view.menuCheckBook();
                    } catch (Exception e){
                        view.showMessageErr("No es válido");
                    }
                    if (optionCheck == 1){
                       addCheckBook();
                    }else{
                        findChecks();
                    }
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
        String options = "Cantidad de cuentas " + managementAccount.getAccounts().size() +
                "\n El promedio de las cuentas es:" + managementAccount.getAverageAccounts()
                + " \n [Yes] Agregar cuenta\n[No] Eliminar cuenta\n[Cancel] Consultar cuenta";

        switch ( view.confirmDialog( options ) ) {
            case 0:
                createAccount();
                break;
            case 1:
                deleteAccount();
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

    private void addCheckBook(){
        try {
            String id = view.readString("Ingrese el ID del cheque que desea cear");
            String numberFrom = view.readString("Ingrese el numero de: ");
            String numberTo = view.readString("Ingrese el numero a: ");
            managementAccount.addCheckBook(id, numberFrom, numberTo);

        } catch (Exception e) {

        }
        managementAccount.getCheckBooks().forEach(checks -> System.out.println(checks));
    }

    private void findChecks() {
        try {
            CheckBook checkBook = managementAccount.findCheckBook(view.readString("Digite el ID del cheque"));
            String dataCheck = "ID de cheque: " + checkBook.getId() + "\nNumero de: " + checkBook.getNumberFrom() + "\nNumero a: " + checkBook.getNumberTo();
            view.showMessage(dataCheck);
        } catch (Exception e){
            view.showMessageErr("Cheque no encontrado");
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
