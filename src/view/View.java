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
        JOptionPane.showMessageDialog(null, message, "error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showMessageWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public String readString(String message) {
        String input = JOptionPane.showInputDialog(null, message, JOptionPane.QUESTION_MESSAGE);
        return input;
    }

    public double readDouble(String message) {
        double inputDouble = Double.parseDouble(JOptionPane.showInputDialog(null, message, JOptionPane.QUESTION_MESSAGE));
        return inputDouble;
    }

    public int confirmDialog(String message) {
        int conf = JOptionPane.showConfirmDialog(null, message, "",JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE);
        return conf;
    }

    public String selection(String massage, String ... options) {
        Object selec = JOptionPane.showInputDialog(null, massage, "", JOptionPane.QUESTION_MESSAGE, null, options, null);
        return selec.toString();
    }

}
