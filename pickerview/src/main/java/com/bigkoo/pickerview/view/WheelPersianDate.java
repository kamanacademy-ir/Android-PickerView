package com.bigkoo.pickerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by peyman on 1/10/16.
 */
public class WheelPersianDate {

    WheelView mWheelYear, mWheelMonth, mWheelDay;
    View mView;
    int mStartYear;

    public WheelPersianDate(View view) {
        super();
        mView = view;
    }

    public void setPickerValues(int start_year, int end_year, ArrayList<String> month, int sel_year, int sel_month, int sel_day) {
        mStartYear = start_year;

        mWheelYear = (WheelView) mView.findViewById(R.id.year);
        mWheelYear.setAdapter(new NumericWheelAdapter(start_year, end_year));
        mWheelYear.setLabel("");
        mWheelYear.setCurrentItem(sel_year - start_year);

        mWheelMonth = (WheelView) mView.findViewById(R.id.month);
        mWheelMonth.setAdapter(new ArrayWheelAdapter<>(month, 12));
        mWheelMonth.setLabel("");
        mWheelMonth.setCurrentItem(sel_month - 1);

        mWheelDay = (WheelView) mView.findViewById(R.id.day);
        if (sel_month >= 7) {
            mWheelDay.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            mWheelDay.setAdapter(new NumericWheelAdapter(1, 31));
        }
        mWheelDay.setLabel("");
        mWheelDay.setCurrentItem(sel_day - 1);

        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int maxItem;
                if (index >= 6) {
                    mWheelDay.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    mWheelDay.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                }
                if (mWheelDay.getCurrentItem() > maxItem - 1){
                    mWheelDay.setCurrentItem(maxItem-1);
                }
            }
        };
        mWheelMonth.setOnItemSelectedListener(wheelListener_month);

        WheelView wv_hours = (WheelView)mView.findViewById(R.id.hour);
        WheelView wv_mins = (WheelView)mView.findViewById(R.id.min);
        wv_hours.setVisibility(View.GONE);
        wv_mins.setVisibility(View.GONE);
        mWheelYear.setTextSize(24);
        mWheelMonth.setTextSize(24);
        mWheelDay.setTextSize(24);
    }

    public int getSelectedYear() { return mWheelYear.getCurrentItem() + mStartYear; }
    public int getSelectedMonth() { return mWheelMonth.getCurrentItem() + 1; }
    public int getSelectedDay() { return mWheelDay.getCurrentItem() + 1; }
    public String getDateString() {
        StringBuffer sb = new StringBuffer();
        sb
                .append((mWheelDay.getCurrentItem() + 1)).append(" ")
                .append((mWheelMonth.getAdapter().getItem(mWheelMonth.getCurrentItem()))).append(" ")
                .append((mWheelYear.getCurrentItem() + mStartYear)).append(" ");
        return sb.toString();
    }

    public void setCyclic(boolean cyclic){
        mWheelYear.setCyclic(cyclic);
        mWheelMonth.setCyclic(cyclic);
        mWheelDay.setCyclic(cyclic);
    }

}
