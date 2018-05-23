package li.com.videobeauty.button;

import android.graphics.Bitmap;


public interface CameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

    void quit();

}
