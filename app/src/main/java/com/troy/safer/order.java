package com.troy.safer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final EditText name = findViewById(R.id.formName);
        final EditText email = findViewById(R.id.formEmail);
        final EditText phone = findViewById(R.id.formphone);
        final EditText details = findViewById(R.id.formDetails);
        final EditText children = findViewById(R.id.formchildren);
        final EditText adults = findViewById(R.id.formadults);
        final EditText food = findViewById(R.id.formfood);
        final EditText nights = findViewById(R.id.formnight);

        Bundle bundle = getIntent().getExtras();
        final String stuff = bundle.getString("name");


        Button order = findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
if (name.getText().toString().equals("")||email.getText().toString().equals("")||phone.getText().toString().equals("")||children.getText().toString().equals("")||adults.getText().toString().equals("")||nights.getText().toString().equals("")||food.getText().toString().equals("")){
    Toast.makeText(order.this,"Please fill all info",Toast.LENGTH_LONG).show();


}else {
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDbRef = mDatabase.getReference().child("orders").push();
    mDbRef.child("hotelorder").setValue(stuff);
    mDbRef.child("name").setValue(name.getText().toString());
    mDbRef.child("email").setValue(email.getText().toString());
    mDbRef.child("phone").setValue(phone.getText().toString());
    mDbRef.child("details").setValue(details.getText().toString());
    mDbRef.child("children").setValue(children.getText().toString());
    mDbRef.child("adult").setValue(adults.getText().toString());
    mDbRef.child("food").setValue(food.getText().toString());
    mDbRef.child("nights").setValue(nights.getText().toString());
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
    String currentDateandTime = sdf.format(new Date());
    mDbRef.child("time").setValue(currentDateandTime);

    Toast.makeText(order.this,"your order submitted",Toast.LENGTH_LONG).show();
    Intent intent = new Intent (order.this, MainActivity.class);
    startActivity(intent);
}
            }
        });


        ImageView back = findViewById(R.id.backinfo);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(order.this, HotelBook.class);
                Bundle bundle = new Bundle();
                bundle.putString("name1", stuff);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

}
