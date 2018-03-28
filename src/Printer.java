import java.text.*;
import java.util.*;

import Jama.*;

public class Printer {

    public static void printSystem(Matrix matrix, List<Double> array, int integerNr, int decimalNr) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                printElement(trimDecimalNumber(decimalNr,matrix.get(i, j)),integerNr,decimalNr);
            }
            if (i == matrix.getRowDimension() / 2) {
                System.out.print("* X =  ");
                System.out.println(trimDecimalNumber(decimalNr, array.get(i)));
            } else {
                System.out.println("       " + trimDecimalNumber(decimalNr, array.get(i)));
            }
        }
    }

    public static void printMatrix(Matrix matrix, int decimalNr){
        for(int i = 0 ; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++)
                System.out.print(trimDecimalNumber(decimalNr,matrix.get(i, j))+"       ");
            System.out.println();
        }
    }

    private static void printElement(double value, int integerNr, int decimalNr){
        int sum = integerNr+decimalNr+1, length = String.valueOf(value).length();
        StringBuilder spaces = new StringBuilder();
        spaces.append("      ");
        for(int i = length; i<sum; i++)
            spaces.append(" ");
        if(value<0)
            System.out.print(value+spaces.toString());
        else
            System.out.print(" "+value+spaces.toString());
    }

    private static double trimDecimalNumber(int decimalNr, double value) {
        StringBuilder format = new StringBuilder();
        format.append("#.");
        for (int i = 0; i < decimalNr; i++)
            format.append("#");
        return Double.parseDouble(new DecimalFormat(format.toString()).format(value));
    }

}
