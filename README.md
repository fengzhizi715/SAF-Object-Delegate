# SAF-Object-Delegate

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

使用Kotlin的委托机制，对SharedPreferences、Extra进行封装

# 一. 封装Extra

## 1.1 下载安装

Gradle:
```groovy
implementation 'com.safframework.delegate:extras-delegate:0.1.0'
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
* 支持对上述类型使用AES算法进行加密，以保障数据安全


没有使用aes算法，SharedPreferences的数据文件大致是这样的：

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="name">tony</string>
    <string name="password">1234abcd</string>
    <int name="age" value="20" />
    <boolean name="isForeigner" value="false" />
</map>
```


使用了aes算法之后，变成这样

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

要使用加密功能的话，需要先初始化秘钥，且秘钥为16位。

```kotlin
import android.content.SharedPreferences
import com.safframework.delegate.domain.User


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

        prefs.initKey("12345678910abcde")
    }

    var name by prefs.string("name",isEncrypt=true)

    var password by prefs.string("password",isEncrypt=true)

    var age by prefs.int("age",isEncrypt=true)

    var isForeigner by prefs.boolean("isForeigner",isEncrypt=true)
}
```

注意，实际使用过程中PrefsHelper应该是单例。


## 2.2 SharedPreferences存放对象类型

使用fastjson、gson来做对象的序列化。