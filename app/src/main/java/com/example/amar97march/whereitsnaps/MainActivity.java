package com.example.amar97march.whereitsnaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mNavDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavDrawerList=(ListView)findViewById(R.id.navList);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mActivityTitle=getTitle().toString();
        //initialize an array with our titles from strings.xml
        String[] navMenuTitles=getResources().getStringArray(R.array.nav_drawer_items);
        //Initialize or ArrayAdapter
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,navMenuTitles);

        //set adapter to the ListView
        mNavDrawerList.setAdapter(mAdapter);
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id) {
                switchFragment(whichItem);
            }
        });
        switchFragment(0);

    }
    private void switchFragment (int position){

        Fragment fragment=null;
        String fragmentID="";
        switch(position){
            case 0:{
                fragmentID="TITLE";
                Bundle args=new Bundle();
                args.putString("Tag","NO_TAG");
                fragment=new TitlesFragment();
                fragment.setArguments(args);
                break;
            }
            case 1:{
                fragmentID="TAGS";
                fragment=new TagsFragment();
                break;
            }
            case 2:{
                fragmentID="CAPTURE";
                fragment=new CaptureFragment();
                break;
            }
            default: break;
        }

        FragmentManager fragmentManager= getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,fragment,fragmentID).commit();
        mNavDrawerList.setItemChecked(position, true);
        mNavDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mNavDrawerList);
    }

    private void setupDrawer(){
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close) {
            //called when the drawer is opened
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Make Selection");
                //triggers call to onPrepareOptionMenu
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onBackPressed(){
        //close the drawer if open
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            //it is open so close it
            mDrawerLayout.closeDrawer(mNavDrawerList);
        }else{
            //go bcak to titles fragment
            //quit id already at titles fragment
            Fragment f=getFragmentManager().findFragmentById(R.id.fragmentHolder);
            if(f instanceof TitlesFragment){
                finish();
                System.exit(0);
            }else{
                switchFragment(0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
