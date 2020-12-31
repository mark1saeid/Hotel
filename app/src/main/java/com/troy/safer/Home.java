package com.troy.safer;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.Locale;


public class Home extends Fragment {
   View view;


    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Recyclemodel, NewsViewHolder> mPeopleRVAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);





        EditText editText = view.findViewById(R.id.searchView);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
             //   filter(s.toString());
            }
        });






        final ShimmerRecyclerView mShimmerRecyclerView = view.findViewById(R.id.shimmer_recycler_view);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mShimmerRecyclerView.setLayoutManager(layoutManager,
                R.layout.homeitems);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("hotels");
        mDatabase.keepSynced(true);
        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("hotels");
        Query personsQuery = personsRef.orderByKey();

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Recyclemodel>().setQuery(personsQuery, Recyclemodel.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<Recyclemodel, Home.NewsViewHolder>(personsOptions) {
            @Override
            protected void onBindViewHolder(Home.NewsViewHolder holder, final int position, final Recyclemodel model) {
                holder.setBednum(model.getBednum());
                holder.setHotelname(model.getHotelname());
                holder.setPlace(model.getPlace());
                holder.setPrice(model.getPrice());
                holder.setStars(model.getStars());
                holder.setimege(model.getImege());

                holder.setpool(model.getPools());
                holder.setfood(model.getFood());
                holder.openhotel(model.getHotelname());

            }

            @Override
            public Home.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.homeitems, parent, false);

                return new Home.NewsViewHolder(view);

            }
        };





        mShimmerRecyclerView.setAdapter(mPeopleRVAdapter);
        mShimmerRecyclerView.showShimmer();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShimmerRecyclerView mShimmerRecyclerView = view.findViewById(R.id.shimmer_recycler_view);
                mShimmerRecyclerView.hideShimmer();
            }
        }, 2000);



        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.ref);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShimmerRecyclerView.setAdapter(mPeopleRVAdapter);
                mShimmerRecyclerView.showShimmer();


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShimmerRecyclerView mShimmerRecyclerView = view.findViewById(R.id.shimmer_recycler_view);
                        mShimmerRecyclerView.hideShimmer();
                        pullToRefresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });

   return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mPeopleRVAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();



    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }


        public void setHotelname(String hotelname) {
          TextView name = mView.findViewById(R.id.hotelname);
          name.setText(hotelname);
        }

        public void setBednum(String bednum) {
            TextView bed = mView.findViewById(R.id.bednum);
            bed.setText(bednum);
        }
        public void setPrice(String price) {
            TextView pr = mView.findViewById(R.id.price);
            pr.setText(price+" "+"LE.");
        }
        public void setStars(String stars) {
            RatingBar ratingBar = mView.findViewById(R.id.ratingBar);
            if (stars.equals("")){}
            else {
                float s = Float.parseFloat(stars);
            ratingBar.setRating(s);
            }
        }
        public void setPlace(String place) {
            TextView pl = mView.findViewById(R.id.hotelplace);
            pl.setText(place);
        }
        public void setimege(String imege) {
if (imege != null){
    RoundedImageView im = mView.findViewById(R.id.imageView1);
    Transformation transformation = new RoundedTransformationBuilder()
            .oval(false)
            .build();
    Picasso.get()
            .load(imege)
            .fit()
            .transform(transformation)
            .into(im);

}{

            }
        }


        public void setpool(String pool) {
            TextView pl = mView.findViewById(R.id.pool);
            pl.setText(pool);
        }
        public void setfood(String food) {
            TextView pl = mView.findViewById(R.id.food);
            pl.setText(food);
        }

        Intent i = new Intent(getActivity(), HotelBook.class);
        public void openhotel(String name){


            Bundle bundle = new Bundle();
            bundle.putString("name1", name);
            i.putExtras(bundle);

            ImageView info = mView.findViewById(R.id.open);
            info.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(i);
                }
            });

        }


    }











}

