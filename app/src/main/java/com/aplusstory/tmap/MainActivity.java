package com.aplusstory.tmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;

public class MainActivity extends AppCompatActivity
        implements TMapGpsManager.onLocationChangedCallback, View.OnClickListener {

    private Context mContext = null;
    private boolean m_bTrackingMode = true;

    private TMapData tmapdata = null;
    private TMapGpsManager tmapgps = null;
    private TMapView tmapview = null;
    private static String mApiKey = "426c8d5b-45a1-49af-8e3d-05f5f36d3b9e";
    private static int mMarkerID;

    private ArrayList<TMapPoint> m_tmapPoint = new ArrayList<TMapPoint>();
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();

    private String address;
    private Double lat = null;
    private Double lon = null;

    private Button bt_find; //주소로 찾기 버튼

    @Override
    public void onLocationChange(Location location) {
        if (m_bTrackingMode) {
            tmapview.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //버튼 선언
        bt_find = (Button) findViewById(R.id.bt_findadd);

        //Tmap 각종 객체 선언
        tmapdata = new TMapData(); //POI검색, 경로검색 등의 지도데이터를 관리하는 클래스
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mapview);
        tmapview = new TMapView(this);

        linearLayout.addView(tmapview);
        tmapview.setSKTMapApiKey(mApiKey);


        /* 현재 보는 방향 */
        tmapview.setCompassMode(true);

        /* 현위치 아이콘표시 */
        tmapview.setIconVisibility(true);

        /* 줌레벨 */
        tmapview.setZoomLevel(15);
        tmapview.setUserScrollZoomEnable(false);

        /* 지도 타입 */
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);

        /* 언어 설정 */
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);



        tmapgps = new TMapGpsManager(MainActivity.this); //단말의 위치탐색을 위한 클래스
        tmapgps.setMinTime(1000); //위치변경 인식 최소시간설정
        tmapgps.setMinDistance(5); //위치변경 인식 최소거리설정
        if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            tmapgps.setProvider(tmapgps.NETWORK_PROVIDER); //네트워크 기반의 위치탐색
            tmapgps.OpenGps();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(this.getApplicationContext(),"FINE_LOCATION 권한 없음",Toast.LENGTH_LONG).show();
        }
        //tmapgps.setProvider(tmapgps.GPS_PROVIDER); //위성기반의 위치탐색
        /*  화면중심을 단말의 현재위치로 이동 */
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);


        //풍선 클릭시
        tmapview.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem markerItem) {

                lat = markerItem.latitude;
                lon = markerItem.longitude;

                //1. 위도, 경도로 주소 검색하기
                tmapdata.convertGpsToAddress(lat, lon, new TMapData.ConvertGPSToAddressListenerCallback() {
                    @Override
                    public void onConvertToGPSToAddress(String strAddress) {
                        address = strAddress;
                    }
                });
                if(address != null){
                    Toast.makeText(MainActivity.this, "주소 : " + address, Toast.LENGTH_SHORT).show();
                }
            }

        });

        //버튼 리스너 등록
        bt_find.setOnClickListener(this);

    }
    //핀 찍을 data
    public void addPoint(String name, double latitude, double longitude) {
        // 강남 //
        m_mapPoint.add(new MapPoint(name, latitude, longitude));
    }

    // 마커(핀) 찍는함수
    public void showMarkerPoint() {
        for (int i = 0; i < m_mapPoint.size(); i++) {
            TMapPoint point = new TMapPoint(m_mapPoint.get(i).getLatitude(),
                    m_mapPoint.get(i).getLongitude());
            TMapMarkerItem item1 = new TMapMarkerItem();
            Bitmap bitmap = null;
            /* 핀 이미지 */
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);

            item1.setTMapPoint(point);
            item1.setName(m_mapPoint.get(i).getName());
            item1.setVisible(item1.VISIBLE);

            item1.setIcon(bitmap);
            /* 핀 이미지 */
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);

            item1.setCalloutTitle(m_mapPoint.get(i).getName());
            item1.setCalloutSubTitle("서울");
            item1.setCanShowCallout(true);
            item1.setAutoCalloutVisible(true);

            /* 풍선 안 우측버튼 */
            Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);

            item1.setCalloutRightButtonImage(bitmap_i);

            String strID = String.format("pmarker%d", mMarkerID++);

            tmapview.addMarkerItem(strID, item1);
            mArrayMarkerID.add(strID);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_findadd:
                convertToAddress();
                break;
        }
    }

    //3. 주소검색으로 위도, 경도 검색하기
    /* 명칭 검색을 통한 주소 변환 */
    public void convertToAddress() {
        //다이얼로그 띄워서, 검색창에 입력받음
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("통합 검색");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strData = input.getText().toString();
                TMapData tmapdata = new TMapData();

                tmapdata.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                        for (int i = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);

                            Log.d("주소로찾기", "POI Name: " + item.getPOIName().toString() + ", " +
                                    "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                    "Point: " + item.getPOIPoint().toString());
                            addPoint(item.getPOIName(),item.getPOIPoint().getLatitude(),item.getPOIPoint().getLongitude());
                        }
                        showMarkerPoint();
                    }
                });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}