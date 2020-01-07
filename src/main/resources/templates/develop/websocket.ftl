<html lang="zh-CN">
<#include "../common/header.ftl">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">
        <div id="page-content-wrapper" style="padding-top: 10px">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h4>
                        WebSocket测试<small> WebSocket Test</small>
                    </h4>
                </div>
                <div class="row clearfix">

                </div>
            </div>
        </div>
    </div>
    <!-- 播放音乐 -->
    <audio id="notice" loop="loop">
        <source src="${request.contextPath}/mp3/song.mp3" type="audio/mpeg">
    </audio>
    <!-- 弹窗 -->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        新消息
                    </h4>
                </div>
                <div class="modal-body">
                    <span id="mySpan"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="javascript:document.getElementById('notice').pause();" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <a id="toUrl" type="button" class="btn btn-primary">查看新申请</a>
                </div>
            </div>
        </div>
    </div>
<script>
    let websocket = null;
    if('WebSocket' in window){
        websocket = new WebSocket("ws://${projectUrl}${request.contextPath}/webSocket");
    }else {
        alert('该浏览器不支持WebSocket')
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    };

    websocket.onclose = function (event) {
        console.log('关闭连接');
    };

    websocket.onmessage = function (event) {
        console.log('收到消息'+event.data);
        //弹窗提醒+播放音乐
        $('#myModal').modal('show');
        let str = event.data.split('&');
        $('#mySpan').html(str[0]);
        $('#toUrl').attr('href', '${request.contextPath}'+str[1]);

        document.getElementById('notice').play();
    };
    websocket.onerror = function (event) {
        alert('websocket发生错误');
    };
    window.onbeforeunload = function () {
        websocket.close();
    };
</script>
</body>
</html>