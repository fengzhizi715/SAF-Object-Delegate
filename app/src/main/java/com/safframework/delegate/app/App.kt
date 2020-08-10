package com.safframework.delegate.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.safframework.log.configL
import com.safframework.log.converter.gson.GsonConverter

/**
 *
 * @FileName:
 *          com.safframework.delegate.app.App
 * @author: Tony Shen
 * @date: 2020-08-10 17:53
 * @version: V1.0 <描述当前版本功能>
 */
@SuppressLint("StaticFieldLeak")
var application: Application? = null

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this

    }

    override fun onCreate() {
        super.onCreate()

        configL { // 使用自定义的 DSL 来配置 L

            converter = GsonConverter()
        }
    }

}