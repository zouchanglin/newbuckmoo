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
                    未认证企业列表 <small>List of unaccredited company</small>
                </h4>
            </div>
            <ul class="breadcrumb">
                <li>
                    <a href="${request.contextPath}/admin/center">后台管理</a>
                </li>
                <li>
                    <a href="${request.contextPath}/admin/company/company-list">企业管理</a>
                </li>
                <li class="active">
                    <a href="${request.contextPath}/admin/company/approve-list">审核列表</a>
                </li>
            </ul>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        工商注册号
                    </th>
                    <th>
                        企业名称
                    </th>
                    <th>
                        企业法人
                    </th>
                    <th>
                        联系方式
                    </th>
                    <th>
                        申请时间
                    </th>
                    <th>
                        审核状态
                    </th>
                    <th>
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list companyApprovePage.content as approve>
                <#if approve.auditStatus == 0>
                    <tr class="info">
                <#elseif approve.auditStatus == 1>
                    <tr class="success">
                <#elseif approve.auditStatus == 2>
                    <tr class="warning">
                </#if>
                        <td>${approve.companyId}</td>
                        <td>${approve.companyName}</td>
                        <td>${approve.companyOwnerName}</td>
                        <td>${approve.userBasicInfo.userPhone !}</td>
                        <td>${approve.getUpdateTime()}</td>
                        <td>${approve.getStatusEnum().getMessage()}</td>
                        <td>
                            <a id="modal-770007" href="#modal-container-770007" role="button" class="btn btn-sm btn-default" data-toggle="modal"
                               onclick="picture('${approve.companyCertificate}')">企业执照</a>
                            <#if approve.getAuditStatus() != 2>
                                <a class="btn btn-sm btn-info" href="${request.contextPath}/admin/company/detail?openId=${approve.openId}">审核</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#--分页-->
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="${request.contextPath}/admin/approve/company-list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..companyApprovePage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="${request.contextPath}/admin/approve/company-list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte companyApprovePage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="${request.contextPath}/admin/approve/company-list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-container-770007" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    企业营业执照<small> Company Id Card</small>
                </h4>
            </div>
            <div class="modal-body">
                <img id="myImg" src="" class="img-rounded"  alt=""/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script>
    function picture(pictUrl) {
        $('#myImg').attr('src', pictUrl);
    }
</script>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>