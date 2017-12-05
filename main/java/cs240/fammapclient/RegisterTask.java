package cs240.fammapclient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cs240.fammapclient.JsonHandling.AllEventsResponse;
import cs240.fammapclient.JsonHandling.AllPersonsResponse;
import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.Models.Person;
import cs240.fammapclient.ServerConnection.Proxy;

class RegisterTask extends AsyncTask<String, String, String> {
    public String registerResults;
    public Context context;
    public String familyPersonData;
    public String familyEventData;
    public AllPersonsResponse apr;
    public AllEventsResponse aer;
    public Activity mainActivity;

    public JSONObject registerResultsJO;
    //    public TaskDataTransfer dataTransfer;
    @Override
    protected String doInBackground(String... strings) {

        //REGISTER USER
        Proxy proxy = new Proxy();
        String firstLast = "";

        registerResults = proxy.register(strings);
        StringBuilder sb = new StringBuilder(registerResults);
        registerResults = sb.toString();
        if(registerResults.equals("{\"null\"}")) {
            return "";
        }
        if(registerResults == null) {
            return "";
        }
        try {
            registerResultsJO = new JSONObject(registerResults);
            if(registerResultsJO.has("message")) {
                return "";
            }

            //GET FAMILY DATA: EVENTS AND PERSONS
            familyPersonData = proxy.findPersons(registerResultsJO);
            familyEventData = proxy.findEvents(registerResultsJO);
            Gson gson = new Gson();
            apr = gson.fromJson(familyPersonData, AllPersonsResponse.class);
            aer = gson.fromJson(familyEventData, AllEventsResponse.class);
            //store information from database in model classes
            DataHolder dh = DataHolder.getInstance();
            dh.setPersonList(apr.getData());
            dh.setEventList(aer.getData());



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //get user for displaying toast
        Person[] personList = apr.getData();
        Person person = personList[0];
        String userFname = person.getFirstName();
        String userLname = person.getLastName();

        StringBuilder sb2 = new StringBuilder();
        sb2.append(userFname);
        sb2.append(" ");
        sb2.append(userLname);
        firstLast = sb2.toString();
        return firstLast;
    }

    @Override
    protected void onPostExecute(String firstLast) {
        if(!registerResultsJO.has("authToken")) {
            //display error message in a toast
            String message = "unable to register";
            Toast toast1 = Toast.makeText(context,message, Toast.LENGTH_LONG);
            toast1.show();
        }
        else {
            //display logged in user's first and last name
            String displayString = "Registered New User" + " "  + firstLast;
            Toast toast2 = Toast.makeText(context, displayString,Toast.LENGTH_LONG );
            toast2.show();
            //start map fragment
            ((MainActivity) mainActivity).startMapFragment();
        }
    }
    public RegisterTask(Context context, Activity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }
}