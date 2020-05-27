<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>客户端演示示例</title>

        <link href="js/jquery-easyui-1.6.7/homepage/css/default.css" rel="stylesheet" type="text/css"/>
        <#include "/common/easyui/header_css.ftl">
        <#include "/common/easyui/header_js.ftl">
        <script type="text/javascript" src='js/jquery-easyui-1.6.7/homepage/homepage.js'></script>
        <script type="text/javascript">
            var _menus = {
                "menus": [
                    {
                        "menuid": "24", "icon": "icon-sys", "menuname": "演示功能",
                        "menus": [{"menuid": "25", "menuname": "调用事件生成器", "icon": "icon-nav", "url": "test1"},
                            {"menuid": "26", "menuname": "商品订单（事件样例）", "icon": "icon-nav", "url": "trade_demotradeorder/gotodemotradeorder"}
                        ]
                    }
                ]
            };

        </script>

    </head>
    <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
        <noscript>
        <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
            <img src="js/jquery-easyui-1.6.7/homepage/images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
        </div>
        </noscript>
        <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
             background: url(js/jquery-easyui-1.6.7/homepage/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
             line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
            <span style="float:right; padding-right:20px;" class="head"></span>
            <span style="padding-left:10px; font-size: 16px; ">
                <img src="../js/jquery-easyui-1.6.7/homepage/images/blocks.gif" width="20" height="20" align="absmiddle"/>
                监控-演示客户端
            </span>
        </div>

        <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
            <div id="nav" class="easyui-accordion" data-options="multiple:true"  border="false">
                <!--  导航内容 -->

            </div>
        </div>

        <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
            <div id="tabs" class="easyui-tabs" fit="true" border="false">
                <div title="首页" style="padding:20px;overflow:hidden; color:blue; ">
                    <h1 style="font-size:24px;">监控-演示客户端</h1>
                    <h1 style="font-size:24px;"></h1>
                </div>
            </div>
        </div>
    </body>
</html>