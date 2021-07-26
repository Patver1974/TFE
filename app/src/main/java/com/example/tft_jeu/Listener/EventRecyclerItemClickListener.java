package com.example.tft_jeu.Listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EventRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnEventClickListener callback;

    public interface OnEventClickListener {
        void onEventClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public EventRecyclerItemClickListener(Context context, OnEventClickListener callback) {
        this.callback = callback;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && callback != null && mGestureDetector.onTouchEvent(e)) {
            callback.onEventClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

