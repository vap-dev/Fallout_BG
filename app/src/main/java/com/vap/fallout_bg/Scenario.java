package com.vap.fallout_bg;

import android.util.Log;

public class Scenario {
    private int id;
    private String Scenario_name;
    private static final String TAG = "myLogs";
    public Scenario(int id, String Scenario_name) {
        this.id = id;
        this.Scenario_name = Scenario_name;
    }

    public String getName() {
        Log.d(TAG, "getName");
        return Scenario_name;
    }

}
