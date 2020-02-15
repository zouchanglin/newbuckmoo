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
                    积分套餐配置 <small>List of unaccredited students</small>
                </h4>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        套餐名称
                    </th>
                    <th>
                        套餐金额(元/人命币)
                    </th>
                    <th>
                        套餐积分
                    </th>
                    <th>

                    </th>
                </tr>
                </thead>
                <tbody>
                    <#list gradeComboList as grade>
                        <tr>
                            <form action="${request.contextPath}/admin/setting/grade-combo/save">
                            <input class="form-control" readonly="readonly" type="hidden" name="gradeId" value="${grade.gradeId}">
                            <td>
                                <label>
                                    <input class="form-control" type="text" name="gradeName" value="${grade.gradeName}">
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input class="form-control" type="number" name="gradeMoney" value="${grade.gradeMoney}">
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input class="form-control" type="number" name="gradeNum" value="${grade.gradeNum}">
                                </label>
                            </td>
                            <td>
                                <input type="submit" class="btn btn-success" value="保存"/>
                                <a class="btn btn-info" href="javascript:location.reload()">恢复上次配置</a>
                                <a class="btn btn-danger" href="${request.contextPath}/admin/setting/grade-combo/delete?gradeComboId=${grade.gradeId}">删除套餐</a>
                            </td>
                            </form>
                        </tr>
                    </#list>
                </tbody>
            </table>
            <hr>
            <form class="form-horizontal" role="form" action="${request.contextPath}/admin/setting/grade-combo/add">
                <input class="form-control" readonly="readonly" type="hidden" name="gradeId" value="0">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-1 control-label">套餐名称</label>
                    <div class="col-sm-4">
                        <input class="form-control" type="text" name="gradeName" id="inputEmail3">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-1 control-label">套餐金额</label>
                    <div class="col-sm-4">
                        <input class="form-control" type="number" name="gradeMoney" id="inputPassword3">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword4" class="col-sm-1 control-label">套餐积分</label>
                    <div class="col-sm-4">
                        <input class="form-control" type="number" name="gradeNum" id="inputPassword4">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-10">
                        <button type="submit" class="btn btn-success">新增积分套餐</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>