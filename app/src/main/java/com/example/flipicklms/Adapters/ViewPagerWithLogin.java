package com.example.flipicklms.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.flipicklms.DescriptionActivity;
import com.example.flipicklms.MainActivity;
import com.example.flipicklms.PlayerActivity;
import com.example.flipicklms.R;
import com.example.flipicklms.Resources.HomeModelWithLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewPagerWithLogin extends PagerAdapter
{

    private Context context;
    private ArrayList<HomeModelWithLogin> arrayList;

    public ViewPagerWithLogin(Context context, ArrayList<HomeModelWithLogin> pager_modelArrayList) {
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
        HomeModelWithLogin currentItem = arrayList.get(position);
        Log.d("zzz","ViewPagerAdapter:  "+arrayList.get(position));
        ImageView imageview = view.findViewById(R.id.imageView);
        ImageView imageviewPlay = view.findViewById(R.id.imgPlay);
        ImageView imageviewInfo = view.findViewById(R.id.imgInfo);
        TextView tvTitle=view.findViewById(R.id.pager_title);
        tvTitle.setText(currentItem.getCourseName());
        Glide.with(context).load(currentItem.getCourseThumbnail()).skipMemoryCache(true).into(imageview);
        imageviewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApi(currentItem.getCourseId());
            }
        });

//        if (currentItem.getIsPackage().equals("1"))
//        {
//            imageviewPlay.setVisibility(View.INVISIBLE);
//        }
//        else {
//            imageviewPlay.setVisibility(View.VISIBLE);
//        }



        imageviewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("videoUrl", currentItem.getVideoURL());
                intent.putExtra("thumb", currentItem.getCourseThumbnail());
                intent.putExtra("title", currentItem.getCourseName());
                intent.putExtra("description", currentItem.getCourseDescription());
                intent.putExtra("age", currentItem.getAgeGuidance());
                intent.putExtra("duration", currentItem.getCourseHours());
//                    intent.putExtra("hd", content.ageGuidance)
//                    intent.putExtra("age", content.ageGuidance)
                intent.putExtra("course_id", currentItem.getCourseId());
//                intent.putExtra("student_id", sharedPrefManager.getMobid());
                context.startActivity(intent);
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 {
                     Intent intent = new Intent(context, DescriptionActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     intent.putExtra("videoUrl", currentItem.getVideoURL());
                     intent.putExtra("thumb", currentItem.getCourseThumbnail());
                     intent.putExtra("title", currentItem.getCourseName());
                     intent.putExtra("description", currentItem.getCourseDescription());
                     intent.putExtra("age", currentItem.getAgeGuidance());
                     intent.putExtra("duration", currentItem.getCourseHours());
//                    intent.putExtra("hd", content.ageGuidance)
//                    intent.putExtra("age", content.ageGuidance)
                     intent.putExtra("course_id", currentItem.getCourseId());
//                intent.putExtra("student_id", sharedPrefManager.getMobid());
                     context.startActivity(intent);
                }
            }
        });


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


    private void callApi(String courseID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://lms1.flipick.com/cmwebapi/api/StudentCourseHBI/GetInstituteCourseListByStudent2",
                response -> {
                    try {
                        Log.d("zzz","getToken response: "+response);
                        JSONObject jsonObject1 = new JSONObject(response);
                        JSONArray jsonArray = jsonObject1.getJSONArray("StudentCourseList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectItems = jsonArray.getJSONObject(i);
                            Intent intent = new Intent(context, PlayerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("videoUrl",jsonObjectItems.getString("BookUrl") );
                            intent.putExtra("title", jsonObjectItems.getString("CourseName"));
                            context.startActivity(intent);
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }, volleyError -> Log.e("alert", "error" + volleyError)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("StudentId" , MainActivity.Companion.getStudentID());
                params.put("CourseId" , courseID);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }





}
