package com.taxiking.driver.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.taxiking.driver.R;

public class Slider extends View implements OnGestureListener {
	private Drawable leftSliderImg, rightSliderImg;
	private int leftSliderX, rightSliderX;
	private int slidersHeight, slidersWidth;
	private GestureDetector mGestures;
	private SliderTriggered mTriggerListener;
	private boolean slidingLeftHandle, slidingRightHandle;
	private static final double mCoeff = 0.8;
	
	public Slider(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestures = new GestureDetector(getContext(), this);
		leftSliderImg = getResources().getDrawable(R.drawable.slider_left);
		rightSliderImg = getResources().getDrawable(R.drawable.slider_right);
		
		slidersHeight = leftSliderImg.getIntrinsicHeight();
		slidersWidth = leftSliderImg.getIntrinsicWidth();
		
		leftSliderX = 0;
		rightSliderX = 0;
		slidingLeftHandle = slidingRightHandle = false;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		rightSliderImg.setBounds(0, getHeight() - slidersHeight, getWidth(), getHeight());
		rightSliderImg.draw(canvas);
		
		leftSliderImg.setBounds(0, getHeight() - slidersHeight, slidersWidth + leftSliderX, getHeight());
		leftSliderImg.draw(canvas);
		
		if (slidingLeftHandle && Math.abs(leftSliderX) >= mCoeff * getWidth()) {
			mTriggerListener.onLeftHandleTriggered();
		} else if (slidingRightHandle && rightSliderX >= mCoeff * getWidth()) {
			mTriggerListener.onRightHandleTriggered();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			leftSliderX = 0;
			rightSliderX = 0;
			slidingLeftHandle = slidingRightHandle = false;
			invalidate();
		}
		
		return mGestures.onTouchEvent(event); 
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (e1.getY() < getHeight() - slidersHeight) {
			return false;
		}
		
		if (e1.getX() < getWidth() / 2) {
			leftSliderX -= distanceX;
			leftSliderX = Math.max(0, leftSliderX);
			slidingLeftHandle = true;
		} else {
//			rightSliderX += distanceX;
//			slidingRightHandle = true;
		}
		invalidate();
		
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	public void setOnTriggerListener(SliderTriggered listener) {
        mTriggerListener = listener;
    }

	public interface SliderTriggered {
		public void onLeftHandleTriggered();
		public void onRightHandleTriggered();
	}
}