/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

/**
 *
 * @author lixin
 */
public class SystemStatusInfo {

    private java.util.Date m_timestamp;

    private RuntimeInfo m_runtime;

    private OsInfo m_os;

    private DiskInfo m_disk;

    private MemoryInfo m_memory;

    private ThreadsInfo m_thread;

    private MessageInfo m_message;

    public DiskInfo getDisk() {
        return m_disk;
    }

    public MemoryInfo getMemory() {
        return m_memory;
    }

    public MessageInfo getMessage() {
        return m_message;
    }

    public OsInfo getOs() {
        return m_os;
    }

    public RuntimeInfo getRuntime() {
        return m_runtime;
    }

    public ThreadsInfo getThread() {
        return m_thread;
    }

    public java.util.Date getTimestamp() {
        return m_timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash = hash * 31 + (m_timestamp == null ? 0 : m_timestamp.hashCode());
        hash = hash * 31 + (m_runtime == null ? 0 : m_runtime.hashCode());
        hash = hash * 31 + (m_os == null ? 0 : m_os.hashCode());
        hash = hash * 31 + (m_disk == null ? 0 : m_disk.hashCode());
        hash = hash * 31 + (m_memory == null ? 0 : m_memory.hashCode());
        hash = hash * 31 + (m_thread == null ? 0 : m_thread.hashCode());
        hash = hash * 31 + (m_message == null ? 0 : m_message.hashCode());

        return hash;
    }

    public void mergeAttributes(SystemStatusInfo other) {
        if (other.getTimestamp() != null) {
            m_timestamp = other.getTimestamp();
        }
    }

    public void setDisk(DiskInfo disk) {
        m_disk = disk;
    }

    public void setMemory(MemoryInfo memory) {
        m_memory = memory;
    }

    public void setMessage(MessageInfo message) {
        m_message = message;
    }

    public void setOs(OsInfo os) {
        m_os = os;
    }

    public void setRuntime(RuntimeInfo runtime) {
        m_runtime = runtime;
    }

    public void setThread(ThreadsInfo thread) {
        m_thread = thread;
    }

    public void setTimestamp(java.util.Date timestamp) {
        m_timestamp = timestamp;
    }

}
