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
                <li><a href="${request.contextPath}/admin/approve/student-list">学生认证</a></li>
                <li><a href="#">学生管理</a></li>
                <li><a href="${request.contextPath}/admin/approve/company-list">企业认证</a></li>
                <li><a href="#">企业管理</a></li>
            </ul>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>兼职管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">兼职相关</li>
                <li><a href="#">兼职审核</a></li>
                <li><a href="#">兼职列表</a></li>
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
                <i class="fa fa-fw fa-plus"></i>参数配置<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">参数修改</li>
                <li><a href="#">用户相关</a></li>
                <li><a href="#">支付相关</a></li>
                <li><a href="#">审核相关</a></li>
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
                <li><a href="#">主机监控平台</a></li>
            </ul>
        </li>

        <li>
            <a href=""><i class="fa fa-fw fa-list-alt"></i> 登出</a>
        </li>
    </ul>
</nav>