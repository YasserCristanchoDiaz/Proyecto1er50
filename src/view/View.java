package view;

import javax.swing.*;

public class View {

    public int optionMenu() {
        int option = Integer.parseInt(JOptionPane.showInputDialog(null, "Seleccion de usuario\n" +
                "[1] Gestión de cuentas\n" +
                "[2] Depositar\n" +
                "[3] Retirar\n" +
                "[4] Cheques\n" +
                "[5] Reportes\n" +
                "[6] Salir\n", "Menu Inicio", JOptionPane.QUESTION_MESSAGE));

        if ( option < 1 || option > 6 )
            showMessageErr("Digito invalido");
        return option;
    }

    public void showMessageErr(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showMessageWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public String readString(String message, String title) {
        String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
        return input;
    }

    public double readDouble(String message, String title) {
        double inputDouble = Double.parseDouble(JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE));
        return inputDouble;
    }

    public int confirmDialog(String message, String title) {
        int conf = JOptionPane.showConfirmDialog(null, message, title,JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE);
        return conf;
    }

    public String selection(String massage, String ... options) {
        Object selec = JOptionPane.showInputDialog(null, massage, "", JOptionPane.QUESTION_MESSAGE, null, options, null);
        return selec.toString();
    }

    public int menuCheckBook() {
        int optionCheck = Integer.parseInt(JOptionPane.showInputDialog(null, "Seleccion\n" +
                "[1] Agregar cheque\n" +
                "[2] Buscar cheque\n", "Menu para cheques", JOptionPane.QUESTION_MESSAGE));

        if ( optionCheck < 1 || optionCheck > 2 )
            showMessageErr("Digito invalido");
        return optionCheck;
    }
}
