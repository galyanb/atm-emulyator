package ru.sbrf.bg;

import java.io.IOException;

public interface ATMService {
    Integer getBalance();
     void saveToFile(String toFile) throws IOException;
}
