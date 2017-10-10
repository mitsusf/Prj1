package com.example.latteforever.wmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherDetailActivity extends FragmentActivity implements WeatherCallback, LocationListener {

        /**
         * The {@link android.support.v4.view.PagerAdapter} that will provide
         * fragments for each of the sections. We use a
         * {@link FragmentPagerAdapter} derivative, which will keep every
         * loaded fragment in memory. If this becomes too memory intensive, it
         * may be best to switch to a
         * {@link android.support.v4.app.FragmentStatePagerAdapter}.
         */
        private SectionsPagerAdapter mSectionsPagerAdapter;
        private WeatherRequest weatherProvider;

        protected LocationManager locationManager;
        protected LocationListener locationListener;
        protected String latitude,longitude;

        /**
         * The {@link ViewPager} that will host the section contents.
         */
        private ViewPager mViewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            // Setup adapter for fragments
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Create weather provider
            weatherProvider = new WeatherRequest(this);
        }

        @Override
        protected void onStart() {
            super.onStart();

            Intent intent = getIntent();
            String city = intent.getStringExtra("city");

            String geo[] = new String[2];
            geo[0] = city;
            geo[1] = "";    // This can be used for country code
            weatherProvider.refresh(geo);
        }


        @Override
        public void resolved(WeatherParser data) {

            try {
                View cview = mViewPager.getChildAt(mViewPager.getCurrentItem()).getRootView();

                TextView location = (TextView) cview.findViewById(R.id.location);
                location.setText(data.getLocation());

                TextView temp = (TextView) cview.findViewById(R.id.temp);
                temp.setText(data.getTemp());

                TextView desc = (TextView) cview.findViewById(R.id.description);
                desc.setText(data.getDescription());

            } catch(Exception e) {
                // error
            }
        }

        @Override
        public void rejected(WeatherParser data) {
            Toast tos = Toast.makeText(getApplicationContext(), data.getError(), Toast.LENGTH_SHORT);
            tos.show();
        }

        @Override
        public void onLocationChanged(Location location) {
            Toast tos = Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()) , Toast.LENGTH_SHORT);
            tos.show();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
            }

            return super.onOptionsItemSelected(item);
        }

        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        // Page-1
                        return PlaceholderFragment.newInstance(position + 1);

                    default:
                        // Page-2
                        Fragment fragment = new PageTwoFragment();
                        return fragment;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        }

        /**
         * The main page
         */
        public static class PlaceholderFragment extends Fragment  {

            // Weather information
            private TextView location;
            private TextView temp;
            private TextView desc;

            public PlaceholderFragment() {
            }

            public static PlaceholderFragment newInstance(int sectionNumber) {
                PlaceholderFragment fragment = new PlaceholderFragment();
                return fragment;
            }

            @Override
            public void onAttach(Context context) {
                super.onAttach(context);
                Activity main;

                if (context instanceof Activity){
                    main = (Activity) context;
                }
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);

                // Weather items
                location = (TextView) rootView.findViewById(R.id.location);
                temp = (TextView) rootView.findViewById(R.id.temp);
                desc = (TextView) rootView.findViewById(R.id.description);

                return rootView;
            }
        }

        /**
         * Page-2
         */
        public static class PageTwoFragment extends Fragment {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_page2, container, false);
                //Bundle args = getArguments();
                ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                        getString(R.string.f2_section_text));
                return rootView;
            }
        }

    }


