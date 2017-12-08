package cs240.fammapclient.Models;


import android.app.Activity;

import cs240.fammapclient.MainActivity;
import cs240.fammapclient.PersonActivity;

public class DataHolder {
    private static DataHolder dh;
    private Event[] eventList;
    private Person[] personList;
    MainActivity mainActivity;
    PersonActivity personActivity;

    public Event[] getEventList(){
        return eventList;
    }
    public void setEventList(Event[] eventList){
        this.eventList = eventList;
    }
    public Person[] getPersonList() {
        return personList;
    }
    public void setPersonList(Person[] personList) {
        this.personList = personList;
    }
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public MainActivity getMainActivity() {
        return this.mainActivity;
    }
    public void setPersonActivity(PersonActivity personActivity) {
        this.personActivity = personActivity;
    }
    public PersonActivity getPersonActivity() {
        return personActivity;
    }
    private DataHolder(){}
    public static DataHolder getInstance() {
        if(dh == null) {
            dh = new DataHolder();
        }
        return dh;
    }
}
