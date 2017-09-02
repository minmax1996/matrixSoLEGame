/**
 * Created by Максим on 22.02.2017.
 */

public class Line {
    public double [] lineVector;
    int m;

    Line(int _m){
        m=_m;
        lineVector = new double[m];
        for (int i=0; i<m;++i) {
            lineVector[i] = 0;
        }
    }

    Line(double [] a, int m)
    {
        this.m=m;
        lineVector = new double[m];
        System.arraycopy(a, 0, lineVector, 0, a.length);
    }

    Line( Line second){
        this.m=second.m;
        this.lineVector=new double[m];
        System.arraycopy(second.lineVector, 0, this.lineVector, 0, m);
    }

    public Line MulVector(double number){
        for (int i = 0; i < this.lineVector.length; ++i) {
            if(this.lineVector[i]!=0.0)
                this.lineVector[i]*= number ;
        }
        return this;
    }

    public Line DivVector(double number){
        if (number!=0){
            for (int i = 0; i < this.lineVector.length; ++i) {
                this.lineVector[i] /= number;
            }
        }
        return this;

    }

    public Line AMulVector(double number){
        Line res=new Line(this.m);
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
        for (int i=0; i<this.m; ++i){
            if (this.lineVector[i]!=0.0) {
                ++count;
            }
        }
        return count;

    }

    public double[] LineWithOut(int i){
        double res[]=new double[this.m-1];
        int j=0;
        for (int k=0;k<this.m;++k){
            if(k!=i){
                res[j]=this.lineVector[k];
                j++;
            }
        }
        return res;
    }
}

