<html lang="zh-CN">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper" style="padding-top: 10px">
        <div class="col-md-12 column">
            <div class="page-header">
                <h4>
                    学生信息详情页 <small>Detail information of student</small>
                </h4>
            </div>
            <ul class="breadcrumb">
                <li>
                    <a href="${request.contextPath}/admin/center">后台管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/manage/student-list">学生管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/approve/student-list">审核列表</a>
                </li>
                <li class="active">
                    学生信息详情
                </li>
            </ul>
            <table class="table table-bordered">
                <thead>

                </thead>
                <tbody>
                <tr>
                    <td>学号</td>
                    <td>${studentDTO.studentId}</td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td>${studentDTO.studentName}</td>
                </tr>
                <tr>
                    <td>学校</td>
                    <td>${studentDTO.studentSchool}</td>
                </tr>
                <tr>
                    <td>联系方式</td>
                    <td>${studentDTO.userBasicInfo.userPhone}</td>
                </tr>
                <tr>
                    <td>学生证</td>
                    <td><img src="${studentDTO.studentCertificate}" alt="资源加载中.."></td>
                </tr>
                <tr>
                    <td>申请时间</td>
                    <td>${studentDTO.getUpdateTime()}</td>
                </tr>
                <tr>
                    <td>审核状态</td>
                    <td>${studentDTO.getStatusEnum().message}</td>
                </tr>
                <tr>
                    <td>审核次数</td>
                    <td>${studentDTO.auditMarkDTO.auditStuCount}</td>
                </tr>
                <tr>
                    <td>最近审核意见</td>
                    <td>${studentDTO.auditMarkDTO.studentMark}</td>
                </tr>
                </tbody>
            </table>
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/student/pass" method="post">
                        <input type="hidden" value="${studentDTO.openId}" name="openId">
                        <div class="form-group">
                            <label for="exampleInputEmail1">审核意见</label><input type="text" name="auditRemark" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <button type="submit" class="btn btn-success">审核通过</button>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/student/refer" method="post">
                        <input type="hidden" value="${studentDTO.openId}" name="openId">
                        <div class="form-group">
                            <label for="exampleInputEmail1">审核意见</label><input type="text" name="auditRemark" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <button type="submit" class="btn btn-danger">审核失败</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>