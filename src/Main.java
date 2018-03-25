import java.util.*;

import Jama.*;

public class Main {

    private static final int N = 7;
    private static final int INTEGER_NUMBER = 2;
    private static final int DECIMAL_NUMBER = 5;


    public static void main(String[] args) {
        execute();
        System.out.println("Done");
    }

    public static void execute() {
        Provider provider = new Provider(N, INTEGER_NUMBER, DECIMAL_NUMBER);
        Matrix initialMatrix = provider.getRandomMatrix();
        List<Double> initialArray = provider.getRandomArray();
        System.out.println("initial system: ");
        Printer.printSystem(initialMatrix,initialArray,INTEGER_NUMBER, DECIMAL_NUMBER);
        System.out.println();

        Gauss gauss = new Gauss(initialMatrix, initialArray, INTEGER_NUMBER, DECIMAL_NUMBER);
        try {
            gauss.transformMatrixAndArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
