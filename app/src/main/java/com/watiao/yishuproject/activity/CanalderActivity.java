package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.Miui9Calendar;
import com.necer.enumeration.CalendarState;
import com.necer.listener.OnCalendarChangedListener;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.RecyclerViewAdapter;

import org.joda.time.LocalDate;

public class CanalderActivity extends AppCompatActivity {

    private Miui9Calendar miui9Calendar;

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canalder);
        tv_result = findViewById(R.id.tv_result);

        miui9Calendar = findViewById(R.id.miui9Calendar);

        miui9Calendar.setCalendarState(CalendarState.WEEK);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);


        miui9Calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                tv_result.setText(year + "年" + month + "月" + "   当前页面选中 " + localDate);
            }
        });

    }
}
