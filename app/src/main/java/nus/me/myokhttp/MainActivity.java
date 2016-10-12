package nus.me.myokhttp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;

import nus.me.myokhttp.net.FileUtils;
import nus.me.myokhttp.net.MyOkHttp3;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String url = " ";
    private ProgressBar pb2;
    private ProgressBar pb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb1 = (ProgressBar) findViewById(R.id.pb1);
        pb2 = (ProgressBar) findViewById(R.id.pb2);

        //Get
        Request request = new Request.Builder()
                .get()
                .url("http://v.juhe.cn/toutiao/index?type=top&key=f59e84b6381469d83e947ab566890987")
                .build();
        MyOkHttp3.getInstance().get_DataFromServer(request, new MyOkHttp3.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String json) {
                Log.e("Get",json);
            }
        });

        //Post
        FormBody body = new FormBody.Builder()
                .add("key", "f59e84b6381469d83e947ab566890987")
                .add("type", "tiyu")
                .build();
        Request request1 = new Request.Builder()
                .post(body)
                .url("http://v.juhe.cn/toutiao/index")
                .build();
        MyOkHttp3.getInstance().post_DataFromServer(request1, new MyOkHttp3.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String json) {
                Log.e("POST",json);

            }
        });
//      download
        Request request3=new Request.Builder()

                .get()
                .url("http://7xnbj0.com1.z0.glb.clouddn.com/IMG_1919.jpg")
                .build();

        MyOkHttp3.getInstance().download4ServerListener(request, new MyOkHttp3.MyCallBackFile() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) {
                File file;
                String dir= Environment.getExternalStorageDirectory()+"/";

                file =new File(dir);
                if(file!=null){
                    file.mkdirs();
                }
               file= FileUtils.saveFile2Local(response,dir,"donload.jpg");

            }
        }, new MyOkHttp3.UIchangeListener() {
            @Override
            public void progressUpdate(long bytesWrite, long contentLength, boolean isFinish) {
                int progress= (int) (bytesWrite*100/contentLength);
                pb1.setProgress(progress);
                pb2.setProgress(progress);

            }
        });



    }


}
