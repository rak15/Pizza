package com.example.rakesh.pizza;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {
    private String[] titles;
    private ListView drawerList;
    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    private int currentPosition = 0;
    ActionBarDrawerToggle drawerToggle;
    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){ //making list view clickable in drawer
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);   //changing fragments
            setActionBarTitle(position);  //changing titles at action bar to current fragment name
            drawerLayout.closeDrawer(drawerList);  //closing the drawerList automatically after clicking item

        }
    };
    private void selectItem(int position) {     //method to replace fragments when item clicked in drawer
        Fragment fragment;
        switch(position) {
            case 1:
                //fragment = new PizzaFragment();
                fragment = new PizzaMaterialFragment();    //using new fragment with recyclerView
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoreFragment();
                break;
            default:
                fragment = new TopFragment();
                currentPosition=position;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment,"attached_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    private void setActionBarTitle(int position) {                //method to change Actionbar title to current page name
        String title;
        if (position == 0){
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);    // to change title at action bar
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerList=(ListView)findViewById(R.id.drawer);      //listView for navigation drawer
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        titles=getResources().getStringArray(R.array.titles);           //to get array data
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_activated_1,titles);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(itemClickListener);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_menu,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener(){
            @Override
            public void onBackStackChanged() {            //called on pressing back button
                FragmentManager fm=getFragmentManager();
                Fragment fragment=fm.findFragmentByTag("attached_fragment");
                if (fragment instanceof TopFragment)
                    currentPosition=0;
                if (fragment instanceof PizzaMaterialFragment)
                    currentPosition=1;
                if (fragment instanceof PastaFragment)
                    currentPosition=2;
                if (fragment instanceof StoreFragment)
                    currentPosition=3;
                setActionBarTitle(currentPosition);              //Set the action bar title and
                drawerList.setItemChecked(currentPosition,true);   // highlight the correct item in the drawer ListView
            }
        }
        );

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }else
            selectItem(0);   //if app opens 1st time, set top fragment in frame layout
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen=drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        //implement this method to add items in in menu resource file to action bar
        getMenuInflater().inflate(R.menu.main_menu,menu);  //this will inflate menu items to menu object i.e. action bar
        MenuItem menuItem=menu.findItem(R.id.action_share);
        shareActionProvider=(ShareActionProvider)menuItem.getActionProvider();   //Get a reference to the share action provider and assign it to the private variable.
        setIntent("This is example text");                                          //Then call the setIntent() method.
        return super.onCreateOptionsMenu(menu);
    }
    private void setIntent(String text){
        Intent intent=new Intent(Intent.ACTION_SEND);       /*We created the setIntent() method.
                                                             It creates an intent, and passes it
                                                               to the share action provider using
                                                              its setShareIntent() method. */
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_create_order:
                Intent intent=new Intent(this,OrderActivity.class);
                startActivity(intent);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();    //Sync the state of the ActionBarDrawerToggle with the state of the drawer

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",currentPosition);
    }
}
