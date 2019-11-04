package ru.sbrf.bg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Starter {
    private ATM atm;
    private String FILE_NAME;

    public static void main(String[] args) throws IOException {
        Starter starter = new Starter();
        starter.FILE_NAME = args[0];
        starter.startAtm();
        starter.startClientInteraction();

    }

    private void startClientInteraction() throws IOException{
        System.out.println("Hello, whats your name?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println(name+" Whats your next action? (add, get, exit)");
        String operation = scanner.nextLine();
        while (! operation.equalsIgnoreCase("exit")){
            switch (operation.toLowerCase()){
                case "add":
                    System.out.println("what's nominal");
                    Integer value = scanner.nextInt();
                    scanner.nextLine();
                    Nominal nominal = Nominal.getNominalFromInt(value);
                    if (nominal != null){
                        List<Nominal> nominals = new ArrayList<>();
                        nominals.add(nominal);
                        atm.putCash(nominals);
                        System.out.println("Succes");
                    } else {
                        System.out.println("What is das?");
                    }
                    break;
                case "get":
                    System.out.println("what's summa?");
                    Integer sum = scanner.nextInt();
                    scanner.nextLine();
                    atm.getCash(sum);
                    break;
                default:
                    System.out.println("Incorrect command");
            }
            System.out.println(" Whats your next action? (add, get, exit)");
            operation = scanner.nextLine();
        }
        ((ATMService) atm).saveToFile(FILE_NAME);
    }

    private void startAtm (){
        atm = new ATMImpl();
    }
}
