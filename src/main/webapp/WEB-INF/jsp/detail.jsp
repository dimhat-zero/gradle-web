<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap 模板</title>
    <%@include file="common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading"><h1>${seckill.name}</h1></div>
    </div>
    <div class="panel-body">
        <h2 class="text-danger">
            <!--time图标-->
            <span class="glyphicon glyphicon-time"></span>
            <!--倒计时-->
            <span class="glyphicon" id="seckill-box"></span>
        </h2>
    </div>
</div>

<!--弹出层-->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyohicon-phone"></span>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" id="killPhoneKey" name="killPhone" placeholder="填写手机号" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-info">
                    <span class="glyphicon glyphicon-phone"></span>Submit
                </button>
            </div>
        </div>
    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script src="//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="//cdn.bootcss.com/jquery-countdown/2.0.1/jquery.countdown.min.js"></script>
<script src="/resource/script/seckill.js"></script>
<script type="text/javascript">
$(function () {
   seckill.detail.init({
      seckillId:${seckill.seckillId},
       startTime:${seckill.startTime.time},
       endTime:${seckill.endTime.time}
   });
});
</script>
</body>
</html>