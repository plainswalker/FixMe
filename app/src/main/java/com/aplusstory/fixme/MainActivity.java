package com.aplusstory.fixme;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ToggleButton noti_togg;
    private Button on;
    private Button off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.on = (Button) findViewById(R.id.onbutton);
        this.off = (Button) findViewById(R.id.offbutton);

        this.on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
            }
        });
        this.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Service 끝",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MyService.class);
                stopService(intent);
            }
        });

        this.noti_togg = (ToggleButton) findViewById(R.id.noti_togg);
        this.noti_togg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent it = new Intent(MainActivity.this, MyService.class);
                if(isChecked){
                    startService(it);
                    Toast.makeText(MainActivity.this,"notification on", Toast.LENGTH_SHORT).show();
                } else{
                    stopService(it);
                    Toast.makeText(MainActivity.this,"notification off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 접근성 권한이 없으면 접근성 권한 설정하는 다이얼로그 띄워주는 부분
        if(!checkAccessibilityPermissions()) {
            setAccessibilityPermissions();
        }

    }

    // 접근성 권한이 있는지 없는지 확인하는 부분
    // 있으면 true, 없으면 false
    public boolean checkAccessibilityPermissions() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);

        // getEnabledAccessibilityServiceList는 현재 접근성 권한을 가진 리스트를 가져오게 된다
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);

        for (int i = 0; i < list.size(); i++) {
            AccessibilityServiceInfo info = list.get(i);

            // 접근성 권한을 가진 앱의 패키지 네임과 패키지 네임이 같으면 현재앱이 접근성 권한을 가지고 있다고 판단함
            if (info.getResolveInfo().serviceInfo.packageName.equals(getApplication().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    // 접근성 설정화면으로 넘겨주는 부분
    public void setAccessibilityPermissions() {
        AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
        gsDialog.setTitle("접근성 권한 설정");
        gsDialog.setMessage("접근성 권한을 필요로 합니다");
        gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 설정화면으로 보내는 부분
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                return;
            }
        }).create().show();
    }


    public void add(View v){
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 + opd2));
    }

    public void sub(View v){ //hyueikeudabyeongsindeula
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 + opd2));
    }
    public void mul(View v){ //hyueikeudabyeongsindeula
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 + opd2));
    }
    public void div(View v){
        EditText opd1_txt = (EditText) findViewById(R.id.opd1);
        EditText opd2_txt = (EditText) findViewById(R.id.opd2);
        TextView res_txt = (TextView) findViewById(R.id.res);

        int opd1 = Integer.parseInt(opd1_txt.getText().toString());
        int opd2 = Integer.parseInt(opd2_txt.getText().toString());

        res_txt.setText(Integer.toString(opd1 / opd2));
    }
}
