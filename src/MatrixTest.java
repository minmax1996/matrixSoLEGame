import sun.nio.cs.ext.MS874;

import static org.junit.Assert.*;

/**
 * Created by Максим on 01.09.2017.
 */
public class MatrixTest {

    private  Matrix TestMatrix5;
    private static double [][] TestArray5={{1,2,3,4,5},{5,4,3,2,1},{3,8,4,2,7},{5,3,2,1,5},{3,1,4,1,4}};

    @org.junit.Before
    public void setUp() throws Exception {
        Matrix TestMatrix5=new Matrix(5,TestArray5);
    }

    @org.junit.Test
    public void swapLines() throws Exception {
        Matrix TestMatrix5=new Matrix(5,TestArray5);
        TestMatrix5.SwapLines(2,3);

        assertArrayEquals(TestArray5[2],TestMatrix5.matrixArray[3].lineVector,0.0001);
        assertArrayEquals(TestArray5[3],TestMatrix5.matrixArray[2].lineVector,0.0001);
    }
    @org.junit.Test
    public void sumVectors() throws Exception {
        Matrix TestMatrix5=new Matrix(5,TestArray5);

        TestMatrix5.SumVectors(0,1);

        assertArrayEquals(new double[]{6,6,6,6,6},TestMatrix5.matrixArray[0].lineVector,0.0001);

        TestMatrix5.SumVectors(2,3,-1); //с вычитаением
        assertArrayEquals(new double[]{-2,5,2,1,2},TestMatrix5.matrixArray[2].lineVector,0.0001);

    }


    @org.junit.Test
    public void mulVector() throws Exception {
        Matrix TestMatrix5=new Matrix(5,TestArray5);

        TestMatrix5.MulVector(0,-1);
        assertArrayEquals(new double[]{-1,-2,-3,-4,-5},TestMatrix5.matrixArray[0].lineVector,0.0001);
        TestMatrix5.MulVector(1,2);
        assertArrayEquals(new double[]{10,8,6,4,2},TestMatrix5.matrixArray[1].lineVector,0.0001);
        TestMatrix5.MulVector(3,0);
        assertArrayEquals(new double[]{0,0,0,0,0},TestMatrix5.matrixArray[3].lineVector,0.0001);
    }

    @org.junit.Test
    public void divVector() throws Exception {
        Matrix TestMatrix5=new Matrix(5,TestArray5);

        TestMatrix5.DivVector(0,-1);
        assertArrayEquals(new double[]{-1,-2,-3,-4,-5},TestMatrix5.matrixArray[0].lineVector,0.0001);
        TestMatrix5.DivVector(1,2);
        assertArrayEquals(new double[]{2.5,2,1.5,1,0.5},TestMatrix5.matrixArray[1].lineVector,0.0001);
        TestMatrix5.DivVector(2,0);
        assertArrayEquals(new double[]{3,8,4,2,7},TestMatrix5.matrixArray[2].lineVector,0.0001);
    }

    @org.junit.Test
    public void m() throws Exception {
        double [][] ExpectedM11={{1,3,4,5},
                                {3,4,2,7},
                                {5,2,1,5},
                                {3,4,1,4}};
        Matrix TestMatrix5=new Matrix(5,TestArray5);

        Matrix m11=TestMatrix5.M(1,1);

        assertArrayEquals(ExpectedM11[0],m11.matrixArray[0].lineVector,0.0001);
        assertArrayEquals(ExpectedM11[1],m11.matrixArray[1].lineVector,0.0001);
        assertArrayEquals(ExpectedM11[2],m11.matrixArray[2].lineVector,0.0001);
        assertArrayEquals(ExpectedM11[3],m11.matrixArray[3].lineVector,0.0001);

    }

    @org.junit.Test
    public void determinant() throws Exception {
        double [][] Det2={{1,3},
                          {4,2}};
        Matrix Det2M=new Matrix(2,Det2);
        assertEquals(-10.0,Det2M.Determinant(),0.0001);

        double [][] Det3={{1,3,-3},
                          {4,2,2},
                          {1,4,-3}};
        Matrix Det3M=new Matrix(3,Det3);
        assertEquals(-14.0,Det3M.Determinant(),0.0001);

        double [][] Det3withZero={{1,3,-3},
                                  {0,2,2},
                                  {1,4,-3}};
        Matrix Det3MwithZero=new Matrix(3,Det3withZero);
        assertEquals(-2.0,Det3MwithZero.Determinant(),0.0001); //тут рушит

        //добавить еще
    }

}