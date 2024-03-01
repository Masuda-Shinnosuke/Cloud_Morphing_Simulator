package CMorph.DataCenter;

public class dataCenterInfo {
    public int dataCenterId;
    public double currentRho;
    public double dataCenterX;
    public double dataCenterY;

    public dataCenterInfo(int dataCenterId,double currentRho,double x,double y ){
        this.dataCenterId=dataCenterId;
        this.currentRho=currentRho;
        this.dataCenterX=x;
        this.dataCenterY=y;
    }
}
