<html lang="zh-CN">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper" style="padding-top: 10px">
        <div class="col-md-8 column">
            <div class="page-header">
                <h4>
                    用户积分配置 <small>List of unaccredited students</small>
                </h4>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        设置项
                    </th>
                    <th>
                        设置值
                    </th>
                    <th>

                    </th>
                </tr>
                </thead>
                <form action="${request.contextPath}/admin/setting/grade/save" method="get">
                <tbody>

                    <#list settingList as setting>
                        <form action="${request.contextPath}/admin/setting/grade/save" method="get">
                        <tr>
                            <td>${setting.systemRemark}</td>
                            <td>
                                <label>
                                    <input class="form-control" type="number" name="${setting.systemKey}" value="${setting.systemValue}">
                                </label>
                            </td>
                            <td>
                                <input type="submit" class="btn btn-success" value="保存"/>
                                <a class="btn btn-info" href="javascript:location.reload();">恢复上次配置</a>
                            </td>
                        </tr>
                    </#list>
                </tbody>
                </form>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>