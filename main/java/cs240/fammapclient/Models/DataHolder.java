package cs240.fammapclient.Models;



public class DataHolder {
    private static DataHolder dh;
    private Event[] eventList;
    private Person[] personList;

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
    private DataHolder(){}
    public static DataHolder getInstance() {
        if(dh == null) {
            dh = new DataHolder();
        }
        return dh;
    }
}
