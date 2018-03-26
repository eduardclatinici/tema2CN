import java.util.*;

import Jama.*;

public class InverseSubstitution {

    private Matrix matrix;
    private List<Double> array;
    private int n;

    public InverseSubstitution(Matrix matrix, List<Double> array) throws Exception {
        this.matrix = matrix;
        this.array = array;
        this.n = this.array.size();
        checkDeterminant();
    }

    public List<Double> getSolution(){
        List<Double> solutions = new ArrayList<>();
        for(int i = n-1; i>=0; i--){
            solutions.add(getXForI(i,solutions));
        }
        Collections.reverse(solutions);
        return solutions;
    }

    private double getXForI(int i, List<Double> previousSolutions){
        double sum = 0;
        for(int j=i+1;j<n;j++)
            sum += matrix.get(i,j)*previousSolutions.get(n-j-1);
        return (array.get(i)-sum)/matrix.get(i,i);
    }

    private void checkDeterminant() throws Exception {
        double determinant=1;
        for(int i=0;i<n;i++)
            determinant *= matrix.get(i,i);
        if(determinant==0)
            throw new Exception("determinantul matricei superior triunghiulara este null");

    }
}
