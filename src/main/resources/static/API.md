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

## 0、文件服务器（临时）

```http
POST http://lslm.live:8090/fileserver/file/fileUpload
```

参数

![](http://lslm.live:8090/fileserver/file/fileDownload?fileUrl=9ec8764b-9d55-474b-a114-547862e33b82.png)

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": "http://lslm.live:8090/fileserver/file/fileDownload?fileUrl=xxxx.png"
}
```

访问data字段的URL即可获得图片

```http
GET http://lslm.live:8090/fileserver/file/fileDownload?fileUrl=xxxx.png
```



## 1、用户基本信息获取

接口权限：全部用户

其实就是从微信服务器获取微信授权，和以前一样，现在改为动态获取，不是一开始强制要求用户同意授权

其实就是获取用户微信基本信息，openId、昵称、地址等信息

```http
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

现在Cookie的域只能是在lslm.live中，开发环境下是tim.natapp1.cc域中，所以在ahojcn.natapp1.cc的域获取不到cookie，目前的解决方式是重定向的时候把openId追加在URL上，比如你填写的是

```http
http://xxx/newbuckmoo/wechat/authorize?returnUrl=http://baidu.com 
```

那么，重定向的结果就是

```http
https://www.baidu.com/?openId=oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk
```



## 2、手机号获取验证码

接口权限：全部用户

```http
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

接口权限：全部用户

修改手机号也是同样的接口

```http
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

接口权限：全部用户

```http
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

接口权限：全部用户

```http
POST /newbuckmoo/approve/company
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

接口权限：全部用户

```http
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
    "code": 1,
    "msg": "注册为学生用户才可以注册社团"
}

{
    "code": 2,
    "msg": "学生信息审核中，通过后才可以注册社团"
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

接口权限：学生用户

```http
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

接口权限：全部用户

根据OpenID，可以获取用户基本信息，当前方式是直接获取到用户的所有信息，如果是企业用户的话就获得企业相关信息、如果还有学生的身份的话就是还包含学生的信息、如果还是社团的注册人的话就还包含社团的信息

```http
POST newbuckmoo/basic-info/getUserInfo
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
        "clubInfo": {},
        "userInfo": {
            "openId": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
            "userName": "Tim",
            "userIcon": "http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOLibgHtYurJxvW0yicXLVcTCUiaDQDqibEyoIKwS7ZRdOsZL02RibF79vdNt6GgFKMr4fuDNV8T7X3ficTfg/132",
            "userCity": "泽西岛",
            "userPhone": "15291418231",
            "userGrade": 0,
            "userSex": 1,
            "userSexStr": "男"
        },
        "companyInfo": {
            "companyName": "骊山鹿鸣有限公司",
            "companyId": "13JDE9W0D8EW9D90DWE",
            "companyOwnerName": "杨楠",
            "companyCertificate": "https://s2.ax1x.com/2020/01/05/lBDRgJ.png",
            "companyDesc": "骊山鹿鸣通过优质资源的有效整合，更好服务于学生群体",
            "auditStatus": 1,
            "auditStatusStr": "已通过",
            "updateTime": 1578710370915
        },
        "studentInfo": {
            "studentId": "41604090109",
            "studentName": "邹长林",
            "studentCertificate": "https://s2.ax1x.com/2020/01/05/lBrMPU.png",
            "studentSchool": "西安工程大学",
            "auditStatus": 1,
            "auditStatusStr": "已通过",
            "updateTime": 1578706346509
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

接口权限：企业用户

```http
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

接口权限：全部用户

