package com.example.kenthomeopathicpharmacy;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PaymentDetails extends AppCompatActivity {

    String nameofperson,city,address,state;

    TextView textView;
    String p,no;

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView delAvail,textView1;
    TextView NoOfItems;
    TextView total_Amnt;

    Button TotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        NoOfItems = findViewById(R.id.NoItems);
        TotalAmount = findViewById(R.id.Rs);
        total_Amnt = findViewById(R.id.total_Amnt);

        textView = findViewById(R.id.show_address);
        textView1=findViewById(R.id.del_time);

        radioGroup = findViewById(R.id.radiogrp);
        delAvail = findViewById(R.id.del_Avail);

        SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("user_details",MODE_PRIVATE);

        p = sharedPreferences1.getString("p_pincode","");
        no = sharedPreferences1.getString("p_pno","");
        nameofperson=sharedPreferences1.getString("p_name","");
        System.out.println(nameofperson);
        city = sharedPreferences1.getString("p_city","");
        address = sharedPreferences1.getString("p_address","");
        state = sharedPreferences1.getString("p_state","");

        textView.setText(nameofperson + " "+ address + " " + city + " " + state +" " +p);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("values",MODE_PRIVATE);
        String size = sharedPreferences.getString("size","");

        String T_amt=sharedPreferences.getString("totalPrice","");

        NoOfItems.setText("Price  "+ "("+size+" items"+ ")");
        TotalAmount.setText("Rs. " + T_amt);
        total_Amnt.setText("Total Amount:  " + T_amt);

    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(getApplicationContext(),""+radioButton.getText(),Toast.LENGTH_SHORT).show();
        //choice.setText("" + radioButton.getText());
        String s = String.valueOf(radioButton.getText());
        System.out.println(s);
        if(s.equalsIgnoreCase("COD - Cash on Delivery"))
        {
            textView.setText("Delivery Not Available at selected Pincode!");
            if(p.equalsIgnoreCase("226003"))
            {
                textView.setText("Delivery charge: 0");
                delAvail.setText("Delivery Available Press Continue");
                textView1.setText("COD Delivery time: 1-2 days");
            }

        }
        else{
            textView.setText(nameofperson + " "+ address + " " + city + " " + state +" " +p);
            delAvail.setText("");
            textView1.setText("");
        }
    }
}
