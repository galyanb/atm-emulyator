package ru.sbrf.bg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class CellImpTest {
    @Test
    public void testGetPresent(){
        CellImpl cell = new CellImpl(Nominal.ONE_HUNDRED,245);
        cell.get(45);
    }

    @Test
    public void testGetAbsent(){
        CellImpl cell = new CellImpl(Nominal.ONE_HUNDRED,245);
        cell.get(445);
    }

}
