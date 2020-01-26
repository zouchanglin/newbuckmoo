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
                    兼职详情页 <small>Detail information of position</small>
                </h4>
            </div>
            <ul class="breadcrumb">
                <li>
                    <a href="#">后台管理</a>
                </li>
                <li>
                    <a href="#">兼职管理</a>
                </li>
                <li>
                    <a href="#">审核列表</a>
                </li>
                <li class="active">
                    兼职详情
                </li>
            </ul>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        Key
                    </th>
                    <th>
                        Value
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>兼职编号</td>
                    <td>${positionInfo.positionId}</td>
                </tr>
                <tr>
                    <td>兼职名称</td>
                    <td>${positionInfo.positionName}</td>
                </tr>
                <tr>
                    <td>职位薪资</td>
                    <td>${positionInfo.positionMoney}</td>
                </tr>
                <tr>
                    <td>结算方式</td>
                    <td>${positionInfo.clearingWayEnum.message}</td>
                </tr>
                <tr>
                    <td>发布企业</td>
                    <td>${positionInfo.companyInfo.companyName}</td>
                </tr>
                <tr>
                    <td>用户编号</td>
                    <td>${positionInfo.companyInfo.openId}</td>
                </tr>
                <tr>
                    <td>用户姓名</td>
                    <td>${positionInfo.companyInfo.companyOwnerName}</td>
                </tr>
                <tr>
                    <td>是否置顶</td>
                    <td>${positionInfo.isTop.message}</td>
                </tr>
                <tr>
                    <td>职位标签</td>
                    <td>
                        <#list positionInfo.categoryList as cate>
                            ${cate.categoryName + '、'}
                        </#list>
                    </td>
                </tr>
                <tr>
                    <td>职位描述</td>
                    <td>${positionInfo.positionDesc}</td>
                </tr>
                <tr>
                    <td>职位地点</td>
                    <td>${positionInfo.positionAddress}</td>
                </tr>
                <tr>
                    <td>招聘人数</td>
                    <td>${positionInfo.positionPeopleNum}</td>
                </tr>
                <tr>
                    <td>联系方式</td>
                    <td>${positionInfo.positionPhone}</td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td>${positionInfo.getCreateTime()}</td>
                </tr>
                <tr>
                    <td>修改时间</td>
                    <td>${positionInfo.getUpdateTime()}</td>
                </tr>
                <tr>
                    <td>审核状态</td>
                    <td>${positionInfo.auditStatusEnum.message}</td>
                </tr>
                <tr>
                    <td>信息浏览量</td>
                    <td>${positionInfo.positionBrowse}</td>
                </tr>
                </tbody>
            </table>
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/position/pass" method="post">
                        <input type="hidden" value="${positionInfo.positionId}" name="positionId">
                        <div class="form-group">
                            <label for="exampleInputEmail1">审核意见</label><input type="text" name="auditRemark" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">预留</label>
                            <input type="text" class="form-control" disabled="true" id="exampleInputPassword1" />
                        </div>
                        <button type="submit" class="btn btn-success">审核通过</button>
                    </form>
                </div>
                <div class="col-md-6 column">
                    <form role="form" action="${request.contextPath}/admin/position/refer" method="post">
                        <input type="hidden" value="${positionInfo.positionId}" name="positionId">
                        <div class="form-group">
                            <label for="exampleInputEmail1">审核意见</label><input type="text" name="auditRemark" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">预留</label>
                            <input type="text" class="form-control" disabled="true" id="exampleInputPassword1" />
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