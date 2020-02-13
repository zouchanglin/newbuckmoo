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
                    编写服务协议 <small>Analog data generator</small>
                </h4>
            </div>
            <div class="col-md-12 column">
                <form role="form" action="${request.contextPath}/admin/clause/save" method="post">
                    <div class="form-group">
                        <label>
                            <textarea class="text-area" cols="156" rows="25" name="text">${serviceAgree}</textarea>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-default">更改</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>