<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>添加表单</title>
    </head>
    <body>
        <div style="padding:2px;border:1px solid #ddd">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title='保存' onclick="submitCustomDataPickLogInfo()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" title='保存' onclick="closeWindow()">取消</a>
        </div>
        <form id="customdatapicklogform" method="post">
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
                                服务名称: &nbsp;
                            </td>
                            <td width="75%">
                                                                                                                                                       <input class="easyui-textbox" data-options="validType:['length[0,250]'] ,required:true " name="serviceName" value="${pj.serviceName!}" size="90"></input>
                                                                                                            
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
                                调用链ID: &nbsp;
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
                                抓取数据: &nbsp;
                            </td>
                            <td width="75%">
                                                                                                                                                       <textarea name="pickData"  style="height:60px;width:600px">${pj.pickData!}</textarea>
                                                                                                            
                            </td>
                        </tr>
                                                    <tr>
                </tr>
            </table>
        </form>
    </body>
</html>