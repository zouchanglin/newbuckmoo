<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a>
                后台管理系统
            </a>
        </li>
        <li>
            <a href="${request.contextPath}/admin/center">
                <i class="fa fa-fw fa-list-alt"></i>控制中心</a>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>用户管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">用户相关</li>
                <li><a href="${request.contextPath}/admin/student/approve-list">学生认证</a></li>
                <li><a href="${request.contextPath}/admin/student/student-list">学生管理</a></li>
                <li><a href="${request.contextPath}/admin/company/approve-list">企业认证</a></li>
                <li><a href="${request.contextPath}/admin/company/company-list">企业管理</a></li>
                <li><a href="${request.contextPath}/admin/club/approve-list">社团认证</a></li>
                <li><a href="${request.contextPath}/admin/club/club-list">社团管理</a></li>
            </ul>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>兼职管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">兼职相关</li>
                <li><a href="${request.contextPath}/admin/position/audit-list">兼职审核</a></li>
                <li><a href="${request.contextPath}/admin/position/list">兼职列表</a></li>
            </ul>
        </li>

        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>活动管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">活动相关</li>
                <li><a href="#">活动审核</a></li>
                <li><a href="#">活动列表</a></li>
            </ul>
        </li>

        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>系统设置<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">参数修改</li>
                <li><a href="${request.contextPath}/admin/setting/grade/editor">积分奖励</a></li>
                <li><a href="#">积分支付</a></li>
                <li><a href="#">用户审核</a></li>
                <li><a href="${request.contextPath}/admin/clause/edit">服务协议</a></li>
            </ul>
        </li>

        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>开发者选项<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">数据</li>
                <li><a href="${request.contextPath}/develop/index">模拟数据生成</a></li>
                <li><a href="#">接口日志查询</a></li>
                <li><a href="${request.contextPath}/druid/index.html">SQL监控平台</a></li>
                <li><a href="#">容器监控平台</a></li>
                <li><a href="http://lslm.live:8080">持续集成 CI/DI</a></li>
                <li><a href="${request.contextPath}/develop/websocket">WebSocket</a></li>
                <li><a href="${request.contextPath}/API.md">获取最新API</a></li>
            </ul>
        </li>
        <li>
            <a href=""><i class="fa fa-fw fa-list-alt"></i> 关于我们</a>
        </li>
        <li>
            <a href="${request.contextPath}/admin/login/logout"><i class="fa fa-fw fa-list-alt"></i> 登出</a>
        </li>
    </ul>
</nav>