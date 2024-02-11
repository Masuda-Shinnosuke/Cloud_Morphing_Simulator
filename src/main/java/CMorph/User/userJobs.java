package CMorph.User;

public class userJobs {
    public double x;
    public double y;
    public double frontCommunication;
    public double backendCommunication;
    public int dataObjectPlace;
    public int currentDatacenter=-1;
    public int elpasedTime=0;

    public userJobs(double x,double y,double frontCommunication,double backendCommunication,int dataObjectPlace){
        this.x=x;
        this.y=y;
        this.frontCommunication=frontCommunication;
        this.backendCommunication=backendCommunication;
        this.dataObjectPlace=dataObjectPlace;
    }

    public void setCurrentDatacenter(int dataCenter){
        this.currentDatacenter=dataCenter;
    }

    public int getCurrentDatacenter(){
        return this.currentDatacenter;
    }
}
