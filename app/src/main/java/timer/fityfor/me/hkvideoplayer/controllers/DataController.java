package timer.fityfor.me.hkvideoplayer.controllers;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import timer.fityfor.me.hkvideoplayer.entities.MyList;
import timer.fityfor.me.hkvideoplayer.entities.MyVideo;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class DataController {

    private static DataController dataController = new DataController();

    private MyList<MyVideo> videosData;

    public static DataController getInstance() {

        return dataController;
    }

    public MyList<MyVideo> getVideosData() {
        return videosData;
    }

    public void setVideosData(MyList<MyVideo> videosData) {
        this.videosData = videosData;
    }
}
