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
                    待审核社团信息详情 <small>Detail information of club</small>
                </h4>
            </div>
            <ul class="breadcrumb">
                <li>
                    <a href="${request.contextPath}/admin/center">后台管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/club/club-list">社团管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/club/approve-list">审核列表</a>
                </li>
                <li class="active">
                    社团信息详情
                </li>
            </ul>
            <table class="table table-bordered">
                <thead>

                </thead>
                <tbody>
                <tr>
                    <td>学校</td>
                    <td>${clubApproveDTO.schoolName}</td>
                </tr>
                <tr>
                    <td>社团名称</td>
                    <td>${clubApproveDTO.clubName}</td>
                </tr>
                <tr>
                    <td>负责人</td>
                    <td>${clubApproveDTO.ownerName}</td>
                </tr>
                <tr>
                    <td>联系方式</td>
                    <td>${clubApproveDTO.userBasicInfo.userPhone}</td>
                </tr>
                <tr>
                    <td>社团简介</td>
                    <td>${clubApproveDTO.clubDesc}</td>
                </tr>
                <tr>
                    <td>申请时间</td>
                    <td>${clubApproveDTO.getUpdateTime()}</td>
                </tr>
                <tr>
                    <td>审核次数</td>
                    <td>${clubApproveDTO.auditMarkDTO.auditClubCount}</td>
                </tr>
                <tr>
                    <td>最近审核意见</td>
                    <td>${clubApproveDTO.auditMarkDTO.clubMark}</td>
                </tr>
                </tbody>
            </table>
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/club/pass" method="post">
                        <input type="hidden" value="${clubApproveDTO.openId}" name="openId">
                        <div class="form-group">
                            <label for="exampleInputEmail1">审核意见</label><input type="text" name="auditRemark" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <button type="submit" class="btn btn-success">审核通过</button>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/club/refer" method="post">
                        <input type="hidden" value="${clubApproveDTO.openId}" name="openId">
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