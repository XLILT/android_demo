package com.sunlands.www.ijktester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 通过Button的 android:onClick属性定义点击事件
     * @param view
     */
    public void onButtonClick(View view) {
        // Toast.makeText(this, "你点击了btn1", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this, VideoActivity.class);

        Bundle bundle = new Bundle();

        EditText editText = (EditText)findViewById(R.id.editText);

        bundle.putString("url", editText.getText().toString());

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
