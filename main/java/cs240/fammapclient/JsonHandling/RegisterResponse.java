package cs240.fammapclient.JsonHandling;

import cs240.fammapclient.JsonHandling.Response;

public class RegisterResponse extends Response {
    /** auth token */
    private String authToken;
    /** newly created username */
    private String userName;
    /** newly assigned personID */
    private String personID;

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return userName;
    }

    public String getPersonId() {
        return personID;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(authToken);
        sb.append(userName);
        sb.append(personID);
        return sb.toString();
    }
    /** Constructor
     *
     * @param authToken
     * @param userName
     * @param personID
     */

    public RegisterResponse(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
//    public RegisterResponse(){}
}
