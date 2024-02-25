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
import CMorph.User.userJobs;;

public class droneScenario {
    private dataCenter [] dataCenters;
    private ArrayList <userJobs> userJobs=new ArrayList<>();
    private double [] previousDatacenterLoad;
    private List<dataCenterInfo> dataCenterData; 
    private ObjectMapper objectMapper;

    public droneScenario(){
        dataCenters = new dataCenter[config.NUM_OF_DATACENTER];
        previousDatacenterLoad=new double[config.NUM_OF_DATACENTER];
        dataCenterData= new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    public void runSimulation(){
        
        // generate datacenters
        for (int i=0;i<config.NUM_OF_DATACENTER;i++){
            // dataCenters[i] = new dataCenter(config.DatacenterX[i], config.DatacenterY[i]);
            Random rand = new Random();
            double dataCenterX=rand.nextInt(4000);
            double dataCenterY=rand.nextInt(4000);
            dataCenters[i] = new dataCenter(dataCenterX, dataCenterY);
        }

        // generate jobs until simPercent=0.4375
        for (int t = 0;t<config.simultionSteps;t++){
            double simPercent = t/config.simultionSteps;

            if (t>7000){
                System.out.println(t);
            }

            if (simPercent<=0.4375){
                Random rand = new Random();
                double front = rand.nextDouble(1.0);
                double back = rand.nextDouble(1.0);
                double jobX=rand.nextInt(100);
                double jobY=rand.nextInt(100);
                int dataPlace = rand.nextInt(config.NUM_OF_DATACENTER);
                // generate jobs 
                userJobs jobs = new userJobs(jobX, jobY, front, back, dataPlace, 10);
                userJobs.add(jobs);
            }else{
                dataCenters[userJobs.get(0).getCurrentDatacenter()].subJob();
            }
            
            for (int i = 0;i<userJobs.size();i++){
                if (userJobs.get(i).getCurrentY()>=config.simultionSteps){
                    userJobs.remove(i);
                }
            }

            // jobs change a dataCenter
            for (int  j = 0;j<userJobs.size();j++){
            
                userJobs.get(j).changeJobCoordinate(t);

                if(userJobs.get(j)!=null){
                    double bestCost=Double.MAX_VALUE;
                    Random rand =new Random();
                    int num = rand.nextInt(100);

                    int bestDC=-1;

                    // An allocationServer caluculate cost for all dataCenters
                    for (int k = 0;k<config.NUM_OF_DATACENTER;k++){
                        double cost = CMorph.AllocationServer.allocationServer.getCost(userJobs.get(j),dataCenters[k].x,dataCenters[k].y,previousDatacenterLoad[k]);
                        if (cost < bestCost){
                            bestCost = cost;
                            bestDC = k;
                        }
                    }

                    // if jobs change dc firstTime
                    if (userJobs.get(j).getCurrentDatacenter()==-1){
                        userJobs.get(j).setCurrentDatacenter(bestDC);
                        dataCenters[bestDC].addJob();
                    }


                    // if currentDC is not bestDC,job changes DC
                    if(userJobs.get(j).getCurrentDatacenter()!=bestDC&&num>95){
                        dataCenters[userJobs.get(j).currentDatacenter].subJob();
                        userJobs.get(j).setCurrentDatacenter(bestDC);
                        dataCenters[bestDC].addJob();
                    }

                }
            }

            for (int k = 0;k<config.NUM_OF_DATACENTER;k++){
                dataCenterInfo dataCenterInfo = new dataCenterInfo(k, dataCenters[k].getLoad());
                dataCenterData.add(dataCenterInfo);
                previousDatacenterLoad[k] = dataCenters[k].getLoad();
            }

        }

        try (FileWriter fileWriter = new FileWriter("/home/masuda/Doxuments/CMorph/output/serverdata.json", false)) {
            objectMapper.writeValue(fileWriter, dataCenterData);
                } catch(IOException e){
                    e.printStackTrace();
                }

    }




























}
