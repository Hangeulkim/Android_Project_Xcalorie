package com.example.trust;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    ListPreference howtoex;

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);
        howtoex=(ListPreference)findPreference("exercise");

        prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());

        if(!prefs.getString("exercise","").equals("")){
            howtoex.setSummary(prefs.getString("exercise","걷기"));
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


            if(key.equals("exercise")){
                howtoex.setSummary(prefs.getString("exercise","걷기"));
            }

        }
    };
}
