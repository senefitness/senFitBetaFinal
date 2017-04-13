package com.seneca.android.senfitbeta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Session session;
    private DbHelper db;
    Button btnLogout,viewList,viewListOfBody,btnTimer,viewSetWork;
    TextView textView;
    private ProgressDialog pDialog;
    private Integer ex_id,mx_id;
    private String TAG = MainActivity.class.getSimpleName();
    private NavigationView nv;

    private static String url = "https://wger.de/api/v2/exercise.json/?language=2&ordering=id&ordering=name&status=2&limit=200";
    private static String m_url = "https://wger.de/api/v2/muscle.json/?ordering=id";
    private static String i_url = "https://wger.de/api/v2/exerciseimage.json/?limit=250&ordering=exercise&ordering=id&ordering=image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sidemenu_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new Session(this);
        db = new DbHelper(this);



        if(!session.loggedin()){
            logout();
        }
        /*************Shared Collector*/

        String email = session.getString();
        if(email != null){
            textView = (TextView)findViewById(R.id.tvEmail);
            textView.setText("You Are Logged In As "+email);
        }

        /*btnLogout =(Button)findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });*/
        viewList =(Button) findViewById(R.id.allworkouts);
        viewList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,exlist.class));
            }
        });

        viewListOfBody = (Button) findViewById(R.id.allMuscle);
        viewListOfBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,musclelist.class));
            }
        });

        btnTimer = (Button) findViewById(R.id.breakTimer);
        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, breakTimer.class));
            }
        });

        viewSetWork = (Button)findViewById(R.id.allBody);
        viewSetWork.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, viewsetitems.class));
            }
        });



        nv = (NavigationView)findViewById(R.id.nav_view);
        nv.bringToFront();
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.workout2):
                        startActivity(new Intent(MainActivity.this,exlist.class));
                        break;
                    case(R.id.breakTimer2):
                        startActivity(new Intent(MainActivity.this, breakTimer.class));
                        break;
                    case(R.id.calendarView2):
                        startActivity(new Intent(MainActivity.this, viewsetitems.class));
                        break;

                    case(R.id.logout):
                        logout();
                        break;
                    case(R.id.share):
                        Uri webpage = Uri.parse("https://www.facebook.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        startActivity(intent);
                        break;



                }
                return true;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        boolean ans = doesDatabaseExist(this,"myapp.db");


        if(!ans){
            new GetFitt().execute();
        }

    }


    public void logout(){
        session.setLoggedin(false,null,null);
        startActivity(new Intent(MainActivity.this,Login.class));
    }
    //check if db exist
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    /******************************ASyncTasK*********/

    /******************************ASyncTasK*********/

    public class GetFitt extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            jsonHandler sh = new jsonHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        ex_id = c.getInt("id");
                        String aut = c.getString("license_author").toString();
                        String dsc = c.getString("description").toString();
                        String htmlDescRemover = Html.fromHtml(dsc).toString();
                        String name = c.getString("name").toString();
                        String nO = c.getString("name_original").toString();
                        String date = c.getString("creation_date").toString();
                        String cat = c.getString("category").toString();

                        db.insertExercise(aut,htmlDescRemover,name,nO,date,cat,ex_id);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }


            //MUSCLES
            jsonHandler shm = new jsonHandler();
            String MusStr = shm.makeServiceCall(m_url);
            Log.e(TAG, "Response from url: " + MusStr);

            if (MusStr != null) {
                try {
                    JSONObject jsonObjM = new JSONObject(MusStr);

                    // Getting JSON Array node
                    JSONArray muscles = jsonObjM.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < muscles.length(); i++) {
                        JSONObject cm = muscles.getJSONObject(i);

                        mx_id = cm.getInt("id");
                        String Muname = cm.getString("name").toString();
                        db.insertMuscles(mx_id,Muname);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }


            //IMAGES
            jsonHandler shi = new jsonHandler();
            String imgStr = shm.makeServiceCall(i_url);
            Log.e(TAG, "Response from url: " + imgStr);

            if (imgStr != null) {
                try {
                    JSONObject jsonObji = new JSONObject(MusStr);

                    // Getting JSON Array node
                    JSONArray images = jsonObji.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < images.length(); i++) {
                        JSONObject c = images.getJSONObject(i);

                        ex_id = c.getInt("id");
                        String images_ = c.getString("image").toString();

                        db.insertImg(ex_id,images_);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





}
