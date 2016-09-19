package com.example.newuser.vangmaterialdesign;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.newuser.vangmaterialdesign.MDB.ServiceMoviesBoxOffice;
import com.example.newuser.vangmaterialdesign.MDB.Sqlite.DBMusic;


import java.io.File;
import java.util.ArrayList;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;


public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID = 100;
    JobScheduler mJobScheduler;
    File f;
    //Run the JobSchedulerService every 2 minutes
    private static final long POLL_FREQUENCY = 28800000;
    private DrawerLayout mDrawerLayout;
    PagerAdapter pagerAdapter;

    private int[] tabIcons = {
            R.drawable.ic_list,
            R.drawable.ic_cloud_download_white_24dp};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobScheduler = JobScheduler.getInstance(this);

        setupJob();
//==========================================================
        toolbarActionbar();
//================================================================
        fab();
//==============================================================
//        drawer();
//====================================================
        swipeTabs();
//==========================================================
makeDir();

    }
  public void makeDir (){
      String folder_main = "NewFolder";

     f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), folder_main);
      if (!f.exists()) {
          f.mkdirs();
      }

  }

    private void setupJob() {
        mJobScheduler = JobScheduler.getInstance(this);
        //set an initial delay with a Handler so that the data loading by the JobScheduler does not clash with the loading inside the Fragment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //schedule the job after the delay has been elapsed
                buildJob();
            }
        }, 30000);
    }

    private void buildJob() {
        //attach the job ID and the name of the Service that will work in the background
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, ServiceMoviesBoxOffice.class));
        //set periodic polling that needs net connection and works across device reboots
        builder.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
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

        switch (id) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
            case R.id.action_settings:


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toolbarActionbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void fab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.coordinator), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        });
    }

    public void drawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    public void swipeTabs() {
        ArrayList<String> tabs = new ArrayList<>();
//        tabs.add("Tab 1");
//        tabLayout.addTab();
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_download));
        tabs.add("لیست");

        tabs.add("دانلود");


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

       TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);


        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {

            FragmentLifecycle fragmentToHide = (FragmentLifecycle)pagerAdapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment();
           Fragment fragc = pagerAdapter.getFragment(newPosition);
            fragc.onDestroy();
            FragmentLifecycle fragmentToShow = (FragmentLifecycle)pagerAdapter.getItem(newPosition);
            fragmentToShow.onResumeFragment();
             fragc.onStart();

            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) { }

        public void onPageScrollStateChanged(int arg0) { }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();


//        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC))));
//        MediaScannerConnection.scanFile(this, new String[]{f.getAbsolutePath()}, new String[]{"music/mp3"}, null);
// scanMediaFiles(this, new String[]{path}, new String[]{"music/mp3"});
    }

//    private void scanFile(String path) {
//
//        MediaScannerConnection.scanFile(MainActivity.this,
//                new String[] { path }, null,
//                new MediaScannerConnection.OnScanCompletedListener() {
//
//                    public void onScanCompleted(String path, Uri uri) {
//                        L.m( "Finished scanning " + path);
//
//                    }
//                });
//    }


//    public static void scanMediaFiles(final Context context, String[] files, String[] mimeTypes) {
//        L.m("mediascan file");
//        MediaScannerConnection.scanFile(context, files, mimeTypes, new MediaScannerConnection.OnScanCompletedListener() {
//
//            @Override
//            public void onScanCompleted(String arg0, Uri arg1) {
//                // TODO
//                L.m("mediascan complete");
//            }
//        });
//    }

}
