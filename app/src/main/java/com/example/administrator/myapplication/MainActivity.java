package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cai.linkagelibrary.CustomAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView addr;
    private Button button;
    private CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 初始化控件
         */
        addr = (TextView) findViewById(R.id.addr);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                alertDialog = new CustomAlertDialog(MainActivity.this);
                showAlertDialog(true, new CustomAlertDialog.OnAlertClickListener() {
                    @Override
                    public void onQuClick(String string) {
                        addr.setText(string);
                    }
                });
        }
    }


    /**
     * 显示信息对话框
     */
    private void showAlertDialog(boolean cancelable, CustomAlertDialog.OnAlertClickListener listener) {
        if (alertDialog == null) {
            alertDialog = new CustomAlertDialog(this);
        }
        alertDialog.setCancelable(cancelable);
        alertDialog.setOnAlertClickListener(listener);
        alertDialog.show();
    }
}