```http
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

## 11、学生/企业/社团获取兼职列表

接口权限：学生用户、企业用户、社团用户

学生和企业用户都是有权限看到全部的兼职信息（已经审核通过）的！

```http
POST newbuckmoo/student/position/list
```

参数

```json
{
    "tag": 0,
	"page":0,
	"size":2,
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk"
}
```

参数解释

* page 分页页码，从第0页开始，必须参数
* size 分页大小，必须参数
* openId 学生的openId，必须参数
* tag 兼职类型，全部类型填写0

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "currentPage": 0,
        "totalPage": 8,
        "size": 2,
        "data": [
            {
                "positionId": "1579859018582904271",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionCompanyName": "骊山鹿鸣有限公司",
                "positionTop": 0,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 255,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月24日 17:43:38",
                "createTime": 2783789789
            },
            {
                "positionId": "1579859018582904273",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionCompanyName": "骊山鹿鸣有限公司",
                "positionTop": 0,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 255,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月24日 17:43:38",
                "createTime": 2783789789
            }
        ]
    }
}

{
    "code": 12,
    "msg": "权限拒绝"
}

{
    "code": 14,
    "msg": "请先完善简历"
}
```

返回值字段说明：

参考9、企业发布/修改兼职信息

* positionClearingWayStr 结算方式
* positionTop 是否置顶 0置顶、1不置顶（JSON数据已经排过是否置顶的顺序，展示即可）
* categoryList 此条兼职信息的标签列表
* currentPage 当前页（从第0页开始）
* totalPage 总页数
* size 每一页的大小



## 12、学生申请一项兼职

接口权限：学生用户

```http
POST newbuckmoo/student/position/apply
```

参数

```json
{
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
	"positionId":"1579859018582904271"
}
```

参数解释

openId：学生用户的微信OpenId

positionId：要申请的兼职Id

返回值

```json
{
    "code": 0,
    "msg": "成功"
}

{
    "code": 12,
    "msg": "权限拒绝"
}

{
    "code": 13,
    "msg": "重复操作"
}

{
    "code":1,
    "msg":"参数错误"
}
```

## 13、学生查看自己的简历

接口权限：学生用户

```http
POST newbuckmoo/student/resume/download
```

参数

```json
{
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk"
}
```

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "resumeName": "邹长林",
        "resumeSex": 1,
        "resumeSexStr": "男",
        "resumeAge": 22,
        "resumeEducation": 3,
        "resumeEducationStr": "本科",
        "resumeHistory": "2016年学生副会长",
        "resumeAddress": "陕鼓大道58号，西安工程大学临潼校区",
        "resumeWork": "作业、辅导、家教",
        "resumeWorkCategory": 1,
        "resumeWorkCategoryStr": "附近兼职",
        "resumeHopeMoney": "150元/天",
        "resumeAboutMyself": "我个性开放，活泼好动",
        "resumeLanguage": "CET4 2020.08.01获得",
        "resumeCredential": "http://AAA.png",
        "updateTime": 1580272559063,
        "updateTimeStr": "2020年01月29日 12:35:59"
    }
}

{
    "code": 2,
    "msg": "审核状态错误"
}

{
    "code": 12,
    "msg": "权限拒绝"
}
```



## 14、企业查看自己发布的兼职

接口权限：企业用户

```http
POST newbuckmoo/company/position/my-list
```

参数

```json
{
	"page":0,
	"size":5,
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk"
}
```

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "currentPage": 0,
        "totalPage": 2,
        "size": 5,
        "data": [
            {
                "positionId": "1579859018582904271",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionTop": 0,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 255,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年03月22日 14:36:58",
                "auditStatus": 1,
                "auditStatusStr": "已通过"
            },
            {
                "positionId": "1579859018582904272",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionTop": 1,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 255,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年03月22日 14:36:58",
                "auditStatus": 1,
                "auditStatusStr": "已通过"
            },
            {
                "positionId": "1580269778604721805",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionTop": 1,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 12,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月29日 11:49:38",
                "auditStatus": 0,
                "auditStatusStr": "未审核"
            },
            {
                "positionId": "1580285117587494119",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionTop": 1,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 12,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月29日 16:05:17",
                "auditStatus": 0,
                "auditStatusStr": "未审核"
            },
            {
                "positionId": "1580285160777296575",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionTop": 1,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 12,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月29日 16:06:00",
                "auditStatus": 0,
                "auditStatusStr": "未审核"
            }
        ]
    }
}

{
    "code": 2,
    "msg": "审核状态错误"
}

{
    "code": 12,
    "msg": "权限拒绝"
}
```

