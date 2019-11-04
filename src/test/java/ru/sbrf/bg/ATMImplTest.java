package ru.sbrf.bg;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ATMImplTest {

    @Test
    public void getCashTestEven() {
        List<Nominal> nominals = new ArrayList<>();
        nominals.add( Nominal.FIVE_HUNDRED );
        nominals.add( Nominal.TWO_HUNDREDS );
        nominals.add( Nominal.ONE_HUNDRED );
        nominals.add( Nominal.ONE_HUNDRED );
        nominals.add( Nominal.ONE_HUNDRED );

        ATM atm = new ATMImpl();
        atm.putCash( nominals );

        List<Nominal> gotList = atm.getCash( 1000 );

        Integer gotCash = 0;
        for ( Nominal nominal : gotList ) {
            gotCash += nominal.getNominal();
        }

        assertEquals( 1000, gotCash );
    }

    @Test
    public void getCashTestLess() {
        List<Nominal> nominals = new ArrayList<>();
        nominals.add( Nominal.FIVE_HUNDRED );
        nominals.add( Nominal.TWO_HUNDREDS );
        nominals.add( Nominal.ONE_HUNDRED );
        nominals.add( Nominal.ONE_HUNDRED );
        nominals.add( Nominal.ONE_HUNDRED );

        ATM atm = new ATMImpl();
        atm.putCash( nominals );

        List<Nominal> gotList = atm.getCash( 900 );

        Integer gotCash = 0;
        for ( Nominal nominal : gotList ) {
            gotCash += nominal.getNominal();
        }

        assertEquals( 900, gotCash );
    }
//    @Test
//    public void getCashTestNegative() {
//        List<Nominal> nominals = new ArrayList<>();
//        nominals.add( Nominal.FIVE_HUNDRED );
//        nominals.add( Nominal.TWO_HUNDREDS );
//        nominals.add( Nominal.ONE_HUNDRED );
//        nominals.add( Nominal.ONE_HUNDRED );
//        nominals.add( Nominal.ONE_HUNDRED );
//
//        ATM atm = new ATMImpl();
//        atm.putCash( nominals );
//
//        assertThrows( IllegalArgumentException.class, () -> {
//            atm.getCash( 950 );
//
//        } );
//    }
//
//    @Test
//    public void getCashTestOverdraft() {
//        List<Nominal> nominals = new ArrayList<>();
//        nominals.add( Nominal.FIVE_HUNDRED );
//        nominals.add( Nominal.TWO_HUNDREDS );
//        nominals.add( Nominal.ONE_HUNDRED );
//        nominals.add( Nominal.ONE_HUNDRED );
//        nominals.add( Nominal.ONE_HUNDRED );
//
//        ATM atm = new ATMImpl();
//        atm.putCash( nominals );
//
//        assertThrows( IllegalArgumentException.class, () -> {
//            atm.getCash( 1200 );
//        } );
//    }
//
//    private void assertThrows(Class<IllegalArgumentException> illegalArgumentExceptionClass, Object o) {
//    }

}
