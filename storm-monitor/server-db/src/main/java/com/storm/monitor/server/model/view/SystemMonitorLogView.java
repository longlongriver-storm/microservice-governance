package com.storm.monitor.server.model.view;

import com.storm.monitor.server.model.SystemMonitorLog;
import com.storm.monitor.core.entity.PageView;
import com.storm.monitor.core.entity.Page;

/**
 * bean SystemMonitorLogView
 * Title: 【系统监控日志表】的View类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class SystemMonitorLogView extends SystemMonitorLog implements PageView {
        
        /**
         * 分页导航
         */
        private Page page = new Page();

                        /**日志时间-开始时间*/
                    private java.util.Date logTimeBegin;
                    /**日志时间-结束时间*/
                    private java.util.Date logTimeEnd;
                                                                                                        
                        public java.util.Date getLogTimeBegin(){
                            return logTimeBegin;
                    }
                    public void setLogTimeBegin(java.util.Date logTimeBegin){
                            this.logTimeBegin = logTimeBegin;
                    }
                    public java.util.Date getLogTimeEnd(){
                            return logTimeEnd;
                    }
                    public void setLogTimeEnd(java.util.Date logTimeEnd){
                            this.logTimeEnd = logTimeEnd;
                    }
                                                                                                        

        @Override
        public Page getPage() {
                return page;
        }

        public void setPage(Page page) {
                this.page = page;
        }
        
}
