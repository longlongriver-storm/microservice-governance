/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client.collector;

import com.storm.monitor.core.client.Honeycomb;
import com.alibaba.fastjson.JSON;
import com.storm.monitor.core.common.Common;
import com.storm.monitor.core.entity.DiskInfo;
import com.storm.monitor.core.entity.DiskVolumeInfo;
import com.storm.monitor.core.entity.GcInfo;
import com.storm.monitor.core.entity.MemoryInfo;
import com.storm.monitor.core.entity.MessageInfo;
import com.storm.monitor.core.entity.MessageTree;
import com.storm.monitor.core.entity.OsInfo;
import com.storm.monitor.core.entity.RuntimeInfo;
import com.storm.monitor.core.entity.SystemStatusInfo;
import com.storm.monitor.core.entity.ThreadsInfo;
import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统性能信息收集
 *
 * @author lixin
 */
public class SystemStatusInfoCollector implements Runnable {

    public static final boolean m_dumpLocked = false;
    public static final boolean m_logThreadDetail = false;

    private static final Logger LOG = LoggerFactory.getLogger(SystemStatusInfoCollector.class);

    /**
     * 系统性能接口监控日志
     */
    private static final Logger LOG_SYSTEM_INFO_MONITOR = LoggerFactory.getLogger("APM-SYSTEM-STATUS-INFO-MONITOR");

