package CMorph.AllocationServer;

import CMorph.DataCenter.dataCenter;
import CMorph.User.userJobs;

public class allocationServer {
    public static double getCost(userJobs job,dataCenter dc){
        double rate = job.frontCommunication/job.backendCommunication;
        double rho = dc.rho;

        if(rho>1.0){
            rho = 0.9;
        }

        if (rate>=2){
            double cost = Math.sqrt(Math.pow((job.x-dc.x),2)+Math.pow((job.y-dc.y), 2));
            return cost; 
        }else{
            double cost = Math.pow((2*rho-1), 2)/(1-rho);
            return cost;
        }
    }
}
