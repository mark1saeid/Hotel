package com.troy.safer;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;


public class test extends Fragment {




    public test() {
        // Required empty public constructor
    }


    private SearchView mSearch;
    private DatabaseReference mRef;
    private SearchPlaceAdapter mAdapter;
    ArrayList<Recyclemodel> arrayList;


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test, container, false);


        final ShimmerRecyclerView mShimmerRecyclerView = view.findViewById(R.id.shimmer_recycler_view);

        mShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()),
                R.layout.homeitems);


        //Recylerview of place

        mShimmerRecyclerView.setHasFixedSize(true);



        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("hotels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    arrayList.add(eventSnapshot.getValue(Recyclemodel.class));
                }

                mAdapter = new SearchPlaceAdapter(getContext(), arrayList);
                mShimmerRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mShimmerRecyclerView.showShimmer();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShimmerRecyclerView mShimmerRecyclerView = view.findViewById(R.id.shimmer_recycler_view);
                mShimmerRecyclerView.hideShimmer();
            }
        }, 2000);

        //searchView of place
        mSearch = (SearchView) view.findViewById(R.id.searchView);
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapter!=null){
                    mAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });


        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.ref);
        pullToRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShimmerRecyclerView.setAdapter(mAdapter);
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

}
