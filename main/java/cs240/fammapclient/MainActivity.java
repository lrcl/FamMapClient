package cs240.fammapclient;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cs240.fammapclient.Models.DataHolder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder dh = DataHolder.getInstance();
        dh.setMainActivity(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            if(bundle.getString("personActivity") != null) {
                startMapFragment();
            }
            if(bundle.getString("mapActivity") != null) {
                startMapFragment();
            }
            if(bundle.getString("settingsActivity") != null) {
                startMapFragment();
            }
            if(bundle.get("filterActivity") != null) {
                startMapFragment();
            }
            if(bundle.get("searchActivity") != null) {
                startMapFragment();
            }
        }
        else {
         startLoginFragment();
        }
    }
    public void startMapFragment() {
        //add mapFragment
        Bundle bundle = new Bundle();
        bundle.putString("mainActivity", "mainActivity");
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFrag mapFragment = new MapFrag();
        mapFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.commit();
    }
    public void startLoginFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
//        Bundle bundle = getIntent().getExtras();
//        if(bundle != null) {
//            bundle.clear();
//            loginFragment.setArguments(bundle);
//        }
        fragmentTransaction.add(R.id.fragment_container, loginFragment);
        fragmentTransaction.commit();
    }
}
