package com.safframework.delegate.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.delegate.domain.User
import com.safframework.delegate.extras.extraDelegate
import com.safframework.log.L

/**
 *
 * @FileName:
 *          com.safframework.delegate.activity.Demo4ExtrasDelegateActivity.java
 * @author: Tony Shen
 * @date: 2018-06-13 17:42
 * @version V1.0 <描述当前版本功能>
 */
class Demo4ExtrasDelegateActivity: AppCompatActivity() {

    private val user: User? by extraDelegate("user")
    private val s:String? by extraDelegate("string")

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        L.json(user)
        L.i(s)
    }
}