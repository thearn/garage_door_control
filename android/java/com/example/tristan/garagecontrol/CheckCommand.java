package com.example.tristan.garagecontrol;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class CheckCommand extends ToggleCommand {

    public CheckCommand(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    protected JSONObject doInBackground(String... args) {

        JSONParser jParser = new JSONParser();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.particle.io")
                .appendPath("v1")
                .appendPath("devices")
                .appendPath("device-id-goes-here") // particle device ID
                .appendPath("light")
                .appendQueryParameter("access_token", "-access-token-goes-here");

        String final_url = builder.build().toString();
        JSONObject json = jParser.getJSONFromUrl(final_url);

        return json;
    }



}
