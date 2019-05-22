package com.example.add;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addHotelNameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);
        findViewById(R.id.btn_getData).setOnClickListener(this);
        //默认添加一个Item
        addViewItem(null);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addHotel://点击添加按钮就动态添加Item
                addViewItem(v);
                break;
            case R.id.btn_getData://打印数据
                printData();
                break;
        }
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            final View childAt = addHotelNameView.getChildAt(i);
            final Button btn_remove = (Button) childAt.findViewById(R.id.btn_addHotel);
            btn_remove.setText("删除");
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    addHotelNameView.removeView(childAt);
                }
            });
            //如果是最后一个ViewItem，就设置为添加
            if (i == (addHotelNameView.getChildCount() - 1)) {
                Button btn_add = (Button) childAt.findViewById(R.id.btn_addHotel);
                btn_add.setText("+新增");
                btn_add.setTag("add");
                btn_add.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (addHotelNameView.getChildCount() == 0) {//如果一个都没有，就添加一个
            View hotelEvaluateView = View.inflate(this, R.layout.item_hotel_evaluate, null);
            Button btn_add = (Button) hotelEvaluateView.findViewById(R.id.btn_addHotel);
            btn_add.setText("+新增");
            btn_add.setTag("add");
            btn_add.setOnClickListener(this);
            addHotelNameView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else if (((String) view.getTag()).equals("add")) {//如果有一个以上的Item,点击为添加的Item则添加
            View hotelEvaluateView = View.inflate(this, R.layout.item_hotel_evaluate, null);
            addHotelNameView.addView(hotelEvaluateView);
            sortHotelViewItem();
        }
        //else {
        //  sortHotelViewItem();
        //}
    }

    //获取所有动态添加的Item，找到控件的id，获取数据
    private void printData() {
        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            View childAt = addHotelNameView.getChildAt(i);
            EditText hotelName = (EditText) childAt.findViewById(R.id.ed_hotelName);
            RatingBar hotelEvaluateStart = (RatingBar) childAt.findViewById(R.id.rb_hotel_evaluate);
            EditText hotelEvaluate = (EditText) childAt.findViewById(R.id.ed_hotelEvaluate);
            Log.e(TAG, "酒店名称：" + hotelName.getText().toString() + "-----评价星数："
                    + (int) hotelEvaluateStart.getRating() + "-----服务评价：" + hotelEvaluate.getText().toString());
        }
    }

}
