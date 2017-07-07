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
    public Line [] matrixArray;
    public int n;

    Matrix(int n) { //создаем еденичную матрицу со значениями 1..n
        this.n=n;
        matrixArray = new Line[n];
        for (int i = 0; i < n; ++i) {
            matrixArray[i] = new Line(n);
            matrixArray[i].lineVector[i] = 1;
            matrixArray[i].value=i+1;
        }

    }

    public  Matrix SwapLines(int first, int second){
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
           System.out.println("\t |"+matrixArray[i].value);
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
                System.out.println(" = " + matrixArray[i].value);
            }
        }
        catch (FileNotFoundException e) {
            System.err.print(Arrays.toString(e.getStackTrace()));
        }
    }

    /** @noinspection Since15*/
    public void ShuffleArray(int count){
        try {
            System.setErr(new PrintStream(new File("shuffle.txt")));
            Random rnd = new Random();

            Matrix ones = new Matrix(this.n);

            for (int i = 0; i < this.n-1; ++i) {
                //this.matrixArray[rnd.nextInt(this.n)].SumVectors(this.matrixArray[rnd.nextInt(this.n)].AMulVector(rnd.nextInt(5)-10));
                for (int j = i+1; j < this.n; ++j) {
                    int bb = rnd.nextInt(count) - 5;
                    this.matrixArray[i].SumVectors(this.matrixArray[j].AMulVector(bb)); //создается верхнетреугольная матрица, имеющаяя решение
                    //System.err.println("складываем" + i + "строку с " + j + "строкой умноженной на" + bb);
                }
            }

            for (int i = 0; i < this.n; ++i) {
                this.matrixArray[i].SumVectors(this.matrixArray[rnd.nextInt(this.n)].AMulVector(rnd.nextInt(count)-5));
            }

            } catch (FileNotFoundException e) {
            System.err.print(Arrays.toString(e.getStackTrace()));
        }
    }

}
