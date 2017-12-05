package cs240.fammapclient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import cs240.fammapclient.JsonHandling.AllEventsResponse;
import cs240.fammapclient.JsonHandling.AllPersonsResponse;
import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.ServerConnection.Proxy;

public class LoginTask extends AsyncTask<String, String, String> {

    String loginResults;
    Context context;
    String familyPersonData;
    String familyEventData;
    public AllPersonsResponse apr;
    public AllEventsResponse aer;
    public Activity mainActivity;

    DataHolder dh;

    private JSONObject loginJO;
    @Override
    protected String doInBackground(String... strings) {
        String firstLast = "";
        Proxy proxy = new Proxy();
        loginResults = proxy.login(strings);
        if(loginResults.equals("null")) {
            return "";
        }
        if(loginResults == null) {
            return "";
        }
        try {
            loginJO = new JSONObject(loginResults);
            if(loginJO.has("message")) {
                return "";
            }
            //GET FAMILY DATA: EVENTS AND PERSONS
            familyPersonData = proxy.findPersons(loginJO);
            familyEventData = proxy.findEvents(loginJO);
            Gson gson = new Gson();
            apr = gson.fromJson(familyPersonData, AllPersonsResponse.class);
            aer = gson.fromJson(familyEventData, AllEventsResponse.class);
            //store information from database in model classes
            dh = DataHolder.getInstance();
            dh.setPersonList(apr.getData());
            dh.setEventList(aer.getData());
        }   catch(Exception e) {
            e.printStackTrace();
            return "";
        }

        //

        StringBuilder sb = new StringBuilder();
        sb.append(strings[4]);
        sb.append(" ");
        sb.append(strings[5]);
        firstLast = sb.toString();
        firstLast = firstLast.toUpperCase();
        return firstLast;
    }

    @Override
    protected void onPostExecute(String firstLast) {
        if(!loginJO.has("authToken")) {
            //display error message in a toast
            String message = "unsuccessful login";
            Toast toast1 = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast1.show();
        }
        else {
            //display logged in user's first and last name
            String displayString = "Logged in User " + firstLast;
            Toast toast2 = Toast.makeText(context, displayString,Toast.LENGTH_LONG );
            toast2.show();
            ((MainActivity) mainActivity).startMapFragment();
        }
    }
    public LoginTask(Context context, Activity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;

    }
}