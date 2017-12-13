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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logoutLine1 = findViewById(R.id.logout_line1);
        logoutLine1.setOnClickListener(this);

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
}
