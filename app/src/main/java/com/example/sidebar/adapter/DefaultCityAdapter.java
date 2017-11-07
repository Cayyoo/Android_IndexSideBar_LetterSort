package com.example.sidebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sidebar.R;
import com.example.sidebar.db.CityInfo;

import java.util.List;

public class DefaultCityAdapter extends BaseAdapter{
	
	private Context context;
	private List<CityInfo> data;

	public DefaultCityAdapter(Context context, List<CityInfo> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data !=null ? data.size():0;
	}

	@Override
	public CityInfo getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_default_city_list, null);
			
			holder = new ViewHolder();
			holder.group_title = (TextView)convertView.findViewById(R.id.group_title);
			holder.city_title = (TextView)convertView.findViewById(R.id.city_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		CityInfo city = data.get(position);
		
		final String cityname = city.getCity();
		final String avater = city.getFirstPY();
		
		holder.city_title.setText(cityname);
		
		if(getPostionForSection(getSectionForPostion(position)) == position){
			holder.group_title.setVisibility(View.VISIBLE);
			holder.group_title.setText(avater);
		} else {
			holder.group_title.setVisibility(View.GONE);
		}

		return convertView;
	}
	
	public void updateListView(List<CityInfo> list){
		this.data = list;
		notifyDataSetChanged();
	}
	
	public int getSectionForPostion(int position){
		return data.get(position).getFirstPY().charAt(0);
	}
	
	public int getPostionForSection(int section){
		for(int i=0; i<getCount(); i++){
			String sortStr = data.get(i).getFirstPY();
			char firstChar = sortStr.toUpperCase().charAt(0);
			
			if(firstChar == section){
				return i;
			}
		}
		
		return -1;
	}
	
	static class ViewHolder{
		TextView group_title;
		TextView city_title;
	}

}
