package CMorph.DataCenter;
import CMorph.Config.config;

public class dataCenter {
    public double x;
    public double y;
    public double rho;
    public int totalJob=0;
    public static dataCenter [] dataCenters;
    

    public dataCenter(double x,double y){
        this.x=x;
        this.y=y;
        this.rho=0;
    }

    public void  addJob(){
        this.totalJob++;
    }
    
    public void subJob(){
        this.totalJob--;
    }

    public double getLoad(){
        rho=this.totalJob*0.001;
        return rho;
    }

    public static void generateDatacenter(){
        for (int i = 0;i<config.NUM_OF_DATACENTER;i++){
            dataCenters[i] = new dataCenter(config.DatacenterX[i], config.DatacenterY[i]);
        }
    }
}
