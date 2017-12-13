package cs240.fammapclient.Models;


import android.app.Activity;

import cs240.fammapclient.MainActivity;
import cs240.fammapclient.PersonActivity;

public class DataHolder {
    private static DataHolder dh;
    private Event[] eventList;
    private Person[] personList;
    private MainActivity mainActivity;
    private PersonActivity personActivity;
    private Event originalEvent;
    private String host;
    private String port;
    private String user;
    private String password;


    public Event[] getEventList(){
        return eventList;
    }
    public Person[] getPersonList() {
        return personList;
    }
    public MainActivity getMainActivity() {
        return this.mainActivity;
    }
    public Event getOriginalEvent() {
        return this.originalEvent;
    }
    public PersonActivity getPersonActivity() {
        return personActivity;
    }
    public String getHost() {
        return this.host;
    }
    public String getPort() {
        return this.port;
    }
    public String getUser() {
        return this.user;
    }
    public String getPassword() {
        return this.password;
    }

    public void setEventList(Event[] eventList){
        this.eventList = eventList;
    }
    public void setPersonList(Person[] personList) {
        this.personList = personList;
    }
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public void setPersonActivity(PersonActivity personActivity) {
        this.personActivity = personActivity;
    }
    public void setOriginalEvent(Event event) {
        this.originalEvent = event;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public void setUser(String username) {
        this.user = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    private DataHolder(){}
    public static DataHolder getInstance() {
        if(dh == null) {
            dh = new DataHolder();
        }
        return dh;
    }
}
