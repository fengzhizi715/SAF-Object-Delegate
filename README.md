# SAF-Object-Delegate

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

基于 Kotlin 的委托机制实现对 Extra、SharedPreferences 的封装

模块|extras-delegate|prefs-delegate|prefs-fastjson-delegate|prefs-gson-delegate
---|:-------------:|:-------------:|:-------------:|:-------------:
最新版本|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/extras-delegate/images/download.svg) ](https://bintray.com/fengzhizi715/maven/extras-delegate/_latestVersion)|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/prefs-delegate/images/download.svg) ](https://bintray.com/fengzhizi715/maven/prefs-delegate/_latestVersion)| [ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/prefs-fastjson-delegate/images/download.svg) ](https://bintray.com/fengzhizi715/maven/prefs-fastjson-delegate/_latestVersion)|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/prefs-gson-delegate/images/download.svg) ](https://bintray.com/fengzhizi715/maven/prefs-gson-delegate/_latestVersion)


# 一. 封装Extra

## 1.1 下载安装

Gradle:

```groovy
implementation 'com.safframework.delegate:extras-delegate:0.1.0'
```

## 1.2 使用

点击某个控件，跳转到下一个页面，并传递参数。

```kotlin
        text1.click{

            val intent = Intent(this@MainActivity, Demo4ExtrasDelegateActivity::class.java)
            val u = User("Tony","123456")
            intent.putExtra("user",u)
            intent.putExtra("string","just a test")
            startActivity(intent)
        }
```

> 这里的 click 函数是一个扩展函数，可以在 https://github.com/fengzhizi715/SAF-Kotlin-Utils 找到。

从Demo4ExtrasDelegateActivity接受来自上一个Activity中传递过来的参数。

```kotlin
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
```

所传递过来的任何对象类型，都可以使用如下的方式获取Extras。只要保证，extra的key正确即可。

```kotlin
    private val user: User? by extraDelegate("user")
    private val s:String? by extraDelegate("string")
```


# 二. 封装SharedPreferences


## 2.1 SharedPreferences存放int、long、float、boolean、string以及Set\<String\>

### 2.1.1 下载安装

Gradle:

```groovy
implementation 'com.safframework.delegate:prefs-delegate:1.0.0'
```

### 2.1.2 特点

* 支持 SharedPreferences 的int、long、float、boolean、string以及Set\<String\>
* 支持对上述类型使用 AES 算法进行加密，以保障数据安全


没有使用 AES 算法，SharedPreferences的数据文件大致是这样的：

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="name">tony</string>
    <string name="password">1234abcd</string>
    <int name="age" value="20" />
    <boolean name="isForeigner" value="false" />
</map>
```


使用了 AES 算法之后，可能会变成这样：

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="PUoKC8qjz+tmMUV+EVz9Pw==">x2jMB8gvbhkLpMsUuvN7VA==</string>
    <string name="/dSJ/TeQoeRnpoMvZWI67A==">lV+O+RVVrE/p4CjmBX3IbA==</string>
    <string name="viAoG1taVD8rPP9+0n4ORg==">O4DxiobDhUeGEPe0cQFrCQ==</string>
    <string name="Mq2QcYiQhvDjHwBohnGWEQ==">er4sZVGF7k45nNmUq6p7Cg==</string>
</map>
```

### 2.1.2 使用

可以编写一个 PrefsHelper，把所需要使用 SharedPreferences 保存的属性放进去。

```kotlin
import android.content.SharedPreferences

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.PrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-28 23:43
 * @version V1.0 <描述当前版本功能>
 */
class PrefsHelper(prefs: SharedPreferences) {

    var name by prefs.string("name")

    var password by prefs.string("password")

    var age by prefs.int("age")

    var isForeigner by prefs.boolean("isForeigner")
}
```

要使用加密功能的话，需要先初始化密钥，且密钥是16位。

```kotlin
import android.content.SharedPreferences


/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.EncryptPrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-13 23:44
 * @version V1.0 <描述当前版本功能>
 */
class EncryptPrefsHelper(prefs: SharedPreferences) {

    init {

        prefs.initKey("12345678910abcde") // 初始化密钥，且密钥是16位的
    }

    var name by prefs.string("name",isEncrypt=true)

    var password by prefs.string("password",isEncrypt=true)

    var age by prefs.int("age",isEncrypt=true)

    var isForeigner by prefs.boolean("isForeigner",isEncrypt=true)
}
```

注意，实际使用过程中 PrefsHelper 应该是单例。

> 由于，com.novoda.bintray-release插件和gradle版本升级到当前最新版本。该模块也已经上传到jcenter，要是引入到项目中出现“Failed to resolve: com.safframework.delegate:prefs-delegate:1.0.0”时，可以采用临时解决方案，在项目根目录的build.gradle中添加如下的代码。
```groovy
allprojects {
    repositories {
        ......
        maven{url 'https://dl.bintray.com/fengzhizi715/maven/'}
    }
}
```


## 2.2 SharedPreferences存放对象类型

支持 fastjson、gson 分别来做对象的序列化。这里做成两个库，主要是考虑到某些项目已经使用了 fastjson 或者 gson，选取合适的库避免额外添加library。

### 2.2.1 下载安装

使用fastjon序列化对象

Gradle:

```groovy
implementation 'com.safframework.delegate:prefs-fastjson-delegate:0.1.0'
```


使用gson序列化对象

Gradle:

```groovy
implementation 'com.safframework.delegate:prefs-gson-delegate:0.1.0'
```

### 2.2.2 使用

类似上面的 PrefsHelper，写了一个 ObjectPrefsHelper。它包含两个 user 对象，分别使用 fastjson 和 gson 来做序列化。

```kotlin
import android.content.SharedPreferences
import com.safframework.delegate.domain.User

/**
 *
 * @FileName:
 *          com.safframework.delegate.prefs.ObjectPrefsHelper.java
 * @author: Tony Shen
 * @date: 2018-06-29 11:56
 * @version V1.0 <描述当前版本功能>
 */
class ObjectPrefsHelper(prefs: SharedPreferences) {

    var user1 by prefs.json<User?>(null)

    var user2 by prefs.gson<User?>(null)

}
```


联系方式
===

Wechat：fengzhizi715


> Java与Android技术栈：每周更新推送原创技术文章，欢迎扫描下方的公众号二维码并关注，期待与您的共同成长和进步。

![](https://github.com/fengzhizi715/NetDiscovery/blob/master/images/gzh.jpeg)

License
-------

    Copyright (C) 2017 Tony Shen.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
