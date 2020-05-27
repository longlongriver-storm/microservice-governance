<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>添加表单</title>
    </head>
    <body>
        <div style="padding:2px;border:1px solid #ddd">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title='保存' onclick="submitApmBusinessCfgInfo()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" title='保存' onclick="closeWindow()">取消</a>
        </div>
        <form id="apmbusinesscfgform" method="post">
            <input type="hidden" name="id" value="${pj.id!}" />
            <table width="98%">
                <tr>
                    <td width="25%" align="right">
                        业务主键: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,200]'] " name="businessKey" value="${pj.businessKey!}" size="90"></input>
                        
                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        标题: &nbsp;
                    </td>
                    <td width="75%">
                        <input class="easyui-textbox" data-options="validType:['length[0,200]'] " name="title" value="${pj.title!}" size="90"></input>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        描述: &nbsp;
                    </td>
                    <td width="75%">
                        <textarea name="description"  style="height:60px;width:600px">${pj.description!}</textarea>

                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        类名方法名: &nbsp;
                    </td>
                    <td width="75%">
                        <textarea name="classMethodName"  style="height:60px;width:600px">${pj.classMethodName!}</textarea>
                        <br><span style="color:red">*类似：com.storm.monitor.demo.service.impl.DemoTradeOrderServiceImpl.addDemoTradeOrder</span>
                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        输入参数: &nbsp;
                    </td>
                    <td width="75%">
                        <textarea name="parameters"  style="height:60px;width:600px">${pj.parameters!}</textarea>
                        <br><span style="color:red">*类似：0;1;2.id</span>
                    </td>
                </tr>
                <tr>
                    <td width="25%" align="right">
                        返回参数: &nbsp;
                    </td>
                    <td width="75%">
                        <textarea name="returnResult"  style="height:60px;width:600px">${pj.returnResult!}</textarea>
                        <br><span style="color:red">*类似：page.begin;name</span>
                    </td>
                </tr>

                <tr style="display: none">
                    <td>
                        <input name="createTime" class="easyui-datetimebox" value="${pj.createTime!}" >
                        <input name="modifyTime" class="easyui-datetimebox" value="${pj.modifyTime!}" >
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>