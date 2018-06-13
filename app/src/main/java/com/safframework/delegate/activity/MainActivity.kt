package com.safframework.delegate.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.delegate.R

/**
 *
 * @FileName:
 *          com.safframework.delegate.activity.MainActivity.java
 * @author: Tony Shen
 * @date: 2018-06-13 12:03
 * @version V1.0 <描述当前版本功能>
 */
class MainActivity:AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}