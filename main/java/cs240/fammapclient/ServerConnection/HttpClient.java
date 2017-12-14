package cs240.fammapclient.ServerConnection;



import java.io.BufferedWriter;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.*;

import cs240.fammapclient.JsonHandling.Request;

public class HttpClient{
    //sendGetRequest--sendPostRequest
    //sendDeleteRequest
    public String sendRequest(Request request, String requestMethod, URL url, String authToken) {
        try {
            //set URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(requestMethod);
            if(!authToken.equals("")) {
                connection.addRequestProperty("Authorization",authToken);
            }
            //include JSON in body of request
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            Gson gson = new Gson();
            String jsonStr = gson.toJson(request);
            out.write(jsonStr);
            //send request body
            out.flush();
            out.close();
            //read response from server
            Scanner in = new Scanner(connection.getInputStream());
            StringBuilder sb = new StringBuilder();
            while(in.hasNext()) {
                sb.append(in.next());
            }
            String responseString = sb.toString();
            return responseString;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
