package com.safframework.delegate.prefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import android.os.Build
import android.annotation.TargetApi


/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.EncryptSharedPreferences.kt
 * @author: Tony Shen
 * @date: 2018-06-28 01:51
 * @version V1.0 <描述当前版本功能>
 */
class EncryptSharedPreferences: SharedPreferences {

    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mContext: Context

    /**
     * constructor
     * @param context should be ApplicationContext not activity
     * @param name file name
     * @param mode context mode
     */
    fun EncryptSharedPreferences(context: Context, name: String, mode: Int) {
        mContext = context
        if (TextUtils.isEmpty(name)) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        } else {
            mSharedPreferences = context.getSharedPreferences(name, mode)
        }

    }

    override fun getAll(): Map<String, String> {
        val encryptMap = mSharedPreferences!!.all
        val decryptMap = HashMap<String, String>()
        for ((key, cipherText) in encryptMap) {
            if (cipherText != null) {
                decryptMap.put(key, cipherText.toString())
            }
        }
        return decryptMap
    }

    /**
     * encrypt function
     * @return cipherText base64
     */
    private fun encryptPreference(plainText: String): String {
        return EncryptUtils.getInstance(mContext).encrypt(plainText)
    }

    /**
     * decrypt function
     * @return plainText
     */
    private fun decryptPreference(cipherText: String): String {
        return EncryptUtils.getInstance(mContext).decrypt(cipherText)
    }

    override fun getString(key: String, defValue: String?): String? {
        val encryptValue = mSharedPreferences!!.getString(encryptPreference(key), null)
        return if (encryptValue == null) defValue else decryptPreference(encryptValue)
    }

    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        val encryptSet = mSharedPreferences!!.getStringSet(encryptPreference(key), null)
                ?: return defValues
        val decryptSet = HashSet<String>()
        for (encryptValue in encryptSet) {
            decryptSet.add(decryptPreference(encryptValue))
        }
        return decryptSet
    }

    override fun getInt(key: String, defValue: Int): Int {
        val encryptValue = mSharedPreferences?.getString(encryptPreference(key), null)
                ?: return defValue
        return Integer.parseInt(decryptPreference(encryptValue))
    }

    override fun getLong(key: String, defValue: Long): Long {
        val encryptValue = mSharedPreferences?.getString(encryptPreference(key), null)
                ?: return defValue
        return java.lang.Long.parseLong(decryptPreference(encryptValue))
    }

    override fun getFloat(key: String, defValue: Float): Float {
        val encryptValue = mSharedPreferences?.getString(encryptPreference(key), null)
                ?: return defValue
        return java.lang.Float.parseFloat(decryptPreference(encryptValue))
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val encryptValue = mSharedPreferences?.getString(encryptPreference(key), null)
                ?: return defValue
        return java.lang.Boolean.parseBoolean(decryptPreference(encryptValue))
    }

    override fun contains(key: String): Boolean {
        return mSharedPreferences?.contains(encryptPreference(key))
    }

    override fun edit(): EncryptEditor {
        return EncryptEditor()
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        mSharedPreferences?.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        mSharedPreferences?.unregisterOnSharedPreferenceChangeListener(listener)
    }

    inner class EncryptEditor : SharedPreferences.Editor{

        private val mEditor: SharedPreferences.Editor

        init {
            mEditor = mSharedPreferences.edit()
        }

        override fun putString(key: String, value: String): SharedPreferences.Editor {
            mEditor.putString(encryptPreference(key), encryptPreference(value))
            return this
        }

        override fun putStringSet(key: String, values: Set<String>): SharedPreferences.Editor {
            val encryptSet = HashSet<String>()
            for (value in values) {
                encryptSet.add(encryptPreference(value))
            }
            mEditor.putStringSet(encryptPreference(key), encryptSet)
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            mEditor.putString(encryptPreference(key), encryptPreference(Integer.toString(value)))
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            mEditor.putString(encryptPreference(key), encryptPreference(java.lang.Long.toString(value)))
            return this
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            mEditor.putString(encryptPreference(key), encryptPreference(java.lang.Float.toString(value)))
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            mEditor.putString(encryptPreference(key), encryptPreference(java.lang.Boolean.toString(value)))
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            mEditor.remove(encryptPreference(key))
            return this
        }

        /**
         * Mark in the editor to remove all values from the preferences.
         * @return this
         */
        override fun clear(): SharedPreferences.Editor {
            mEditor.clear()
            return this
        }

        /**
         * 提交数据到本地
         * @return Boolean 判断是否提交成功
         */
        override fun commit(): Boolean {

            return mEditor.commit()
        }

        /**
         * Unlike commit(), which writes its preferences out to persistent storage synchronously,
         * apply() commits its changes to the in-memory SharedPreferences immediately but starts
         * an asynchronous commit to disk and you won't be notified of any failures.
         */
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        override fun apply() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                mEditor.apply()
            } else {
                commit()
            }
        }
    }

}