package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
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
                showAlertDialog(R.color.colorPrimary,alertDialog.THE_SECONDARY_LINKAGE, new CustomAlertDialog.OnAlertClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onQuClick(String province, String city, String district,String code) {
                        if (city ==  null){
                            addr.setText(province + district);
                        }else {
                            addr.setText(province + city + district);
                        }
                    }
                });
        }
    }


    /**
     * 显示信息对话框
     */
    private void showAlertDialog(int selectedColor, int type, CustomAlertDialog.OnAlertClickListener listener) {
        if (alertDialog == null) {
            alertDialog = new CustomAlertDialog(this);
        }
        alertDialog.setContent(selectedColor,type);
        alertDialog.setOnAlertClickListener(listener);
        alertDialog.show();
    }
}
