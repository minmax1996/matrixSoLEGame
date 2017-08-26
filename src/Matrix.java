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



    public void Determinant(double[][] array){
//        if (array[0].length==2){
        //потом через рекурсию и миноры
//            return array[0][0]*array[1][1]-array[0][1]*array[1][0];
//        }
//        else
//        {
//            double res=0;
//            for(int i=0; i<array[0].length; ++i){
//                if ()
//                res+=
//            }
//        }
   }
}
