package com.ricardogwill.startedserviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class TheService extends Service {
    // New internal class (TheThread) allows threads (and thus implements Runnable).
    final class TheThread implements Runnable {
        // Variable to use in Constructor
        int serviceID;
        // Constructor
        public TheThread(int serviceID) {
            this.serviceID = serviceID;
        }

        @Override
        public void run() {
            // The "run" method is necessary. In this case, we are filling it with a Service
            // that will take 20 seconds to complete, but WON'T block other processes
            // because it is asynchronous and runs in a thread.
            synchronized (this) {
                try {
                    wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Stops the Service after 10 seconds.
                stopSelf(this.serviceID);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started.", Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(new TheThread(startId));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed.", Toast.LENGTH_SHORT).show();
    }
}
