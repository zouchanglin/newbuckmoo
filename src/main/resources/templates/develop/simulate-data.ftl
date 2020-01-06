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
                        模拟数据生成器 <small>Analog data generator</small>
                    </h4>
                </div>
                <div class="row clearfix">
                    <div class="col-md-4 column">
                        <h3>生成学生数据</h3>
                        <form role="form" action="${request.contextPath}/develop/generator-student">
                            <div class="form-group">
                                <input type="number" name="number" placeholder="生成数量" class="form-control" id="exampleInputEmail1"/>
                            </div>
                            <button type="submit" class="btn btn-default">生成</button>
                        </form>
                    </div>
                    <div class="col-md-4 column">
                        <h3>生成企业数据</h3>
                        <form role="form" action="${request.contextPath}/develop/generator-company" method="get">
                            <div class="form-group">
                                <input type="number" name="number" placeholder="生成数量" class="form-control" id="exampleInputEmail1"/>
                            </div>
                            <button type="submit" class="btn btn-default">生成</button>
                        </form>
                    </div>
                    <div class="col-md-4 column">
                        <h3>生成社团数据</h3>
                        <form role="form">
                            <div class="form-group">
                                <input type="number" name="number" placeholder="生成数量" class="form-control" id="exampleInputEmail1"/>
                            </div>
                            <button type="submit" class="btn btn-default">生成</button>
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