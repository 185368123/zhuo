package zhuozhuo.com.zhuo.base;

import android.view.View;
import android.widget.EditText;

public class ClearTextClickListener implements View.OnClickListener{

    private EditText editText;

    public ClearTextClickListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        if (editText != null){
            editText.setText(null);
        }
    }
}
