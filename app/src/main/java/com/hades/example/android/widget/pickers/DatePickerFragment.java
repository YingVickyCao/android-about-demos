package com.hades.example.android.widget.pickers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;

import java.util.Calendar;

public class DatePickerFragment extends BaseFragment {

    private TextView selectDate;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_datepicker, container, false);

        selectDate = view.findViewById(R.id.selectDate);

        DatePicker mDatePicker = view.findViewById(R.id.datePicker);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);

        mDatePicker.init(mYear, mMonth, mDay, (arg0, year, month, day) -> {
            mYear = year;
            mMonth = month;
            mDay = day;

            showDate(mYear, mMonth, mDay, mHour, mMinute);
        });
        return view;
    }

    private void showDate(int year, int month, int day, int hour, int minute) {
        selectDate.setText(year + "年" + (month + 1) + "月" + day + "日  " + hour + "时" + minute + "分");
    }
}
