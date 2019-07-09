package com.example.kenthomeopathicpharmacy;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Drawerlayout extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    ArrayList<String> list;         // for autocompletetextview arraylist
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);
        //Navigation Drawer
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);                                                           // we have to add icon to drawable
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue= Volley.newRequestQueue(this);

        list=new ArrayList<>();        // for autocompletetextview arraylist

        AutoCompleteTextView editText =(AutoCompleteTextView)findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        editText.setAdapter(adapter);
        jsonParse();

    }

    private void jsonParse() {
        StringRequest request =  new StringRequest(Request.Method.POST, "http://serv.kesbokar.com.au/jil.0.1/v2/product?page=1&per_page=15&caturl=Services+For+Hire&catid=115&api_token=FSMNrrMCrXp2zbym9cun7phBi3n2gs924aYCMDEkFoz17XovFHhIcZZfCCdK", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String ll=jsonObject.getString("name");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                   // without this icon will not work which is at the left of screen

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
