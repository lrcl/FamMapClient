package cs240.fammapclient;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.Map;

import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Event;
import cs240.fammapclient.Models.Person;

public class MapFrag extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener{

    private GoogleMap mMap;
    DataHolder dh;
    Event[] eventList;
    Person[] personList;
    TextView eventInfo;
    ImageView image;
    View view;
    MapView mapView;
    String personID;

    boolean calledFromMapActivity;
    Event clickedEvent;
    boolean mapMarkerWasClicked = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map_frag, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        eventInfo = (TextView) view.findViewById(R.id.eventInfoDisplay);
        image = (ImageView) view.findViewById(R.id.mapEventDisplayIcon);
        image.setOnClickListener(this);
        eventInfo.setOnClickListener(this);
        //might have to override the lifecycle methods mapview.resume etc
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calledFromMapActivity = false;
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            calledFromMapActivity = true;
            if(bundle.getString("personID") != null) {
                this.personID = bundle.getString("personID");
            }
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.map_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize((getContext()));
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        loadEventsToMap();
        mMap.setOnMarkerClickListener(this);
        if(!calledFromMapActivity) {
            eventInfo.setText("Click on any pin");
        }
        if(calledFromMapActivity) {
            String eventID = "";
            String personID = "";
            Bundle bundle = this.getArguments();
            if(bundle != null) {
                if(bundle.getString("eventID") != null) {
                    eventID = bundle.getString("eventID");
                }
                if(bundle.getString("personID") != null) {
                    personID = bundle.getString("personID");
                }
            }
            clickedEvent = findEventInList(eventID);
            center(clickedEvent);
            Person currentPerson = getPersonById(personID);
            displayEventInfo(clickedEvent, currentPerson);
        }



    }
    public Event findEventInList(String eventID) {
        Event[] events = dh.getEventList();
        for(Event event: events) {
            if(event.getEventID().equals(eventID)) {
                return event;
            }
        }
        return null;
    }
    public void loadEventsToMap() {
        dh = DataHolder.getInstance();
        eventList = dh.getEventList();
        personList = dh.getPersonList();
        for(Event event: eventList) {
            LatLng event1 = new LatLng(event.getLatitude(),event.getLongitude());
            MarkerOptions options = new MarkerOptions()
                    .position(event1)
                    .title(event.getCity());
            if(event.getEventType().equals("birth")) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }
            else if(event.getEventType().equals("marriage")) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            }
            else if(event.getEventType().equals("death")) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }

            Marker marker = mMap.addMarker(options);
            marker.setTag(event.getEventID());
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                //start search activity
                return true;
            case R.id.filter:
                //start filter activity
                return true;
            case R.id.settings:
                //start settings activity
                return true;
        }
        return true;
    }
    public MapFrag() {}
    @Override
    public boolean onMarkerClick(Marker marker) {
        //match clicked event with event from model class
        String eventID = (String) marker.getTag();
        Event currentEvent = null;
        currentEvent = matchEventID(currentEvent, eventID);

        //retrieve person of event for display
        String gender = "";
        Person currentPerson = null;
        currentPerson = matchPersonID(currentEvent, currentPerson);
        gender = currentPerson.getGender();

        //display gender matched icon
        displayGenderIcon(gender);
        if(currentPerson != null) {
            personID = currentPerson.getPersonID();
            displayEventInfo(currentEvent,currentPerson);
        }
        mapMarkerWasClicked = true;

        return true;
    }
    public void displayEventInfo(Event currentEvent, Person currentPerson) {
        //display person first and last name
        //display event type, city, country, year
        StringBuilder sb = new StringBuilder();
        sb.append(currentPerson.getFirstName());
        sb.append(" ");
        sb.append(currentPerson.getLastName());
        sb.append('\n');
        sb.append(currentEvent.getEventType());
        sb.append(": ");
        sb.append(currentEvent.getCity());
        sb.append(", ");
        sb.append(currentEvent.getCountry());
        sb.append(" ");
        sb.append(currentEvent.getYear());
        eventInfo.setText(sb.toString());

        //optional functionality here
        center(currentEvent);

    }
    public void center(Event currentEvent) {
        if(currentEvent != null) {
            LatLng latLng = new LatLng(currentEvent.getLatitude(),currentEvent.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
             CameraUpdate update = CameraUpdateFactory.zoomTo(3);
             mMap.moveCamera(update);
        }



    }
    public Event matchEventID(Event currentEvent, String eventID) {
        int count = 0;
        for(Event event: eventList) {
            if(event.getEventID().equals(eventID)) {
                currentEvent = event;
                count++;
                break;
            }
        }
        return currentEvent;
    }
    public Person matchPersonID(Event currentEvent, Person currentPerson){
        int counter = 0;
        for(Person person: personList) {
            if(person.getPersonID().equals(currentEvent.getPersonID())) {
                currentPerson = person;
                counter++;
                break;
            }
            if(person.getFatherID() != null) {
                if (person.getFatherID().equals(currentEvent.getPersonID())) {
                    currentPerson = getPersonById(person.getFatherID());
                    counter++;
                    break;
                }
            }
            if(person.getMotherID() != null) {
                if (person.getMotherID().equals(currentEvent.getPersonID())) {
                    currentPerson = getPersonById(person.getMotherID());
                    counter++;
                    break;
                }
            }
            if(person.getSpouseID() != null) {
                if (person.getSpouseID().equals(currentEvent.getPersonID())) {
                    currentPerson = getPersonById(person.getSpouseID());
                    counter++;
                    break;
                }
            }
        }
        return currentPerson;
    }
    public void displayGenderIcon(String gender){
        Iconify.with(new FontAwesomeModule());
        if(gender.equals("f")) {
            Drawable icon = new IconDrawable(this.getActivity(), FontAwesomeIcons.fa_female)
                    .colorRes(R.color.red)
                    .sizeDp(30);
            image.setImageDrawable(icon);
        }
        if(gender.equals("m")) {
            Drawable icon = new IconDrawable(this.getActivity(), FontAwesomeIcons.fa_male)
                    .colorRes(R.color.blue)
                    .sizeDp(30);
            image.setImageDrawable(icon);
        }
    }
    public Person getPersonById(String Id) {
        dh = DataHolder.getInstance();
        Person[] people = dh.getPersonList();
        for(Person person: people) {
            if(person.getPersonID().equals(Id)) {
                return person;
            }
        }
        return null;
    }
    @Override
    public void onClick(View v) {
        //MapActivity mainActivity = (MainActivity) getActivity();
        startPersonActivity();
    }
    public void startPersonActivity() {
        Intent i = new Intent(getActivity().getApplicationContext(), PersonActivity.class);
        if(!mapMarkerWasClicked) {
           // Person person = matchPersonID()
            Bundle b = getArguments();
            if(b != null) {
                if(b.getString("personID") != null) {
                    personID = b.getString("personID");
                }
            }
        }
        i.putExtra("personID", personID);
        startActivity(i);

    }
}
