package xyz.photosnooze.ui;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by shine on 16/6/10.
 */
public class BaseActivity extends AppCompatActivity{


    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


}
