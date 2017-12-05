package cs240.fammapclient.JsonHandling;

import cs240.fammapclient.Models.Person;

public class AllPersonsResponse {

    /** contains an array  of Persons */
    private Person[] data;

    /** return the array  of Persons */

    public Person[] getData() {
        return data;
    }
    /** constructor
     * @param data */

    public AllPersonsResponse(Person[] data) {
        this.data = data;
    }

}

