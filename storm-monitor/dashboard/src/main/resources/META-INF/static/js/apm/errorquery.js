/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var currentTableNamePrefix = "service";
var currentTableNameStuffix = "_monitor_log_hour";
function AttentionCellStyler(value, row, index) {
    if (value > 5) {
        return 'background-color:#ffee00;color:red;';
    }
    return 'color:red;';
}

function timeColorChange(value, row, index) {
    var nowTime = (new Date()).getTime(); 
    var oneDateInteval=60*60*24*1000;
    if (nowTime-value <oneDateInteval) {
        return 'color:red;';
    }
    return '';
}

function DelayCellStyler(value, row, index) {
    if (value > 1000) {
        return 'background-color:#ffee00;color:red;';
    }
    return 'color:red;';
}

function shortServiceName(val, row) {
    var name = val.split(".");
    if (name.length > 3) {
        return "[" + name[2] + "]" + name[name.length - 2] + "." + name[name.length - 1];
    }
    return val;
}

function formateDate2Str(val, row) {
    var oDate = new Date(val);
    oYear = oDate.getFullYear();
    oMonth = oDate.getMonth()+1; 
    oDay = oDate.getDate();
    oHour = oDate.getHours();
    oMin = oDate.getMinutes(); 
    oSen = oDate.getSeconds();
    oTime = oYear +'-'+ oMonth +'-'+ oDay +' '+ oHour +':'+ oMin +':'+oSen;//最后拼接时间  
    return oTime;  
}

function formateUserKey(val, row) {
    return val.split("").reverse().join("");
}

function onDblClickRowByDG(index,row){
    //alert(JSON.stringify(row));
    var s="<br><br><br>";
    for(var str in row){
        if(str=='logTime' && row[str]!=null){
            var unixTimestamp = new Date(row[str]) ;
            s +="<strong>"+str+"</strong>=<span style='color:red'>"+unixTimestamp.toLocaleString()+"</span><br>";
        }else{
            s +="<strong>"+str+"</strong>=<span style='color:red'>"+row[str]+"</span><br>";
        }
    }
    //$.messager.alert('记录明细',s,'info');
    $.messager.show({
            title:'记录明细',
            msg:s,
            showType:'slide',
            timeout:10000,
            width:'560px',
            height:'350px'
    });



}

//弹出加载层
 function load() {
     $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
     $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
 }  
   
 //取消加载层  
 function disLoad() {  
     $(".datagrid-mask").remove();  
     $(".datagrid-mask-msg").remove();  
 }

function searchError() {
    document.getElementById("alertMsg").innerHTML = "查询中，请稍候....";
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(searchError2, 50);
}
function searchError2(){
    
    var _userKey=document.getElementById("userKey").value;
    var _traceId=document.getElementById("traceId").value;
    var _errorMsg=document.getElementById("errorMsg").value;
    
    var _machineAddress=document.getElementById("machineAddress").value;
    var _serviceName=document.getElementById("serviceName").value;
    
    var _logTimeBegin = $('#logTimeBegin').datetimebox('getValue');
    var _logTimeEnd = $('#logTimeEnd').datetimebox('getValue');
    
    var myData={userKey: _userKey.split("").reverse().join(""),
            traceId:_traceId,
            errorMsg:_errorMsg,
            serviceName:_serviceName,
            machineAddress:_machineAddress,
            "page.end":1000,
            "page.length":1000,
            "page.sortColumn":"log_time",
            "page.orderBy":"desc"
        };
    if(_logTimeBegin!=null && _logTimeBegin!=""){
        myData.logTimeBegin=_logTimeBegin;
    }
    if(_logTimeEnd!=null && _logTimeEnd!=""){
        myData.logTimeEnd=_logTimeEnd;
    }
    $.ajax({
        type: "post",
        url: "querycustomererror",
        beforeSend: function () {/**
            document.getElementById("alertMsg").innerHTML="查询中，请稍候....";
            var win = $.messager.progress({
				title:'请稍候',
				msg:'搜索中，请稍候……'
			});*/
        },
        complete: function () {
            document.getElementById("alertMsg").innerHTML="";
             $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            //alert(JSON.stringify(data1));
            var data ={"total":2,"rows":[]};
            data.total=data1.length;
            data.rows=data1;
            $('#m_buyerror').datagrid('loadData', data); 
            //alert(data1.length);
            $('#m_buyerror').datagrid("getPanel").panel("setTitle", "查询结果("+data1.length+")");
        },
        error: function (data, type) {
            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}




