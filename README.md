# myLinkage
## 目录  
* [背景介绍](#背景介绍)  
* [项目介绍](#项目介绍)  
* [使用说明](#使用说明)  
* [其他](#其他)
<a name="背景介绍"></a>  
## 背景介绍  
  
*myLinkage*，是最近公司项目重构，需要用到省市三级联动，UI设计不是市面上滚动式所以自己在网上找了部分资料修改后完成本开源库。<br/>  

<a name="项目介绍"></a>  
## 项目介绍  
  
主要就是调起选择边框，内置好了数据库。<br/>
由于是第一次上线，所有的功能还未完善，接下来会慢慢改善。<br/>

<a name="使用说明"></a>  
## 使用说明  

###使用gradle方式依赖

首先在项目的根build.gradle中添加

	allprojects {
		    repositories {
                ...
                maven { url 'https://jitpack.io' }
		    }
	}
	
然后在项目的build.gradle中添加

	dependencies {
	    implementation 'com.github.caixiquan:myLinkage:1.0.0'
	}
###使用maven方式依赖

第一步

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
第二步

	<dependency>
	    <groupId>com.github.caixiquan</groupId>
	    <artifactId>myLinkage</artifactId>
	    <version>1.0.0</version>
	</dependency>

###在项目中如何使用

在activity的OnCreate中初始化

```
private CustomAlertDialog alertDialog;
new CustomAlertDialog(MainActivity.this);
```

之后需要配置相应的参数以及实现点击接口

```
     /**
     * 显示信息对话框
     */
    private void showAlertDialog(boolean cancelable, CustomAlertDialog.OnAlertClickListener listener) {
        if (alertDialog == null) {
            alertDialog = new CustomAlertDialog(this);
        }
        alertDialog.setCancelable(cancelable);
        alertDialog.setOnAlertClickListener(listener);
        alertDialog.show();
    }
```    
在需要使用该功能的控件点击事件中如下使用

```
showAlertDialog(true, new CustomAlertDialog.OnAlertClickListener() {
                    @Override
                    public void onQuClick(String string) {
                        addr.setText(string);
                    }
                });
```

<a name="其他"></a>  
## 其他  
  
时间仓促，功能简陋，望您包涵。

特别希望看到该项目对您哪怕一点点的帮助。任意的意见和建议，欢迎随意与我沟通,联系方式：  
  
* Email: <caixiquan@sina.cn>  
* QQ:417244808  
  
项目的Bug和改进点，以issue的方式直接提交给我。