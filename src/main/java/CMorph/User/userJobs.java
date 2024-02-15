package CMorph.User;

public class userJobs {
    
    public double x;
    public double y;
   
    public double initialY;
    public double initialSpeed;
    public double frontCommunication;
    public double backendCommunication;
    public int dataObjectPlace;
    public int currentDatacenter=-1;
    public int elpasedTime=0;

    public userJobs(double x,double y,double frontCommunication,double backendCommunication,int dataObjectPlace,double initialSpeed){
        this.x=x;
        this.initialY=y;
        this.y=y;
        this.initialSpeed=initialSpeed;
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

    public double getCurrentX(){
        return this.x;
    }

    public double getCurrentY(){
        return this.y;
    }

    public void changeJobCoordinate(int t){
        this.y=initialY+initialSpeed*t;
    }
}
