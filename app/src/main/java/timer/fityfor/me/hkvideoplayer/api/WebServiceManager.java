package timer.fityfor.me.hkvideoplayer.api;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class WebServiceManager {

    public static void getVideos(AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpClient.get("", null, asyncHttpResponseHandler);
    }
}
