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

## 3、WeChat绑定手机

修改手机号也是同样的接口

```
POST /newbuckmoo/basic-info/bind-phone
```

参数

```json
{
	"openId":"78397897HJIDE78D56D345DEW",
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
	"openId": "78397897HJIDE78D56D345DEW",
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

{
    "code": 9,
    "msg": "请先绑定手机"
}

{
    "code": 11,
    "msg": "审核中不允许更改信息"
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
	"openId": "78397897HJIDE78D56D345DEW",
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

{
    "code": 9,
    "msg": "请先绑定手机"
}

{
    "code": 11,
    "msg": "审核中不允许更改信息"
}
```

和学生信息注册的一样！

## 6、社团身份信息注册

```
POST /newbuckmoo/approve/club
```

参数

```json
{
	"openId":"1578372062890510227",
	"clubName":"读书奋进会",
	"clubDesc": "读书XXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
	"schoolName": "西安工程大学",
	"ownerName":"张三",
	"clubCode":"123456"
}
```

参数解释

* openId 微信用户OpenId
* clubName 社团名称
* clubDesc 社团描述
* schoolName 社团所在学校
* ownerName 社团负责人
* clubCode 邀请码

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "status_code": 0,
        "openId": "1578372062890510227",
        "status": "未审核"
    }
}

{
    "code": 9,
    "msg": "请先绑定手机"
}

{
    "code": 11,
    "msg": "审核中不允许更改信息"
}
```

## 7、学生简历信息上传

```
POST newbuckmoo/student/resume/upload
```

参数

```json
{
	"openId": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
	"resumeName": "邹长林",
	"resumeSex": 1,
	"resumeAge": 22,
	"resumeEducation": 1,
	"resumeHistory": "2016年学生会副会长",
	"resumeAddress": "陕鼓大道58号，西安工程大学临潼校区",
	"resumeWork": "作业、辅导、家教",
	"resumeWorkCategory": 1,
	"resumeHopeMoney": "150元/天",
	"resumeAboutMyself": "我个性开放，活泼好动",
	"resumeLanguage":"CET4 2020.08.01获得",
	"resumeCredential": "2020.01.02获得辩论赛冠军"
}
```

参数说明：

* openId：openid
* resumeName：姓名
* resumeSex：性别
* resumeAge：年龄
* resumeEducation：学历（1博士、2硕士、3本科、4大专）
* resumeHistory：校园经历（没有就填无）
* resumeAddress：现居住地
* resumeWork：期望职位
* resumeWorkCategory：期望职位分类（从分类信息里选择）
* resumeHopeMoney：期望薪资（自己想怎么写就怎么写吧）
* resumeAboutMyself：自我简介1-100字
* resumeLanguage：语言能力（如获得CET4/6等等）
* resumeCredential：获奖经历

返回值

```json
{
    "code": 0,
    "msg": "成功"
}

{
    "code": 1,
    "msg": "参数错误"
}
```

## 8、获取用户详细信息

根据OpenID，可以获取用户基本信息，当前方式是直接获取到用户的所有信息，如果是企业用户的话就获得企业相关信息、如果还有学生的身份的话就是还包含学生的信息、如果还是社团的注册人的话就还包含社团的信息

```
GET newbuckmoo/basic-info/getUserInfo
```

参数

```json
{
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk"
}
```

返回值

下面是一个比如邹长林即是社团负责人、又是企业负责人的情况：

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "club": {
            "openId": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
            "studentId": "41604090109",
            "studentName": "邹长林",
            "studentCertificate": "https://s2.ax1x.com/2020/01/05/lBrMPU.png",
            "studentSchool": "西安工程大学",
            "auditStatus": 0,
            "userBasicInfo": {
                "openId": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
                "userName": "Tim",
                "userIcon": "http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOL",
                "userCity": "泽西岛",
                "userPhone": "15291418231",
                "userGrade": 0,
                "userSex": 1
            }
        },
        "company": {
            "openId": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
            "companyName": "骊山鹿鸣有限公司",
            "companyId": "13JDE9W0D8EW9D90DWE",
            "companyOwnerName": "杨楠",
            "companyCertificate": "https://s2.ax1x.com/2020/01/05/lBDRgJ.png",
            "companyDesc": "骊山鹿鸣通过优质资源的有效整合，更好服务于学生群体",
            "auditStatus": 0,
            "userBasicInfo": {
                "openId": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
                "userName": "Tim",
                "userIcon": "http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOL",
                "userCity": "泽西岛",
                "userPhone": "15291418231",
                "userGrade": 0,
                "userSex": 1
            }
        }
    }
}
```





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

# 四、接口更新说明

## 1、参数由openid改为openId

改变的接口：

3、WeChat绑定手机

4、学生身份信息注册

5、企业身份信息注册

6、社团身份信息注册

## 2、信息注册新增出错返回值

像这种，改变的接口有：

```json
{
    "code": 9,
    "msg": "请先绑定手机"
}

{
    "code": 11,
    "msg": "审核中不允许更改信息"
}
```

4、学生身份信息注册

5、企业身份信息注册

6、社团身份信息注册

