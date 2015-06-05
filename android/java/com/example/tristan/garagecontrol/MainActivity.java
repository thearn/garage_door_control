package com.example.tristan.garagecontrol;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public ToggleCommand mainParser = new ToggleCommand(this);
    public CheckCommand checkParser = new CheckCommand(this);
    public Activity this_activity = this;
    public SharedPreferences prefs;
    Boolean home_only = true;
    public boolean location_allow = true;

    public void sendToggleCommand() {
        TextView connect_text = (TextView) this_activity.findViewById(R.id.connect_text);
        connect_text.setText("...");
        mainParser.cancel(true);
        mainParser = (ToggleCommand) new ToggleCommand(this_activity).execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        ImageButton open_button = (ImageButton) this.findViewById(R.id.openButton);
        open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                home_only = prefs.getBoolean("homeonly_checkbox", true);

                if (home_only) {
                    WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    String ssid = wifiInfo.getSSID();
                    if (ssid.substring(1, ssid.length() - 1).equals("your-ssid")) {
                        sendToggleCommand();
                    }
                    else {
                        Toast toast = Toast.makeText(this_activity, "Not on home WIFI!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                else {
                    sendToggleCommand();
                }

            }
        });

        Button check_button = (Button) this.findViewById(R.id.check_btn);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView connect_text = (TextView) this_activity.findViewById(R.id.connect_text);
                connect_text.setText("...");
                checkParser.cancel(true);
                checkParser = (CheckCommand) new CheckCommand(this_activity).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
