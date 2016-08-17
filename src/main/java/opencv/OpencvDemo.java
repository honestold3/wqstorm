package opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Created by wq on 16/7/29.
 */
public class OpencvDemo {
    public static void main(String[] args) {
        //System.loadLibrary("/usr/local/Cellar/opencv/2.4.12_2/share/OpenCV/java");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.load("/usr/local/Cellar/opencv/2.4.12_2/share/OpenCV/java");
        //System.load("/Users/wq/opt/opencv-2.4.13/build/lib");
        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("m = " + m.dump());
    }
}
