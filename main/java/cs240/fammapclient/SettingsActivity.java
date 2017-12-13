package cs240.fammapclient;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import cs240.fammapclient.Models.DataHolder;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView logoutLine1;
    private TextView familyTree1;
    private TextView familyTree2;
    private TextView spouseLine1;
    private TextView spouseLine2;
    private TextView mapType1;
    private TextView mapType2;
    private TextView resync1;
    private TextView resync2;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setWidgets();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                startMainActivityFragment();
                return true;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.logout_line1:
                DataHolder dh = DataHolder.getInstance();
                dh.setOriginalEvent(null);
                startMainActivityLogin();
            case R.id.resyncLine1:
                DataHolder dh2 = DataHolder.getInstance();
                dh2.setOriginalEvent(null);
                //start resyncTask
                ResyncTask resyncTask = new ResyncTask(getApplicationContext());
                resyncTask.execute();
        }
    }
    public void startMainActivityLogin() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void startMainActivityFragment() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("settingsActivity", "settingsActivity");
        startActivity(intent);

    }
    public void setWidgets() {
        logoutLine1 = findViewById(R.id.logout_line1);
        logoutLine1.setOnClickListener(this);

        familyTree1 = findViewById(R.id.familyTreeLine1);
        familyTree2 = findViewById(R.id.familyTreeLine2);

        spouseLine1 = findViewById(R.id.spouseLine1);
        spouseLine2 = findViewById(R.id.spouseLine2);

        mapType1 = findViewById(R.id.mapTypeLine1);
        mapType2 = findViewById(R.id.mapTypeLine2);

        resync1 = findViewById(R.id.resyncLine1);
        resync2 = findViewById(R.id.resyncLine2);
        resync1.setOnClickListener(this);
        resync2.setOnClickListener(this);



    }
}
