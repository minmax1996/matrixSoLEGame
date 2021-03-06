import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import javafx.scene.Parent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Максим on 26.08.2017.
 */
public class SOLEMatrix extends Matrix {
    public double [] ValueVector;


    public SOLEMatrix(int n){
        super(n);
        this.ValueVector=new double[n];
        Random rnd = new Random();
        for (int i = 0; i < n; ++i) {
            this.ValueVector[i]= rnd.nextInt(RANDOM_BOUND)-RANDOM_BOUND/2;
        }
    }

    public SOLEMatrix(int n, double [][]a,double []b){
        super(n,a);

        this.ValueVector=new double[n];
        System.arraycopy(b, 0, this.ValueVector, 0, a.length);
    }

    public boolean isSolved(){
        for(int i=0; i<this.n; ++i){
            if(matrixArray[i].CountOfNotZeroElements()!=1 || this.ValueVector[i]==0){
                return false;
            }
        }
        return true;
    }

    @Override
    public SOLEMatrix SwapLines(int first, int second) {
        super.SwapLines(first,second);

        double tmp=ValueVector[first];
        ValueVector[first]=ValueVector[second];
        ValueVector[second]=tmp;
        return this;
    }

    @Override
    public void PrintArray() {
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("\t %s",matrixArray[i].lineVector[j]);
            }
            System.out.println("\t |"+ValueVector[i]);
        }
    }

    public void PrintEq() {
        try {
            System.setErr(new PrintStream(new File("log.txt")));

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    String sign = "";
                    if (matrixArray[i].lineVector[j] != 0) {
                        if (j != 0) {
                            if (matrixArray[i].lineVector[j] < 0) {
                                sign = " - ";
                            } else {
                                sign = " + ";
                            }
                        } else {
                            if (matrixArray[i].lineVector[j] < 0) {
                                sign = "- ";
                            } else
                                sign = "  ";
                        }
                    } else {
                        sign = "0";
                    }

                    if (!sign.equals("0")) {
                        System.out.printf("%s%s*x%d ",sign, Math.abs(matrixArray[i].lineVector[j]),(j + 1));
                    }
                }
                System.out.println(" = " + ValueVector[i]);
            }
        }
        catch (FileNotFoundException e) {
            System.err.print(Arrays.toString(e.getStackTrace()));
        }
    }


    @Override
    public SOLEMatrix SumVectors(int first, int to) {
        super.SumVectors(first, to);
        this.ValueVector[first]+=this.ValueVector[to];
        return this;
    }

    @Override
    public SOLEMatrix SumVectors(int first, int to, double Mul) {
        super.SumVectors(first, to, Mul);
        ValueVector[first]+=ValueVector[to]*Mul;
        return this;
    }

    @Override
    public SOLEMatrix MulVector(int first,double MulValue){
        super.MulVector(first, MulValue);
        ValueVector[first]*=MulValue;
        return this;
    }

    @Override
    public SOLEMatrix DivVector(int first, double DivValue){
        if(DivValue!=0) {
            super.DivVector(first,DivValue);
            ValueVector[first] /= DivValue;
        }
        return this;
    }

    public double [] getSolvedSolution(){
        if(this.isSolved()){
            double [] res = new double[this.n];
            System.arraycopy(this.ValueVector,0,res,0,this.n);
            return res;
        }
        else return null;
    }

    public boolean checkSolution(){
        return (this.getSolvedSolution() != null) && checkSolution(this.getSolvedSolution());
    }

    public boolean checkSolution(double [] solution){
        try {
            for(int i=0;i<this.n;++i) {
                if(Math.abs(checkSolution(i,solution)-this.ValueVector[i])>this.eps){
                    return false;
                }
            }
            return true;
        }catch (IndexOutOfBoundsException e){
            System.err.print(e.getMessage()+"\t"+e.getStackTrace().toString());
            return false;
        }
    }

    private double checkSolution(int row,double [] solution){
        if(solution.length!=this.get(row).length){
            throw new IndexOutOfBoundsException("разные размеры решения и уравнения");
        }
        double res=0;
        for(int j=0;j<this.get(row).length;++j){
            res+=this.get(row,j)*solution[j];
        }
        return res;
    }

    /** @noinspection Since15*/
    public void ShuffleArray(){
        try {
            System.setErr(new PrintStream(new File("shuffle.txt")));
            Random rnd = new Random();

            //создается верхнетреугольная матрица, имеющаяя решение
            for (int i = 0; i < this.n-1; ++i) {
                for (int j = i+1; j < this.n; ++j) {
                    int bb = rnd.nextInt(RANDOM_BOUND) - RANDOM_BOUND/2;
                    if (bb==0) bb++;
                    //i'тая строка складывается с j'той умноженной на рандомное число
                    this.SumVectors(i,j,bb);
                    System.err.println("C"+(i+1)+" +"+bb+"C"+(j+1));
                }
            }

            //еще один прогон, i'тая строка складываается с рандомной строкой, умноженной на рандомое число
            for (int i = 0; i < this.n; ++i) {
                this.SumVectors(i,rnd.nextInt(this.n),rnd.nextInt(RANDOM_BOUND)-RANDOM_BOUND/2);
            }

        } catch (FileNotFoundException e) {
            System.err.print(Arrays.toString(e.getStackTrace()));
        }
    }

    //Матричный метод решения
    public double [] MatrixMethod(){
        double[] X=new double[this.n];

        //убедимся, что определитель не равен 0;
        double Det=this.Determinant();
        System.out.println("det ="+Det);
        if (Det==0){
            return X;
        }

        //создадим союзную матрицу, собранную из алгебраических дополнений
        double [][] UnionMatrix=new double[this.n][this.n];
        for (int i=0; i<this.n; ++i){
            for (int j=0; j<this.n; ++j){
                UnionMatrix[i][j]=this.A(i,j);
            }
        }
        Matrix Union =new Matrix(this.n,UnionMatrix);
        System.out.println("________");
        Union.PrintArray();

        //умножив транспонированную союзную матрицу на 1/Det получаем обратную матрицу
        Matrix Am1 = Union.T().MulMatrix(1/Det);

        //теперь перемножив обратную матрицу на вектор значений получаем ответ
        for (int i=0; i<this.n; ++i) {
            for (int j=0; j<this.n; ++j){
                X[i]+=Am1.get(i,j)*this.ValueVector[j];
            }
            //X[i]=Math.round(X[i]); //округленное
            System.out.println("X"+i+"="+X[i]);
        }

        return X;
    }
}

