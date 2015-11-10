package com.eastcom.testaidl;

/**
 * Created by rockgarden on 15/11/8.
 */
public class MyThread extends Thread {

    private  String name;

    public MyThread(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i=0;i<100;i++){
            System.out.print(name+":"+i);
        }
        super.run();
    }
}
