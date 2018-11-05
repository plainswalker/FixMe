package com.aplusstory.fixme;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
//import android.support.v4.app.FragmentManager;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class ScheduleActivity extends AppCompatActivity implements ScheduleFragment.OnFragmentInteractionListener {
    Toolbar toolbar;
    private FragmentManager fm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_full_menu);

        if(this.fm == null){
            this.fm = this.getFragmentManager();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean rt = super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.add_schedule:
                if(this.fm != null && !this.fm.isDestroyed()){
                    Fragment schfrg = (Fragment) new ScheduleFragment();
                    FragmentTransaction ft = this.fm.beginTransaction();
                    ft.add(R.id.fragment_blank, schfrg);
//                    ft.addToBackStack(null);
                    ft.commit();
                }
                rt = true;
                break;
            case android.R.id.home:
                Toast.makeText(this, "Menu",Toast.LENGTH_SHORT).show();
                rt =  true;
                break;
            default:
                //error
        }

        return rt;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
}*/

}
