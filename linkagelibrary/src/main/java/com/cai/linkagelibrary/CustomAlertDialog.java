package com.cai.linkagelibrary;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * 作者：小蔡 on 2016/8/16 11:57
 * 邮箱：caixiquan@sina.cn
 */
public class CustomAlertDialog extends Dialog implements View.OnClickListener {
    private TextView sheng, shi, qu;
    private GridView gridview;
    private DBManager dbm;
    private SQLiteDatabase db;
    private String province = null;// 省
    private String city = null;// 市
    private String district = null;// 区
    private Context mContext;
    private String province_code = null;
    private int index = 0;


    private OnAlertClickListener listener;

    public CustomAlertDialog(Context context) {
        super(context, R.style.AlertDialogTheme);
        mContext = context;
        createView();
    }

    private void createView() {
        setContentView(R.layout.linkage_main);

        /**
         * 初始化控件
         */

        sheng = (TextView) findViewById(R.id.sheng);
        shi = (TextView) findViewById(R.id.shi);
        qu = (TextView) findViewById(R.id.qu);
        gridview = (GridView) findViewById(R.id.gridview);
        sheng.setOnClickListener(this);
        shi.setOnClickListener(this);
        qu.setOnClickListener(this);
        sheng();
        sheng.setBackgroundColor(Color.parseColor("#33b5e5"));
        shi.setBackgroundColor(Color.parseColor("#ffffffff"));
        qu.setBackgroundColor(Color.parseColor("#ffffffff"));



        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = getWindow();
        //设置dialog弹出的动画，从屏幕底部弹出     window.setWindowAnimations(R.style.take_photo_anim);
        //最重要的一句话，一定要加上！要不然怎么设置都不行！
         window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        //获取屏幕宽
        wlp.width = (int) (d.getWidth());
        wlp.height = (int) (d.getHeight() * 0.72);
        //宽度按屏幕大小的百分比设置，这里我设置的是全屏显示
        wlp.gravity = Gravity.BOTTOM;
        if (wlp.gravity == Gravity.BOTTOM)
            wlp.y = 0;
        //如果是底部显示，则距离底部的距离是0
        window.setAttributes(wlp);
    }

    public void setOnAlertClickListener(OnAlertClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener == null) return;
        int i = view.getId();
        if (i == R.id.sheng) {
            sheng();
            sheng.setBackgroundColor(Color.parseColor("#33b5e5"));
            shi.setBackgroundColor(Color.parseColor("#ffffffff"));
            qu.setBackgroundColor(Color.parseColor("#ffffffff"));
            province = null;
            city = null;
            index = 0;
            sheng.setText("省");
            shi.setText("市");
            qu.setText("区");

        } else if (i == R.id.shi) {
            if (index == 0) {
                Toast.makeText(mContext, "请先选择省份！", Toast.LENGTH_SHORT).show();
            } else if (index == 1) {
                return;
            } else if (index == 2) {
                city = null;
                // 通过省的编号查找该省所对应的市
                shi(province_code);
                sheng.setBackgroundColor(Color.parseColor("#ffffffff"));
                shi.setBackgroundColor(Color.parseColor("#33b5e5"));
                qu.setBackgroundColor(Color.parseColor("#ffffffff"));
            }

        } else if (i == R.id.qu) {
            Log.e("ggggggggg", "gg" + shi + "jjjj" + "");
            if (city == null) {
                Toast.makeText(mContext, "请先选择市！", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 查询省
     */

    public void sheng() {
        // 创建数据库类对象
        dbm = new DBManager(mContext);
        // 打开本地数据库资源
        dbm.openDatabase();
        // 获取数据库省数据
        db = dbm.getDatabase();
        final ArrayList<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from province";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "gbk");
                MyListItem myListItem = new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "gbk");
            MyListItem myListItem = new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            // 将所有的省的名称和编号放入list集合中，方便在下面显示
            list.add(myListItem);

        } catch (Exception e) {
        }
        // 查询数据完毕后，关闭数据库连接
        dbm.closeDatabase();
        db.close();

        Adapter adapter = new Adapter(mContext, list);
        // 设置GridView的适配器为新建的adapter
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // 点击省份后，通过点击的位置，在ArrayList中找出该省的名称和编号
                province_code = list.get(position).getPcode();
                province = list.get(position).getName();
                sheng.setText(province);
                if (province_code.equals("110000")){
                    shi.setText("北京");
                    index = 1;
                    qu("110100");
                    sheng.setBackgroundColor(Color.parseColor("#ffffffff"));
                    qu.setBackgroundColor(Color.parseColor("#33b5e5"));
                }else if (province_code.equals("120000")){
                    shi.setText("天津");
                    index = 1;
                    qu("120100");
                    sheng.setBackgroundColor(Color.parseColor("#ffffffff"));
                    qu.setBackgroundColor(Color.parseColor("#33b5e5"));
                }else if (province_code.equals("310000")){
                    shi.setText("上海");
                    index = 1;
                    qu("310100");
                    sheng.setBackgroundColor(Color.parseColor("#ffffffff"));
                    qu.setBackgroundColor(Color.parseColor("#33b5e5"));
                }else if (province_code.equals("500000")){
                    shi.setText("重庆");
                    index = 1;
                    qu("500100");
                    sheng.setBackgroundColor(Color.parseColor("#ffffffff"));
                    qu.setBackgroundColor(Color.parseColor("#33b5e5"));
                }else {
                    // 通过省的编号查找该省所对应的市
                    shi(province_code);
                    sheng.setBackgroundColor(Color.parseColor("#ffffffff"));
                    shi.setBackgroundColor(Color.parseColor("#33b5e5"));
                }
            }
        });
    }

    /**
     * 查询市
     *
     * @param pcode
     */

    public void shi(String pcode) {
        dbm = new DBManager(mContext);
        dbm.openDatabase();
        db = dbm.getDatabase();
        final ArrayList<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from city where pcode='" + pcode + "'";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "gbk");
                MyListItem myListItem = new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "gbk");
            MyListItem myListItem = new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();
        index = 2;
        Log.i("wujie", list.size() + "个市区" + list.get(0).getName());
        Adapter adapter = new Adapter(mContext, list);
        // 设置GridView的适配器为新建的adapter
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String city_code = list.get(position).getPcode();
                city = list.get(position).getName();
                shi.setText(city);
                qu(city_code);
                Log.i("wujie", city_code + "的" + city);
                shi.setBackgroundColor(Color.parseColor("#ffffffff"));
                qu.setBackgroundColor(Color.parseColor("#33b5e5"));
            }
        });
    }

    /**
     * 查询区，县
     *
     * @param pcode
     */

    public void qu(String pcode) {
        dbm = new DBManager(mContext);
        dbm.openDatabase();
        db = dbm.getDatabase();
        final ArrayList<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from district where pcode='" + pcode + "'";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[] = cursor.getBlob(2);
                String name = new String(bytes, "gbk");
                MyListItem myListItem = new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code = cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[] = cursor.getBlob(2);
            String name = new String(bytes, "gbk");
            MyListItem myListItem = new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();

        Adapter adapter = new Adapter(mContext, list);
        // 设置GridView的适配器为新建的simpleAdapter
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String district_code = list.get(position).getPcode();
                district = list.get(position).getName();
                qu.setText(district);
                if (city == null){
                    listener.onQuClick(province + district);
                }else {
                    listener.onQuClick(province + city + district);
                }
                dismiss();
            }
        });
    }

    public interface OnAlertClickListener {

        void onQuClick(String string);

    }
}
