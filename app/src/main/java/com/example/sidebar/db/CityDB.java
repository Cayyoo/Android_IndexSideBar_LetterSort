package com.example.sidebar.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CityDB {
	public static final String CITY_DB_NAME = "city.db";
	private static final String CITY_TABLE_NAME = "city";

	private SQLiteDatabase db;

	public CityDB(Context context, String path){
		db = context.openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
	}

	public List<CityInfo> getAllCity(){
		List<CityInfo> list = new ArrayList<CityInfo>();

		Cursor cursor = db.rawQuery("SELECT * from " + CITY_TABLE_NAME + " order by firstpy", null);
		while(cursor.moveToNext()){
			String province = cursor.getString(cursor.getColumnIndex("province"));
			String city = cursor.getString(cursor.getColumnIndex("city"));
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String allPY = cursor.getString(cursor.getColumnIndex("allpy"));
			String allFirstPY = cursor.getString(cursor.getColumnIndex("allfirstpy"));
			String firstPY = cursor.getString(cursor.getColumnIndex("firstpy"));

			CityInfo item = new CityInfo(province, city, number, firstPY, allPY, allFirstPY);
			list.add(item);
		}
		return list;
	}
}
