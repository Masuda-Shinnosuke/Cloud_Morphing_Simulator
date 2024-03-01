package CMorph.Scenario;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import CMorph.Config.config;
import CMorph.DataCenter.dataCenter;
import CMorph.DataCenter.dataCenterInfo;
import CMorph.User.userJobs;


public class stayBuffer {
    private dataCenter [] dataCenters;
    private ArrayList <userJobs> userJobs=new ArrayList<>();
    private double [] previousDatacenterLoad;
    private List<dataCenterInfo> dataCenterData; 
    private ObjectMapper objectMapper;


    public stayBuffer(){
        dataCenters = new dataCenter[config.NUM_OF_DATACENTER];
        previousDatacenterLoad = new double[config.NUM_OF_DATACENTER];
        dataCenterData= new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    public void runSimulation(){

        // generate datacenters
        // dataCenter.generateDatacenter();

        for (int i=0;i<config.NUM_OF_DATACENTER;i++){
            dataCenters[i] = new dataCenter(config.DatacenterX[i], config.DatacenterY[i]);
        }

        // generate jobs until simstep=0.4375 and remove jobs if simstep>0.4375
        for (int t = 0;t<config.simultionSteps;t++){
            simulationOperate(t);
            for (int i = 0;i<userJobs.size();i++){
                changeDatacenter(i, t);
            }
            
            for (int k = 0;k<config.NUM_OF_DATACENTER;k++){
                dataCenterInfo datacenterinfo = new dataCenterInfo(k, dataCenters[k].getLoad(),dataCenters[k].x,dataCenters[k].y);
                dataCenterData.add(datacenterinfo);
                previousDatacenterLoad[k]=dataCenters[k].getLoad();
            }

           
        }

        try (FileWriter fileWriter = new FileWriter("src/main/java/CMorph/output/serverdata.json", true)) {
            objectMapper.writeValue(fileWriter, dataCenterData);
                } catch(IOException e){
                    e.printStackTrace();
                }
        

    }

    public void simulationOperate(int t){
        Random rand = new Random();
        int front = rand.nextInt(500);
        int back = rand.nextInt(500);
        int dataPlace = rand.nextInt(config.NUM_OF_DATACENTER);
        double simStep = t/config.simultionSteps;
        if (t>=0&&simStep<=0.4375){
            // userJobs job = new userJobs(150, 150, front, back, dataPlace);
            // userJobs.add(job);
        }else if(0.4375<simStep&&1<=simStep){
            dataCenters[userJobs.get(0).getCurrentDatacenter()].subJob();
        }
    }

    public void changeDatacenter(int i,int t){
       userJobs.get(i).elpasedTime++;
       double bestCost = Double.MAX_VALUE;
       int bestDC=-1;
       if(userJobs.get(i)!=null){
        double rate =userJobs.get(i).frontCommunication/userJobs.get(i).backendCommunication;
        for (int k=0;k<config.NUM_OF_DATACENTER;k++){
            double cost=CMorph.AllocationServer.allocationServer.getCost(userJobs.get(i),dataCenters[k].x,dataCenters[k].y,previousDatacenterLoad[k] );
            if(cost<bestCost){
                bestCost=cost;
                bestDC=k;
            }
            // if (rate>3){
            //     bestDC=userJobs.get(i).dataObjectPlace;
            //     break;
            // }else{
            //     double cost = CMorph.AllocationServer.allocationServer.getCost(userJobs.get(i),dataCenters[k].x,dataCenters[k].y,previousDatacenterLoad[k] );
            //     if (cost<bestCost){
            //         bestCost=cost;
            //         bestDC=k;
            //     }
            // }
        }
        if (userJobs.get(i).currentDatacenter==-1) {
            userJobs.get(i).setCurrentDatacenter(bestDC);
            userJobs.get(i).elpasedTime=0;
            dataCenters[bestDC].addJob();
        }else{
            if(userJobs.get(i).getCurrentDatacenter()!=bestDC){
                dataCenters[userJobs.get(i).getCurrentDatacenter()].subJob();
                userJobs.get(i).setCurrentDatacenter(bestDC);
                userJobs.get(i).elpasedTime=0;
                dataCenters[bestDC].addJob();
            }
        }

       }
    }
}
