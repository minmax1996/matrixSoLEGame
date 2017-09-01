import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;



/**
 * Created by Максим on 22.02.2017.
 */


public class Matrix {
    public static final int RANDOM_BOUND = 10;

    public Line [] matrixArray;
    public int n;

    Matrix(int n) { //создаем еденичную матрицу со значениями 1..n и рандомным значением вектора [-5,5]
        this.n=n;
        matrixArray = new Line[n];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(n);
            matrixArray[i].lineVector[i] = 1;
        }
    }

    Matrix(int n,double[][] a){
        this.n=n;
        matrixArray=new Line[n];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(a[i] ,n);
        }
    }

    Matrix(Matrix Second){
        this.n=Second.n;
        this.matrixArray=new Line[n];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(Second.matrixArray[i]);
        }
    }


    public Matrix SwapLines(int first, int second){
        for (int i = 0; i < n; ++i) {
            Line tmp = new Line(matrixArray[first]);
            matrixArray[first]=matrixArray[second];
            matrixArray[second]=tmp;
        }
    return this;
    }

    public void PrintArray() {
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("\t %s",matrixArray[i].lineVector[j]);
            }
        }
    }

    public Matrix SumVectors(int first, int to){
        this.matrixArray[first].SumVectors(this.matrixArray[to]);
        return this;
    }

    public Matrix SumVectors(int first, int to, double Mul){
        this.matrixArray[first].SumVectors(this.matrixArray[to].AMulVector(Mul));
        return this;
    }

    public Matrix MulVector(int first,double MulValue){
        this.matrixArray[first].MulVector(MulValue);
        return this;
    }

    public Matrix DivVector(int first, double DivValue){
        this.matrixArray[first].DivVector(DivValue);
        return this;
    }

    /*
    * выделение минора матрицы
    * пример: М23 матрица без 2 строки и 3 столбца
    * */
    public Matrix M(int row,int column)
    {
        double[][] A = new double[this.n-1][this.matrixArray[0].n-1];

        int line=0;
        for (int k=0; k<this.n; ++k) {
            if (k!=row){
                A[line]=this.matrixArray[k].LineWithOut(column);
                line++;
            }
        }

        return new Matrix(this.n-1,A);
    }

    public double Determinant(){
        Matrix Array=new Matrix(this);
        return Determinant(Array);
    }

    private double Determinant(Matrix Array){
        if (Array.matrixArray[0].lineVector.length==2){
            return Array.matrixArray[0].lineVector[0] * Array.matrixArray[1].lineVector[1]
                    - Array.matrixArray[0].lineVector[1] * Array.matrixArray[1].lineVector[0];
        }
        else
        {
            double res=0;
            int minRowValue=5;
            int minRowIndex=0;
            for(int j=0; j<Array.matrixArray.length; ++j){
                if(Array.matrixArray[j].CountOfNotZeroElements() < minRowValue){
                    minRowValue=Array.matrixArray[j].CountOfNotZeroElements(); //TODO переделать перевызов
                    minRowIndex=j;
                }
            }
            //на этом моменте мы определили самую "пустую" строку, теперь по ней нам надо найти определитель

            for(int i=0; i<Array.matrixArray.length; ++i){
                if (Math.abs(Array.matrixArray[minRowIndex].lineVector[i])>=0.0001){
                    res += (((i+1 + minRowIndex+1)%2==0)? 1: -1) * Array.matrixArray[minRowIndex].lineVector[i] * Determinant(Array.M(minRowIndex,i));
                }
            }

            return res;
        }
   }
}
