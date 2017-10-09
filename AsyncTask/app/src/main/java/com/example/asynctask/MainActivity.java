package com.example.asynctask;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    //这里是Listitem的内容，在这个例子中，将在后台任务中逐个加入
    private static String[] items = {"one","two","three","four","five","six","seven","eight","nine","ten"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //在这个例子中，我们并没有导入items的数据，注意item数据为新建的ArrayList，即无内容
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>()));
        //创建后台任务的对象，并通过execute启动后台线程，调用doIbBackground的代码，execute中的参数类型为参数1，这里我们不需要传递任何内容
        new AddStringTask().execute();
    }
     //创建AsnycTask子类 参数1是Void的范式类型，参数2是String的范式类型，参数3是Void。其中参数1：向后台任务的执行方法传递参数的类型；参数2：在后台任务执行过程中，
    //要求主UI线程处理中间状态通常是一些UI处理中传递的参数类型；参数3：后台任务执行完返回时的参数类型
    private class AddStringTask extends AsyncTask<Void,String,Void> {
        private void printInfo(String info){
            Log.d("Current",info+":Thread is"+Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (String item:items){
                publishProgress(item);
                printInfo("doInBackground"+item);
                SystemClock.sleep(200);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            printInfo("onProgressUpdate get Param"+values[0]);
            ((ArrayAdapter<String>)getListAdapter()).add(values[0]);
        }
       //定义后台进程执行完后的处理
        @Override
        protected void onPostExecute(Void aVoid) {
            printInfo("onPostExecute");
            Toast.makeText(MainActivity.this,"Done!",Toast.LENGTH_SHORT).show();
        }
    }
}
