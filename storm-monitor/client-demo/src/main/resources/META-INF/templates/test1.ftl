<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>首页</title>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script>
            var idx = 0;
            var intervalId = null;
            function callRequest() {
                idx++;
                $.ajax({
                    type: "post",
                    url: "testRandom",
                    complete: function () {
                        $.messager.progress('close');
                    },
                    async: false,
                    dataType: "json",
                    success: function (data1) {
                        $('#inxSpan').html(idx);
                        $('#codeSpan').html(data1.resultCode);
                        $('#msgSpan').html(data1.resultCodeMsg);
                        $('#bizSpan').html(data1.businessObject);
                        
                        if(data1.resultCode=='000000'){ //成功
                            $('#codeSpan').css('background-color', "#00ff00");
                        }else{ //失败
                            $('#codeSpan').css('background-color', "#d9534f");
                        }
                        //alert(data1.length);
                    },
                    error: function (data, type) {
                        $.messager.alert('提示', '数据加载失败', 'info');
                        endClock();
                    }
                });
            }

            function startClock() {
                if(intervalId==null){
                    intervalId=setInterval(callRequest, 1000 * 15);   //15秒执行一次
                    $('#statusSpan').html("启动");
                    $('#statusSpan').css('background-color', "#00ff00");
                    callRequest();
                }
            }


            function endClock() {
                if(intervalId!=null){
                    clearInterval(intervalId);
                    $('#statusSpan').html("停止");
                    $('#statusSpan').css('background-color', "#d9534f");
                    intervalId=null;
                }
            }
        </script>
    </head>

    <body>
        <h2>调用事件生成器</h2>
        <p style="color: red">定时生成后台调用事件（时间间隔15秒）。可以持续为后台提供被采集指标的事件来源。</p>
        <div style="margin:20px 0 10px 0;">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" onclick="javascript:startClock()">启动定时器</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" onclick="javascript:endClock()">停止定时器</a>
        </div>
        <div class="easyui-panel" style="height:500px;padding:5px;">
            <div id="p" class="easyui-panel" title="事件面板" style="width:600px;height:400px;padding:10px;"
                 data-options="iconCls:'icon-save',collapsible:true,minimizable:true,maximizable:true,closable:true,maximized:true">
                <div style="margin-top: 30px">定时器状态<span id="statusSpan" style="font-size: x-large;padding: 10px;background-color: #d9534f">停止</span></div>
                <div style="margin-top: 30px">当前第<span id="inxSpan" style="font-size: x-large;padding: 10px;background-color: #ff8080">0</span>次调用</div>
                <div style="margin-top: 30px">调用返回码：<span id="codeSpan" style="font-size: x-large;padding: 10px;background-color: #00ee00"></span></div>
                <div style="margin-top: 30px;line-height: 50px;">调用返回信息：<span id="msgSpan" style="font-size: x-large;padding: 10px;background-color: #00bbee"></span></div>
                <div style="margin-top: 30px;line-height: 50px;">业务信息：<span id="bizSpan" style="font-size: x-large;padding: 10px;background-color: #00bbee"></span></div>
            </div>
        </div>

    </body>
</html>