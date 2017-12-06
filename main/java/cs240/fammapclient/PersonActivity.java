package cs240.fammapclient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;

import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Event;
import cs240.fammapclient.Models.Person;

public class PersonActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TextView firstline;
    private ImageView listIcon;
    // private TextView secondline;
    private CustomAdapter adapter;
    private Person[] personList;
    private Event[] eventList;
    private Holder holder;
    DataHolder dh;
    String[] items;
    View view;
    String personID;

    public PersonActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        firstline = (TextView) findViewById(R.id.firstline);
        // secondline = (TextView) findViewById(R.id.secondline);
        listIcon = (ImageView) findViewById(R.id.person_rv_icon);

        rv = (RecyclerView) findViewById(R.id.events_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //set up lists here
        setUpLists();
        //this.items = new String[]{"info1" + "\n" + "secondrow", "info2", "info3"};
        adapter = new CustomAdapter(this, items);
        rv.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("personID") != null) {
            personID = bundle.getString("personID");
        }
        // setUpLists();


    }

    public void setUpLists() {
        dh = DataHolder.getInstance();
        eventList = dh.getEventList();
        personList = dh.getPersonList();
        String firstName = "";
        String lastName = "";
        for(Person person: personList) {
            if(person.getPersonID().equals(personID)){
                firstName = person.getFirstName();
                lastName = person.getLastName();
            }
        }
        ArrayList<String> eventItems = new ArrayList<String>();
        for(Event event: eventList) {
         //   if(event.getPersonID().equals(personID)) {
                StringBuilder sb = new StringBuilder();
                sb.append(event.getEventType());
                sb.append(": ");
                sb.append(event.getCity());
                sb.append(", ");
                sb.append(event.getCountry());
                sb.append("(");
                sb.append(event.getYear());
                sb.append(")");
                sb.append("\n");
                sb.append(firstName);
                sb.append(" ");
                sb.append(lastName);
                eventItems.add(sb.toString());
           // }

        }
        this.items = eventItems.toArray(new String[eventItems.size()]);

    }

    class CustomAdapter extends RecyclerView.Adapter<Holder> {
        private String[] items;
        private LayoutInflater inflater;

        public CustomAdapter(Context context, String[] items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = inflater.inflate(R.layout.person_rv_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            String item = items[position];
            if (holder == null) {
                holder = new Holder(view);
            }
            //if female, pass in "f"
            //if male, pass in "m"
            //if "e" it's neither, so use a pin as icon
            String gender = "f";
            holder.bind(item, gender);

        }

        @Override
        public int getItemCount() {
            return items.length;
        }
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView firstrow;
        private ImageView icon;
        private String item;

        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(getApplicationContext(), "item clicked", Toast.LENGTH_LONG);
            toast.show();
        }

        public Holder(View view) {
            super(view);
            firstrow = (TextView) view.findViewById(R.id.firstline);
            icon = (ImageView) view.findViewById(R.id.person_rv_icon);
            // secondrow = (TextView) view.findViewById(R.id.secondline);
            firstrow.setOnClickListener(this);
            //  secondrow.setOnClickListener(this);
        }

        public void bind(String item, String gender) {
            this.item = item;
            firstrow.setText(item);
            setIcon(gender);

        }
        public void setIcon(String gender) {
            Iconify.with(new FontAwesomeModule());
            if (gender.equals("f")) {
                Drawable mIcon = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_female)
                        .colorRes(R.color.red)
                        .sizeDp(20);
                icon.setImageDrawable(mIcon);
            }
            if (gender.equals("m")) {
                Drawable mIcon = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_male)
                        .colorRes(R.color.blue)
                        .sizeDp(20);
                icon.setImageDrawable(mIcon);


                // secondrow.setText(item);
            }
        }
    }
}
