# API
## 1、用户基本信息获取

其实就是从微信服务器获取微信授权，和以前一样，现在改为动态获取，不是一开始强制要求用户同意授权

```
GET newbuckmoo/wechat/authorize
```

参数

```
returnUrl:返回重定向路径
```

返回值

```
重定向路径
```

授权完成会在cookie中设置永久cookie，请求其他需要openId的接口时在cookie中取就行了

## 2、具体身份信息注册

```
POST newbuckmoo/user-basic/student-attestation
```

参数

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

返回值

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





