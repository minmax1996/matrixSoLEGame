/**
 * Created by Максим on 22.02.2017.
 */

public class Line {
    public double [] lineVector;
//    public double value;
    int n;

    Line(int _n){
        n=_n;
        lineVector = new double[n];
        for (int i=0; i<n;++i) {
            lineVector[i] = 0;
        }
    }

    Line(double [] a, int n)
    {
        this.n=n;
        lineVector = new double[n];
        System.arraycopy(a, 0, lineVector, 0, a.length);
    }

    Line( Line second){
        this.n=second.n;
        this.lineVector=new double[n];
        System.arraycopy(second.lineVector, 0, this.lineVector, 0, n);
    }

    public Line MulVector(double number){
        for (int i = 0; i < this.lineVector.length; ++i) {
            if(this.lineVector[i]!=0.0)
                this.lineVector[i]*= number ;
        }
        return this;
    }

    public Line DivVector(double number){
        for (int i = 0; i < this.lineVector.length; ++i) {
            this.lineVector[i] /= number;
        }
        return this;
    }

    public Line AMulVector(double number){
        Line res=new Line(this.n);
        for (int i = 0; i < res.lineVector.length; ++i) {
            res.lineVector[i] = this.lineVector[i] * number;
        }
        return res;
    }

    public Line SumVectors(Line second) {
        for (int i = 0; i < this.lineVector.length; ++i) {
            this.lineVector[i] += second.lineVector[i];
        }
        return this;
    }

    public int CountOfNotZeroElements(){
        int count=0;
        for (int i=0; i<this.n; ++i){
            if (this.lineVector[i]!=0.0) {
                ++count;
            }
        }
        return count;

    }

    public double[] LineWithOut(int i){
        double res[]=new double[this.n-1];
        int j=0;
        for (int k=0;k<this.n;++k){
            if(k!=i){
                res[j]=this.lineVector[k];
                j++;
            }
        }
        return res;
    }
}

