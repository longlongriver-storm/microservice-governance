<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>浏览表单</title>
    </head>
    <body>
        <div style="padding:2px;border:1px solid #ddd">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" title='退出' onclick="closeWindow()">退出</a>
        </div>
        <form id="errorlogform" method="post">
            <input type="hidden" name="id" value="${pj.id!}" />
            <table width="98%">
                <tr>
                    <td width="25%" align="right">
                        日志时间: &nbsp;
                    </td>
                    <td width="75%">
                        <input name="logTime" class="easyui-datetimebox" value="${pj.logTime!}" style="width:150px;">

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        主机地址: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,50]'] ,required:true " name="machineAddress" value="${pj.machineAddress!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        服务名称: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,250]'] ,required:true " name="serviceName" value="${pj.serviceName!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        异常类型: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox"    name="errorType" value="${pj.errorType!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        跟踪ID: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,100]'] " name="traceId" value="${pj.traceId!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        用户标识: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,50]'] " name="userKey" value="${pj.userKey!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        错误代码: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,400]'] " name="errorCode" value="${pj.errorCode!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        错误信息: &nbsp;
                    </td>
                    <td width="75%">
                        <textarea name="errorMsg"  style="height:60px;width:600px">${pj.errorMsg!}</textarea>

                    </td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>
    </body>
</html>