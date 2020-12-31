package com.troy.safer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class SearchPlaceAdapter extends RecyclerView.Adapter<SearchPlaceAdapter.SearchPlaceAdapterViewHolder> implements Filterable {

    Context mCntx;
    public ArrayList<Recyclemodel> arrayList;
    public ArrayList<Recyclemodel> arrayListFiltered;

    public SearchPlaceAdapter(Context mCntx, ArrayList<Recyclemodel> arrayList)
    {
        this.mCntx = mCntx;
        this.arrayList = arrayList;
        this.arrayListFiltered = new ArrayList<>(arrayList);
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public SearchPlaceAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeitems, parent, false);

        SearchPlaceAdapterViewHolder viewHolder = new SearchPlaceAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchPlaceAdapterViewHolder holder, final int position)
    {
        final Recyclemodel model = arrayList.get(position);

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


    public class SearchPlaceAdapterViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public SearchPlaceAdapterViewHolder(View itemView){
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

        Intent i = new Intent(mCntx, HotelBook.class);
        public void openhotel(String name){


            Bundle bundle = new Bundle();
            bundle.putString("name1", name);
            i.putExtras(bundle);

            ImageView info = mView.findViewById(R.id.open);
            info.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mCntx.startActivity(i);
                }
            });

        }
    }

    public Filter getFilter()
    {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Recyclemodel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Recyclemodel item : arrayListFiltered) {
                    if (item.getHotelname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}