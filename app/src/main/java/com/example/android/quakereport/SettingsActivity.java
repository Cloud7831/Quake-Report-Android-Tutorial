package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }

        @Override
        public boolean onPreferenceChange(Preference pref, Object value){
            String stringValue = value.toString();

            if(pref instanceof ListPreference){
                ListPreference listPreference = (ListPreference)pref;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if(prefIndex >= 0){
                    CharSequence[] labels = listPreference.getEntries();
                    pref.setSummary(labels[prefIndex]);
                }
            }
            else{
                pref.setSummary(stringValue);
            }

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference pref){
            pref.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
            String preferenceString = preferences.getString(pref.getKey(), "");
            onPreferenceChange(pref, preferenceString);

        }
    }

}
