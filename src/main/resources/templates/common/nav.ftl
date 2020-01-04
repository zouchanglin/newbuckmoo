<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
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
                <li><a href="#">兼职统计</a></li>
                <li><a href="#">兼职审核</a></li>
                <li><a href="#">兼职列表</a></li>
            </ul>
        </li>

        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i>活动管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">活动相关</li>
                <li><a href="#">活动统计</a></li>
                <li><a href="#">活动审核</a></li>
                <li><a href="#">活动列表</a></li>
            </ul>
        </li>
        <li>
            <a href=""><i class="fa fa-fw fa-list-alt"></i> 登出</a>
        </li>
    </ul>
</nav>