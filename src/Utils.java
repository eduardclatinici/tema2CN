import static java.lang.Math.*;

import java.util.*;

import Jama.*;
import javafx.util.*;

public class Utils {

    public static double getDeterminant(Matrix matrix) {
        return matrix.det();
    }

    public static Pair<Matrix, List<Double>> getPivotedSystem(Matrix matrix, List<Double> array, int linel) throws Exception {
        int pivotLine = getPivotLine(matrix, linel);
        if(matrix.get(pivotLine,linel)==0.0)
            throw new Exception("Matrix is singular");
        Matrix pivotedMatrix = switchMatrixLines(matrix, pivotLine, linel, matrix.getRowDimension());
        List<Double> pivotedArray = switchArrayValues(array, pivotLine, linel);
        return new Pair<>(pivotedMatrix, pivotedArray);
    }

    public static int getPivotLine(Matrix matrix, int l) {
        Map<Double, Integer> map = new HashMap<>();
        for (int i = l; i < matrix.getRowDimension(); i++)
            map.put(abs(matrix.get(i, l)), i);
        return map.get(Collections.max(map.keySet()));
    }

    public static Matrix switchMatrixLines(Matrix matrix, int line1, int line2, int n) {
        if (line1 != line2) {
            List<Double> aux = getLine(matrix, line1);
            matrix = setLine(matrix, getLine(matrix, line2), line1);
            matrix = setLine(matrix, aux, line2);
        }
        return matrix;
    }

    public static List<Double> switchArrayValues(List<Double> array, int position1, int position2) {
        if (position1 != position2) {
            List<Double> newArray = new ArrayList<>();
            Double value1 = array.get(position1), value2 = array.get(position2);
            for (int i = 0; i < array.size(); i++) {
                if (i == position1) newArray.add(value2);
                else if (i == position2) newArray.add(value1);
                else newArray.add(array.get(i));
            }
            return newArray;
        }
        return array;
    }

    private static List<Double> getLine(Matrix matrix, int line) {
        List<Double> lineList = new ArrayList<>();
        for (int i = 0; i <= matrix.getColumnDimension() - 1; i++)
            lineList.add(matrix.get(line, i));
        return lineList;
    }

    private static Matrix setLine(Matrix matrix, List<Double> list, int line) {
        for (int i = 0; i <= matrix.getColumnDimension() - 1; i++)
            matrix.set(line, i, list.get(i));
        return matrix;
    }

    public static Matrix transformMatrix(Matrix matrix, int linei, int linel, double value) {
        List<Double> newList = new ArrayList<>();
        for (int i = 0; i < matrix.getColumnDimension(); i++)
            newList.add(matrix.get(linel, i) * value + matrix.get(linei, i));
        return setLine(matrix, newList, linei);
    }

    public static List<Double> transformArray(List<Double> array, int linei, int linel, double value) {
        List<Double> newArray = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if (i == linei)
                newArray.add(array.get(i) + value * array.get(linel));
            else
                newArray.add(array.get(i));
        }
        return newArray;
    }

}
