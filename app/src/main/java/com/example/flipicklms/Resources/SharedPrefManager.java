package com.example.flipicklms.Resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

/**
 * Created by Jai.
 */

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "LoginSharedPref";
    private static final String COLLEGE_ID = "college_id";
    private static final String STUDENT_ID = "student_id";
    private static final String EMAIL_KEY = "email";
    private static final String STATUS = "status";
    private static final String DATA = "data";
    private static final String IS_DEFAULT = "isDefault";
    private static final String MOBID = "mobId";



    private static final String User_Name = "username";

    private static final String CODE = "code";
    private static final String POSTID = "post_id";
    private static final String USERID = "user_id";
    private static final String NAME = "name";
    private static final String FOLLOWERS = "followers";
    private static final String FOLLOWING = "following";
    private static final String PHONE = "phone";
    private static final String PICURI = "picuri";
    private static final String POSTBLOGS = "postblogs";
    private static final String USERIDBLOGS = "useridblogs";
    private static  final String PRICE = "price";
    private static  final String FAVID = "fav_id";
    private static  final String BLOGSID = "blogs_id";
    private static  final String CATEGORY_NAME = "catogry_name";
    private static final String STRART_DATE = "start_date";

//    private static final String STRART_DATE = "start_date";

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static Bitmap bitmap;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    SharedPrefManager(Bitmap bitmapimg) {
        bitmap = bitmapimg;
    }


    public boolean saveCollegeId(String CollegeId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COLLEGE_ID, CollegeId);
        editor.apply();
        return true;
    }

    public String getCollegeId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COLLEGE_ID, null);
    }

    public boolean saveStudentID(String studentID) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STUDENT_ID, studentID);
        editor.apply();
        return true;
    }

    public String getStudentID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STUDENT_ID, null);
    }

    public boolean saveEmail(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.apply();
        return true;
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean saveuserToken(String status,String data,String isDefault,String mobId,String phoneOrEmail) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STATUS, status);
        editor.putString(DATA, data);
        editor.putString(IS_DEFAULT, isDefault);
        editor.putString(MOBID, mobId);
        editor.putString(PHONE, phoneOrEmail);
        editor.apply();
        return true;
    }

    public boolean saveuserToken_onSignup(String status,String data,String isDefault,String mobId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STATUS, status);
        editor.putString(DATA, data);
        editor.putString(IS_DEFAULT, isDefault);
        editor.putString(MOBID, mobId);
        editor.apply();
        return true;
    }


    public String getIsDefault() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(IS_DEFAULT, null);
    }

    public String getStatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STATUS, null);
    }

//    public String getEmailid() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(EMAILID, null);
//    }
//
//    public String getLastname() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(LASTNAME, null);
//    }


    public boolean getuserToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(STATUS,"");
        sharedPreferences.getString(DATA,"");
        sharedPreferences.getString(IS_DEFAULT,"");
        sharedPreferences.getString(MOBID,"");

        return true;
    }


    public boolean savecat(String category_id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CATEGORY_NAME, category_id);
        editor.apply();
        return true;
    }


    public String getblogsid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(BLOGSID, null);
    }


    public boolean saveblogsid(String blogsid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BLOGSID, blogsid);
        editor.apply();
        return true;
    }

    public void resetPreferences() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.apply();
    }


    public boolean savefav_id(String fav_id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FAVID, fav_id);
        editor.apply();
        return true;
    }

    public String getFavid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FAVID, null);
    }



    public boolean saveprice(String price) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID, price);
        editor.apply();
        return true;
    }

    public String getPrice() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PRICE, null);
    }


    public boolean savepostblogs(String postblogs) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(POSTBLOGS, postblogs);
        editor.apply();
        return true;
    }


    public String getPostblogs() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(POSTBLOGS, null);
    }

    public String getPhone() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE, null);
    }

    public boolean savepicuri(String picuri) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PICURI, picuri);
        editor.apply();
        return true;
    }

    public String getpicuri() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PICURI, null);
    }


    public boolean savephone(String phone) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE, phone);
        editor.apply();
        return true;
    }



    public boolean savename(String name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, name);
        editor.apply();
        return true;
    }

    public String getName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAME, null);
    }


    public String getFollowers() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FOLLOWERS, null);
    }


    public boolean savefollowers(String emailid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FOLLOWERS, emailid);
        editor.apply();
        return true;
    }


    public String getFollowing() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FOLLOWING, null);
    }


    public boolean saveFollowings(String emailid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FOLLOWING, emailid);
        editor.apply();
        return true;
    }


    public boolean saveemailid(String emailid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(EMAILID, emailid);
        editor.apply();
        return true;
    }

    public String getUserid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERID, null);
    }


    public boolean savepostid(String postid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(POSTID, postid);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getpostid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(POSTID, null);
    }

    public boolean savecode(String code) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CODE, code);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getcode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CODE, null);
    }

//    public boolean savevenderuserid(String vender_userid) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(VENDER_USER_ID, vender_userid);
//        editor.apply();
//        return true;
//    }

    //this method will fetch the device token from shared preferences
//    public String getvender_userid() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(VENDER_USER_ID, null);
//    }

//    public boolean savevenderid(String vender_id) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(VENDER_ID, vender_id);
//        editor.apply();
//        return true;
//    }

    //this method will fetch the device token from shared preferences
//    public String getvenderid() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(VENDER_ID, null);
//    }
//
//    public boolean saveDeviceToken(String token) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(TOKEN_KEY, token);
//        editor.apply();
//        return true;
//    }
//
//    //this method will fetch the device token from shared preferences
//    public String getdevicetoken() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(TOKEN_KEY, null);
//    }


    public boolean saveuseridblogs(String userid) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERIDBLOGS, userid);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getuseridblogs() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERIDBLOGS, null);
    }

    //this method will fetch the device token from shared preferences
//    public String getmytoken() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(TOKEN_KEY, null);
//    }

    public boolean saveuser_name(String user_name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(User_Name, user_name);
        editor.apply();
        return true;
    }

    public String getmyuser_name() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(User_Name, null);
    }


    public boolean savestartdate(String start_date) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STRART_DATE, start_date);
        editor.apply();
        return true;
    }

    public String getStrartDate() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STRART_DATE, null);
    }
}
