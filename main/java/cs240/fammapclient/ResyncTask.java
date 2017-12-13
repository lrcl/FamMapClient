package cs240.fammapclient;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import cs240.fammapclient.JsonHandling.AllEventsResponse;
import cs240.fammapclient.JsonHandling.AllPersonsResponse;
import cs240.fammapclient.Models.DataHolder;
import cs240.fammapclient.ServerConnection.Proxy;

public class ResyncTask extends AsyncTask<String, String, String>{
    private String loginResults;
    private String familyPersonData;
    private String familyEventData;
    private AllPersonsResponse apr;
    private AllEventsResponse aer;
    private JSONObject loginJO;
    private Context context;
    @Override
    protected String doInBackground(String... strings) {
        DataHolder dh = DataHolder.getInstance();
        dh.setOriginalEvent(null);
        dh.setEventList(null);
        dh.setPersonList(null);
        //create String[] with session info
        String[] sessionInfo = new String[] {dh.getHost(), dh.getPort(), dh.getUser(), dh.getPassword()};
        //get Data from Server
        Proxy proxy = new Proxy();
        loginResults = proxy.login(sessionInfo);
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

        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        if(!loginJO.has("authToken")) {
            //display error message in a toast
            String message = "unsuccessful resync";
            Toast toast1 = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast1.show();
        }
        else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("resyncTask", "resyncTask");
            context.startActivity(intent);
        }
    }
    public ResyncTask(Context context) {
        this.context = context;
    }

}
