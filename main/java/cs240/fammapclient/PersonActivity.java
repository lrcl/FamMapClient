package cs240.fammapclient;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Event;
import cs240.fammapclient.Models.Person;
import cs240.fammapclient.ServerConnection.Proxy;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rv;
    private RecyclerView rv2;
    private TextView firstline;
    private ImageView listIcon;
    private CustomAdapter adapter;
    private CustomAdapter adapter2;
    private Person[] personList;
    private Person[] personList2;
    private Event[] eventList;
    private Holder holder;
    DataHolder dh;
    DataHolder dh2;
    String[] items;
    String[] familyArray;
    View view;
    String personID;
    String type;

    TextView m1;
    TextView m2;
    TextView m3;
    TextView m4;

    TextView firstName;
    TextView lastName;
    TextView gender;

    String clickedPersonfname;
    String clickedPersonlname;
    String clickedPersonGender;

    ArrayList<Event> displayedEvents = new ArrayList<Event>();

    Person clickedPerson;

    public PersonActivity() {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_act_toolbar);
        setActionBar(toolbar);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firstline = (TextView) findViewById(R.id.firstline);
        listIcon = (ImageView) findViewById(R.id.person_rv_icon);
        firstName = (TextView) findViewById(R.id.personActFname);
        lastName = (TextView) findViewById(R.id.personActLname);
        gender = (TextView) findViewById(R.id.personActGender);
        setUpList();

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

        setUpFamilyMemberList();
    }


    public void setUpFamilyMemberList() {
        dh2 = DataHolder.getInstance();
        personList2 = dh2.getPersonList();
        Person clickedPerson = null;
        Person child = null;
        //find clicked person and his/her child
        for(Person person: personList2) {
            if(person.getPersonID().equals(personID)){
                clickedPerson = person;
            }
            if(person.getFatherID() !=  null) {
                if(person.getFatherID().equals(personID)) {
                    child = person;
                }
            }
            if(person.getMotherID() != null) {
                if(person.getMotherID().equals(personID)) {
                    child = person;
                }
            }
        }
        //get father, mother, and spouse ids of clicked person
        String fatherID = "";
        String motherID = "";
        String spouseID = "";
        if(clickedPerson.getFatherID() != null) {
            fatherID = clickedPerson.getFatherID();
        }
        if(clickedPerson.getMotherID() != null) {
            motherID = clickedPerson.getMotherID();
        }
        if(clickedPerson.getSpouseID() != null) {
            spouseID = clickedPerson.getSpouseID();
        }
        //get father mother and spouse out of list
        Person father = getPersonFromList(fatherID);
        Person mother = getPersonFromList(motherID);
        Person spouse = getPersonFromList(spouseID);

        //add all to familyArray
        Person[] relatives = {father, mother};
        addFamilyToList(relatives, spouse, child);

    }
    public void addFamilyToList(Person[] relatives, Person spouse, Person child) {
        ArrayList<String> familyList = new ArrayList<String>();

        String first = "";
        String last = "";
        String type = "";
        for(Person person: relatives) {
            StringBuilder sb = new StringBuilder();
            if(person != null) {
                first = person.getFirstName();
                last = person.getLastName();
                if(person.getGender().equals("f")) {
                    type = "Mother";
                }
                if(person.getGender().equals("m")) {
                    type = "Father";
                }
                sb.append(first);
                sb.append(" ");
                sb.append(last);
                sb.append("\n");
                sb.append(type);
                sb.append(" ");
                sb.append("Id: ");
                sb.append(person.getPersonID());
                familyList.add(sb.toString());
            }
        }
        if(familyList.size() == 2) {
            m1.setText(familyList.get(0));
            m2.setText(familyList.get(1));
        }
        if(familyList.size() == 1) {
            m1.setText(familyList.get(0));
        }
        if(spouse != null) {
            StringBuilder sb = new StringBuilder();
            first = spouse.getFirstName();
            last = spouse.getLastName();
            type = "Spouse";
            sb.append(first);
            sb.append(" ");
            sb.append(last);
            sb.append("\n");
            sb.append(type);
            sb.append(" ");
            sb.append("Id: ");
            sb.append(spouse.getPersonID());
            if(familyList.size() == 2) {
                m3.setText(sb.toString());
            }
            if(familyList.size() == 1) {
                m2.setText(sb.toString());
            }
            if(familyList.size() == 0) {
                m1.setText(sb.toString());
            }
            familyList.add(sb.toString());
        }
        if(child != null) {
            StringBuilder sb = new StringBuilder();
            first = child.getFirstName();
            last = child.getLastName();
            type = "Child";
            sb.append(first);
            sb.append(" ");
            sb.append(last);
            sb.append("\n");
            sb.append(type);
            sb.append(" ");
            sb.append("Id: ");
            sb.append(child.getPersonID());
            if(familyList.size() == 3) {
                m4.setText(sb.toString());
            }
            if(familyList.size() == 2) {
                m3.setText(sb.toString());
            }
            if(familyList.size() == 1) {
                m2.setText(sb.toString());
            }
            if(familyList.size() == 0) {
                m1.setText(sb.toString());
            }
            familyList.add(sb.toString());
        }
        //convert to array
     //   this.familyArray = familyList.toArray(new String[familyList.size()]);


    }
    public void setUpList() {
        m1 = (TextView) findViewById(R.id.member1);
        m2 = (TextView) findViewById(R.id.member2);
        m3 = (TextView) findViewById(R.id.member3);
        m4 = (TextView) findViewById(R.id.member4);

        m1.setOnClickListener(this);
        m2.setOnClickListener(this);
        m3.setOnClickListener(this);
        m4.setOnClickListener(this);


    }
    public void displayFamilyMembers() {
        int size = familyArray.length;
        if(size == 4) {
            m1.setText(familyArray[0]);
            m2.setText(familyArray[1]);
            m3.setText(familyArray[2]);
            m4.setText(familyArray[3]);
        }
        if(size == 3) {
            m1.setText(familyArray[0]);
            m2.setText(familyArray[1]);
            m3.setText(familyArray[2]);
        }
        if(size == 2) {
            m1.setText(familyArray[0]);
            m2.setText(familyArray[1]);
        }
        if(size == 1) {
            m1.setText(familyArray[0]);
        }
    }
    public Person getPersonFromList(String Id) {
        for(Person person: personList2) {
            if(person.getPersonID().equals(Id)) {
                return person;
            }
        }
        return null;
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
        type = "e";
        for(Person person: personList) {
            if(person.getPersonID().equals(this.personID)){
                clickedPersonfname = person.getFirstName();
                clickedPersonlname = person.getLastName();
                clickedPersonGender = person.getGender();
                clickedPerson = person;
            }
        }
        setUpGridLayout();

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
                sb.append(clickedPersonfname);
                sb.append(" ");
                sb.append(clickedPersonlname);
                sb.append(" ");
                sb.append("eID: ");
                sb.append(event.getEventID());
                eventItems.add(sb.toString());
                displayedEvents.add(event);
            }

        }
        this.items = eventItems.toArray(new String[eventItems.size()]);

    }

    @Override
    public void onClick(View v) {
        //get personID pass it to intent have the intent start a new person activity
        CharSequence info = null;
        if(v.getId() == m1.getId()) {
            info = m1.getText();
        }
        if(v.getId() == m2.getId()) {
            info = m2.getText();
        }
        if(v.getId() == m3.getId()) {
            info = m3.getText();
        }
        if(v.getId() == m4.getId()) {
            info = m4.getText();
        }
        ArrayList<String> parsedInfo = parsePersonInfo(info);
        String personID = parsedInfo.get(parsedInfo.size()-1);

        //start new person activity with chosen family member's personID
        Intent i = new Intent(getApplicationContext(), PersonActivity.class);
        i.putExtra("personID", personID);
        startActivity(i);
    }
    public ArrayList<String> parsePersonInfo(CharSequence info) {
        StringBuilder sb = new StringBuilder();
        String personInfo = info.toString();
        char[] pInfo = personInfo.toCharArray();
        ArrayList<String> parsedPersonInfo = new ArrayList<>();
        for(char c: pInfo) {
            if(c != ' ') {
                sb.append(c);
            }
            if(c == ' ') {
                parsedPersonInfo.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        parsedPersonInfo.add(sb.toString());

        return parsedPersonInfo;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                DataHolder dh = DataHolder.getInstance();
                dh.setPersonActivity(this);
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                MapFrag mapFragment = new MapFrag();
//                transaction.replace(R.id.frag_container3, mapFragment);
//                transaction.commit();
                PersonActivity personActivity = dh.getPersonActivity();
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
                return true;
        }
        return true;
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
            ArrayList<String> events= matchClickedEventID();
            String chosenEventID = events.get(events.size()-1);
            Intent i = new Intent(getApplicationContext(), MapActivity.class);
            i.putExtra("eventID", chosenEventID);
            i.putExtra("personID", clickedPerson.getPersonID());
            startActivity(i);

        }
        public ArrayList<String> matchClickedEventID() {

            CharSequence info = firstrow.getText();
            String eventInfo = info.toString();
            StringBuilder sb = new StringBuilder();
            char[] eInfo = eventInfo.toCharArray();
            ArrayList<String> parsedEventInfo = new ArrayList<>();
            for(char c: eInfo) {
                if(c != ' ') {
                    sb.append(c);
                }
                if(c == ' ') {
                    parsedEventInfo.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
            parsedEventInfo.add(sb.toString());


            return parsedEventInfo;
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
