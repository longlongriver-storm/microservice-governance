/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    search();
});

function AttentionCellStyler(value, row, index) {
    if (value > 5) {
        return 'background-color:#ffee00;color:red;';
    }
    return 'color:red;';
}

function timeColorChange(value, row, index) {
    var nowTime = (new Date()).getTime();
    var oneDateInteval = 60 * 60 * 24 * 1000;
    if (nowTime - value < oneDateInteval) {
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
    oMonth = oDate.getMonth() + 1;
    oDay = oDate.getDate();
    oHour = oDate.getHours();
    oMin = oDate.getMinutes();
    oSen = oDate.getSeconds();
    oTime = oYear + '-' + oMonth + '-' + oDay + ' ' + oHour + ':' + oMin + ':' + oSen;//最后拼接时间  
    return oTime;
}

function formateUserKey(val, row) {
    return val.split("").reverse().join("");
}

function onDblClickRowByDG(index, row) {
    //alert(JSON.stringify(row));
    var s = "<br><br><br>";
    for (var str in row) {
        if (str == 'logTime' && row[str] != null) {
            var unixTimestamp = new Date(row[str]);
            s += "<strong>" + str + "</strong>=<span style='color:red'>" + unixTimestamp.toLocaleString() + "</span><br>";
        } else {
            s += "<strong>" + str + "</strong>=<span style='color:red'>" + row[str] + "</span><br>";
        }
    }
    //$.messager.alert('记录明细',s,'info');
    $.messager.show({
        title: '记录明细',
        msg: s,
        showType: 'slide',
        timeout: 10000,
        width: '560px',
        height: '350px'
    });
}

//弹出加载层
function load() {
    $("<div class=\"datagrid-mask\"></div>").css({display: "block", width: "100%", height: $(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2});
}

//取消加载层  
function disLoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

function search() {
    document.getElementById("alertMsg").innerHTML = "查询中，请稍候....";
    var win = $.messager.progress({
        title: '请稍候',
        msg: '搜索中，请稍候……'
    });
    setTimeout(search2, 0);
}
function search2() {

    var myData = $("#chartcfglist").serializeArray();
    //alert(JSON.stringify(myData));
    $.ajax({
        type: "post",
        url: "querychartconfig",
        beforeSend: function () {/**
         document.getElementById("alertMsg").innerHTML="查询中，请稍候....";
         var win = $.messager.progress({
         title:'请稍候',
         msg:'搜索中，请稍候……'
         });*/
        },
        complete: function () {
            document.getElementById("alertMsg").innerHTML = "";
            $.messager.progress('close');
        },
        data: myData,
        async: false,
        dataType: "json",
        success: function (data1) {
            //alert(JSON.stringify(data1));
            var data = {"total": 2, "rows": []};
            data.total = data1.length;
            data.rows = data1;
            $('#m_buyerror').datagrid('loadData', data);
            //alert(data1.length);
            $('#m_buyerror').datagrid("getPanel").panel("setTitle", "查询结果(" + data1.length + ")");
        },
        error: function (data, type) {

            $.messager.alert('提示', '数据加载失败:' + data + type, 'info');
        }
    });
}

//添加表单
function openAddChartCfgDialog() {
    showWindow({
        title: '添加表单',
        href: 'gotochartconfigedit',
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

function gotoChartDashboard(){
    var ids="";
    var names="";
    var rows = $('#m_buyerror').datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids +=rows[i].id+",";
        names +=rows[i].chartName+"+";
    }
    names=names.substr(0,names.length-1);
    var url="apmbizreport/bizreport?ids="+ids;
    var icon="icon icon-nav";
    parent.addTab(names, url, icon);
}

/**
 * 删除一条记录
 * @returns {undefined}
 */
function deleteChartCfg(){
    var row = $('#m_buyerror').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要删除的记录！', 'info');
        return;
    }
    $.messager.confirm('?','您确定要删除本图表配置吗?',function(r) {
		if (r) {
			$.ajax({
				type : "post",
				url : "deletechartconfig",
				data : {
					id:row.id
				},
				async : false,
				success : function(data1) {
					if(data1==true){
						 $.messager.alert('提示','名称为['+row.chartName+ ']的图表配置删除成功！', 'info');
                                                 search();
					}else{
						 $.messager.alert('提示', '图表配置删除失败:' + data1, 'info');
					}
				},
				error : function(data, type) {
					$.messager.alert('提示', '分类删除失败:'+data + type, 'info');
				}
			});
		}
	});
}

//添加表单
function openUpdateChartCfgDialog() {
    var row = $('#m_buyerror').datagrid('getSelected');
    if (row == undefined && row == null) {
        $.messager.alert('提示', '请先选择一条要修改的记录！', 'info');
        return;
    }
    showWindow({
        title: '添加表单',
        href: 'gotochartconfigedit?id='+row.id,
        width: 1000,
        height: 700,
        loadingMessage: '表单打开中...',
        onLoad: function () {

        }

    });
}

// 弹出窗口
function showWindow(options) {
    jQuery("#MyPopWindow").window(options);
}

// 关闭弹出窗口
function closeWindow() {
    $("#MyPopWindow").window('close');
}

//提交表单数据
function submitChartCfgInfo() {
    //alert($('#chartcfgform').form('validate'));
    if($('#chartcfgform').form('validate')==false){
        return;
    }
    var pjData = $("#chartcfgform").serializeArray();

    //alert(JSON.stringify(pjData));
    $.ajax({
        type: "post",
        url: "addchartconfig",
        dataType: "json",
        data: pjData,
        async: false,
        success: function (data1) {
            if (data1 && data1.id) {
                $('#m_buyerror').datagrid('load', {

                });
                //关闭新增/修改窗口
                closeWindow();
                //刷新查询列表
                search();
            } else {
                $.messager.alert('提示', '表单添加失败！', 'info');
            }
        },
        error: function (data, type) {
            $.messager.alert('提示', '表单添加失败:' + data + type, 'info');
        }
    });
}




