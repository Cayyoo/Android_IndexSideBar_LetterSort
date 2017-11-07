/**
 * 
 */
package com.example.sidebar.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.example.sidebar.R;


/**
 * @author Administrator
 *
 */
public class ClearEditText extends EditText implements OnFocusChangeListener{
	
	private Drawable mClearDrawable;
	
	public ClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		mClearDrawable = getCompoundDrawables()[2];

		if(mClearDrawable == null){
			//mClearDrawable = getResources().getDrawable(R.drawable.search_clear);//过时
			mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.search_clear);
			//mClearDrawable = ResourcesCompat.getDrawable(getResources(),R.drawable.search_clear,null);
		}
		
		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());

		setClearIconVisible(false);
		setOnFocusChangeListener(this);

		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				setClearIconVisible(arg0.length() > 0);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {

			}
			
			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		
	}
	
	protected void setClearIconVisible(boolean visible){
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(getCompoundDrawables()[2] != null && event.getAction() == MotionEvent.ACTION_UP){
			boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth())
					&& event.getX() < (getWidth() - getPaddingRight());
			
			if(touchable){
				setText("");
			}
		}

		return super.onTouchEvent(event);
	}
	
	
}
