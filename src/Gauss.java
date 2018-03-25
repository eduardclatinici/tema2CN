import java.util.*;

import Jama.*;
import javafx.util.*;

public class Gauss {

    private Matrix matrix;

    private List<Double> array;

    private int n;

    private final int integerNr;
    private final int decimalNr;

    public Gauss(Matrix matrix, List<Double> array,int integerNumber, int decimalNumber) {
        this.matrix = matrix;
        this.array = array;
        this.n = array.size();
        this.integerNr = integerNumber;
        this.decimalNr = decimalNumber;
    }

    public void transformMatrixAndArray() throws Exception {
        Pair<Matrix, List<Double>> pivoted; double element;
        Matrix pivotedMatrix = matrix;
        List<Double> pivotedArray = array;
        for (int l = 0; l < n - 1; l++) {
            pivoted = Utils.getPivotedSystem(pivotedMatrix, pivotedArray, l);
            pivotedMatrix = pivoted.getKey();
            pivotedArray = pivoted.getValue();
            for (int i = l + 1; i < n; i++){
                element = pivotedMatrix.get(i,l)/pivotedMatrix.get(l,l);
                for (int j = l + 1; j < n; j++){
                    pivotedMatrix.set(i,j,pivotedMatrix.get(i,j)-(element*pivotedMatrix.get(l,j)));
                }
                pivotedArray.set(i,pivotedArray.get(i)-(element*pivotedArray.get(l)));
                pivotedMatrix.set(i,l,0.0);
            }
        }
        System.out.println("After Gauss:");
        Printer.printSystem(pivotedMatrix,pivotedArray,integerNr,decimalNr);
    }

    void transform(int linei, int linel, double value) {
        this.matrix = Utils.transformMatrix(matrix, linei, linel, value);
        this.array = Utils.transformArray(array, linei, linel, value);
    }
}
