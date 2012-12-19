package com.example.testlooper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class LooperThread extends Thread {
    private Handler mHandler;
    private Looper mLooper;
    
    private static final String TAG = "LooperThread";
    
    public Looper getLooper(){
    	return mLooper;
    }
    
    public Handler getHandler(){
    	synchronized(this){
    		while (mHandler == null){
    			try{
    				wait();
    			}catch(InterruptedException e){}
    		}
    	}
    	return mHandler;
    }
    
    static class MyHandler extends Handler{
    	public void handleMessage(Message msg){
			Log.d(TAG, Thread.currentThread().getName()+", "+ msg.obj.toString());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
		}
    }
    
    
	public void run(){
		Looper.prepare();
		synchronized(this){
			mHandler = new LooperThread.MyHandler();
			mLooper = Looper.myLooper();
			notifyAll();
		}
		Looper.loop();
		
	}
}
