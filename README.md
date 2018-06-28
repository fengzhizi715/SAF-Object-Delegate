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


## 2.1 

### 2.1.1 下载安装

Gradle:
```groovy
implementation 'com.safframework.delegate:prefs-delegate:1.0.0'
```

### 2.1.2 特点

* 支持 SharedPreferences 的int、long、float、boolean、string、Set\<String\>
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
