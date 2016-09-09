//js模块化
var seckill ={
    URL:{
        now : function () {
            return '/seckill/time/now';
        },
        exporse:function (seckillId) {
            return "/seckillId/"+seckillId+"/exporse";
        },
        execution:function (seckillId, md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },
    validataPhone :function (phone) {
        if(phone && phone.length==11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    handleSeckillkill:function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckillId.URL.exporse(),{},function (result) {
            if(result && result['success']){
                var exporse = result['data'];
                if(exporse['exporse']){
                    var md5=exporse['md5'];
                    var killUrl  = seckillId.URL.execution(seckillId,md5);
                    $("#killBtn").one('click',function () {//绑定一次点击
                        $(this).addClass("disabled");
                        $.post(killUrl,{},function () {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo  = killResult['stateInfo']
                                node.html('<span class="label label-success">'+stateInfo+'"</span>"')
                            }else {
                                console.log('result='+result);
                            }
                        });
                    });
                    node.show();
                }else{
                    seckillId.countdown(exporse['now'],exporse['start'],exporse['end']);
                }
            }else {
                console.log('result='+result);
            }
        })

    },
    countdown:function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $("#seckill-box");
        if(nowTime>endTime){
            seckillBox.html("秒杀结束！");
        }else if(nowTime<startTime){
            seckillBox.html("秒杀未开始！");
            var killTime = new Date(startTime+1000);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function(){
                //获取秒杀地址，执行秒杀
                seckill.handleSeckillkill(seckillId, node);
            });
        }else{
            seckill.handleSeckillkill(seckillId, node);
        }
    },
    detail:{
        init:function (params) {
            var killPhone  = $.cookie('killPhone');
            var startTime = params['startTime'];
            var entTime = params['endTime'];
            var seckillId = params['seckillId'];
            //验证手机号
            if(!seckill.validataPhone(killPhone)){
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show:true,
                    backdrap:'static',
                    keyboard:false
                });
                $("#killPhoneBtn").click(function () {
                   var inputPhone = $("#killPhoneKey").val();
                    if(seckill.validataPhone(inputPhone)){
                        $.cookie('killPhone',inputPhone,{expires:7,path:'seckill'});
                        windows.location.reload();
                    }else{
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误！</label>').show('300');
                    }
                });
            }
            //已经登录
            $.get(seckill.URL.now(),{},function (result) {
                if(result && result['success']){
                    var nowTime = result['data'];
                    seckill.countDown(seckillId,nowTime,startTime,endTime);
                }else {
                    console.log('result='+result);
                }
            })
        }
    }
}