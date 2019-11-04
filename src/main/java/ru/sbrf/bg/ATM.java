package ru.sbrf.bg;

import java.util.List;

public interface ATM {
    void putCash (List<Nominal> list);

    List<Nominal> getCash (Integer sum);

}
