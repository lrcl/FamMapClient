package cs240.fammapclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    public void startMainActivityFragment() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("searchActivity", "searchActivity");
        startActivity(intent);
    }
}
