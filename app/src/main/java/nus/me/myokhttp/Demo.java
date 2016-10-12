package nus.me.myokhttp;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nus on 16-10-12.
 */

public class Demo {
    public Object okGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        // build.addhead(key,value)

//        Request.Builder builder = new Request.Builder();
//        builder.cacheControl(CacheControl.FORCE_CACHE);
        final Request request = new Request.Builder().url(url).build();
        //get
        Response response = client.newCall(request).execute();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().toString();
                InputStream is = response.body().byteStream();

            }
        });


        return response;
    }


    public void okPost(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user","admin");
        builder.add("passwd","admin");
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        //Sync
        client.newCall(request).execute();

        //Async
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }


    public void fileUpload(String url,String... path){

//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/demo.jpg");
        File f1 = new File(path[0]);
        RequestBody f1body = RequestBody.create(MediaType.parse("application/octet-stream"),f1);
        String f1Name = "tf1.txt";

        File f2 = new File(path[1]);
        RequestBody f2body = RequestBody.create(MediaType.parse("application/octet-stream"),f2);
        String f2Name = "tf2.txt";

          /* form的分割线,定义 */
        String boundary = "zxc-?-zxc";

        MultipartBody.Builder builder = new MultipartBody.Builder(boundary);
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("key","value");
        builder.addPart(f1body);
        builder.addPart(f2body);
        MultipartBody body = builder.build();

        final Request request = new Request.Builder().url(url).post(body).build();

        //Async
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                final boolean isOk = response.isSuccessful();


            }
        });


    }

}
