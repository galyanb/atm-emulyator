package ru.sbrf.bg;

public interface Cell {
    void put(Integer count);
    Integer get (Integer count);
    Integer getCount();
    Nominal getNominal();

}
