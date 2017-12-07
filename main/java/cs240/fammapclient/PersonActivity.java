package cs240.fammapclient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Event;
import cs240.fammapclient.Models.Person;

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

    TextView lifeEventsTitle;

    List<Group> groups;
    Group group;

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
        lifeEventsTitle = (TextView) findViewById(R.id.lifeEventsTitle);

        rv = (RecyclerView) findViewById(R.id.events_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("personID") != null) {
            personID = bundle.getString("personID");
        }

        setUpEventList();

        adapter = new CustomAdapter(this.getApplicationContext(), groups);
        rv.setAdapter(adapter);

        //set up listeners
        setUpListeners();

    }
    public void setUpListeners() {
        adapter.setExpandCollapseListener(
                new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @Override
                    public void onParentExpanded(int parentPosition) {
                        expandGroup(parentPosition);
                    }

                    @Override
                    public void onParentCollapsed(int parentPosition) {
                        adapter.collapseParent(parentPosition);
                    }
                }
        );
    }
    public void expandGroup(int parentPosition) {
        adapter.collapseAllParents();
        adapter.expandParent(parentPosition);
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
            }
        }
        setUpGridLayout();
        //set up FAMILY LIST?

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
                eventItems.add(sb.toString());
            }

        }
        //put list of child and name of parent into group
        this.items = eventItems.toArray(new String[eventItems.size()]);
        group = new Group("LIFE EVENTS", items);
        ArrayList<Group> im = new ArrayList<Group>();
        im.add(group);
        groups = new ArrayList<Group>(im);

    }
    public class Group implements Parent<String> {
        String name;
        String[] values;
        public Group(String name, String[] values) {
            this.name = name;
            this.values = values;
        }
        @Override
        public List<String> getChildList() {
            return Arrays.asList(values);
        }
        @Override
        public boolean isInitiallyExpanded() {
            return true; //return false?
        }
    }
    class CustomAdapter extends ExpandableRecyclerAdapter<Group, String, GroupHolder, Holder> {
        private String[] items;
        private LayoutInflater inflater;

        public CustomAdapter(Context context, List<Group> groups) {
            super(groups);
            //this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GroupHolder onCreateParentViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.person_events_parent, viewGroup, false);
            return new GroupHolder(view);
        }
        @Override
        public Holder onCreateChildViewHolder(ViewGroup parent, int viewType) {

            view = inflater.inflate(R.layout.person_events_child, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolder holder, int i, Group group) {
            holder.bind(group);

        }

        @Override
        public void onBindChildViewHolder(@NonNull Holder holder, int i, int j, String item) {
            holder.bind(item);

        }

        @Override
        public int getItemCount() {
            return group.values.length;
        }
    }
    class GroupHolder extends ParentViewHolder {
        //private TextView lifeEventsTitle;
        private View view;
        public GroupHolder(View view) {
            super(view);
            this.view = view;
       //     parentTitle = (TextView) this.view.findViewById(R.id.lifeEventsTitle);
        }
        void bind(Group group) {
            String title = group.name;
            lifeEventsTitle.setText(title);
        }
    }
    class Holder extends ChildViewHolder implements View.OnClickListener {
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
//TO DO
//get the expand and collapse to work
//why is death event missing from list of events of person activity?
//figure out how to do two expandable recycler views