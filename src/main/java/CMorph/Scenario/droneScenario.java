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
            dataCenters[i] = new dataCenter(config.DatacenterX[i], config.DatacenterY[i]);
        }

         // generate jobs until simstep=0.4375 and remove jobs if job arrive simstep
         for (int t = 0;t<config.simultionSteps;t++){
            createDrone(t);
            // ドローンの移動.仮に座標がシミュレーションのステップ数を超えたら削除
            for(int i = 0;i<userJobs.size();i++){
                userJobs.get(i).changeJobCoordinate(t);
            }
            
            // for (userJobs jobs:userJobs){
            //     jobs.changeJobCoordinate(t);
            //     if (jobs.y>=config.simultionSteps) {
            //         userJobs.remove(jobs);
            //     }
            // }
            for (int i = 0;i<userJobs.size();i++){
                changeDatacenter(i, t);
            }
            for (int k = 0;k<config.NUM_OF_DATACENTER;k++){
                dataCenterInfo datacenterinfo = new dataCenterInfo(k, dataCenters[k].getLoad());
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

    public void createDrone(int t){
        Random rand =new Random();
        int front =rand.nextInt(500);
        int back = rand.nextInt(500);
        double DroneX=rand.nextInt(100);
        double DroneY=rand.nextInt(100);
        double initialSpeed=rand.nextInt(20);
        int dataPlace = rand.nextInt(config.NUM_OF_DATACENTER);
        double simStep = t/config.simultionSteps;

        if(0<=t&&simStep<=0.4375){
            userJobs job = new userJobs(DroneX,DroneY,front,back,dataPlace,initialSpeed);
            userJobs.add(job);
        }
    }

    public void changeDatacenter(int i,int t){
        double bestCost=Double.MAX_VALUE;
        int bestDC=-1;
        
        if(userJobs.get(i)!=null){
            double rate = userJobs.get(i).frontCommunication/userJobs.get(i).backendCommunication;

            for (int k = 0;k<config.NUM_OF_DATACENTER;k++){
                double cost=CMorph.AllocationServer.allocationServer.getCost(userJobs.get(i),dataCenters[k].x,dataCenters[k].y,previousDatacenterLoad[k] );
                if(cost<bestCost){
                    bestCost=cost;
                    bestDC=k;
                }
                // if(useMasuda){

                // }

                if(userJobs.get(i).currentDatacenter==-1){
                    userJobs.get(i).setCurrentDatacenter(bestDC);
                    dataCenters[bestDC].addJob();
                }else{
                    if(userJobs.get(i).getCurrentDatacenter()!=bestDC){
                        dataCenters[userJobs.get(i).getCurrentDatacenter()].subJob();
                        userJobs.get(i).setCurrentDatacenter(bestDC);
                        dataCenters[bestDC].addJob();
                    }
                }
            }
        }
    }




























}
