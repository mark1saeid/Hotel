package com.troy.safer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class HotelBook extends AppCompatActivity {

    private String[] imageUrls = new String[4] ;
    private DatabaseReference database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_view);




        final TextView hotelname = findViewById(R.id.hotelname);




        Bundle bundle = getIntent().getExtras();
        final String stuff = bundle.getString("name1");
        hotelname.setText(stuff);




        Button button = findViewById(R.id.receipt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HotelBook.this, order.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",stuff);
                i.putExtras(bundle);
                startActivity(i);
            }
        });






        database = FirebaseDatabase.getInstance().getReference();
        database.keepSynced(true);
        ref = database.child("hotels");





        final Query userQuery = ref.orderByChild("hotelname").equalTo(stuff);

        userQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                final TextView hotelplace = findViewById(R.id.hotelplace);
                final TextView hotelprice = findViewById(R.id.textView2);
                final TextView hoteldesc = findViewById(R.id.textView4);

                final TextView hotelbed = findViewById(R.id.bednum);
                final TextView hotelfood = findViewById(R.id.food);
                final TextView hotelpool = findViewById(R.id.pool);
                final RatingBar hotelstars = findViewById(R.id.ratingBar);

                String price = dataSnapshot.child("price").getValue(String.class);
                hotelprice.setText(price);
                String place = dataSnapshot.child("place").getValue(String.class);
                hotelplace.setText(place);
                String stars = dataSnapshot.child("stars").getValue(String.class);
                hotelstars.setRating(Float.parseFloat(stars));
                String desc = dataSnapshot.child("desc").getValue(String.class);
                hoteldesc.setText(desc);
                String bednum = dataSnapshot.child("bednum").getValue(String.class);
                hotelbed.setText(bednum);
                String food = dataSnapshot.child("food").getValue(String.class);
                hotelfood.setText(food);
                String pool = dataSnapshot.child("pools").getValue(String.class);
                hotelpool.setText(pool);



                String image1 = dataSnapshot.child("imege").getValue(String.class);
                String image2 = dataSnapshot.child("image1").getValue(String.class);
                String image3 = dataSnapshot.child("image2").getValue(String.class);
                String image4 = dataSnapshot.child("image3").getValue(String.class);

                imageUrls[0]=image1;
                imageUrls[1]=image2;
                imageUrls[2]=image3;
                imageUrls[3]=image4;

                ViewPager viewPager = findViewById(R.id.pager);
                ViewPagerAdapter adapter = new ViewPagerAdapter(HotelBook.this, imageUrls);
                viewPager.setAdapter(adapter);

               DotsIndicator dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
                dotsIndicator.setViewPager(viewPager);

                String link = dataSnapshot.child("link").getValue(String.class);
                linkopen(link);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
    public void back(View view) {
        Intent intent = new Intent (HotelBook.this, MainActivity.class);
        startActivity(intent);
    }
    public void linkopen(final String name){

        ImageView linkopen = findViewById(R.id.open);
        linkopen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
                startActivity(browserIntent);
            }
        });

    }
}
