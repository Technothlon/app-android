package org.techniche.technothlon.katana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import org.techniche.technothlon.katana.tcd.TCDContent;

import java.util.Locale;

public class TCDSingleViewer extends ActionBarActivity implements ViewPager.OnPageChangeListener {
    private static int pages;
    private ShareActionProvider mShareActionProvider;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private int activePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcdsingle_viewer);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        pages = TCDContent.ITEMS.size();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        activePage = getIntent().getExtras().getInt("open");
        mViewPager.setCurrentItem(activePage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tcdsingle_viewer, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Toast.makeText(getApplicationContext(), "Creating Intent with "+activePage, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_SEND);
        TCDContent.TCDQuestion q = TCDContent.ITEM_MAP.get(TCDContent.ITEMS.get(this.activePage).id);
        if (q != null)
        {
            intent.putExtra(Intent.EXTRA_SUBJECT, "Techno Coup D'œil #" + q.id + " - " + q.title);
            intent.putExtra(Intent.EXTRA_TITLE, "Techno Coup D'œil #" + q.id + " - " + q.title);
//            intent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this. " + q.facebook + "\nFollow Technothlon for more.");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this Technothlon's question.\n" + q.question);
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, R.drawable.ic_launcher);
        }
        intent.setType("text/*");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings: return true;
            case R.id.action_share: mShareActionProvider.setShareIntent(getDefaultIntent()); return false;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        activePage = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance((position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return pages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            TCDContent.TCDQuestion q = TCDContent.ITEM_MAP.get(TCDContent.ITEMS.get(position).id);
            String title = "#"+q.id+" - "+q.title;
            return title;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        // TODO create view here

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tcdsingle_viewer, container, false);
            TextView id = (TextView) rootView.findViewById(R.id.tcd_id),
                    title = (TextView) rootView.findViewById(R.id.tcd_title),
                    question = (TextView) rootView.findViewById(R.id.tcd_question),
                    by = (TextView) rootView.findViewById(R.id.tcd_posted_by),
                    status = (TextView) rootView.findViewById(R.id.tcd_status);
            int position = getArguments().getInt(ARG_SECTION_NUMBER);
            TCDContent.TCDQuestion q = TCDContent.ITEM_MAP.get(TCDContent.ITEMS.get(position).id);
            id.setText("#"+q.id);
            id.setBackgroundResource(q.color);
            title.setText(q.title);
            question.setText(q.question);
            by.setText(q.by);
            status.setText(q.getStatus());
            return rootView;
        }
    }

}
