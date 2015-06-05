package com.example.tristan.garagecontrol;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class ToggleCommand extends AsyncTask<String, String, JSONObject> {

    public Activity mainActivity;

    public ToggleCommand() {

    }

    public ToggleCommand(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected JSONObject doInBackground(String... args) {

        JSONParser jParser = new JSONParser();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.particle.io")
                .appendPath("v1")
                .appendPath("devices")
                .appendPath("your-device-id") // particle device ID
                .appendPath("toggle")
                .appendQueryParameter("access_token", "your-access-token");

        String final_url = builder.build().toString();
        //Log.d("??", final_url);
        JSONObject json = jParser.getJSONFromUrl(final_url);
        return json;
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        TextView connect_text = (TextView) mainActivity.findViewById(R.id.connect_text);
        TextView light_text = (TextView) mainActivity.findViewById(R.id.light_text);
        String error_name = "";
        int error = 0;
        int light = 0;

        try {
            error_name = (String) jsonObject.get("error");
            String error_text = "Particle API Error: " + error_name;
            Toast toast = Toast.makeText(mainActivity, error_text, Toast.LENGTH_SHORT);
            toast.show();
            error++;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try { // now get the value

            light  = (int) jsonObject.get("return_value");
            light_text.setText(Integer.toString(light));

        } catch (JSONException e) {
            error++;
            if (error == 1) {
                Toast toast = Toast.makeText(mainActivity, "Could not parse light value", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (error > 0) {

            connect_text.setText("ERROR");
        }
        else {
            connect_text.setText("OK");
        }

    }
}
