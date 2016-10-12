package nus.me.myokhttp.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nus on 16-10-12.
 */

public class MyOkHttp3 {

    private OkHttpClient mOkHttpClient;

    private Handler mainHanlder;
    public  Gson gson;
    private final OkHttpClient.Builder builder;

    private static class MyOkHttp3Holder{
        public static MyOkHttp3 mInstance=new MyOkHttp3();

    }

    private MyOkHttp3() {

        mOkHttpClient = new OkHttpClient();
        // mOkHttpClient.networkInterceptors().add(new StethoInterceptor());

        builder = mOkHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);


        gson=new Gson();

        //更新UI线程
        mainHanlder = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }
    public static MyOkHttp3 getInstance(){
        return MyOkHttp3Holder.mInstance;
    }




    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String CHARSET_NAME = "UTF-8";


    public void get_DataFromServer(Request request,final  MyCallBack myCallBack){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.onResponse(json);
                    }
                });
            }
        });
    }


//    public static String sessionId = "";
//    private Map getCookie(Response response , String url){
//        Map<String,List<String>> map = new HashMap<String ,List<String>>();
//
//
//        return map;
//    }

        public void post_DataFromServer(Request request, final MyCallBack callBack){
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String json = response.body().string();
                    mainHanlder.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onResponse(json);
                        }
                    });
                }
            });

        }

    public void uploadPost2ServerProgress(Context context, String url, RequestBody requestBody, MyCallBack myCallBack, final UIchangeListener uIchangeListener){
        ProgressRequestBody progressRequestBody = ProgressHelper.addProgressRequestListener(requestBody, new UIProgressRequestListener() {
            @Override
            public void onUIRequestProgress(long bytesWrite, long contentLength, boolean done) {
                uIchangeListener.progressUpdate(bytesWrite,contentLength,done);
            }
        });
    }
    /**
     * 下载监听
     */

    public void download4ServerListener(Request request, final MyCallBackFile callBackFile, final  UIchangeListener uIchangeListener){
        ProgressHelper.addProgressChangeListener(builder, new UIProgressResponseListener() {
            @Override
            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {

            }
        }).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBackFile.onResponse(response);
            }
        });
    }





    public interface MyCallBack{
        void onFailure(Request request,IOException e);
        void onResponse(String json);
    }

    public interface MyCallBackFile {
        void onFailure(Request request, IOException e);

        void onResponse(Response response);


    }

    public static  MyCallBack myCallBack;
    public void addGetResult(MyCallBack myCallBack){
        this.myCallBack = myCallBack;
    }

    public interface UIchangeListener{
        void progressUpdate(long bytesWrite,long contentLength,boolean isFinish);
    }


}
