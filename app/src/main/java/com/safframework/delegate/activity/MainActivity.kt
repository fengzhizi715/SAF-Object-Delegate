package com.safframework.delegate.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.delegate.R
import com.safframework.delegate.domain.User
import com.safframework.ext.click
import kotlinx.android.synthetic.main.activity_main.*

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

        initViews()
    }

    private fun initViews() {

        text1.click{

            val intent = Intent(this@MainActivity, Demo4ExtrasDelegateActivity::class.java)
            val u = User("Tony","123456")
            intent.putExtra("user",u)
            intent.putExtra("string","just a test")
            startActivity(intent)
        }

        text2.click {

            val intent = Intent(this@MainActivity, Demo4PrefsDelegateActivity::class.java)
            startActivity(intent)
        }

        text3.click {

            val intent = Intent(this@MainActivity, Demo4EncryptPrefsDelegateActivity::class.java)
            startActivity(intent)
        }

        text4 .click {

            val intent = Intent(this@MainActivity, Demo4ObjectPrefsDelegateActivity::class.java)
            startActivity(intent)
        }
    }
}