package timer.fityfor.me.hkvideoplayer.utils;

import android.util.Log;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class LogUtils {

    private static final String LOG = "HK_LOG";
    private static final boolean isDebug = true;

    public static void d(String message){
        if (isDebug)
        Log.d(LOG, message);
    }
}
