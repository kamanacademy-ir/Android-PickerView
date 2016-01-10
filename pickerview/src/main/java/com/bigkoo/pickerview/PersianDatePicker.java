package com.bigkoo.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelPersianDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by peyman on 1/10/16.
 */
public class PersianDatePicker extends BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public interface PersianDatePickerListener {
        void onPicked(int year, int month, int day, String date_string);
        void onCancel();
    }

    WheelPersianDate wheelPersian;
    private Button btnSubmit, btnCancel;
    private TextView tvTitle;
    private PersianDatePickerListener mListener;
    private ArrayList<String> mMonthNames;

    public PersianDatePicker(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    }
    public void setMonthNames(ArrayList<String> month_names) {
        mMonthNames = month_names;
    }
    public void setPicker(int year, int month, int day, int start_year, int end_year, PersianDatePickerListener listener) {
        final View time_picker_view = findViewById(R.id.timepicker);

        if (mMonthNames == null) {
            String[] month_names = time_picker_view.getResources().getStringArray(R.array.pickerview_persian_month_names);
            mMonthNames = new ArrayList<>(Arrays.asList(month_names));
        }

        wheelPersian = new WheelPersianDate(time_picker_view);
        wheelPersian.setPickerValues(start_year, end_year, mMonthNames, year, month, day);
        wheelPersian.setCyclic(true);
        mListener = listener;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }
    public void setCancelText(String text) {
        btnCancel.setText(text);
    }
    public void setSubmitText(String text) {
        btnSubmit.setText(text);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (tag.equals(TAG_CANCEL)) {
            if (mListener != null) {
                mListener.onCancel();
            }
            dismiss();
        } else {
            if (mListener != null) {
                mListener.onPicked(wheelPersian.getSelectedYear(), wheelPersian.getSelectedMonth(), wheelPersian.getSelectedDay(), wheelPersian.getDateString());
            }
            dismiss();
        }
    }
}
