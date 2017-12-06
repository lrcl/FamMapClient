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

import org.w3c.dom.Text;

import java.util.ArrayList;

import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Event;
import cs240.fammapclient.Models.Person;
import cs240.fammapclient.ServerConnection.Proxy;

public class PersonActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TextView firstline;
    private ImageView listIcon;
    private CustomAdapter adapter;
    private Person[] personList;
    private Event[] eventList;
    private Holder holder;
    DataHolder dh;
    String[] items;
    View view;
    String personID;
    String type;

    TextView firstName;
    TextView lastName;
    TextView gender;

    String clickedPersonfname;
    String clickedPersonlname;
    String clickedPersonGender;

    public PersonActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        firstline = (TextView) findViewById(R.id.firstline);
        listIcon = (ImageView) findViewById(R.id.person_rv_icon);
        firstName = (TextView) findViewById(R.id.personActFname);
        lastName = (TextView) findViewById(R.id.personActLname);
        gender = (TextView) findViewById(R.id.personActGender);

        rv = (RecyclerView) findViewById(R.id.events_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("personID") != null) {
            personID = bundle.getString("personID");
        }
        setUpEventList();
        //this.items = new String[]{"info1" + "\n" + "secondrow", "info2", "info3"};
        adapter = new CustomAdapter(this, items);
        rv.setAdapter(adapter);

        // setUpLists();


    }
    public void setUpGridLayout() {
        firstName.setText(clickedPersonfname);
        lastName.setText(clickedPersonlname);
        if(clickedPersonGender.equals("f")) {
            clickedPersonGender = "Female";
        }
        if(clickedPersonGender.equals("m")) {
            clickedPersonGender = "Male";
        }
        gender.setText(clickedPersonGender);

    }

    public void setUpEventList() {
        dh = DataHolder.getInstance();
        eventList = dh.getEventList();
        personList = dh.getPersonList();
        String firstName = "";
        String lastName = "";
        type = "e";
        for(Person person: personList) {
            if(person.getPersonID().equals(this.personID)){
                clickedPersonfname = person.getFirstName();
                clickedPersonlname = person.getLastName();
                clickedPersonGender = person.getGender();
            }
        }
        setUpGridLayout();
        //set up FAMILY LIST

        ArrayList<String> eventItems = new ArrayList<String>();
        for(Event event: eventList) {
            if(event.getPersonID().equals(personID)) {
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
            }

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
            holder.bind(item);

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
            firstrow.setOnClickListener(this);
        }

        public void bind(String item) {
            this.item = item;
            firstrow.setText(item);
            setIcon(type);

        }
        public void setIcon(String type) {
            Iconify.with(new FontAwesomeModule());
            if (type.equals("f")) {
                Drawable mIcon = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_female)
                        .colorRes(R.color.red)
                        .sizeDp(20);
                icon.setImageDrawable(mIcon);
            }
            if (type.equals("m")) {
                Drawable mIcon = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_male)
                        .colorRes(R.color.blue)
                        .sizeDp(20);
                icon.setImageDrawable(mIcon);
            }
            if(type.equals("e")) {
                Drawable mIcon = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_map_marker)
                        .colorRes(R.color.orange)
                        .sizeDp(20);
                icon.setImageDrawable(mIcon);
            }
        }
    }
}
