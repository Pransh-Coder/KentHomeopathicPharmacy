package com.example.kenthomeopathicpharmacy;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Drawerlayout extends AppCompatActivity {

    //Navigation Drawer
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //ViewPager
    ViewPager viewPager;

    ArrayList<String> list;         // for autocompletetextview arraylist

    RequestQueue queue;

    // for category
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter1;
    private RecyclerView.LayoutManager layoutManager;

    List<Category> categoryList =new ArrayList<>();

    // for TopSeller
    RecyclerView recyclerView3;
    RecyclerView.Adapter adapter3;
    RecyclerView.LayoutManager layoutManager3;

    List<TopSeller> topSellerList = new ArrayList<>();

    RequestQueue queue1;
    RequestQueue queue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);


        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);            //for category lists
        queue1=Volley.newRequestQueue(this);
        //initialize RecyclerView
        layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView3 = (RecyclerView)findViewById(R.id.recyclerView3);
        queue3 =Volley.newRequestQueue(this);
        layoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);

        //ViewPager
        viewPager =(ViewPager)findViewById(R.id.viewpager);
        //Initialise ViewPager Adapter
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        /* Timer - A facility for threads to schedule tasks for future execution in a background thread.
        Tasks may be scheduled for one-time execution, or for repeated execution at regular intervals
         */

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Autoslide(),2000,4000);


        //Navigation Drawer
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);                                                           // we have to add icon to drawable
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);  //for auto complete textview

        list=new ArrayList<>();        // for autocompletetextview arraylist

        AutoCompleteTextView editText =(AutoCompleteTextView)findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        editText.setAdapter(adapter);


        jsonParse();
        parseCategory();
        parseTopSeller();
    }

    public  class Autoslide extends TimerTask{              //TimerTask()-A task that can be scheduled for one-time or repeated execution by a Timer.
        @Override
        public void run() {
            //Runs the specified action on the UI thread.  UI thread-The default, primary thread created anytime an Android application is launched. It is in charge of handling all user interface and activities, unless otherwise specified. Runnable is an interface meant to handle sharing code between threads.
            Drawerlayout.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                 if(viewPager.getCurrentItem()==0)
                 {
                     viewPager.setCurrentItem(1);
                 }
                 else  if(viewPager.getCurrentItem()==1)
                 {
                     viewPager.setCurrentItem(2);
                 }
                 else {
                     viewPager.setCurrentItem(0);
                 }
                }
            });
        }

    }

    private void parseCategory() {
        //StringReques- A canned request for retrieving the response body at a given URL as a String.
        StringRequest request =  new StringRequest(Request.Method.POST, "http://www.json-generator.com/api/json/get/bVvBnCUBgy?indent=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject =jsonArray.getJSONObject(i);

                        final Category  category =new Category();

                        category.setCategorynames(jsonObject.getString("cat"));
                        //category.setImages(jsonObject.getString("logo"));

                        categoryList.add(category);     //The data that we retrived  in obj category we pass it to arraylist

                    }
                    adapter1 = new RecyclerAdapter(Drawerlayout.this,categoryList);      //RecyclerAdapter constructor
                    recyclerView.setAdapter(adapter1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue1.add(request);
    }

    private void jsonParse() {
        StringRequest request =  new StringRequest(Request.Method.POST, "http://try11.in/admin/app_link/match_list.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String ll=jsonObject.getString("Team-A");
                        list.add(ll);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    private void parseTopSeller() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://www.json-generator.com/api/json/get/cpVYYBdrVK?indent=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final TopSeller topSeller = new TopSeller();    //topSeller-obj

                        topSeller.setItemname(jsonObject.getString("item"));
                        //topSeller.setPics(jsonObject.getString("img"));

                        topSellerList.add(topSeller);
                    }
                    adapter3 = new RecyclerAdapterTopSeller(Drawerlayout.this,topSellerList);
                    recyclerView3.setAdapter(adapter3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue3.add(request);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                   // without this icon will not work which is at the left of screen

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
