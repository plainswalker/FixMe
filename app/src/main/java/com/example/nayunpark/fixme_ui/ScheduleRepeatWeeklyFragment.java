package com.example.nayunpark.fixme_ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleRepeatWeeklyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleRepeatWeeklyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleRepeatWeeklyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    boolean[] checkedDay;


    private String checkDay = ""; // has information which day is checked

    public void setCheckDay(String checkDay) {
        this.checkDay = checkDay;
    }

    public String getCheckDay() {
        return checkDay;
    }

    private OnFragmentInteractionListener mListener;

    public ScheduleRepeatWeeklyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleRepeatWeeklyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleRepeatWeeklyFragment newInstance(String param1, String param2) {
        ScheduleRepeatWeeklyFragment fragment = new ScheduleRepeatWeeklyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View returnView = inflater.inflate(R.layout.fragment_schedule_repeat_weekly, container, false);

        final CheckBox checkBoxMon = (CheckBox) returnView.findViewById(R.id.checkMon);
        final CheckBox checkBoxTue = (CheckBox) returnView.findViewById(R.id.checkTue);
        final CheckBox checkBoxWed = (CheckBox) returnView.findViewById(R.id.checkWed);
        final CheckBox checkBoxThu = (CheckBox) returnView.findViewById(R.id.checkThu);
        final CheckBox checkBoxFri = (CheckBox) returnView.findViewById(R.id.checkFri);
        final CheckBox checkBoxSat = (CheckBox) returnView.findViewById(R.id.checkSat);
        final CheckBox checkBoxSun = (CheckBox) returnView.findViewById(R.id.checkSun);





        Button confirmButton = (Button) returnView.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = "";

                if(checkBoxMon.isChecked() == true) temp += "월";
                if(checkBoxTue.isChecked() == true) temp += "화";
                if(checkBoxWed.isChecked() == true) temp += "수";
                if(checkBoxThu.isChecked() == true) temp += "목";
                if(checkBoxFri.isChecked() == true) temp += "금";
                if(checkBoxSat.isChecked() == true) temp += "토";
                if(checkBoxSun.isChecked() == true) temp += "일";

                setCheckDay(temp);

                Toast.makeText(getContext(), "매주 "+checkDay, Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().hide(ScheduleRepeatWeeklyFragment.this).commit();
                //fragmentManager.popBackStack();
            }
        });

        return returnView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
