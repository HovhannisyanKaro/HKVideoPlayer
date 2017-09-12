package timer.fityfor.me.hkvideoplayer.entities;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class MyVideo {
    private String videoUrl;

    public MyVideo() {
    }

    public MyVideo(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyVideo myVideo = (MyVideo) o;

        return videoUrl != null ? videoUrl.equals(myVideo.videoUrl) : myVideo.videoUrl == null;

    }

    @Override
    public int hashCode() {
        return videoUrl != null ? videoUrl.hashCode() : 0;
    }
}
