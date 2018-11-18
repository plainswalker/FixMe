package com.aplusstory.fixme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapView;

public class TMapFragment extends AppCompatActivity {
    LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey( "426c8d5b-45a1-49af-8e3d-05f5f36d3b9e" );
        linearLayoutTmap.addView( tMapView );
    }


}
