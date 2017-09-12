package timer.fityfor.me.hkvideoplayer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hovhannisyan.Karo on 12.09.2017.
 */

public class RegaxUtils {

    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "\"(.*?\\.mp4)\"";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(1), urlMatcher.end(1)));
        }
        return containedUrls;
    }
}
