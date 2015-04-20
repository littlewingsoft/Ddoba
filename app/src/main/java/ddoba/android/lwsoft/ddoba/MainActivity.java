package ddoba.android.lwsoft.ddoba;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    public  static MainActivity inst;

    private AdView mAdView;

    SamplePagerAdapter mPagerAdapter;
    //PagerAdapter mPagerAdapter;
    ViewPager mViewPager;


    public class SamplePagerAdapter extends FragmentPagerAdapter {

        private Context context;

        HashMap<Integer,String> mTabName=new HashMap<>();
        HashMap<Integer,Fragment > mFragList= new HashMap<>();

        public void addFragment( Context context, int index, String title ){

            if( mFragList.containsKey(index) == false ){
                fragment_contentslist fm = fragment_contentslist.newInstance( index );
                //Bundle args = new Bundle();
                // Our object is just an integer :-P
                //args.putString(fragment_contentslist.ARG_TITLE, title);
                //fm.setArguments(args);
                mFragList.put(index, fm );
            }
        }

        public SamplePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;

            mTabName.put(0,"tv" );
            mTabName.put(1,"movie" );
            mTabName.put(2,"xxx" );

            for( Integer key: mTabName.keySet() ){
                addFragment(context, key, mTabName.get(key));
            }

        }

        @Override
        public int getCount() {
            return mTabName.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return mTabName.get(position);
        }
    }

    //FragmentStatePagerAdapter
    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter( Context context, FragmentManager fm) {
            super(fm);
            addFragment(context, 0,"tv");
            addFragment(context, 1,"movie");
            addFragment(context, 2,"xxx");
        }
        HashMap<Integer,Fragment > mFragList= new HashMap<Integer,Fragment>();

        public void addFragment( Context context, int index, String title ){
            if( mFragList.containsKey(index) == false ){
                fragment_contentslist fm = fragment_contentslist.newInstance(index);
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putString(fm.ARG_TITLE, title);
                fm.setArguments(args);
                mFragList.put(index, fm );
            }
        }

        @Override
        public Fragment getItem(int i) {
            fragment_contentslist tv = (fragment_contentslist)mFragList.get(i);

            return tv;
        }

        @Override
        public int getCount() {
            return mFragList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            fragment_contentslist tv =(fragment_contentslist) mFragList.get(position);
            if( tv != null )
            {
                return tv.mTitle;
            }
            return "none title";
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab,
                              android.app.FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
                                android.app.FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
                                android.app.FragmentTransaction fragmentTransaction) {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        inst = this;
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(ddoba.android.lwsoft.ddoba.R.layout.activity_main);

        // Set up the action bar.
        final ActionBar  actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.hide();

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mPagerAdapter = new SamplePagerAdapter(getSupportFragmentManager(),this );
        mViewPager.setAdapter( mPagerAdapter );

        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab()
                    .setText(mPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
        //Log.i("tag_ddoba", getURLtoText("http;//www.dasibogi.com"));

       // webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        settingAD();
    }

    //TODO 여기서 ad 없어지는거 처리 해야됨.
    void settingAD(){
        Log.i("tag_", "settingAD");
        // adView를 만듭니다.
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setVisibility(View.GONE);
                Log.i("tag_", "click on settingAD ");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(ddoba.android.lwsoft.ddoba.R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        //webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        mAdView.resume();
    }

    @Override
    protected  void onDestroy(){
        mAdView.destroy();
        super.onDestroy();

    }
    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
       // if (inCustomView()) {
        //    hideCustomView();
        //}
    }

    @Override
    public void onBackPressed() {
        //if (this.browserFragment != null && this.browserFragment.canGoBack()) {
         //   this.browserFragment.goBack();
        int curr = mViewPager.getCurrentItem();
        if( curr == 0 )
        {
            super.onBackPressed();
        } else {
            // The back key event only counts if we execute super.onBackPressed();
            mViewPager.setCurrentItem(curr-1,true);
        }
    }

    /* chaging onBackPressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/



}
