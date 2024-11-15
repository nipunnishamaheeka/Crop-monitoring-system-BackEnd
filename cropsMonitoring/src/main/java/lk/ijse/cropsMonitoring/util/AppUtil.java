package lk.ijse.cropsMonitoring.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String createCropId() {
        return "CROPS : " + UUID.randomUUID();
    }
    public static String toBase64FieldImage(byte[] profilePic) {
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
