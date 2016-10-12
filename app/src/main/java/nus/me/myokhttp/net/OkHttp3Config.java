package nus.me.myokhttp.net;

import android.content.Context;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.OkHttpClient;

/**
 * Created by nus on 16-10-12.
 */

public class OkHttp3Config {
     static  int READ_TIMEOUT = 30;
     static  int WRITE_TIMEOUT = 10;

     static  int CONNECT_TIMEOUT = 10;

    // cookie
    public CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER);

    private int readTime = READ_TIMEOUT;
    private int writeTime = WRITE_TIMEOUT;
    private int connectTime = CONNECT_TIMEOUT;

    private int CacheSize = 50*1024*1024;
    private String CacheDIR;

    public enum CacheStrategy{
        DEFAULT,
        FORCE_NETWORK,
        FORCE_CACHE
    }

    public OkHttp3Config(Context context){
        CacheDIR = context.getCacheDir().getAbsolutePath();
    }


}
