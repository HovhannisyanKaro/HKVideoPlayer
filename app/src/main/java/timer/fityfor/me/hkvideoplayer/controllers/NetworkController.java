package timer.fityfor.me.hkvideoplayer.controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import timer.fityfor.me.hkvideoplayer.R;
import timer.fityfor.me.hkvideoplayer.api.WebServiceManager;
import timer.fityfor.me.hkvideoplayer.entities.MyList;
import timer.fityfor.me.hkvideoplayer.entities.MyVideo;
import timer.fityfor.me.hkvideoplayer.interfaces.OnNetworkCallListener;
import timer.fityfor.me.hkvideoplayer.utils.RegaxUtils;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class NetworkController {

    public static void getVideos(final OnNetworkCallListener onNetworkCallListener) {

        WebServiceManager.getVideos(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String str = "N/A";
                try {
                    str = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                MyList<MyVideo> data = new MyList<>();
                for (String videoUrl : RegaxUtils.extractUrls(str)) {
                    data.add(new MyVideo(videoUrl));
                }
                DataController.getInstance().setVideosData(data);
                onNetworkCallListener.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onNetworkCallListener.onFailure(error.toString());
            }
        });
    }
}
