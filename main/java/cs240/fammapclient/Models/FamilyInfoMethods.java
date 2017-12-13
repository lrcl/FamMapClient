package cs240.fammapclient.Models;

//separate class for testing

public class FamilyInfoMethods {


    public Event findEventInList(String eventID, Event[] events) {

        for(Event event: events) {
            if(event.getEventID().equals(eventID)) {
                return event;
            }
        }
        return null;
    }
    public Event matchEventID(Event currentEvent, String eventID, Event[] eventList) {
        int count = 0;
        for(Event event: eventList) {
            if(event.getEventID().equals(eventID)) {
                currentEvent = event;
                count++;
                return currentEvent;
            }
        }
        return null;
    }
    public Person matchFamilyMember(Event currentEvent, Person currentPerson, Person[] personList){
        int counter = 0;
        for(Person person: personList) {
            if(person.getPersonID().equals(currentEvent.getPersonID())) {
                currentPerson = person;
                counter++;
                return currentPerson;

            }
            if(person.getFatherID() != null) {
                if (person.getFatherID().equals(currentEvent.getPersonID())) {
                    currentPerson = getPersonById(person.getFatherID(), personList);
                    counter++;
                    return currentPerson;

                }
            }
            if(person.getMotherID() != null) {
                if (person.getMotherID().equals(currentEvent.getPersonID())) {
                    currentPerson = getPersonById(person.getMotherID(), personList);
                    counter++;
                    return currentPerson;

                }
            }
            if(person.getSpouseID() != null) {
                if (person.getSpouseID().equals(currentEvent.getPersonID())) {
                    currentPerson = getPersonById(person.getSpouseID(), personList);
                    counter++;
                    return currentPerson;
                }
            }
        }
        return null;
    }
    public Person getPersonById(String Id, Person[] people) {
        for(Person person: people) {
            if(person.getPersonID().equals(Id)) {
                return person;
            }
        }
        return null;
    }
    //constructor
    public FamilyInfoMethods() {
    }
}
