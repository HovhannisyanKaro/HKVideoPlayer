package timer.fityfor.me.hkvideoplayer.download;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import timer.fityfor.me.hkvideoplayer.controllers.ViewController;

import static timer.fityfor.me.hkvideoplayer.api.HttpClient.BASE_URL;
import static timer.fityfor.me.hkvideoplayer.utils.LogUtils.d;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class DownloadVideo extends AsyncTask<String, String, String> {

    private volatile boolean isExists = false;


    @Override
    protected String doInBackground(String... strings) {
        downloadFile(strings[0]);
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        if (!isExists) {
            ViewController.getViewController().showToast("Video was succesfuly downloaded");
        }
    }

    private void downloadFile(String fileName) {
        String rootDir = Environment.getExternalStorageDirectory() + File.separator + "Video";
        File rootFile = new File(rootDir);
        rootFile.mkdir();

        if (isExists(rootDir + "/" + fileName)) {
            d("File is exists...");
            isExists = true;
        } else {
            d("File is downloading...");
            isExists = false;
            try {
                String fileUrl = BASE_URL + fileName;
                URL url = new URL(fileUrl);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                FileOutputStream f = new FileOutputStream(new File(rootFile, fileName));
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
            } catch (IOException e) {
                isExists = false;
                Log.d("Error....", e.toString());
            }
        }
    }

    private boolean isExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

}
