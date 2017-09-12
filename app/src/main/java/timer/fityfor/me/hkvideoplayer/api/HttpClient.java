package timer.fityfor.me.hkvideoplayer.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import timer.fityfor.me.hkvideoplayer.controllers.ViewController;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class HttpClient {

    public static final String BASE_URL = "http://93.94.217.144:8080/videos/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Context context1 = ViewController.getViewController().getContext();
        client.get(context1, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
