package CMorph.AllocationServer;

import CMorph.Config.config;
import CMorph.User.userJobs;

public class allocationServer {
    public static double getCost(userJobs job,double dataCenterX,double dataCenterY,double dcRho){
        double rate = job.frontCommunication/job.backendCommunication;
        double rho = dcRho;

        if(rho>1.0){
            rho = 0.9;
        }

        if (rate>=2){
            // double cost = Math.sqrt(Math.pow((job.x-dataCenterX),2)+Math.pow((job.y-dataCenterY), 2));
            double cost = 0.0;
            return cost; 
        }else{
            double dist = Math.sqrt(Math.pow((dataCenterX-job.x),2)+Math.pow((dataCenterY-job.y),2));
            double cost = Math.pow((2*rho-1), 2)/(1-rho) + dist/Math.sqrt(config.mapHeight*config.mapWidth+config.mapHeight*config.mapWidth);;
            return cost;
        }
    }
}
