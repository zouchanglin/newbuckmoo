# 一、功能说明

## 1、学生用户

1、学生信息认证

2、上传个人简历

3、查看兼职信息（包括联系方式）学生信息审核通过后才能看

4、活动报名

## 2、企业用户

1、企业认证

2、发布兼职（N次免费试用，以后需要支付）

3、查看（预联系）学生简历

4、发布活动（免费一次、剩下付钱）

## 3、社团用户

1、社团认证

2、发布活动

3、查看报名信息

# 二、API 文档

## 1、用户基本信息获取

其实就是从微信服务器获取微信授权，和以前一样，现在改为动态获取，不是一开始强制要求用户同意授权

```
GET /newbuckmoo/wechat/authorize
```

参数：

```
returnUrl:返回重定向路径
```

返回值：

```
重定向路径
```

授权完成会在cookie中设置永久cookie，请求其他需要openId的接口时在cookie中取就行了

## 2、手机号获取验证码

```
POST /newbuckmoo/basic-info/verify-key
```

参数

```json
{
	"phone":"15291418231"
}
```

返回值

```json
{
    "code": 0,
    "msg": "成功"
}
```

## 3、系统绑定手机号

修改手机号也是同样的接口

```
POST /newbuckmoo/basic-info/bind-phone
```

参数

```json
{
	"openid":"78397897HJIDE78D56D345DEW",
	"phone":"15291418231",
	"verifyKey":"752682"
}
```

返回值

```json
{
    "code": 0,
    "msg": "成功"
}
```

## 4、学生身份信息注册

```
POST /newbuckmoo/approve/student
```

参数：

```json
{
	"openid": "78397897HJIDE78D56D345DEW",
	"certificate": "https://s2.ax1x.com/2020/01/04/ldYcI1.png",
	"name": "邹长林",
	"school": "西安工程大学",
	"number": "41604090109"
}
```

参数解释：

* certificate：学生证照片路径（图床使用七牛云即可，二级域名解析到仓库）
* number：学生的学号

* openid：用户的微信openid

返回值：

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "status_code": 0,
        "openId": "78397897HJIDE78D56D345DEW",
        "status": "未审核"
    }
}
```

status_code：审核状态

status：审核状态的文字表示

## 5、企业身份信息注册

```
POST /newbuckmoo/approve/student
```

参数：

```json
{
	"openid": "78397897HJIDE78D56D345DEW",
	"name": "骊山鹿鸣有限公司",
	"owner": "杨楠",
	"describe": "骊山鹿鸣通过优质资源的有效整合，更好服务于学生群体",
	"number": "13JDE9W0D8EW9D90DWE",
	"certificate": "http://xxxx.png"
}
```

参数说明：

* openid：微信用户的openid
* name：企业名称
* owner：企业法人姓名
* describe：经营范围描述
* number：工商注册号

返回值：

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "status_code": 0,
        "openID": "78397897HJIDE78D56D345DEW",
        "status": "未审核"
    }
}
```

和学生信息注册的一样！



# 三、运行参数
MySQL IP：

```
lslm.live
```

MySQL密码：

```
1Lishanluming$.
```

管理员地址：
```
http://lslm.live/newbuckmoo/admin/center
```

后端接口地址： 
```
http://lslm.live/newbuckmoo/
```



数据库监控（账号：root 密码：root）

```
http://lslm.live/newbuckmoo/druid/index.html  
```



 













