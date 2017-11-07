package com.example.sidebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.sidebar.R;
import com.example.sidebar.db.CityInfo;

import java.util.ArrayList;
import java.util.List;

public class ShowCityAdapter extends BaseAdapter implements Filterable{

	private List<CityInfo> mAllCitys;
	private List<CityInfo> mResults;
	private Context mContext;
	
	public ShowCityAdapter(Context mContext, List<CityInfo> mAllCitys) {
		this.mAllCitys = mAllCitys;
		this.mContext = mContext;
		this.mResults = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mResults!=null? mResults.size() :0;
	}

	@Override
	public CityInfo getItem(int postion) {
		return mResults.get(postion);
	}

	@Override
	public long getItemId(int postion) {
		return postion;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_show_city_list, null);
		}
		
		TextView province = (TextView)convertView.findViewById(R.id.search_provice_title);
		province.setText(mResults.get(postion).getProvince());
		
		TextView city = (TextView)convertView.findViewById(R.id.search_city_title);
		city.setText(mResults.get(postion).getCity());
		return convertView;
	}
//
//	@Override
//	public Filter getFilter() {
//		Filter filter = new Filter(){
//
//			@Override
//			protected FilterResults performFiltering(CharSequence s) {
//				String str = s.toString().toUpperCase();
//				
//				FilterResults result = new FilterResults();
//				ArrayList<CityInfo> cityList = new ArrayList<CityInfo>();
//				if(mAllCitys != null && mAllCitys.size() != 0){
//					for(CityInfo cb : mAllCitys){
//						if(cb.getAllFirstPY().indexOf(str) > -1
//								|| cb.getAllPY().indexOf(str) > -1
//								|| cb.getCity().indexOf(str) > -1){
//							cityList.add(cb);
//						}
//					}
//				}
//				
//				result.values = cityList;
//				result.count = cityList.size();
//				return result;
//			}
//
//			@Override
//			protected void publishResults(CharSequence s, FilterResults result) {
//				mResults = (List<CityInfo>)result.values;
//				if(result.count > 0){
//					notifyDataSetChanged();
//				} else {
//					notifyDataSetInvalidated();
//				}
//			}
//			
//		};
//		return filter;
//	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				mResults = (List<CityInfo>)results.values;

				if(results.count > 0){
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				String s = constraint.toString().toUpperCase();
				
				List<CityInfo> filterList = new ArrayList<>();
				for(CityInfo city : mAllCitys){
					if(city.getFirstPY().indexOf(s) > -1 || city.getAllPY().indexOf(s) > -1 || city.getAllFirstPY().indexOf(s) > -1){
						filterList.add(city);
					}
				}

				FilterResults fr = new FilterResults();
				fr.values = filterList;
				fr.count = filterList.size();
				return fr;
			}
		};
		return filter;
	}

}
