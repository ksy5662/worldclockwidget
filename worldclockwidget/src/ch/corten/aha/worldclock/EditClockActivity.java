/*
 * Copyright (C) 2012  Armin Häberling
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package ch.corten.aha.worldclock;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import ch.corten.aha.worldclock.provider.WorldClock.Clocks;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class EditClockActivity extends SherlockFragmentActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.edit_clock);
        
        FragmentManager fm = getSupportFragmentManager();
        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            EditClockFragment fragment = new EditClockFragment();
            fm.beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
    
    private EditClockFragment getFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (EditClockFragment) fm.findFragmentById(android.R.id.content);
    }
    
    public void ok(View view) {
        getFragment().ok(view);
    }
    
    public void cancel(View view) {
        getFragment().cancel(view);
    }
    
    public static class EditClockFragment extends SherlockFragment {
        private static final String[] PROJECTION = {
            Clocks.CITY,
            Clocks.AREA,
            Clocks.LATITUDE,
            Clocks.LONGITUDE,
            Clocks.USE_IN_WIDGET
        };
        private long mId;
        private EditText mCityText;
        private EditText mDescText;
        private EditText mLatitudeText;
        private EditText mLongitudeText;
        private CheckBox mUseInWidgetCheckBox;
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (savedInstanceState != null) {
                mId = savedInstanceState.getLong(Clocks._ID);
            } else {
                mId = getActivity().getIntent().getExtras().getLong(Clocks._ID);
            }
            Uri uri = ContentUris.withAppendedId(Clocks.CONTENT_URI, mId);
            Cursor c = getActivity().getContentResolver().query(uri, PROJECTION, null, null, null);
            try {
                c.moveToFirst();
                
                mCityText = (EditText) getView().findViewById(R.id.city_edittext);
                mCityText.setText(c.getString(c.getColumnIndex(Clocks.CITY)));
                mDescText = (EditText) getView().findViewById(R.id.description_edittext);
                mDescText.setText(c.getString(c.getColumnIndex(Clocks.AREA)));
                mLatitudeText = (EditText) getView().findViewById(R.id.latitude_edittext);
                mLatitudeText.setText(printNumber(c.getDouble(c.getColumnIndex(Clocks.LATITUDE))));
                mLongitudeText = (EditText) getView().findViewById(R.id.longitude_edittext);
                mLongitudeText.setText(printNumber(c.getDouble(c.getColumnIndex(Clocks.LONGITUDE))));
                mUseInWidgetCheckBox = (CheckBox) getView().findViewById(R.id.use_in_widget_checkbox);
                mUseInWidgetCheckBox.setChecked(c.getInt(c.getColumnIndex(Clocks.USE_IN_WIDGET)) != 0);
            } finally {
                c.close();
            }
        }
        
        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putLong(Clocks._ID, mId);
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.edit_clock, null);
        }
        
        private static double parseNumber(Editable editable) {
            try {
                return Double.parseDouble(editable.toString().trim());
            } catch (NumberFormatException e) {
                Log.e("EditClockActivity", "Failure to parse number!", e);
                return 0.0;
            }
        }
        
        private static String printNumber(double d) {
            return Double.toString(d);
        }
        
        public void cancel(View view) {
            getActivity().finish();
        }
        
        public void ok(View view) {
            saveChanges();
            getActivity().finish();
        }
        
        private void saveChanges() {
            Uri uri = ContentUris.withAppendedId(Clocks.CONTENT_URI, mId);
            ContentValues values = new ContentValues();
            values.put(Clocks.CITY, mCityText.getText().toString().trim());
            values.put(Clocks.AREA, mDescText.getText().toString().trim());
            double latitude = parseNumber(mLatitudeText.getText());
            values.put(Clocks.LATITUDE, latitude);
            double longitude = parseNumber(mLongitudeText.getText());
            values.put(Clocks.LONGITUDE, longitude);
            values.put(Clocks.USE_IN_WIDGET, mUseInWidgetCheckBox.isChecked());
            int changed = getActivity().getContentResolver().update(uri, values, null, null);
            getActivity().setResult(changed);
        }
    }
}