2 号说明，企业处于未审核/审核不通过状态

12 号说明不是企业用户 

## 15、企业查看某兼职的申请列表

接口权限：企业用户

```http
POST newbuckmoo/company/position/apply-list
```

请求参数

```json
{
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk",
	"positionId":"1579859018582904271",
	"page":0,
	"size":2
}
```

参数解释

positionId 某一个兼职(自己发布的)的ID

返回值

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "currentPage": 0,
        "totalPage": 1,
        "size": 2,
        "data": [
            {
                "studentId": "41604090109",
                "studentName": "邹长林",
                "studentCertificate": "https://s2.ax1x.com/2020/01/05/lBrMPU.png",
                "studentSchool": "西安工程大学",
                "studentResume": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk2",
                "auditStatus": 1,
                "auditStatusStr": "已通过",
                "updateTime": 1578706346509
            },
            {
                "studentId": "41604090109",
                "studentName": "邹长林2",
                "studentCertificate": "https://s2.ax1x.com/2020/01/05/lBrMPU.png",
                "studentSchool": "西安工程大学",
                "studentResume": "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk2",
                "auditStatus": 1,
                "auditStatusStr": "已通过",
                "updateTime": 1578706346509
            }
        ]
    }
}
```



## 16、职位浏览量统计接口

每次访问此接口，对应兼职信息浏览量就会加1

```http
POST newbuckmoo/student/position/add-browse
```

参数

```json
{
	"positionId":"1580022960792934855"
}
```

返回值

```json
{
    "code": 0,
    "msg": "成功"
}
```



# 三、运行参数

MySQL IP：

```http
lslm.live
```

MySQL密码：

```
1Lishanluming$.
```

管理员地址（账号15291418231 密码15291418231）：
```http
http://lslm.live/newbuckmoo/admin/center
```

后端接口地址： 
```http
http://lslm.live/newbuckmoo/
```

数据库监控（账号：root 密码：root）

```http
http://lslm.live/newbuckmoo/druid/index.html  
```

持续集成CI/DI（账号admin 密码lhl123456an+）：

```http
http://lslm.live:8080
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

## 5、每个接口新增了权限说明

其实就是说明了，这个接口是给谁用的

## 6、获取用户详细信息的JSON格式

影响的接口

8、获取用户详细信息

## 7、学生获取兼职列表分页返回值

现在返回的data字段中，新增了

currentPage，代表当前页（从第0页开始）

totalPage 代表总页数

size 代表每页大小

data 具体的字段

影响的接口

11、学生获取兼职列表

## 8、学生获取兼职添加简历审核

需要完善简历才能查看兼职信息，新增返回值

```json
{
    "code": 14,
    "msg": "请先完善简历"
}
```

影响的接口

11、学生获取兼职列表



## 9、社团用户注册新增权限

必须是学生才能注册社团！错误情况返回值新增如下：

```json
{
    "code": 1,
    "msg": "注册为学生用户才可以注册社团"
}

{
    "code": 2,
    "msg": "学生信息审核中，通过后才可以注册社团"
}
```

影响的接口

6、社团身份信息注册

## 10、获取兼职列表新增时间戳

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "currentPage": 0,
        "totalPage": 8,
        "size": 2,
        "data": [
            {
                "positionId": "1579859018582904271",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionCompanyName": "骊山鹿鸣有限公司",
                "positionTop": 0,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 255,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月24日 17:43:38",
                "createTime": 2783789789
            },
            {
                "positionId": "1579859018582904273",
                "positionName": "周末影院兼职",
                "positionMoney": "600元/天",
                "positionClearingWayStr": "日结",
                "positionCompanyId": "13JDE9W0D8EW9D90DWE",
                "positionCompanyName": "骊山鹿鸣有限公司",
                "positionTop": 0,
                "positionDesc": "周末影院兼职,负责检验票据等简单的工作",
                "positionAddress": "临潼太平洋影城3层7号厅",
                "positionPeopleNum": 255,
                "positionPhone": "15291418231",
                "categoryList": [
                    {
                        "categoryId": 1,
                        "categoryName": "附近兼职"
                    },
                    {
                        "categoryId": 3,
                        "categoryName": "高薪兼职"
                    }
                ],
                "positionBrowse": 0,
                "createTimeStr": "2020年01月24日 17:43:38",
                "createTime": 2783789789
            }
        ]
    }
}

