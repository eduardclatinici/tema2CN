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

    public Pair<Matrix, List<Double>> transformMatrixAndArray() throws Exception {
        Pair<Matrix, List<Double>> pivoted; double element;
        Matrix transformedMatrix = matrix;
        List<Double> transformedArray = array;
        for (int l = 0; l < n - 1; l++) {
            pivoted = Utils.getPivotedSystem(transformedMatrix, transformedArray, l);
            transformedMatrix = pivoted.getKey();
            transformedArray = pivoted.getValue();
            for (int i = l + 1; i < n; i++){
                element = transformedMatrix.get(i,l)/transformedMatrix.get(l,l);
                for (int j = l + 1; j < n; j++){
                    transformedMatrix.set(i,j,transformedMatrix.get(i,j)-(element*transformedMatrix.get(l,j)));
                }
                transformedArray.set(i,transformedArray.get(i)-(element*transformedArray.get(l)));
                transformedMatrix.set(i,l,0.0);
            }
        }
        System.out.println("After Gauss:");
        Printer.printSystem(transformedMatrix,transformedArray,integerNr,decimalNr);
        return new Pair<>(transformedMatrix,transformedArray);
    }

    void transform(int linei, int linel, double value) {
        this.matrix = Utils.transformMatrix(matrix, linei, linel, value);
        this.array = Utils.transformArray(array, linei, linel, value);
    }
}
