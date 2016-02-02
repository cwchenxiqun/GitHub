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
    <%@ include file="../system/admin/top.jsp" %>

<body>
<div class="navbar navbar-inverse">

</div><!--/.navbar-->
<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <div class="error-container">
                <div class="well">
                    <h2 class="grey lighter smaller">
                        结算设置
                    </h2>

                    <div>
                        <form action="settlement/save.do" name="userForm" id="userForm" method="post">
                            <table id="table_report" class="table table-striped table-bordered table-hover" style="width: 400px;border:none">
                                <tr>
                                    <td style="width: 200px;border:none"><a>统一结算单价：</a></td>
                                    <td style="width: 200px;border:none"><input type="number" name="price" id="price" value="${pd.price }" maxlength="5" placeholder="这里输入统一结算单价" title="统一结算单价"/></td>
                                </tr>
                                <tr>
                                    <td style="border:none"><a>统一渠道系数：</a></td>
                                    <td style="border:none"><input type="number" name="coefficient" id="coefficient" value="${pd.coefficient }" maxlength="5" placeholder="这里输入统一渠道系数" title="统一渠道系数"/></td>
                                </tr>
                            </table>
                        </form>
                    </div>

                    <hr/>
                    <div class="space"></div>
                    <a onclick="save();" class="btn btn-small btn-info"><i class="icon-edit"></i>保存</a>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(top.hangge());

        //保存
        function save() {
            if ($("#price").val() == "") {
                $("#price").tips({
                    side: 3,
                    msg: '输入统一结算单价',
                    bg: '#AE81FF',
                    time: 2
                });

                $("#price").focus();
                return false;
            } else if ($("#coefficient").val() == "") {
                $("#coefficient").tips({
                    side: 3,
                    msg: '输入统一渠道系数',
                    bg: '#AE81FF',
                    time: 2
                });

                $("#coefficient").focus();
                return false;
            } else {
                $("#userForm").submit();
            }
        }
    </script>
</body>
</html>