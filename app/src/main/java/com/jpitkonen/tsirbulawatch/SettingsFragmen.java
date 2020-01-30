package com.jpitkonen.tsirbulawatch;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragmen extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs, rootKey);
    }
}
