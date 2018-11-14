package com.cumulations.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewthreadCount;

    private MyService myService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewthreadCount=findViewById(R.id.textViewthreadCount);
        serviceIntent=new Intent(getApplicationContext(),MyService.class);
    }

    public void startService(View view) {
        startService(serviceIntent);
    }

    public void stopService(View view) {
        stopService(serviceIntent);
    }

    public void bindService(View view) {

        if(serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    MyService.MyServiceBinder myServiceBinder=(MyService.MyServiceBinder)iBinder;
                    myService=myServiceBinder.getService();
                    isServiceBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound=false;
                }
            };
        }

        bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound=false;
        }
    }

    public void getRandomNumber(View view) {
        if(isServiceBound){
            textViewthreadCount.setText("Random number: "+myService.getRandomNumber());
        }else{
            textViewthreadCount.setText("Service not bound");
        }
    }

    public void startIntentService(View view) {
        startService(new Intent(this,MyIntentService.class));
    }

    public void stopIntentService(View view) {
        stopService(new Intent(this,MyIntentService.class));
    }
}
