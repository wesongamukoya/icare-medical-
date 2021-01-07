package com.mukoya.icare;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class PlaceTask extends AsyncTask<String,Integer,String> {
     @Override
     protected String doInBackground(String... strings) {
         String data = null;

         try {
             //initialize data
              data = downloadUrl(strings[0]);
         } catch (IOException e) {
             e.printStackTrace();
         }

         return data;
     }

    @Override
    protected void onPostExecute(String s) {
        //execute parser task
        new ParserTask().execute(s);
    }

    private String downloadUrl(String string) throws IOException {

         //initialize Url
         URL url = new URL(string);
         //initialize connection
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         //connect connection
         connection.connect();
         //initialize input stream
         InputStream stream = connection.getInputStream();
         //initialize buffer reader
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
         //Initialize string builder
         StringBuilder builder = new StringBuilder();
         //initialize string variable
         String line = "";
         //use while loop
         while ((line=reader.readLine()) !=null){
             //append line
             builder.append(line);
         }

         //get append data
         String data = builder.toString();
         //close reader
         reader.close();
         return data;


     }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {

            //create json parser class
            JsonParser jsonParser = new JsonParser();
            //initialize hash map list
            List<HashMap<String,String>> mapList = null;
            JSONObject object=null;

            try {
                //initialize json object
                 object = new JSONObject(strings[0]);
                 //parse json object
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return map list

            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //clear map
            ArrayList map = null;
            map.clear();

            //use for loop
            for (int i= 0; i<hashMaps.size(); i++){
                //initialize hash maps
                HashMap<String,String> hashMapList = hashMaps.get(i);
                //get latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //get longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //get name
                String name = hashMapList.get("name");
                //concat latitude
                LatLng latLng = new LatLng(lat,lng);
                //initialize marker options
                MarkerOptions options = new MarkerOptions();
                //set positions
                options.position(latLng);
                //set title
                options.title(name);
                //add marker on map
                map.addMarker(options);

            }
        }
    }
}
