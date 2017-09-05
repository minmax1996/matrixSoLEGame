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

    Matrix(int n) { //создаем еденичную матрицу размера n x n
        this.n=n;
        matrixArray = new Line[n];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(n);
            matrixArray[i].lineVector[i] = 1;
        }
    }

    Matrix(int n,double[][] a){
        this.n=n;
        matrixArray=new Line[a[0].length];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(a[i] ,a[i].length);
        }
    }

    Matrix(Matrix Second){
        this.n=Second.n;
        this.matrixArray=new Line[n];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(Second.matrixArray[i]);
        }
    }

    public double get(int row, int column){
        return this.matrixArray[row].lineVector[column];
    }
    public double[] get(int row){
        return this.matrixArray[row].lineVector;
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
            System.out.println();
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

    public Matrix MulMatrix(double MulValue){
        for (int i=0; i<this.n; ++i){
            this.MulVector(i,MulValue);
        }
        return this;
    }

    public Matrix DivVector(int first, double DivValue){
        this.matrixArray[first].DivVector(DivValue);
        return this;
    }

    public Matrix MulMatrixVector(double [][] Vector){
        //потом
        return this;
    }
    /*
    * выделение минора матрицы
    * пример: М23 матрица без 2 строки и 3 столбца
    * пораждает новую матрицу
    * */
    public Matrix M(int row,int column) {
        double[][] A = new double[this.n-1][this.matrixArray[0].m-1];

        int line=0;
        for (int k=0; k<this.n; ++k) {
            if (k!=row){
                A[line]=this.matrixArray[k].LineWithOut(column);
                line++;
            }
        }

        return new Matrix(this.n-1,A);
    }

    /*
    * Нахождение определителя по нашей матрице, пораждает новую матрицу и ее разбивает на миноры
    * */
    public double Determinant(){
        Matrix Array=new Matrix(this);
        return Determinant(Array);
    }

    /*
    * Нахождение определителя определенной матрицы
    * */
    private double Determinant(Matrix Array){
        if (Array.matrixArray[0].lineVector.length==2){
            return Array.get(0,0) * Array.get(1,1)
                    - Array.get(0,1) * Array.get(1,0);
        }
        else
        {
            double res=0;
            int minRowValue=5;
            int minRowIndex=0;
            for(int i=0; i<Array.matrixArray.length; ++i){
                if(Array.matrixArray[i].CountOfNotZeroElements() < minRowValue){
                    minRowValue=Array.matrixArray[i].CountOfNotZeroElements(); //TODO переделать перевызов
                    minRowIndex=i;
                }
            }
            //на этом моменте мы определили самую "пустую" строку, теперь по ней нам надо найти определитель

            for(int j=0; j<Array.matrixArray.length; ++j){
                if (Math.abs(Array.matrixArray[minRowIndex].lineVector[j])>=0.0001){
                    res += this.A(minRowIndex,j)*this.get(minRowIndex,j);
                }
            }

            return res;
        }
   }

    /*
     * Нахождение транспонированной матрицы, пораждает новую
     * */
    public Matrix T(){
        double [][] ResultArray=new double[n][n];
        for (int i=0; i<n; ++i){
            for(int j=0; j<n; ++j){
                ResultArray[j][i]=this.get(i,j);
            }
        }
        return new Matrix(n,ResultArray);
    } //

    /*
    * нахождение алгебраического дополнения
    * */
    public double A(int i, int j){
        return (((i+1 + j+1)%2==0)? 1: -1) * Determinant(this.M(i,j));
    }
}
