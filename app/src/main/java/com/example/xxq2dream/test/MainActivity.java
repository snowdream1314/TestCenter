package com.example.xxq2dream.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxq2dream.test.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private TextView toolbarLeft, toolbarTitle,toolbarRight;

    private RecyclerView recyclerView;
    private List<String> items = new ArrayList<>();
    private DrawerLayout mDrawer;
    private MyAdapter mMyAdapter;
    private NavigationView mNavigationView;
    private NavigationView mNavigationView1;

    private ListView lvListView, mListViewRight;
    private String [] devices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarLeft = (TextView) findViewById(R.id.toolbar_left);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarRight = (TextView) findViewById(R.id.toolbar_right);

        initListView();

//        initData();
//        initNavView();

//        initRecycler();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();


    }

    private void initListView() {
        devices = getResources().getStringArray(R.array.menu_array);
        lvListView = (ListView) findViewById(R.id.lvListView);
        lvListView.setAdapter(new ArrayAdapter<String>(this, R.layout.left_drawer_menu_layout, R.id.item,  devices));
        lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, devices[position], Toast.LENGTH_SHORT).show();
            }
        });

        mListViewRight = (ListView) findViewById(R.id.lvListViewRight);
        mListViewRight.setAdapter(new ArrayAdapter<String>(this, R.layout.left_drawer_menu_layout,R.id.item, devices));
        mListViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, devices[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            items.add("item " + i);
        }
    }

    private void initNavView() {
//        View menuLayout = LayoutInflater.from(this).inflate(R.layout.drawer_navigation_content, null, false);

//        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
//        mNavigationView1 = (NavigationView) findViewById(R.id.nav_view_1);
//        mNavigationView.setNavigationItemSelectedListener(this);
//        mNavigationView1.setNavigationItemSelectedListener(this);

//        mNavigationView1.addHeaderView(menuLayout);
//        mNavigationView1.addView(menuLayout, 1);
    }

    private void initRecycler() {
//        recyclerView = (RecyclerView) findViewById(R.id.recycler);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        mMyAdapter = new MyAdapter(this, items);
//        recyclerView.setAdapter(mMyAdapter);
    }


    private void initView() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left:
                if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.toolbar_right:
                if (!mDrawer.isDrawerOpen(GravityCompat.END)) {
                    mDrawer.openDrawer(GravityCompat.END);
                }
                break;
            default:
                break;
        }
    }


}
