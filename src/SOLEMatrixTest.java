import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Максим on 01.09.2017.
 */
public class SOLEMatrixTest {


    private static final double [][] TestArray5={{1,2,3,4,5},{5,4,3,2,1},{3,8,4,2,7},{5,3,2,1,5},{3,1,4,1,4}};
    private static final double[] TestValue5={3,5,1,-3,4};

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void isSolved() throws Exception {
        double [][] SolvedArray4={
                {1,0,0,0},
                {0,1,0,0},
                {0,0,1,0},
                {0,0,0,1}};
        double [] SolvedValue4={1,2,3,5};

        SOLEMatrix TestSolvedMatrix5=new SOLEMatrix(4,SolvedArray4,SolvedValue4);
        assertEquals(true,TestSolvedMatrix5.isSolved());

        SOLEMatrix TestMatrix5=new SOLEMatrix(5,TestArray5,TestValue5);
        assertEquals(false,TestMatrix5.isSolved());
    }

    @Test
    public void swapLines() throws Exception {
        SOLEMatrix TestMatrix5=new SOLEMatrix(5,TestArray5,TestValue5);
        TestMatrix5.SwapLines(2,3);

        assertArrayEquals(TestArray5[2],TestMatrix5.matrixArray[3].lineVector,0.0001);
        assertEquals(TestValue5[2],TestMatrix5.ValueVector[3],0.0001);

        assertArrayEquals(TestArray5[3],TestMatrix5.matrixArray[2].lineVector,0.0001);
        assertEquals(TestValue5[3],TestMatrix5.ValueVector[2],0.0001);
    }

    @Test
    public void sumVectors() throws Exception {
        SOLEMatrix TestMatrix5=new SOLEMatrix(5,TestArray5,TestValue5);

        TestMatrix5.SumVectors(0,1);

        assertArrayEquals(new double[]{6,6,6,6,6},TestMatrix5.matrixArray[0].lineVector,0.0001);
        assertEquals(8,TestMatrix5.ValueVector[0],0.0001);

        TestMatrix5.SumVectors(2,3,-1); //с вычитаением
        assertArrayEquals(new double[]{-2,5,2,1,2},TestMatrix5.matrixArray[2].lineVector,0.0001);
        assertEquals(4.0,TestMatrix5.ValueVector[2],0.0001);

    }


    @Test
    public void mulVector() throws Exception {
        SOLEMatrix TestMatrix5=new SOLEMatrix(5,TestArray5,TestValue5);

        TestMatrix5.MulVector(0,-1);
        assertArrayEquals(new double[]{-1,-2,-3,-4,-5},TestMatrix5.matrixArray[0].lineVector,0.0001);
        assertEquals(-3.0,TestMatrix5.ValueVector[0],0.0001);

        TestMatrix5.MulVector(1,2);
        assertArrayEquals(new double[]{10,8,6,4,2},TestMatrix5.matrixArray[1].lineVector,0.0001);
        assertEquals(10.0,TestMatrix5.ValueVector[1],0.0001);


        TestMatrix5.MulVector(3,0);
        assertArrayEquals(new double[]{0,0,0,0,0},TestMatrix5.matrixArray[3].lineVector,0.0001);
        assertEquals(0.0,TestMatrix5.ValueVector[3],0.0001);
    }


    @Test
    public void divVector() throws Exception {
        SOLEMatrix TestMatrix5=new SOLEMatrix(5,TestArray5,TestValue5);


        TestMatrix5.DivVector(0,-1);
        assertArrayEquals(new double[]{-1,-2,-3,-4,-5},TestMatrix5.matrixArray[0].lineVector,0.0001);
        assertEquals(-3.0,TestMatrix5.ValueVector[0],0.0001);

        TestMatrix5.DivVector(1,2);
        assertArrayEquals(new double[]{2.5,2,1.5,1,0.5},TestMatrix5.matrixArray[1].lineVector,0.0001);
        assertEquals(2.5,TestMatrix5.ValueVector[1],0.0001);

        TestMatrix5.DivVector(2,0);
        assertArrayEquals(new double[]{3,8,4,2,7},TestMatrix5.matrixArray[2].lineVector,0.0001);
        assertEquals(1.0,TestMatrix5.ValueVector[2],0.0001);

    }


    @Test
    public void MatrixMethod() throws Exception {
        double [][] Example = {{3,2,-1},{2,-1,5},{1,7,-1}};
        double [] Val = {4,23,5};
        double [] Result = {2,1,4};
        SOLEMatrix SLAU=new SOLEMatrix(3,Example,Val);
        double [] X=SLAU.MatrixMethod();

        assertArrayEquals(Result,X,0.0001);
    }
}