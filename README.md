# 自定义WebView验证隐式Intent的使用

本实验通过自定义WebView加载URL来验证隐式Intent 的使用。 

实验包含两个应用：

1. 第一个应用：获取URL地址并启动隐式Intent的调用。

2. 第二个应用：自定义WebView来加载URL。

   

（一）创建一个WebViewActivity，设置Intent-filter属性

```java
   <activity android:name=".WebViewActivity">
            <intent-filter tools:ignore="AppLinkUrlError">
                <action android:name="android.intent.action.VIEW" /><!-- 和系统打开网页的Action一致 -->
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="http" />
            </intent-filter>
        </activity>
```

（二）创建隐式Intent

```
package com.example.exp_04;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private Button btn_go;
    private EditText et_url;
    private String urlHead="https://";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = (EditText) findViewById(R.id.editText);
        btn_go = (Button) findViewById(R.id.button);
        btn_go.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText et = findViewById(R.id.editText);
                String n;
                n = et.getText().toString();
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://www.baidu.com"));

                startActivity(intent);
            }
        });

    }
}

```

activity_main的xml的布局文件

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView"/>
    <EditText
        android:id="@+id/editText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/textView"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="26dp"
        android:ems="10"
        android:hint="在此输入网址">
        <requestFocus />
    </EditText>

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/editText"
        android:layout_marginTop="14dp"
        android:layout_toRightOf="@+id/textView"
        android:layout_marginLeft="160dp"
        android:text="浏览该网页"/>
</RelativeLayout>

```

输入网址，可选择手机自带谷歌浏览器浏览网页

![ARV_V_VI8BDYHXR95_68ATC.png](https://i.loli.net/2020/11/02/3Nn4EqdBlcPfjvG.png)

![_6LQ_MVGU2_P`R_P@6DA__6.png](https://i.loli.net/2020/11/02/sTgUkm7PSV5FQMK.png)

（三）WebViewActivity 自定义浏览器补全

```java
package com.example.exp_04;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = (WebView)findViewById(R.id.webView);
        Intent intent = getIntent();
        //获取Intent的Data属性
        Uri data = intent.getData();
        URL url = null;
        try {
            //创建一个URL对象，参数为协议，主机名，路径
            url = new URL(data.getScheme(), data.getHost(), data.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView加载web资源
        webView.loadUrl(url.toString());
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);//true允许使用JavaScript脚本
    }

}
```

activity_web_view的Xml布局文件

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context=".WebViewActivity">
    <WebView
        android:id="@+id/webView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

运行效果

![@0PK5_E_T@DJSSKH6Y_3I8I.png](https://i.loli.net/2020/11/02/46VyoxYb1HMFKBt.png)

选择自定义浏览器，但是出现WebView无法显示网页net:ERR_CLEARTEXT_NOT_PERMITTED

![46__@32S_F4YN_251I9_Q3K.png](https://i.loli.net/2020/11/02/RCep3qsQEiArjbl.png)

解决方法：在AndroidManifest.xml中添加一个属性

```Java
android:usesCleartextTraffic="true
```

![4__K@URA5HDAM9ZH2___C1H.png](https://i.loli.net/2020/11/02/abRMcpu6B85w2jP.png)