package model;

import exceptions.ValueException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ManagementAccountTest {
    private ManagementAccount mng;

    private Account account;

    String[] dataOne = {"001", "50000"};
    String[] dataTwo = {"063", "20000"};
    String[] dataThree = {"021", "85000"};
    String[] dataFour = {"0049", "23000"};

    private void setup() {
         mng = new ManagementAccount();

        try {
            mng.addAccount(ETypeAccount.CURRENT, dataOne);
            mng.addAccount(ETypeAccount.DEPOSIT, dataTwo);
        } catch (ValueException valueException) {
            valueException.printStackTrace();
        }

    }

    @Test
    void deposity() {
        setup();
        account = mng.findAccount("001");
        try {
            mng.deposity("001", 10000);
            assertEquals(60000, mng.findAccount("001").getResidue());
            mng.deposity("001", 30_000);
            assertEquals(90000, mng.findAccount("001").getResidue());

            mng.addAccount(ETypeAccount.CURRENT, dataThree);
            mng.deposity("021", 15_000);
            assertEquals(100_000, mng.findAccount("021").getResidue());
            mng.deposity("021", 40_000);
            assertEquals(140_000, mng.findAccount("021").getResidue());

        } catch (ValueException valueException) {
            valueException.printStackTrace();
        }


    }

    @Test
    void retirement() {
        setup();

        try {
            assertTrue(mng.retirement("001", 10000));
            assertEquals(35500, mng.findAccount("001").getResidue());
            assertTrue(mng.retirement("001", 31000));
            assertFalse(mng.retirement("001", 100));
            assertFalse(mng.retirement("063", 100));
            mng.addAccount(ETypeAccount.DEPOSIT, dataFour);
            assertTrue(mng.retirement("0049", 3000));
            assertFalse(mng.retirement("0049", 3000));
            String[] dataFive = {"0156", "45000"};
            mng.addAccount(ETypeAccount.DEPOSIT, dataFive);
            assertTrue(mng.retirement("0156", 13000));
            assertEquals(32000, mng.findAccount("0156").getResidue());

        } catch (Exception e) {
            //valueException.printStackTrace();
        }
    }

    @Test
    void findAccount() {
        setup();

        assertNotNull(mng.findAccount(dataOne[0]));
        assertNotNull(mng.findAccount(dataTwo[0]));
        assertNull(mng.findAccount(dataThree[0]));


    }

    @Test
    void addAccount() {
        setup();

        try {
            assertTrue(mng.addAccount(ETypeAccount.CURRENT, dataThree));
            assertFalse(mng.addAccount(ETypeAccount.CURRENT, dataOne));
            assertTrue(mng.addAccount(ETypeAccount.DEPOSIT, dataFour));
            assertEquals(4, mng.getAccounts().size());
            assertFalse(mng.addAccount(ETypeAccount.DEPOSIT, dataFour));
        } catch (ValueException valueException) {
            valueException.printStackTrace();
        }
    }

    @Test
    void findCheckBook() {
        setup();
        assertNull(mng.findCheckBook("1"));

        assertNull(mng.findCheckBook("3"));

        mng.addCheckBook("1", "234", "21");
        mng.addCheckBook("2", "543", "2543");
        assertNotNull(mng.findCheckBook("2"));
        assertEquals(2, mng.getCheckBooks().size());
    }

    @Test
    void addCheckBook() {
        setup();
        assertTrue(mng.addCheckBook("1", "234", "21"));
        assertFalse(mng.findCheckBooks("3"));
        assertTrue(mng.findCheckBooks("1"));
        assertTrue(mng.addCheckBook("2", "23454", "21432"));
    }

    @Test
    void getAverageAccounts() {
        setup();

        assertEquals(35000, mng.getAverageAccounts());

        try {
            mng.addAccount(ETypeAccount.DEPOSIT, dataFour);
        } catch (ValueException valueException) {
            valueException.printStackTrace();
        }

        assertEquals(31000, mng.getAverageAccounts());
    }

    @Test
    void deleteAccount() {
        setup();

        account = mng.findAccount("001");
        assertTrue(mng.deleteAccount("001"));
        assertFalse(mng.deleteAccount("001"));

        try {
            mng.addAccount(ETypeAccount.CURRENT, dataThree);
            assertTrue(mng.deleteAccount("021"));
            assertFalse(mng.deleteAccount("021"));
        } catch (ValueException valueException) {
            valueException.printStackTrace();
        }

    }


}