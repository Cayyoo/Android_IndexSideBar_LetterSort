package com.example.sidebar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidebar.adapter.DefaultCityAdapter;
import com.example.sidebar.adapter.ShowCityAdapter;
import com.example.sidebar.db.CityDBHelper;
import com.example.sidebar.db.CityInfo;
import com.example.sidebar.widget.ClearEditText;
import com.example.sidebar.widget.LetterSortView;

import java.util.ArrayList;
import java.util.List;

/**
 * 共有两个列表：
 * 1、默认呈现的列表
 * 2、展示搜索内容的列表（搜索后，覆盖默认列表）
 */

/**
 * assets目录下有city.db文件
 *
 * 类似联系人列表搜索功能，通过数据库操作，查询城市
 */
public class CitySelMainActivity extends Activity {

	private Context context = CitySelMainActivity.this;
	
	private ListView listView;
	private List<CityInfo> mlist = new ArrayList<>();
	private DefaultCityAdapter mAdapter;
	
	private ClearEditText mClearEditText;
	
	private ListView mSearchList;
	private ShowCityAdapter mSearchAdapter;
	private FrameLayout mSearchContainer;
	private View mCityContainer;
	
	private LetterSortView letterView;
	private TextView mLetterText;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			mAdapter.updateListView(mlist);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.city_main);
        
        initView();
        setListener();
        initData();
    }

    private void initData(){
    	new Thread(new Runnable() {
			@Override
			public void run() {
				CityDBHelper dbHelper = new CityDBHelper(context);
				mlist = dbHelper.getCityDB().getAllCity();
				mHandler.sendEmptyMessage(0);
			}
		}).start();
    	
    	mAdapter = new DefaultCityAdapter(context, mlist);
    	listView.setAdapter(mAdapter);
    }
    
    private void initView(){
    	listView = (ListView)findViewById(R.id.list);
    	mClearEditText = (ClearEditText)findViewById(R.id.edit_msg_search);
    	mCityContainer = findViewById(R.id.city_content_container);
    	mSearchContainer = (FrameLayout)findViewById(R.id.search_container);
    	mSearchList = (ListView)findViewById(R.id.search_list);
    	mSearchContainer.setVisibility(View.GONE);
    	
    	mLetterText = (TextView)findViewById(R.id.city_middle_letter);
    	letterView = (LetterSortView)findViewById(R.id.right_letter);
    }
    
    private void setListener(){
    	listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(CitySelMainActivity.this,mAdapter.getItem(position).getCity(),Toast.LENGTH_SHORT).show();
			}
		});

    	mSearchList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(CitySelMainActivity.this,mSearchAdapter.getItem(position).getCity(),Toast.LENGTH_SHORT).show();
			}
		});
    	
    	mClearEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				mSearchAdapter = new ShowCityAdapter(CitySelMainActivity.this, mlist);
				mSearchList.setAdapter(mSearchAdapter);
				mSearchList.setTextFilterEnabled(true);
				
				if(mlist.size() < 1 || TextUtils.isEmpty(s.toString())){
					mCityContainer.setVisibility(View.VISIBLE);
					mSearchContainer.setVisibility(View.INVISIBLE);
				} else {
					mCityContainer.setVisibility(View.INVISIBLE);
					mSearchContainer.setVisibility(View.VISIBLE);
					mSearchAdapter.getFilter().filter(s.toString());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {

			}
			
			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
    	
    	letterView.setOnTouchChangedListener(new LetterSortView.OnTouchChangedListener() {
			@Override
			public void onTouchUp() {
				mLetterText.setVisibility(View.GONE);
			}
			
			@Override
			public void onTouchChanged(String s) {
				mLetterText.setText(s);
				mLetterText.setVisibility(View.VISIBLE);
				
				int position = mAdapter.getPostionForSection(s.charAt(0));
				if(position != -1){
					listView.setSelection(position);
				}
			}

		});

    }
    
}
