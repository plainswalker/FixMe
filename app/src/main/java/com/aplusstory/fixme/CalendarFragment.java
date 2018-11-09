package com.aplusstory.fixme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aplusstory.fixme.cal.OneDayView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private TextView thisMonthTv = null;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getName(), "Created");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        thisMonthTv = (TextView)v.findViewById(R.id.this_month_tv);

        MonthlyFragment mf = (MonthlyFragment) getChildFragmentManager().findFragmentById(R.id.monthly_calender);
        mf.setOnMonthChangeListener(new MonthlyFragment.OnMonthChangeListener() {
            @Override
            public void onChange(int year, int month) {
                Log.d(CalendarFragment.this.getClass().getName(), "onChange " + year + "." + month);
                if(CalendarFragment.this.thisMonthTv != null) {
                    CalendarFragment.this.thisMonthTv.setText(year + "." + (month + 1));
                }
            }

            @Override
            public void onDayClick(OneDayView dayView) {
                int month = dayView.get(Calendar.MONTH);
                int day = dayView.get(Calendar.DAY_OF_MONTH);
                Log.d(CalendarFragment.this.getClass().getName(), "onDayClick " + month + "." + day);
                Toast.makeText(CalendarFragment.this.getContext(), "Click  " + month + "/" + day, Toast.LENGTH_SHORT)
                        .show();
            }

        });

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
