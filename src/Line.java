/**
 * Created by Максим on 22.02.2017.
 */

public class Line {
    public double [] lineVector;
    public double value;
    int n;

    Line(int _n){
        n=_n;
        lineVector = new double[n];
        for (int i=0; i<n;++i) {
            lineVector[i] = 0;
        }
        value=0;
    }

    Line(double [] a, double b, int n)
    {
        n=n;
        lineVector = new double[n];
        //lineVector=System.arraycopy();
        System.arraycopy(a, 0, lineVector, 0, a.length);
        this.value=b;
    }

    Line( Line second){
        n=second.n;
        lineVector=new double[n];
        System.arraycopy(second.lineVector, 0, lineVector, 0, n);
        value=second.value;
    }

    public Line MulVector(double number){
        for (int i = 0; i < this.lineVector.length; ++i) {
            this.lineVector[i] *= number;
        }
        this.value*=number;
        return this;
    }

    public Line DivVector(double number){
        for (int i = 0; i < this.lineVector.length; ++i) {
            this.lineVector[i] /= number;
        }
        this.value/=number;
        return this;
    }

    public  Line AMulVector(double number){
        Line res=new Line(this.n);
        for (int i = 0; i < res.lineVector.length; ++i) {
            res.lineVector[i] = this.lineVector[i] * number;
        }
        res.value=this.value*number;
        return res;
    }


    public Line SumVectors(Line second) {
        for (int i = 0; i < this.lineVector.length; ++i) {
            this.lineVector[i] += second.lineVector[i];
        }
        this.value+=second.value;
        return this;
    }
}

