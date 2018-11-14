package com.aplusstory.tmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        final TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey( "426c8d5b-45a1-49af-8e3d-05f5f36d3b9e" );
        linearLayoutTmap.addView( tMapView );

        Button button1 = (Button) findViewById(R.id.btnSearch) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            EditText etLatitude = (EditText)findViewById(R.id.latitude);
            EditText etLongitude = (EditText)findViewById(R.id.longitude);
            TMapMarkerItem markerItem1 = new TMapMarkerItem();
            Bitmap markerImage = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            @Override
            public void onClick(View view) {
                TMapPoint tMapPoint1 = new TMapPoint(Double.parseDouble(etLatitude.getText().toString()),Double.parseDouble(etLongitude.getText().toString()));
                // TODO : click event
                markerItem1.setIcon(markerImage);
                markerItem1.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
                markerItem1.setTMapPoint( tMapPoint1 ); // 마커의 좌표 지정
                markerItem1.setName("SKT타워"); // 마커의 타이틀 지정
                markerItem1.setVisible(VISIBLE);
                tMapView.addMarkerItem("markerItem1", markerItem1); // 지도에 마커 추가
                tMapView.setCenterPoint(Double.parseDouble(etLatitude.getText().toString()),Double.parseDouble(etLongitude.getText().toString()),true);
            }
        });
    }



}
