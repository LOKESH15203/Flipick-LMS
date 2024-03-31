package com.example.flipicklms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.flipicklms.R;
import com.example.flipicklms.MainActivity;
import com.example.flipicklms.Resources.HomeClass;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter
        {

    private Context context;
    private ArrayList<HomeClass> arrayList;

    public ViewPagerAdapter(Context context, ArrayList<HomeClass> pager_modelArrayList) {
        this.context = context;
        arrayList = pager_modelArrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager_layout, null);
        HomeClass currentItem = arrayList.get(position);
        Log.d("zzz","ViewPagerAdapter:  "+arrayList.get(position));
        ImageView imageview = view.findViewById(R.id.imageView);
        ImageView imageviewPlay = view.findViewById(R.id.imgPlay);
        ImageView imageviewInfo = view.findViewById(R.id.imgInfo);

        TextView tvTitle=view.findViewById(R.id.pager_title);
        tvTitle.setText(currentItem.getCourseName());
        Glide.with(context).load(currentItem.getCourseThumbnail()).skipMemoryCache(true).into(imageview);

//        imageviewPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                MainActivity.layoutSubscriber.setVisibility(View.VISIBLE);
//
//            }
//        });

//        imageviewInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.layoutSubscriber.setVisibility(View.VISIBLE);
//
//            }
//        });


//        imageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                MainActivity.layoutSubscriber.setVisibility(View.VISIBLE);
//
//            }
//        });


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

}
