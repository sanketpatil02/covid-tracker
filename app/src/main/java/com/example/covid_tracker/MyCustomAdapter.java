package com.example.covid_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<CountryModel>{

    private Context context;
    private List<CountryModel> countryModelsList;
    private List<CountryModel> countryModelsListFilter;

    public MyCustomAdapter(Context context, List<CountryModel>countryModelsList) {
        super(context, R.layout.list_custom_item, countryModelsList);

        this.context = context;
        this.countryModelsList = countryModelsList;
        this.countryModelsListFilter = countryModelsList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        ImageView imageView = view.findViewById(R.id.imageFlag);

        tvCountryName.setText(countryModelsListFilter.get(position).getCountry());
        Glide.with(context).load(countryModelsListFilter.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFilter.size();
    }

    @Override
    public CountryModel getItem(int position) {
        return countryModelsListFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public Filter getFilter() {
//        return super.getFilter();
//    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;
                }
                else {
                    List<CountryModel> resultsModel = new ArrayList<> ();
                    String searchStr = constraint.toString().toLowerCase();
                    for(CountryModel itemsModel: countryModelsList) {
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryModelsListFilter = (List<CountryModel>) results.values;
                AffectedCountries.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
