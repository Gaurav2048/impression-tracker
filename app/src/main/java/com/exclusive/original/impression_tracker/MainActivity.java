package com.exclusive.original.impression_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.exclusive.original.impression_tracker.Helper.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView impressionRecyclerView;
    LinearLayoutManager layoutManager;
    Map<Integer, Integer> impressions = new HashMap<>();

    int firstCompleteVisiblePosition = 0, lastCompleteVisibleItem ;
    double lastIdleTimeStamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        impressionRecyclerView = findViewById(R.id.impressionRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        impressionRecyclerView.setLayoutManager(layoutManager);

        impressionRecyclerView.setAdapter(new ImpressionAdapter());
        lastIdleTimeStamp = System.currentTimeMillis();


        impressionRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                firstCompleteVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                lastCompleteVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                impressionRecyclerView.addOnScrollListener(scrollListener);
            }
        });



    }

    boolean isRecyclerViewScrollingAfterIdle = false;

    RecyclerView.OnScrollListener scrollListener =  new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                if(!isRecyclerViewScrollingAfterIdle) return;
                double currentIdleTimeStamp = System.currentTimeMillis();
                if(Utils.impressionChecker(lastIdleTimeStamp, currentIdleTimeStamp )){
                    updateImpressionList();
                }

                firstCompleteVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                lastCompleteVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                lastIdleTimeStamp = System.currentTimeMillis();
                isRecyclerViewScrollingAfterIdle = false;
            }else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                isRecyclerViewScrollingAfterIdle = true;
            }
        }
    };

    private void updateImpressionList() {
        for(int i = firstCompleteVisiblePosition; i <= lastCompleteVisibleItem; i++){
            if(impressions.get(i)==null){
                impressions.put(i, 1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.e( "onDestroy: ", new Gson().toJson(impressions));
        Intent intent = new Intent(MainActivity.this, IntentService.class);
        intent.putExtra("check", "data");
        startService(intent);
        super.onDestroy();
    }
}