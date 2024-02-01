package com.example.flipicklms.Resources

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap

/**
 * Created by Jai.
 */
class SharedPrefManager {
    constructor(context: Context) {
        com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx = context
    }

    internal constructor(bitmapimg: Bitmap) {
        com.example.flipicklms.Resources.SharedPrefManager.Companion.bitmap = bitmapimg
    }

    fun saveuserToken(
        status: String?,
        data: String?,
        isDefault: String?,
        mobId: String?,
        phoneOrEmail: String?
    ): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.STATUS, status)
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.DATA, data)
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.IS_DEFAULT,
            isDefault
        )
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.MOBID, mobId)
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.PHONE,
            phoneOrEmail
        )
        editor.apply()
        return true
    }

    fun saveuserToken_onSignup(
        status: String?,
        data: String?,
        isDefault: String?,
        mobId: String?
    ): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.STATUS, status)
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.DATA, data)
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.IS_DEFAULT,
            isDefault
        )
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.MOBID, mobId)
        editor.apply()
        return true
    }

    val mobid: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.MOBID,
                null
            )
        }
    val data: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.DATA,
                null
            )
        }
    val isDefault: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.IS_DEFAULT,
                null
            )
        }
    val status: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.STATUS,
                null
            )
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
    fun getuserToken(): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.STATUS,
            ""
        )
        sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.DATA,
            ""
        )
        sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.IS_DEFAULT,
            ""
        )
        sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.MOBID,
            ""
        )
        return true
    }

    fun savecat(category_id: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.CATEGORY_NAME,
            category_id
        )
        editor.apply()
        return true
    }

    fun getblogsid(): String? {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.BLOGSID,
            null
        )
    }

    fun saveblogsid(blogsid: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.BLOGSID,
            blogsid
        )
        editor.apply()
        return true
    }

    fun resetPreferences() {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
        editor.apply()
    }

    fun savefav_id(fav_id: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.FAVID, fav_id)
        editor.apply()
        return true
    }

    val favid: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.FAVID,
                null
            )
        }

    fun saveprice(price: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.USERID, price)
        editor.apply()
        return true
    }

    val price: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.PRICE,
                null
            )
        }

    fun savepostblogs(postblogs: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.POSTBLOGS,
            postblogs
        )
        editor.apply()
        return true
    }

    val postblogs: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.POSTBLOGS,
                null
            )
        }
    val phone: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.PHONE,
                null
            )
        }

    fun savepicuri(picuri: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.PICURI, picuri)
        editor.apply()
        return true
    }

    fun getpicuri(): String? {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.PICURI,
            null
        )
    }

    fun savephone(phone: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.PHONE, phone)
        editor.apply()
        return true
    }

    fun savename(name: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.NAME, name)
        editor.apply()
        return true
    }

    val name: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.NAME,
                null
            )
        }
    val followers: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.FOLLOWERS,
                null
            )
        }

    fun savefollowers(emailid: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.FOLLOWERS,
            emailid
        )
        editor.apply()
        return true
    }

    val following: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.FOLLOWING,
                null
            )
        }

    fun saveFollowings(emailid: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.FOLLOWING,
            emailid
        )
        editor.apply()
        return true
    }

    fun saveemailid(emailid: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        //        editor.putString(EMAILID, emailid);
        editor.apply()
        return true
    }

    val userid: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.USERID,
                null
            )
        }

    fun savepostid(postid: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.POSTID, postid)
        editor.apply()
        return true
    }

    //this method will fetch the device token from shared preferences
    fun getpostid(): String? {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.POSTID,
            null
        )
    }

    fun savecode(code: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(com.example.flipicklms.Resources.SharedPrefManager.Companion.CODE, code)
        editor.apply()
        return true
    }

    //this method will fetch the device token from shared preferences
    fun getcode(): String? {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.CODE,
            null
        )
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
    fun saveuseridblogs(userid: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.USERIDBLOGS,
            userid
        )
        editor.apply()
        return true
    }

    //this method will fetch the device token from shared preferences
    fun getuseridblogs(): String? {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.USERIDBLOGS,
            null
        )
    }

    //this method will fetch the device token from shared preferences
    //    public String getmytoken() {
    //        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    //        return sharedPreferences.getString(TOKEN_KEY, null);
    //    }
    fun saveuser_name(user_name: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.User_Name,
            user_name
        )
        editor.apply()
        return true
    }

    fun getmyuser_name(): String? {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.User_Name,
            null
        )
    }

    fun savestartdate(start_date: String?): Boolean {
        val sharedPreferences: SharedPreferences =
            com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putString(
            com.example.flipicklms.Resources.SharedPrefManager.Companion.STRART_DATE,
            start_date
        )
        editor.apply()
        return true
    }

    val strartDate: String?
        get() {
            val sharedPreferences: SharedPreferences =
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mCtx!!.getSharedPreferences(
                    com.example.flipicklms.Resources.SharedPrefManager.Companion.SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
            return sharedPreferences.getString(
                com.example.flipicklms.Resources.SharedPrefManager.Companion.STRART_DATE,
                null
            )
        }

    companion object {
        private const val SHARED_PREF_NAME = "LoginSharedPref"
        private const val STATUS = "status"
        private const val DATA = "data"
        private const val IS_DEFAULT = "isDefault"
        private const val MOBID = "mobId"
        private const val User_Name = "username"
        private const val CODE = "code"
        private const val POSTID = "post_id"
        private const val USERID = "user_id"
        private const val NAME = "name"
        private const val FOLLOWERS = "followers"
        private const val FOLLOWING = "following"
        private const val PHONE = "phone"
        private const val PICURI = "picuri"
        private const val POSTBLOGS = "postblogs"
        private const val USERIDBLOGS = "useridblogs"
        private const val PRICE = "price"
        private const val FAVID = "fav_id"
        private const val BLOGSID = "blogs_id"
        private const val CATEGORY_NAME = "catogry_name"
        private const val STRART_DATE = "start_date"

        //    private static final String STRART_DATE = "start_date";
        private var mInstance: com.example.flipicklms.Resources.SharedPrefManager? = null
        private var mCtx: Context? = null
        private var bitmap: Bitmap? = null
        @Synchronized
        fun getInstance(context: Context?): com.example.flipicklms.Resources.SharedPrefManager {
            if (com.example.flipicklms.Resources.SharedPrefManager.Companion.mInstance == null) {
                com.example.flipicklms.Resources.SharedPrefManager.Companion.mInstance =
                    context?.let { com.example.flipicklms.Resources.SharedPrefManager(it) }
            }
            return com.example.flipicklms.Resources.SharedPrefManager.Companion.mInstance!!
        }
    }
}
