package timer.fityfor.me.hkvideoplayer.controllers;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

import timer.fityfor.me.hkvideoplayer.MainActivity;
import timer.fityfor.me.hkvideoplayer.utils.ToastUtils;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class ViewController {

    private static ViewController viewController = null;
    private MainActivity mainActivity;
    private Activity context;

    public static ViewController getViewController() {
        if (viewController == null) {
            viewController = new ViewController();
        }
        return viewController;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showToast(final String errorMsg) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.t(errorMsg);
            }
        });
    }

    public void toFullScreen(boolean toFullScreen, View view) {
        if (toFullScreen) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        view.requestLayout();
    }
}
