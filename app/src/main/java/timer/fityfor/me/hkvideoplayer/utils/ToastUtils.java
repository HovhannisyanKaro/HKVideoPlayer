package timer.fityfor.me.hkvideoplayer.utils;

import android.widget.Toast;

import timer.fityfor.me.hkvideoplayer.controllers.ViewController;


/**
 * Created by karo.hovhannisyan on 12/09/2017.
 */

public class ToastUtils {

    private final static boolean isDebug = true;

    public static void t(String text) {
        if (isDebug) {
            Toast.makeText(ViewController.getViewController().getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
