import java.util.*;

import Jama.*;

public class InverseSubstitution {

    private Matrix matrix;
    private List<Double> array;
    private int n;

    public InverseSubstitution(Matrix matrix, List<Double> array) {
        this.matrix = matrix;
        this.array = array;
        this.n = this.array.size();
    }
}
