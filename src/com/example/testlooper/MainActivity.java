package com.example.testlooper;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	private LooperThread mLooper;
	private Messenger mMessenger;
	
	private Button mSendButton;
	
	private int i=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mSendButton = (Button)this.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Message msg = Message.obtain(null, 0, new String("str"+i));
				i++;
				try {
					Log.d(TAG, Thread.currentThread().getName()+", "+ msg.obj.toString());
					mMessenger.send(msg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        });
        
        mLooper = new LooperThread();
        mLooper.start();
        
        mMessenger = new Messenger(mLooper.getHandler());
    }
    
    

    public void onDestroy(){
    	mLooper.getLooper().quit();
    	super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
