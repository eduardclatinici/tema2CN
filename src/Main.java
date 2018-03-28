import java.util.*;

import Jama.*;
import javafx.util.*;

public class Main {

    private static final int N = 5;
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
        Pair<Matrix, List<Double>> transformed;
        List<Double> solutions;

        System.out.println("matrix inverse: ");
        Printer.printMatrix(initialMatrix.copy().inverse(),DECIMAL_NUMBER);
        System.out.println("initial system: ");
        Printer.printSystem(initialMatrix.copy(),initialArray,INTEGER_NUMBER, DECIMAL_NUMBER);
        System.out.println();

        Gauss gauss = new Gauss(initialMatrix.copy(), initialArray, INTEGER_NUMBER, DECIMAL_NUMBER);
        try {
            transformed = gauss.transformMatrixAndArray();
            InverseSubstitution inverseSubstitution = new InverseSubstitution(transformed.getKey(),transformed.getValue());
            solutions = inverseSubstitution.getSolution();
            Utils.printSolution(initialMatrix.copy(),initialArray,solutions,DECIMAL_NUMBER, INTEGER_NUMBER,true);
            Matrix solutionsLib = Utils.calculateSolLib(initialMatrix, initialArray);
            Utils.calculateSecondNorm(solutionsLib,solutions,DECIMAL_NUMBER);
            Utils.printSolution(initialMatrix.copy().inverse(),initialArray,solutions,DECIMAL_NUMBER,INTEGER_NUMBER,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
