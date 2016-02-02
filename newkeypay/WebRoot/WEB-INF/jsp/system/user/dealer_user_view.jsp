<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title></title>

    <meta name="description" content="404 Error Page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="../admin/top.jsp" %>

<body>
<div class="navbar navbar-inverse">

</div><!--/.navbar-->
<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <div class="error-container">
                <div class="well">
                    <h2 class="grey lighter smaller">
                        个人信息
                    </h2>

                    <div>
                        <table id="table_report" class="table table-striped table-bordered table-hover" style="width: 400px">
                            <tr>
                                <td style="width: 200px"><a>我的激活码：</a></td>
                                <td style="width: 200px">${pd.DEALER_CODE }</td>
                            </tr>
                            <tr>
                                <td><a>登录用户名：</a></td>
                                <td>${pd.USERNAME }</td>
                            </tr>
                            <tr>
                                <td><a>姓名：</a></td>
                                <td>${pd.NAME }</td>
                            </tr>
                            <tr>
                                <td><a>省份/城市/地区：</a></td>
                                <td>
                                    <c:if test="${pd.PROVINCE != null && pd.PROVINCE != ''}">
                                        ${pd.PROVINCE }
                                    </c:if>
                                    <c:if test="${pd.CITY != null && pd.CITY != ''}">
                                    /${pd.CITY }
                                    </c:if>
                                    <c:if test="${pd.DISTRICT != null && pd.DISTRICT != ''}">
                                    /${pd.DISTRICT }
                                    </c:if>
                            </tr>
                            <tr>
                                <td style="width: 100px"><a>手机：</a></td>
                                <td>${pd.PHONE }</td>
                            </tr>
                            <tr>
                                <td style="width: 100px"><a>邮箱：</a></td>
                                <td>${pd.EMAIL }</td>
                            </tr>
                        </table>
                    </div>

                    <hr/>
                    <div class="space"></div>
                    <a onclick="editUserH();" class="btn btn-small btn-info"><i class="icon-edit"></i>修改</a>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(top.hangge());
        //修改个人资料
        function editUserH(){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="个人资料";
            diag.URL = '<%=basePath%>/dealerUser/goEditUByOneself.do';
            diag.Width = 245;
            diag.Height = 425;
            diag.CancelEvent = function(){ //关闭事件
                location.reload();
                diag.close();
            };
            diag.show();
        }
    </script>
</body>
</html>