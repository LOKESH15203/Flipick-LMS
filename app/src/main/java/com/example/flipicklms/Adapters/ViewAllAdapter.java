// ##########################
// ##########################
// PLAYER activity
// ##########################
// ##########################


package com.example.flipicklms.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.flipicklms.DescriptionActivity;
import com.example.flipicklms.MainActivity;
import com.example.flipicklms.Resources.Data;
import com.example.flipicklms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.SingleItemRowHolder> {
    private ArrayList<Data> itemsList;
    private Context mContext;

    public ViewAllAdapter(Context context, ArrayList<Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reccycler_childs_child, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        Data singleItem = itemsList.get(i);

        Log.d("aaaa","imageUrl"+singleItem.getCourseThumbnail());

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
//                ((MainActivityWithLogin)mContext).showInfo(singleItem.getCourseDescription());

            }
        });
        holder.imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ((MainActivityWithLogin)mContext).playVideo(singleItem.getCourseId());
                callApi(singleItem.getCourseId());


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
//                    intent.putExtra("hd", content.ageGuidance)
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

        }

    }

    private void callApi(String courseID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://lms1.flipick.com/cmwebapi/api/StudentCourseHBI/GetInstituteCourseListByStudent2",
                response -> {
                    try {
                        Log.d("zzz","getToken response: "+response);
                        JSONObject jsonObject1 = new JSONObject(response);
                        JSONArray jsonArray = jsonObject1.getJSONArray("StudentCourseList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectItems = jsonArray.getJSONObject(i);
                            // Intent intent = new Intent(mContext, PlayerActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra("videoUrl",jsonObjectItems.getString("BookUrl") );
//                            intent.putExtra("title", jsonObjectItems.getString("CourseName"));
//                            mContext.startActivity(intent);
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }, volleyError -> Log.e("alert", "error" + volleyError)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //################################################################################
                //################################################################################
                //################################################################################
                //################################################################################
//                params.put("StudentId" , MainActivity.Companion.getStudentID());
                //################################################################################
                //################################################################################
                //################################################################################
                //################################################################################
                params.put("CourseId" , courseID);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

}