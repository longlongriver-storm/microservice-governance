function serializeFormArray2Object(serializeArray) {
    var obj = new Object();
    $.each(serializeArray, function (index, param) {
        if (!(param.name in obj)) {
            obj[param.name] = param.value;
        }
    });
    return obj;
}
;

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

/**
 * 格式化日期列
 * @param {type} val
 * @param {type} row
 * @returns {String|oTime}
 */
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

//对超文本进行编码
function HTMLEncode(text) {
    var textold;
    do {
        textold = text;

        text = text.replace(/&/g, "＆");
        text = text.replace(/</g, "＜");
        text = text.replace(/>/g, "＞");
    } while (textold != text);

    return text;
}

// 弹出窗口
function showWindow(options) {
    jQuery("#MyPopWindow").window(options);
}

// 关闭弹出窗口
function closeWindow() {
    $("#MyPopWindow").window('close');
}