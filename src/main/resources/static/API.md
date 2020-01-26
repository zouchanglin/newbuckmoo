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

其实就是获取用户微信基本信息，openId、昵称、地址等信息

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
        "student": {
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

返回值字段解释：

student里面时学生信息：

openId：学生用户的openId

studentId：学号

staudentName：姓名

studentCertificate：学生证照片URL

studentSchool：学生学校

auditStatus：信息审核状态

userBasicInfo：用户基本信息

company里面的信息：

companyName：企业名称

companyId：企业的Id注册号

companyOwnerName：企业法人

companyCertificate：营业执照的图片URL

companyDesc：经营范围的描述

auditStatus：企业信息审核状态

userBasicInfo：用户基本信息



## 9、企业发布/修改兼职信息

```
POST newbuckmoo/company/position/create
```

参数

```json
{
    "positionId": "1579755815901769118",
	"positionName":"周末影院兼职",
	"positionMoney": "900元/天",
	"positionClearingWay":0,
	"positionCompanyId":"13JDE9W0D8EW9D90DWE",
	"positionTop":1,
	"positionCategory":[1,3,5,7],
	"positionDesc":"周末影院兼职,负责检验票据等简单的工作",
	"positionAddress": "临潼太平洋影城3层7号厅",
	"positionPeopleNum": 12,
	"positionPhone": "15291418231",
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk"
}
```

参数解释：

positionId ：此字段代表兼职信息的ID，可以为空，positionId为空就代表新增兼职信息，不为空即是修改兼职

positionName：兼职名称

positionMoney：薪酬情况，可以是"200/天"，也可以是"面议"，"至少500/周" 等用户自定义的字符串

positionClearingWay：工资结算方式，0 日结、1 周结、 2 月结、3 完工结算

positionCompanyId：企业ID，意思就是这条兼职信息是哪个企业发的

positionTop：是否需要置顶，0 置顶、1不置顶

positionCategory：兼职标签集合（实际上是标签ID的集合），获取所有标签的接口后续会给出

positionDesc：兼职具体工作的描述

positionAddress：兼职的地点

positionPeopleNum：兼职需要的人数

positionPhone：兼职信息联系方式（目前只对学生可见）

openId：兼职的发布者的OpenID，虽然已知企业信息就可以知道openId，但是为了安全起见还是openId字段和企业ID字段做联合校验



返回值

```json
{
    "code": 0,
    "msg": "成功"
}

{
    "code": 2,
    "msg": "审核状态错误"
}

{
    "code": 12,
    "msg": "审核状态拒绝"
}
```

## 10、获取兼职标签列表

```
GET newbuckmoo/company/position/categories
```

参数

```
无
```

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "categoryId": 1,
            "categoryName": "附近兼职"
        },
        {
            "categoryId": 2,
            "categoryName": "线上兼职"
        },
        {
            "categoryId": 3,
            "categoryName": "高薪兼职"
        }
    ]
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

## 3、获取用户详细信息的返回值

原本是学生信息结果对应到了社团信息，现在把club对应到了社团信息，student对应到了学生信息

影响的接口：

8、获取用户详细信息

## 4、返回值新增几种错误说明

增加了可能失败的情况：

* openId与companyId对应不上
* openId与companyId可以对应上，但是企业信息审核还未通过

影响的接口：

9、企业发布兼职信息



# 五、后台管理Wiki

2020-01-25 新增兼职部分的后台管理