    @Override
    public void run() {
        while (true) {
            Calendar cal = Calendar.getInstance();
            int second = cal.get(Calendar.SECOND);

            // try to avoid send heartbeat at 59-01 second
            if (second < 2 || second > 58) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // ignore it
                }
            } else {
                break;
            }
        }

        collect();
    }

    /**
     * 系统信息收集
     */
    public void collect() {
        SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String minute_time = Honeycomb.getIstance().isIsLogStoreLocal()?format_time.format(new Date()):String.valueOf(Common.getCompleteMinuteForTime(System.currentTimeMillis()));

        SystemStatusInfo status = new SystemStatusInfo();
        status.setRuntime(new RuntimeInfo());
        status.setOs(new OsInfo());
        status.setDisk(new DiskInfo());
        status.setMemory(new MemoryInfo());
        status.setThread(new ThreadsInfo());
        status.setMessage(new MessageInfo());
        try {
            visitRuntime(status.getRuntime());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitRuntime() error:{}", ex);
        }

        try {
            visitOs(status.getOs());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitOs() error:{}", ex);
        }

        try {
            visitDisk(status.getDisk());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitDisk() error:{}", ex);
        }

        try {
            visitMemory(status.getMemory());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitMemory() error:{}", ex);
        }

        try {
            visitThread(status.getThread());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitThread() error:{}", ex);
        }

        try {
            boolean isRemoteSenderOK=false;
            if(Honeycomb.getIstance().isIsLogStoreLocal()==false){  //**远程日志采集**
                MessageTree msg = Honeycomb.getIstance().buildMessageTree(MessageTree.MESSAGE_TYPE_SYSTEM);
                msg.setMessage(String.format("%s,SystemStatusInfo=%s;", minute_time, JSON.toJSONString(status)));
                isRemoteSenderOK=Honeycomb.getIstance().sendMsg(msg);
            }
            if (Honeycomb.getIstance().isIsLogStoreLocal() || isRemoteSenderOK==false) {   //***本地日志存储/远程发送失败***
                LOG_SYSTEM_INFO_MONITOR.info("{},SystemStatusInfo={};", minute_time, JSON.toJSONString(status));
            } 

        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call log.info() error:{}", ex);
        }

        //System.out.println(JSON.toJSONString(status));
    }

    private String buildClasspath() {
        String m_jars = null;
        ClassLoader loader = SystemStatusInfoCollector.class.getClassLoader();
        StringBuilder sb = new StringBuilder();

        buildClasspath(loader, sb);
        if (sb.length() > 0) {
            m_jars = sb.substring(0, sb.length() - 1);
        }
        return m_jars;
    }

    private void buildClasspath(ClassLoader loader, StringBuilder sb) {
        if (loader instanceof URLClassLoader) {
            URL[] urLs = ((URLClassLoader) loader).getURLs();
            for (URL url : urLs) {
                String jar = parseJar(url.toExternalForm());

                if (jar != null) {
                    sb.append(jar).append(',');
                }
            }
            ClassLoader parent = loader.getParent();

            buildClasspath(parent, sb);
        }
    }

    private String parseJar(String path) {
        if (path.endsWith(".jar")) {
            int index = path.lastIndexOf('/');

            if (index > -1) {
                return path.substring(index + 1);
            }
        }
        return null;
    }

    public void visitRuntime(RuntimeInfo runtime) {
        String m_jars = null;

        try {
            m_jars = buildClasspath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

        runtime.setStartTime(bean.getStartTime());
        runtime.setUpTime(bean.getUptime());
        runtime.setJavaClasspath(m_jars);
        runtime.setJavaVersion(System.getProperty("java.version"));
        runtime.setUserDir(System.getProperty("user.dir"));
        runtime.setUserName(System.getProperty("user.name"));
    }

    public void visitOs(OsInfo os) {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();

        os.setArch(bean.getArch());        //操作系统的架构
        os.setName(bean.getName());        //操作系统名称
        os.setVersion(bean.getVersion());  //操作系统的版本
        os.setAvailableProcessors(bean.getAvailableProcessors());    //Java 虚拟机可以使用的处理器数目
        os.setSystemLoadAverage(bean.getSystemLoadAverage());        //最后一分钟内系统加载平均值

        // for Sun JDK
        if (isInstanceOfInterface(bean.getClass(), "com.sun.management.OperatingSystemMXBean")) {
            com.sun.management.OperatingSystemMXBean b = (com.sun.management.OperatingSystemMXBean) bean;

            os.setTotalPhysicalMemory(b.getTotalPhysicalMemorySize());          //总物理内存量
            os.setFreePhysicalMemory(b.getFreePhysicalMemorySize());            //未分配物理内存量
            os.setTotalSwapSpace(b.getTotalSwapSpaceSize());                    //总交换空间量
            os.setFreeSwapSpace(b.getFreeSwapSpaceSize());                      //未分配交换空间量
            os.setProcessTime(b.getProcessCpuTime());                           //java到当前为止所占用的CPU处理时间
            os.setCommittedVirtualMemory(b.getCommittedVirtualMemorySize());    //java运行进程保证可用的虚拟内存大小
        }
    }

    public void visitDisk(DiskInfo disk) {
        File[] roots = File.listRoots();

        if (roots != null) {
            for (File root : roots) {
                disk.addDiskVolume(new DiskVolumeInfo(root.getAbsolutePath()));
            }
        }

        String m_dataPath = "/data";
        File data = new File(m_dataPath);

        if (data.exists()) {
            disk.addDiskVolume(new DiskVolumeInfo(data.getAbsolutePath()));
        }

        //super.visitDisk(disk);
        for (DiskVolumeInfo diskVolume : disk.getDiskVolumes()) {
            visitDiskVolume(diskVolume);
        }
    }

    public void visitDiskVolume(DiskVolumeInfo diskVolume) {
        File volume = new File(diskVolume.getId());

        diskVolume.setTotal(volume.getTotalSpace());   //总空间
        diskVolume.setFree(volume.getFreeSpace());     //未分配空间
        diskVolume.setUsable(volume.getUsableSpace()); //可使用空间
        diskVolume.setId(diskVolume.getId().replaceAll("\\\\", "/"));    //将windows风格的路径转换成UNIX风格的路径
    }

    public void visitMemory(MemoryInfo memory) {
        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        Runtime runtime = Runtime.getRuntime();

        memory.setMax(runtime.maxMemory());       //Java 虚拟机试图使用的最大内存量
        memory.setTotal(runtime.totalMemory());   //Java 虚拟机中的内存总量
        memory.setFree(runtime.freeMemory());     //Java 虚拟机中的空闲内存量
        memory.setHeapUsage(bean.getHeapMemoryUsage().getUsed());         //用于对象分配的堆的当前内存使用量(返回已使用的内存量)
        memory.setNonHeapUsage(bean.getNonHeapMemoryUsage().getUsed());   //Java 虚拟机使用的非堆内存的当前内存使用量

        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();  //用于 Java 虚拟机的垃圾回收的管理接口( 返回 Java 虚拟机中的 GarbageCollectorMXBean 对象列表)

        for (GarbageCollectorMXBean mxbean : beans) {  //各个垃圾收集器
            if (mxbean.isValid()) {
                GcInfo gc = new GcInfo();
                String name = mxbean.getName();
                long count = mxbean.getCollectionCount();

                gc.setName(name);
                gc.setCount(count);
                gc.setTime(mxbean.getCollectionTime());
                memory.addGc(gc);

            }
        }

        //super.visitMemory(memory);
        for (GcInfo gc : memory.getGcs()) {
            visitGc(gc);
        }
    }

    public void visitGc(GcInfo gc) {
    }

    public void visitThread(ThreadsInfo thread) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        bean.setThreadContentionMonitoringEnabled(true);

        ThreadInfo[] threads;

        if (m_dumpLocked) {
            threads = bean.dumpAllThreads(true, true);   //返回所有活动线程的线程信息，并带有堆栈跟踪和同步信息。 当此方法返回时，返回数组中包含的一些线程可能已经终止。 
        } else {
            threads = bean.dumpAllThreads(false, false);
        }

        thread.setCount(bean.getThreadCount());
        thread.setDaemonCount(bean.getDaemonThreadCount());                                 //返回活动守护线程的当前数目。
        thread.setPeekCount(bean.getPeakThreadCount());                                     //返回自从 Java 虚拟机启动或峰值重置以来峰值活动线程计数。
        thread.setTotalStartedCount((int) bean.getTotalStartedThreadCount());               // 返回自从 Java 虚拟机启动以来创建和启动的线程总数目。

        int jbossThreadsCount = countThreadsByPrefix(threads, "http-", "catalina-exec-");   //jboss启动的线程数
        int jettyThreadsCount = countThreadsBySubstring(threads, "@qtp");                   //jetty启动的线程数

        if (m_logThreadDetail) {
            thread.setDump(getThreadDump(threads));
        }

        thread.setHttpThread(jbossThreadsCount + jettyThreadsCount);
        thread.setCatThreadCount(countThreadsByPrefix(threads, "APM-"));
        //frameworkThread.findOrCreateExtensionDetail("CatThread").setValue(countThreadsByPrefix(threads, "Cat-"));
        thread.setPigeonThread(countThreadsByPrefix(threads, "Pigeon-", "DPSF-", "Netty-", "Client-ResponseProcessor"));
        thread.setActiveThread(bean.getThreadCount());
        thread.setStartedThread(bean.getTotalStartedThreadCount());
    }

    private int countThreadsByPrefix(ThreadInfo[] threads, String... prefixes) {
        int count = 0;

        for (ThreadInfo thread : threads) {
            for (String prefix : prefixes) {
                if (thread.getThreadName().startsWith(prefix)) {
                    count++;
                }
            }
        }

        return count;
    }

    private int countThreadsBySubstring(ThreadInfo[] threads, String... substrings) {
        int count = 0;

        for (ThreadInfo thread : threads) {
            for (String str : substrings) {
                if (thread.getThreadName().contains(str)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 获取每个线程的详细信息,是一个非常长的字符串
     *
     * @param threads
     * @return
     */
    private String getThreadDump(ThreadInfo[] threads) {
        StringBuilder sb = new StringBuilder(32768);
        int index = 1;

        TreeMap<String, ThreadInfo> sortedThreads = new TreeMap<String, ThreadInfo>();

        //对线程进行排序
        for (ThreadInfo thread : threads) {
            sortedThreads.put(thread.getThreadName(), thread);
        }

        //导出排序后的线程
        for (ThreadInfo thread : sortedThreads.values()) {
            sb.append(index++).append(": ").append(thread);
        }

        return sb.toString();
    }

    boolean isInstanceOfInterface(Class<?> clazz, String interfaceName) {
        if (clazz == Object.class) {
            return false;
        } else if (clazz.getName().equals(interfaceName)) {
            return true;
        }

        Class<?>[] interfaceclasses = clazz.getInterfaces();

        for (Class<?> interfaceClass : interfaceclasses) {
            if (isInstanceOfInterface(interfaceClass, interfaceName)) {
                return true;
            }
        }

        return isInstanceOfInterface(clazz.getSuperclass(), interfaceName);
    }
    
    public String collecttest() {
        SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String minute_time = Honeycomb.getIstance().isIsLogStoreLocal()?format_time.format(new Date()):String.valueOf(Common.getCompleteMinuteForTime(System.currentTimeMillis()));

        SystemStatusInfo status = new SystemStatusInfo();
        status.setRuntime(new RuntimeInfo());
        status.setOs(new OsInfo());
        status.setDisk(new DiskInfo());
        status.setMemory(new MemoryInfo());
        status.setThread(new ThreadsInfo());
        status.setMessage(new MessageInfo());
        try {
            visitRuntime(status.getRuntime());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitRuntime() error:{}", ex);
        }

        try {
            visitOs(status.getOs());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitOs() error:{}", ex);
        }

        try {
            visitDisk(status.getDisk());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitDisk() error:{}", ex);
        }

        try {
            visitMemory(status.getMemory());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitMemory() error:{}", ex);
        }

        try {
            visitThread(status.getThread());
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call visitThread() error:{}", ex);
        }

        try {
            String str= JSON.toJSONString(status);
            return str;
        } catch (Exception ex) {
            LOG.error("SystemStatusInfoCollector.collect call log.info() error:{}", ex);
        }
        return null;
        //System.out.println(JSON.toJSONString(status));
    }
    
    public static void main(String[] args) throws Exception{
        SystemStatusInfoCollector ssic=new SystemStatusInfoCollector();
        String str=ssic.collecttest();
        System.out.println(str);
        SystemStatusInfo status=JSON.parseObject(str, SystemStatusInfo.class);
        System.out.println(status.getDisk().getDiskVolumes().size());
    }

}
