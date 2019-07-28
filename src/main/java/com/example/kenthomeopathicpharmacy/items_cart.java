package com.example.kenthomeopathicpharmacy;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class items_cart extends AppCompatActivity {

    String p,no;

    //for storing in cart
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    //List<CartItems> cartItemsList = new ArrayList<>();        //Made list of cart items to fill data that we retrive from json

    TextView name,price,filladdressdetails,setname;
    ImageView imageView;

    RequestQueue queue;    // for storing in cart i.e storetocart() func
    RequestQueue queue1;
    RequestQueue queue2;  // for filling items in cart i.e filltocart()

    Button button;
    Button removeItem;

    Button  rupees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_cart);

        queue= Volley.newRequestQueue(this);
        queue1=Volley.newRequestQueue(this);

        queue2=Volley.newRequestQueue(this);

        //layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //recyclerView.setLayoutManager(layoutManager);

        /*name = findViewById(R.id.itemname);
        price=findViewById(R.id.itemprice);
        imageView = findViewById(R.id.itemimg);*/

        SharedPreferences sharedPreferences =getApplication().getSharedPreferences("username",MODE_PRIVATE);
        String a= sharedPreferences.getString("u_id","");
        System.out.println(a);

        final Intent intent = getIntent();           // Receiving data from RecyclerAdapterTopseller (id) in blank activity
        final String ids = intent.getStringExtra("id");


        //storetocart();

        filltocart(ids,a);

        //removeItem = findViewById(R.id.removeitem);

/*        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeproducts(ids);
            }
        });*/


        rupees = findViewById(R.id.Rs);

        filladdressdetails = findViewById(R.id.filladdress);
        setname = findViewById(R.id.setname);

        //Getting address from EnterDetailsofAdress using sharedPrefrence
        SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("user_details",MODE_PRIVATE);

        p = sharedPreferences1.getString("p_pincode","");
        //System.out.println(p);
        no = sharedPreferences1.getString("p_pno","");
        //System.out.println(no);
        String name=sharedPreferences.getString("p_name","");
        System.out.println(name);
        String city = sharedPreferences1.getString("p_city","");
        String address = sharedPreferences1.getString("p_address","");
        String state = sharedPreferences1.getString("p_state","");

        setname.setText(name);

        filladdressdetails.setText(city + "\n" + address + " ," + state+"\n" + p +"\n"+ no);

        button=findViewById(R.id.chgAdress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(items_cart.this,ShowAdress.class);
                startActivity(intent1);
            }
        });
    }

    private void filltocart(final String id,final String name) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/add_to_cart.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("product_id",id);
                map.put("user_id",name);
                return map;
            }
        };
        queue2.add(request);
    }


    /*private void storetocart() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sakardeal.com/android/product_list.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        final  CartItems cartItems = new CartItems();           //  cartItems - obj of class CartItems

                        cartItems.setP_name(jsonObject.getString(""));
                        cartItems.setP_price(jsonObject.getString(""));
                        cartItems.setImg(jsonObject.getString(""));

                        cartItemsList.add(cartItems);     //adding obj to list of cartitemsList to fill the data in adapter
                    }
                    adapter = new RecyclerAdapterCartItems(items_cart.this,cartItemsList);          //constructor of RecyclerAdapterCartItems
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }*/

    private void removeproducts(final  String remove) {
        StringRequest request = new StringRequest(Request.Method.POST, "http://crazymall.co.in/admin/not_usable/delete_item.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                System.out.println(response);

                if(response.equalsIgnoreCase("true"))
                {
                    finish();
                    startActivity(getIntent());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap <String,String>map = new HashMap<>();
                map.put("id",remove) ;                                                        //map.put(String key,String value)
                return map;
            }
        };
        queue1.add(request);
    }
}
