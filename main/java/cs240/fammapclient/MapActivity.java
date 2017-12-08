package cs240.fammapclient;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import cs240.fammapclient.Models.DataHolder;

public class MapActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.map_act_toolbar);
        setActionBar(toolbar);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFrag mapFragment = new MapFrag();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String eventID = "";
        String personID = "";
        if(b != null) {
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
        }
        transaction.replace(R.id.frag_container2, mapFragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               intent.putExtra("mapActivity", "mapActivity");
               startActivity(intent);
               return true;
        }
        return true;
    }
}