{
    "code": 12,
    "msg": "权限拒绝"
}

{
    "code": 14,
    "msg": "请先完善简历"
}
```

影响的接口：

11、学生/企业/社团获取兼职列表

## 11、获取兼职列表新增标签参数

新增参数 tag

```json
{
    "tag": 0,
	"page":0,
	"size":2,
	"openId":"oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk"
}
```

影响的接口：

11、学生/企业/社团获取兼职列表

## 12、获取兼职列表BUG修复

11、学生/企业/社团获取兼职列表

# 五、后台管理Wiki

2020-01-25 新增兼职部分的后台管理

# 六、数据库表说明

## 1、user_basic_info

微信用户基本信息表

* open_id openID
* user_name 用户昵称
* user_city 用户所在城市
* user_phone 用户手机号码
* user_icon 用户头像
* user_grade 用户积分
* user_sex 用户性别（1男 其他女）

## 2、system_settings

系统设置参数表

* system_key 系统设置项
* system_value 系统设置项的值
* system_remark 系统设置项的说明备注

## 3、student_info

学生信息表

* open_id openId
* student_id 学生学号
* student_name 学生姓名
* student_certificate 学生证照片URL
* student_school 学生所在学校
* student_resume 学生简历ID（对应4、简历表主键）
* audit_status 学生信息审核状态（0未审核 1已通过 2未通过）
* update_time 学生信息修改时间 （时间戳表示的，下面的都是时间戳）

## 4、student_resume

学生简历表

* open_id openId
* resume_name 姓名
* resume_sex 性别（1男 2女）
* resume_age 年龄
* resume_education 学历（1博士 2硕士 3本科 4大专）
* resume_history  校园经历
* resume_address 现居住地
* resume_work 期望职位
* resume_work_category 期望职位类型/标签（对应7、兼职标签表主键）
* resume_hope_money 期望薪资
* resume_about_myself 自我描述
* resume_language 语言能力
* resume_credential 获得证书URL
* update_time 信息更新时间

## 5、company_info

企业信息表

* open_id openId
* company_name 企业名称
* company_id 企业注册号
* company_owner_name 企业法人姓名
* company_certificate 营业执照URL
* company_desc 经营范围

* audit_status 审核状态（0未审核 1已通过 2未通过）

* update_time 信息更新时间

## 6、position_info

兼职信息表

* position_id 兼职信息ID（主键）
* position_name 兼职名称
* position_money 兼职薪酬
* position_clearing_way 薪水结算方式（0日结 1周结 2月结 3完工结算）
* position_company_id 发布者企业Id
* position_top 是否置顶（0置顶 1不置顶）
* position_category 兼职标签（1#2#5#9）（就是把标签表的主键通过#拼接为这样的字符串）
* position_desc 兼职具体描述
* position_address 兼职地点
* position_people_num 兼职需要人数
* position_phone 兼职联系方式
* position_browse 本条兼职信息浏览量
* open_id 发布者openId 
* create_time 信息创建时间
* update_time 信息更新时间
* audit_status 兼职审核状态（0未审核 1已通过 2未通过）
* audit_remark 兼职审核结论备注

## 7、category_info

兼职信息的标签表

* category_id 兼职标签Id
* category_name 兼职标签名称

## 8、school_club_info

社团信息表

* open_id openId
* club_name 社团名称
* club_desc 社团描述
* school_name 学校名称
* owner_name 社团负责人姓名
* audit_status  审核状态（0未审核 1已通过 2未通过）
* update_time 信息更新时间
* club_code 社团邀请码（不用管）

## 9、apply_position

兼职申请表，学生申请兼职

* apply_id 申请表主键
* position_id 兼职信息Id
* open_id openId
* read_status 是否已读（0未读 1已读）
* create_time 信息创建时间
* update_time 信息更新时间

