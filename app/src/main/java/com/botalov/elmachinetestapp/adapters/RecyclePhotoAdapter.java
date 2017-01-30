package com.botalov.elmachinetestapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.botalov.elmachinetestapp.R;
import com.botalov.elmachinetestapp.models.PhotoModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecyclePhotoAdapter extends RecyclerView.Adapter<RecyclePhotoAdapter.ViewHolder> implements Filterable {

    private List<PhotoModel> photosData;
    private Context context;
    private PhotoFilter filter;


    public RecyclePhotoAdapter(List<PhotoModel> dataset, Context context) {
        this.photosData = dataset;
        this.context = context;
    }

    @Override
    public RecyclePhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            final PhotoModel photoModel = photosData.get(position);
            String pathToPhoto;
            if(photoModel.getIsAlbum()){
                pathToPhoto = context.getString(R.string.baseimageUrl) + photoModel.getCover() + ".jpg";
            }
            else {
                pathToPhoto = context.getString(R.string.baseimageUrl) + photoModel.getId() + ".jpg";
            }
            RequestCreator rec = Picasso.with(context).load(pathToPhoto);
            if(rec!=null){
                rec.into(holder.photoImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.titleTextView.setText(photoModel.getTitle());
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return photosData.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
            filter = new PhotoFilter(this, photosData);
        return filter;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView photoImageView;

        ViewHolder(View v) {
            super(v);
            titleTextView = (TextView)v.findViewById(R.id.photoTitleTextView);
            photoImageView = (ImageView) v.findViewById(R.id.photoImageView);
        }
    }



    private static class PhotoFilter extends Filter {
        private final RecyclePhotoAdapter adapter;
        private final List<PhotoModel> originalList;
        private final List<PhotoModel> filteredList;

        private PhotoFilter(RecyclePhotoAdapter adapter, List<PhotoModel> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final PhotoModel user : originalList) {
                    if (user.getTitle().toLowerCase().trim().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.photosData.clear();
            adapter.photosData.addAll((ArrayList<PhotoModel>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
