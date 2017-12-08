package cs240.fammapclient;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
       // ActionBar ab = getActionBar();
       // ab.setDisplayHomeAsUpEnabled(true);
        //add mapFragment
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFrag mapFragment = new MapFrag();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String eventID = "";
        String personID = "";
        if(b.getString("eventID") != null) {
            eventID = b.getString("eventID");
        }
        if(b.getString("personID") != null) {
            personID = b.getString("personID");
        }
        Bundle bundle = new Bundle();
        bundle.putString("eventID", eventID);
        bundle.putString("personID", personID);
        mapFragment.setArguments(bundle);
        transaction.replace(R.id.frag_container2, mapFragment);
        transaction.commit();
    }

}
