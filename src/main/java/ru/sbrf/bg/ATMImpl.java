package ru.sbrf.bg;

import java.io.*;
import java.util.*;

public class ATMImpl implements ATMService, ATM {
    private Map<Nominal, Cell> atmStorage;

    public ATMImpl() {
        this.atmStorage = new HashMap<>();
        for ( Nominal nominal : Nominal.values() ) {
            this.atmStorage.put( nominal, new CellImpl( nominal, 0 ) );
        }
    }

    public ATMImpl(String fileName) throws IOException {
        File file = new File(fileName);
        this.atmStorage = new HashMap<>();
        boolean marker = true;
        if (file.exists()){
            FileReader fileReader = new FileReader(file);
            BufferedReader buf = new BufferedReader(fileReader);
            String s;
            try {
              while ((s=buf.readLine()) != null) {
                  Integer value = Integer.parseInt(s.substring(0,s.indexOf(":")));
                  Nominal nominal = Nominal.getNominalFromInt(value);
                  int count = Integer.parseInt(s.substring(s.indexOf(":")+1),s.length());
                 // System.out.println(s+" "+value+" "+count);
                  this.atmStorage.put( nominal, new CellImpl( nominal, count) );
                  marker=false;
              }
          } catch (NumberFormatException nfe){
              System.out.println("unpossible convert string to number "+nfe.getMessage());
          }
          buf.close();
        } else {
            System.out.println("File not found");
        }
        if (marker){
            for ( Nominal nominal : Nominal.values() ) {
                this.atmStorage.put( nominal, new CellImpl( nominal, 0 ) );
            }
        }
    }

    public void cellOut (Nominal nominal){
        if (this.atmStorage.containsKey(nominal)) {
            this.atmStorage.remove(nominal);
            System.out.println(nominal+" cell out");
        } else {
            System.out.println(nominal+" cell not exist");}

    }

    @Override
    public void putCash( List<Nominal> cashList ) {
        for ( Nominal banknoteNominal : cashList ) {
            Cell curCell = this.atmStorage.get( banknoteNominal );
            curCell.put( 1 );
            this.atmStorage.replace( banknoteNominal, curCell );
        }
    }

    @Override
    public List<Nominal> getCash( Integer sum ) {
        if ( sum % 100 != 0 ) {
            throw new IllegalArgumentException( "Введена некорректная сумма. Минимальная купюра - 100р." );
        }
        if ( sum > this.getBalance() ) {
            throw new IllegalArgumentException( "Запрашиваемая сумма превышает остаток денег в банкомате." );
        }
        List<Nominal> outList = new ArrayList<>();
        List<Nominal> nominalList = new ArrayList<>( this.atmStorage.keySet() );
        nominalList.sort( Comparator.reverseOrder() );

        Map<Nominal, Integer> checkMap = new HashMap<>();

        for ( Nominal nominal : nominalList ) {
            Cell cell = this.atmStorage.get( nominal );
            Integer mustGive = sum / nominal.getNominal();
            sum = sum % nominal.getNominal();

            Integer canGive = cell.getCount();

            if ( canGive < mustGive ) {
                sum += ( mustGive - canGive ) * nominal.getNominal();
                checkMap.put( nominal, canGive );
            } else {
                checkMap.put( nominal, mustGive );
            }
        }
        if ( sum != 0 ) {
            int iHave = 0;
            for ( Nominal key : checkMap.keySet() ) {
                iHave += key.getNominal() * checkMap.get( key );
            }
            throw new IllegalArgumentException( "Невозможно выдать запрашиваемую сумму имеющимися купюрами, максимально возможная сумма: " + iHave );
        } else {
            for ( Nominal key : checkMap.keySet() ) {
                addBanknotes( checkMap.get( key ), key, outList );
            }
        }
        return outList;
    }

    private void addBanknotes( Integer number, Nominal nominal, List<Nominal> outList ) {
        for ( int i = 0; i < number; i++ ) {
            outList.add( nominal );
        }
    }

    @Override
    public Integer getBalance() {
        Integer balance = 0;
        for ( Cell cell : this.atmStorage.values() ) {
            balance += cell.getCount() * cell.getNominal().getNominal();
        }
        return balance;
    }

    @Override
    public void saveToFile( String fileName ) throws IOException {
        File file = new File( fileName );
        if ( file.exists() ) {
            file.delete();
        }
        try ( FileWriter writer = new FileWriter( file ) ) {
            for ( Cell item : atmStorage.values() ) {
                String str = item.getNominal().getNominal() + ":" + item.getCount() + "\n";
                writer.write( str );
            }
        }
    }

}
