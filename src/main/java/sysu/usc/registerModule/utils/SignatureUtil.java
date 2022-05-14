package sysu.usc.registerModule.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignatureUtil {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
    /**
     * 从学工号和时间戳解析出签名地址
     * 时间戳由主键id中解析出来
     * @param userId
     * @param timeStamp
     * @return
     */
    public static String parseSignaturePathFromUserIdAndTimeStamp(String userId, long timeStamp){
        String format = DATE_FORMAT.format(new Date(timeStamp));
        return userId + "/" + format;
    }
}
