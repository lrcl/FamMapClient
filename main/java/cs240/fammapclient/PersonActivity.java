package cs240.fammapclient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Event;
import cs240.fammapclient.Models.Person;

public class PersonActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TextView firstline;
    private TextView secondline;
    private Adapter adapter;
    private Person[] personList;
    private Event[] eventList;
    DataHolder dh;
    String[] items;

    public PersonActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        firstline = (TextView) findViewById(R.id.firstline);
        secondline = (TextView) findViewById(R.id.secondline);

        rv = (RecyclerView) findViewById(R.id.events_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        setUpLists();


    }
    public void setUpLists() {
        String[] items = {"info1", "info2", "info3"};
        adapter = new Adapter(this,items);
    }
    class Adapter extends RecyclerView.Adapter<Holder> {
        private String[] items;
        private LayoutInflater inflater;

        public Adapter(Context context, String[] items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.person_rv_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            String item = items[position];
            holder.bind(item);

        }

        @Override
        public int getItemCount() {
            return items.length;
        }
    }
    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView firstrow;
        private TextView secondrow;
        private String item;
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(getApplicationContext(), "item clicked", Toast.LENGTH_LONG);
            toast.show();
        }

        public Holder(View view) {
            super(view);
            firstrow = (TextView) view.findViewById(R.id.firstline);
            secondrow = (TextView) view.findViewById(R.id.secondline);
            firstrow.setOnClickListener(this);
            secondrow.setOnClickListener(this);
        }
        public void bind(String item) {
            this.item = item;
            firstline.setText(item);
            secondline.setText(item);
        }
    }
}
