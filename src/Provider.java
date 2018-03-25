import java.text.*;
import java.util.*;

import Jama.*;

public class Provider {

    private final int integerNumber;

    private final int decimalNumber;

    private final int n;

    Provider(int n, int integerNumber, int decimalNumber) {
        this.integerNumber = integerNumber;
        this.decimalNumber = decimalNumber;
        this.n = n;
    }

    public Matrix getRandomMatrix() {
        Matrix matrix = Matrix.random(n, n);
        for (int i = 0; i < matrix.getRowDimension(); i++)
            for (int j = 0; j < matrix.getColumnDimension(); j++)
                matrix.set(i, j, trimDecimalNumber(decimalNumber, getIntegerNumber(integerNumber, matrix.get(i, j))));
        return matrix;
    }

    public List<Double> getRandomArray() {
        final Random random = new Random();
        System.out.println();
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(trimDecimalNumber(decimalNumber, getIntegerNumber(integerNumber, random.nextDouble())));
        }
        return list;
    }

    private double getIntegerNumber(int integerNr, double value) {
        int factor = 1;
        for(int i=0; i<integerNr;i++)
            factor*=10;
        return value * factor;
    }

    private double trimDecimalNumber(int decimalNr, double value) {
        StringBuilder format = new StringBuilder();
        format.append("#.");
        for (int i = 0; i < decimalNr; i++)
            format.append("#");
        return Double.parseDouble(new DecimalFormat(format.toString()).format(value));
    }
}
