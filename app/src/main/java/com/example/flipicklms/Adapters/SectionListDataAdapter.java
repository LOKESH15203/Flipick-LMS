package com.example.flipicklms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flipicklms.MainActivity;
import com.example.flipicklms.Resources.Data;
import com.example.flipicklms.R;
import java.util.ArrayList;
import java.util.Objects;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<Data> itemsList;
    private Context mContext;
    public SectionListDataAdapter(Context context, ArrayList<Data> itemsList) {
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
        Data singleItem = itemsList.get(i);

//        Log.d("aaaa","imageUrl"+singleItem.getUrl());

        String result = singleItem.getCourseHours().substring(3);
        holder.textDuration.setText(result);
        if (!Objects.equals(singleItem.getAgeGuidance(), ""))
        {
            holder.imageU.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageU.setVisibility(View.INVISIBLE);

        }

        Glide.with(mContext).load(singleItem.getCourseThumbnail()).skipMemoryCache(true).into( holder.itemImage);


//        holder.itemImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mContext instanceof MainActivity) {
//                    ((MainActivity)mContext).openRegisterDialog();
//                }
//
//            }
//        });
        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, DescriptionActivity.class);
//                intent.putExtra("videoUrl", singleItem.getVideoURL());
//                intent.putExtra("thumb", singleItem.getCourseThumbnail());
//                intent.putExtra("title", singleItem.getCourseName());
//                intent.putExtra("description", singleItem.getCourseDescription());
//                intent.putExtra("age", singleItem.getAgeGuidance());
//                intent.putExtra("duration", singleItem.getCourseHours());
////                    intent.putExtra("hd", content.ageGuidance)
////                    intent.putExtra("age", content.ageGuidance)
//                intent.putExtra("course_id", singleItem.getCourseId());
////                intent.putExtra("student_id", sharedPrefManager.getMobid());
//                mContext.startActivity(intent);
//                if (mContext instanceof MainActivity) {
//                    ((MainActivity)mContext).openRegisterDialog();
//                }
            }
        });
//        holder.imgPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mContext instanceof MainActivity) {
//                    ((MainActivity)mContext).openRegisterDialog();
//                }
//            }
//        });



        //        holder.tvTitle.setText(singleItem.getTitle().toString());



    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView textDuration,tvtitle2;
        protected SeekBar customProgress;
        protected ImageView itemImage,imageU,imageHD,imgInfo,imgPlay;



        public SingleItemRowHolder(View view) {
            super(view);

//            this.tvTitle = (TextView) view.findViewById(R.id.title1);
            this.textDuration = (TextView) view.findViewById(R.id.textDuration);
            this.itemImage = (ImageView) view.findViewById(R.id.image_child);
//            this.customProgress = (SeekBar) view.findViewById(R.id.customProgress);
            this.imageU = (ImageView) view.findViewById(R.id.imageU);
            this.imgPlay=(ImageView) view.findViewById(R.id.imgPlay);
            this.imageHD = (ImageView) view.findViewById(R.id.imageHD);
            this.imgInfo = (ImageView) view.findViewById(R.id.imgInfo);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((MainActivity)v.getContext()).openRegisterDialog();
//
//                }
//            });


        }

    }

}