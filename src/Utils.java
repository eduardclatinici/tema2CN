import static java.lang.Math.*;

import java.math.*;
import java.text.*;
import java.util.*;

import Jama.*;
import javafx.util.*;

public class Utils {

    public static double getDeterminant(Matrix matrix) {
        return matrix.det();
    }

    public static Pair<Matrix, List<Double>> getPivotedSystem(Matrix matrix, List<Double> array,
                                                              int linel) throws Exception {
        int pivotLine = getPivotLine(matrix, linel);
        if (matrix.get(pivotLine, linel) == 0.0)
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

    public static void printSolution(Matrix initialMatrix, List<Double> initialArray, List<Double> solutions,
                                     int decimalNr, int integerNr, boolean flag) {
        List<BigDecimal> solvedMatrix = new ArrayList<>();
        double sum;
        for (int i = 0; i < initialArray.size(); i++) {
            sum = 0;
            for (int j = 0; j < initialArray.size(); j++) {
                sum += trimDecimalNumber(decimalNr,
                        initialMatrix.get(i, j) * trimDecimalNumber(decimalNr, solutions.get(j)));
            }
            sum = trimDecimalNumber(decimalNr, sum);
            BigDecimal bigDecimal = new BigDecimal(sum - initialArray.get(i)).setScale(decimalNr,
                    BigDecimal.ROUND_DOWN);
            solvedMatrix.add(bigDecimal);
        }
        if (flag) {
            for (BigDecimal d : solvedMatrix) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
        printSolutionNorm(solvedMatrix, decimalNr, integerNr, flag);
    }

    public static void printSolutionNorm(List<BigDecimal> solvedMatrix, int decimalNr, int integerNr, boolean flag) {
        BigDecimal sum = new BigDecimal(0).setScale(decimalNr + integerNr, BigDecimal.ROUND_DOWN);
        for (BigDecimal d : solvedMatrix)
            sum = sum.add(d.abs());
        if(flag)
            System.out.println("Norma solutiei : " + trimDecimalNumber(decimalNr, sqrt(sum.doubleValue())));
        else
            System.out.println("Norma solutiei (inversa matricei): " + trimDecimalNumber(decimalNr, sqrt(sum.doubleValue())));
        //System.out.println("Norma solutiei sqrt: "+ sqrt(sum, decimalNr));
    }

    private static double trimDecimalNumber(int decimalNr, double value) {
        StringBuilder format = new StringBuilder();
        format.append("#.");
        for (int i = 0; i < decimalNr; i++)
            format.append("#");
        return Double.parseDouble(new DecimalFormat(format.toString()).format(value));
    }

//    private static BigDecimal sqrt(BigDecimal x, int scale)
//    {
//        // Check that x >= 0.
//        if (x.signum() < 0) {
//            throw new IllegalArgumentException("x < 0");
//        }
//
//        // n = x*(10^(2*scale))
//        BigInteger n = x.movePointRight(scale << 1).toBigInteger();
//
//        // The first approximation is the upper half of n.
//        int bits = (n.bitLength() + 1) >> 1;
//        BigInteger ix = n.shiftRight(bits);
//        BigInteger ixPrev;
//
//        // Loop until the approximations converge
//        // (two successive approximations are equal after rounding).
//        do {
//            ixPrev = ix;
//
//            // x = (x + n/x)/2
//            ix = ix.add(n.divide(ix)).shiftRight(1);
//
//            Thread.yield();
//        } while (ix.compareTo(ixPrev) != 0);
//
//        return new BigDecimal(ix, scale);
//    }

    public static void calculateSecondNorm(Matrix solutionLib, List<Double> solution, int decimalNr) {
        BigDecimal sum = new BigDecimal(0).setScale(decimalNr, RoundingMode.DOWN);
        for (int i = 0; i < solution.size(); i++)
            sum = sum.add(new BigDecimal(Math.abs(solution.get(i) - solutionLib.get(i, 0))).setScale(decimalNr,
                    RoundingMode.DOWN));
        System.out.println("Second Norm: " + trimDecimalNumber(decimalNr, Math.sqrt(sum.doubleValue())));
    }

    public static Matrix calculateSolLib(Matrix initialMatrix, List<Double> initialArray) {
        LUDecomposition luDecomposition = new LUDecomposition(initialMatrix);
        Matrix arrayMatrix = new Matrix(initialArray.size(), 1);
        for (int i = 0; i < initialArray.size(); i++)
            arrayMatrix.set(i, 0, initialArray.get(i));
        return luDecomposition.solve(arrayMatrix);
    }
}
