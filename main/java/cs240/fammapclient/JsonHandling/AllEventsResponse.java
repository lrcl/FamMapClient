package cs240.fammapclient.JsonHandling;


import cs240.fammapclient.Models.Event;

public class AllEventsResponse {
    /**
     * an array list of Event objects
     */
    private Event[] data;

    /**
     * get allEvents
     *@return allEvents array list
     */
    public Event[] getData() {
        return this.data;
    }
    /**
     * constructor
     * @param data
     */
    public AllEventsResponse(Event[] data) {
        this.data = data;

    }

}

