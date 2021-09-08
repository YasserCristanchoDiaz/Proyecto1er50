package view;

import controller.Control;

import javax.swing.*;
import java.util.ConcurrentModificationException;

public class Runner {

    static public void main( String ... args ) {
        new Control().initialMenu();
    }
}
