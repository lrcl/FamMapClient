package cs240.fammapclient.ServerConnection;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cs240.fammapclient.JsonHandling.RegisterRequest;
import cs240.fammapclient.JsonHandling.RegisterResponse;
import cs240.fammapclient.JsonHandling.Request;
import cs240.fammapclient.JsonHandling.LoginRequest;
import cs240.fammapclient.Models.Event;


public class Proxy {
    String registerRequest;
    RegisterResponse allPersons;
    String PROTOCOL = "http://";
    public String login(String[] strings){
        try{
            String host = strings[0];
            String port = strings[1];
            String userName = strings[2];
            String password = strings[3];
            String loginUrl = PROTOCOL + host + ":" + port + "/user/login";
            URL url = new URL("http://10.0.2.2:8888/user/login"); //changed localhost to 10.0.2.2/NOTE, NOT USING INPUT VARIABLES FOR URL
            String requestMethod = "GET";
            Request loginRequest = new LoginRequest(userName, password);
            HttpClient client = new HttpClient();
            String loginResponse = client.sendRequest(loginRequest,requestMethod,url,"");
            Gson gson = new Gson();
           // String jsonString = gson.toJson(loginResponse);
            return loginResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String register(String[] registerInfo) {
        try{
            Gson gson = new Gson();
            String host = registerInfo[0];
            String port = registerInfo[1];
            String userName = registerInfo[2];
            String password = registerInfo[3];
            String firstName = registerInfo[4];
            String lastName = registerInfo[5];
            String email = registerInfo[6];
            String gender = registerInfo[7];
//            String username, String password, String email, String firstname, String lastname, String gender
            String registerUrl = PROTOCOL + host + ":" + port + "/user/register";
            URL url = new URL("http://10.0.2.2:8888/user/register"); //changed localhost to 10.0.2.2 /NOTE: NOT USING INPUT VARIABLES FOR URL
            String requestMethod = "GET";
            HttpClient client = new HttpClient();
            Request registerRequest = new RegisterRequest(userName, password, email, firstName, lastName, gender);
            String response = client.sendRequest(registerRequest, requestMethod, url, "");
          //  String jsonString = gson.toJson(response);
            return response;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String findPersons(JSONObject registerResultsJO) {
        try{
            Gson gson = new Gson();
            String authToken = registerResultsJO.getString("authToken");
            URL url = new URL("http://10.0.2.2:8888/person/"); //changed localhost to 10.0.2.2 /NOTE: NOT USING INPUT VARIABLES FOR URL
            Request blankRequestBody = new Request(); //?
            String requestMethod = "GET";
            HttpClient client = new HttpClient();
            String response = client.sendRequest(blankRequestBody, requestMethod, url, authToken);
           // String jsonString = gson.toJson(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String parseAuthToken(String info) {
        //18-24
        StringBuilder sb = new StringBuilder();
        sb.append(info.charAt(18));
        sb.append(info.charAt(19));
        sb.append(info.charAt(20));
        sb.append(info.charAt(21));
        sb.append(info.charAt(22));
        sb.append(info.charAt(23));
        sb.append(info.charAt(24));

        return sb.toString();
    }

    public String findEvents(JSONObject registerResultsJO) {

        try {
            Gson gson = new Gson();
            String authToken = registerResultsJO.getString("authToken");
            URL url = new URL("http://10.0.2.2:8888/event/"); //changed localhost to 10.0.2.2 /NOTE: NOT USING INPUT VARIABLES FOR URL
            Request blankRequestBody = new Request(); //?
            String requestMethod = "GET";
            HttpClient client = new HttpClient();
            String response = client.sendRequest(blankRequestBody, requestMethod, url, authToken);
           // String jsonString = gson.toJson(response);
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //public Event[] getEventsByPersonID() {
      //  try{
        //    Gson gson = new Gson();

        //}
    //}
}

