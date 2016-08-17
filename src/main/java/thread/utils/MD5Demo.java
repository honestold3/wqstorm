package thread.utils;

import com.google.common.io.BaseEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by wq on 16/1/12.
 */
public class MD5Demo {
    public static void main(String[] args) throws Exception{
        String mrluo = "www.mr-luo.com";
        String stringMd5 = getStringMd5(mrluo);
        System.out.println("Md5::::"+stringMd5);
        System.out.println("stringMd5 bytes==>"+stringMd5.getBytes());
        byte[] kk = stringMd5.getBytes();
        for (int i =0; i<kk.length;i++){
            System.out.print(kk[i]+" ");
        }
        System.out.println("stringMd5 ==>" + stringMd5);

        byte[] encodedBytes = Base64.encodeBase64(stringMd5.getBytes());
        System.out.println("encodedBytes " + new String(encodedBytes));
        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
        System.out.println("decodedBytes " + new String(decodedBytes));

        String base64String = BaseEncoding.base64().encode(stringMd5.getBytes("gbk"));
//decode
        System.out.println("Base64:" + base64String);//SGVsbG8gVmnhu4d0IE5hbQ==
        byte[] contentInBytes = BaseEncoding.base64().decode(base64String);
        System.out.println("Source content: " + new String(contentInBytes, "UTF-8"));

        String kankan = com.sun.org.apache.xml.internal.security.utils.Base64.encode(stringMd5.getBytes());
        System.out.println(kankan);

        byte[] srtbyte = {(byte)245,(byte)221,(byte)149,(byte)198,(byte)248,(byte)220,(byte)97,(byte)210,(byte)39
                ,(byte)72,(byte)217,(byte)178,(byte)75,(byte)120,(byte)80,(byte)141};

        String res = new String(srtbyte);
        System.out.println(res);
    }

    public static String getStringMd5(String str) {
        String md5Hex = DigestUtils.md5Hex(str);
        return md5Hex;
    }
}
