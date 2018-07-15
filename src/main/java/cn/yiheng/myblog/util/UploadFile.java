package cn.yiheng.myblog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UploadFile {
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String saveFile(InputStream file, String path, String fileName) {
        File newFile = new File(path);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        newFile = new File(path + "/" + fileName);
        try (FileOutputStream fop = new FileOutputStream(newFile)) {
            // if file doesn't exists, then create it
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            // get the content in bytes
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = file.read(buffer, 0, 8192)) != -1) {
                fop.write(buffer, 0, bytesRead);
            }
            //fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return fileName;
    }

    public static String getFileMD5(InputStream inputStream) {
        String name = "";
        try {
            byte[] bytes = new byte[1024];
            int len = 0;
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            while ((len = inputStream.read(bytes)) > 0) {
                messagedigest.update(bytes, 0, len);
            }
            name = bufferToHex(messagedigest.digest());
            inputStream.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return name;
    }

    public static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换    
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同    
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换    
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
