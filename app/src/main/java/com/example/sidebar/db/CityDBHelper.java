package com.example.sidebar.db;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CityDBHelper {
	private Context context;
	private CityDB mcityDB;
	
	public CityDBHelper(Context context){
		this.context = context;
	}
	
	public CityDB getCityDB(){
		if(mcityDB == null){
			mcityDB = openCityDB();
		}
		return mcityDB;
	}
	
	private CityDB openCityDB(){
		String path = "/data" + Environment.getDataDirectory().getAbsolutePath()
				+ File.separator + "com.example.sidebar"
				+ File.separator + CityDB.CITY_DB_NAME;
		
		File db = new File(path);

		if(!db.exists()){
			try {
				InputStream is = context.getAssets().open("city.db");
				FileOutputStream fout = new FileOutputStream(db);
				int len = -1;
				byte[] buffer = new byte[1024];

				while((len = is.read(buffer)) != -1){
					fout.write(buffer, 0, len);
					fout.flush();
				}
				
				fout.close();
				is.close();
			} catch (IOException e) {

			}
		}
		return new CityDB(context, path);
	}

}
