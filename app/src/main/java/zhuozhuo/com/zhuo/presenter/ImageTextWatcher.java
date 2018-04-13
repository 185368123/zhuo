package zhuozhuo.com.zhuo.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 */
public class ImageTextWatcher implements TextWatcher {

    private EditText editText;

    private ImageView imageView;

    public ImageTextWatcher(EditText editText, ImageView imageView) {
        this.editText = editText;
        this.imageView = imageView;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (editText != null && imageView != null) {
            String text = editText.getText().toString();
            if ((text != null) && (text.length() > 0)) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
