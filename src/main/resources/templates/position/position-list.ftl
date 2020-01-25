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
                    兼职信息列表 <small>List of position</small>
                </h4>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        发布企业
                    </th>
                    <th>
                        职位名称
                    </th>
                    <th>
                        职位地点
                    </th>
                    <th>
                        招聘人数
                    </th>
                    <th>
                        联系方式
                    </th>
                    <th>
                        发布时间
                    </th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list positionPage.content as approve>
                    <#if approve.auditStatus == 0>
                        <tr class="info">
                    <#elseif approve.auditStatus == 1>
                        <tr class="success">
                    <#elseif approve.auditStatus == 2>
                        <tr class="warning">
                    </#if>
                        <td>${approve.companyInfo.companyName}</td>
                        <td>${approve.positionName}</td>
                        <td>${approve.positionAddress}</td>
                        <td>${approve.positionPeopleNum}</td>
                        <td>${approve.positionPhone}</td>
                        <td>${approve.getUpdateTime()}</td>
                        <td>
                            <a class="btn btn-default btn-sm">详情</a>
                            <a class="btn btn-danger btn-sm">删除</a>
                            <a class="btn btn-default btn-sm">其他</a>
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
                        <li><a href="${request.contextPath}/admin/position/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..positionPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="${request.contextPath}/admin/position/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte positionPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="${request.contextPath}/admin/position/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>