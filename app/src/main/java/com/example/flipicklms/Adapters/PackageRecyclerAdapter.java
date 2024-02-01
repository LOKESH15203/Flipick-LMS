package com.example.flipicklms.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flipicklms.DescriptionActivity;
import com.example.flipicklms.R;
import com.example.flipicklms.Resources.PackageCourseInformation;

import java.util.ArrayList;
import java.util.Objects;

public class PackageRecyclerAdapter extends RecyclerView.Adapter<PackageRecyclerAdapter.SingleItemRowHolder> {
    private ArrayList<PackageCourseInformation> itemsList;
    private Context mContext;

    public PackageRecyclerAdapter(Context context, ArrayList<PackageCourseInformation> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reccycler_childs_child, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        PackageCourseInformation singleItem = itemsList.get(i);

        holder.textDuration.setText(singleItem.getCourseHours());
        if (!Objects.equals(singleItem.getAgeGuidance(), ""))
        {
            holder.imageU.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageU.setVisibility(View.INVISIBLE);

        }

        Glide.with(mContext).load(singleItem.getCourseThumbnail()).skipMemoryCache(true).into( holder.itemImage);
        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("videoUrl", singleItem.getVideoURL());
                intent.putExtra("thumb", singleItem.getCourseThumbnail());
                intent.putExtra("title", singleItem.getCourseName());
                intent.putExtra("description", singleItem.getCourseDescription());
                intent.putExtra("age", singleItem.getAgeGuidance());
                intent.putExtra("duration", singleItem.getCourseHours());
//                    intent.putExtra("hd", content.ageGuidance)
//                    intent.putExtra("age", content.ageGuidance)
                intent.putExtra("course_id", singleItem.getCourseId());
//                intent.putExtra("student_id", sharedPrefManager.getMobid());
                mContext.startActivity(intent);
            }
        });
        holder.imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("videoUrl", singleItem.getVideoURL());
                intent.putExtra("thumb", singleItem.getCourseThumbnail());
                intent.putExtra("title", singleItem.getCourseName());
                intent.putExtra("description", singleItem.getCourseDescription());
                intent.putExtra("age", singleItem.getAgeGuidance());
                intent.putExtra("duration", singleItem.getCourseHours());
//                    intent.putExtra("hd", content.ageGuidance)
//                    intent.putExtra("age", content.ageGuidance)
                intent.putExtra("course_id", singleItem.getCourseId());
//                intent.putExtra("student_id", sharedPrefManager.getMobid());
                mContext.startActivity(intent);
            }
        });
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("videoUrl", singleItem.getVideoURL());
                intent.putExtra("thumb", singleItem.getCourseThumbnail());
                intent.putExtra("title", singleItem.getCourseName());
                intent.putExtra("description", singleItem.getCourseDescription());
                intent.putExtra("age", singleItem.getAgeGuidance());
                intent.putExtra("duration", singleItem.getCourseHours());
//                    intent.putExtra("adUrl", singleItem.)
//                    intent.putExtra("age", content.ageGuidance)
                intent.putExtra("course_id", singleItem.getCourseId());
//                intent.putExtra("student_id", sharedPrefManager.getMobid());
                mContext.startActivity(intent);


            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle,tvtitle2;
        protected SeekBar customProgress;
        protected ImageView itemImage,itemImage_2;

        protected TextView textDuration;
        protected ImageView imageU,imageHD,imgInfo,imagePlay;




        public SingleItemRowHolder(View view) {
            super(view);
//            this.tvTitle = (TextView) view.findViewById(R.id.title1);
            this.textDuration = (TextView) view.findViewById(R.id.textDuration);
            this.itemImage = (ImageView) view.findViewById(R.id.image_child);
//            this.customProgress = (SeekBar) view.findViewById(R.id.customProgress);
            this.imageU = (ImageView) view.findViewById(R.id.imageU);
            this.imageHD = (ImageView) view.findViewById(R.id.imageHD);
            this.imgInfo = (ImageView) view.findViewById(R.id.imgInfo);
            this.imagePlay = (ImageView) view.findViewById(R.id.imgPlay);
//            this.imagePlay.setVisibility(View.GONE);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

}
