package controller;

import exceptions.ValueException;

public class Validations {

    public void valCant(double cant ) throws ValueException {
        if ( cant < 20000 ) {
            throw new ValueException("Asegurese de que el valor sea igual o mayor a 20000 para crear la cuenta");
        }
    }

    public void valNull(String number) {

    }
}
