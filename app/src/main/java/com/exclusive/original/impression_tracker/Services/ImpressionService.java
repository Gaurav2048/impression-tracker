package com.exclusive.original.impression_tracker.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ImpressionService extends Service {
    private final IBinder impressionBinder = new ImpressionBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e( "onCreate: ","called" );
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e( "onStartCommand: ",intent.getStringExtra("check") );
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e( "onStartCommand: ",intent.getStringExtra("check") );
        return impressionBinder;
    }

    public class ImpressionBinder extends Binder{

    }

}
