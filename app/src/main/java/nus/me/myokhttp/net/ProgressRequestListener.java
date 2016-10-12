package nus.me.myokhttp.net;

public interface ProgressRequestListener {
        void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}