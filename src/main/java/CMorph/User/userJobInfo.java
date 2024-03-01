package CMorph.User;

public class userJobInfo {
    public int id;
    public double currentX;
    public double currentY;
    public int currentDatacenter;

    public userJobInfo(int id,double x,double y,int dataCenter){
        this.id=id;
        this.currentX=x;
        this.currentY=y;
        this.currentDatacenter=dataCenter;
    }
}
