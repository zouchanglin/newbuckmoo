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
                    企业信息详情页 <small>Detail information of company</small>
                </h4>
            </div>
            <ul class="breadcrumb">
                <li>
                    <a href="${request.contextPath}/admin/center">后台管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/manage/company-list">企业管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/approve/company-list">审核列表</a>
                </li>
                <li class="active">
                    企业信息详情
                </li>
            </ul>
            <table class="table table-bordered">
                <thead>

                </thead>
                <tbody>
                <tr>
                    <td>企业名称</td>
                    <td>${companyApproveDTO.companyName}</td>
                </tr>
                <tr>
                    <td>工商注册号</td>
                    <td>${companyApproveDTO.companyId}</td>
                </tr>
                <tr>
                    <td>企业代表人</td>
                    <td>${companyApproveDTO.companyOwnerName}</td>
                </tr>
                <tr>
                    <td>企业执照</td>
                    <td><img src="${companyApproveDTO.companyCertificate}" alt=""></td>
                </tr>
                <tr>
                    <td>联系方式</td>
                    <td>${companyApproveDTO.userBasicInfo.userPhone}</td>
                </tr>
                <tr>
                    <td>申请时间</td>
                    <td>${companyApproveDTO.getUpdateTime()}</td>
                </tr>
                <tr>
                    <td>审核次数</td>
                    <td>${companyApproveDTO.auditMarkDTO.auditCompanyCount}</td>
                </tr>
                <tr>
                    <td>最近审核意见</td>
                    <td>${companyApproveDTO.auditMarkDTO.companyMark}</td>
                </tr>
                </tbody>
            </table>
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/company/pass" method="post">
                        <input type="hidden" value="${companyApproveDTO.openId}" name="openId">
                        <div class="form-group">
                            <label for="exampleInputEmail1">审核意见</label><input type="text" name="auditRemark" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <button type="submit" class="btn btn-success">审核通过</button>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/company/refer" method="post">
                        <input type="hidden" value="${companyApproveDTO.openId}" name="openId">
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