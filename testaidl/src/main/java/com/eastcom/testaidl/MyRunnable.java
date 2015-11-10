package com.eastcom.testaidl;

/**
 * Created by rockgarden on 15/11/8.
 */
public class MyRunnable implements Runnable {

    private  String name;



    public MyRunnable(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i=0;i<1000;i++) {
            System.out.print(name + ":" + i);
        }
    }
}
