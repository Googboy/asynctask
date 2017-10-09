package com.example.message;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ProgressBar progressBar = null;
    private boolean isRunning = false;
    /*
 //创建Handle，并通过handleMessage给出当收到消息时UI需要进行如何处理
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressBar.incrementProgressBy(5);
        }
    };
    */
    Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.incrementProgressBy(5);//每次增长5个百分比
            Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setProgress(0);
       //建立后台线程处理，采用Thread其中run()的内容就是线程并行处理的内容
        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for (int i=0;i<20&&isRunning;i++){
                        Thread.sleep(5000);
                        //发送message到队列中，参数中obtainMessage是用于给出一个新Message，本例无参数，对应的在handle队列中收到这条消息时，则通过handleMessage进行处理
                        handler.sendMessage(handler.obtainMessage());
                        runOnUiThread(runnable);
                       // handler.post(runnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        isRunning = true;
        background.start();
        runOnUiThread(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
