package com.storm.monitor.core.entity;

public class OsInfo {

    private String m_name;

    private String m_arch;

    private String m_version;

    private int m_availableProcessors;

    private double m_systemLoadAverage;

    private long m_processTime;

    private long m_totalPhysicalMemory;

    private long m_freePhysicalMemory;

    private long m_committedVirtualMemory;

    private long m_totalSwapSpace;

    private long m_freeSwapSpace;

    public OsInfo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OsInfo) {
            OsInfo _o = (OsInfo) obj;
            String name = _o.getName();
            String arch = _o.getArch();
            String version = _o.getVersion();
            int availableProcessors = _o.getAvailableProcessors();
            double systemLoadAverage = _o.getSystemLoadAverage();
            long processTime = _o.getProcessTime();
            long totalPhysicalMemory = _o.getTotalPhysicalMemory();
            long freePhysicalMemory = _o.getFreePhysicalMemory();
            long committedVirtualMemory = _o.getCommittedVirtualMemory();
            long totalSwapSpace = _o.getTotalSwapSpace();
            long freeSwapSpace = _o.getFreeSwapSpace();
            boolean result = true;

            result &= (m_name == name || m_name != null && m_name.equals(name));
            result &= (m_arch == arch || m_arch != null && m_arch.equals(arch));
            result &= (m_version == version || m_version != null && m_version.equals(version));
            result &= (m_availableProcessors == availableProcessors);
            result &= (m_systemLoadAverage == systemLoadAverage);
            result &= (m_processTime == processTime);
            result &= (m_totalPhysicalMemory == totalPhysicalMemory);
            result &= (m_freePhysicalMemory == freePhysicalMemory);
            result &= (m_committedVirtualMemory == committedVirtualMemory);
            result &= (m_totalSwapSpace == totalSwapSpace);
            result &= (m_freeSwapSpace == freeSwapSpace);

            return result;
        }

        return false;
    }

    public String getArch() {
        return m_arch;
    }

    public int getAvailableProcessors() {
        return m_availableProcessors;
    }

    public long getCommittedVirtualMemory() {
        return m_committedVirtualMemory;
    }

    public long getFreePhysicalMemory() {
        return m_freePhysicalMemory;
    }

    public long getFreeSwapSpace() {
        return m_freeSwapSpace;
    }

    public String getName() {
        return m_name;
    }

    public long getProcessTime() {
        return m_processTime;
    }

    public double getSystemLoadAverage() {
        return m_systemLoadAverage;
    }

    public long getTotalPhysicalMemory() {
        return m_totalPhysicalMemory;
    }

    public long getTotalSwapSpace() {
        return m_totalSwapSpace;
    }

    public String getVersion() {
        return m_version;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
        hash = hash * 31 + (m_arch == null ? 0 : m_arch.hashCode());
        hash = hash * 31 + (m_version == null ? 0 : m_version.hashCode());
        hash = hash * 31 + m_availableProcessors;
        hash = hash * 31 + (int) (Double.doubleToLongBits(m_systemLoadAverage) ^ (Double.doubleToLongBits(m_systemLoadAverage) >>> 32));
        hash = hash * 31 + (int) (m_processTime ^ (m_processTime >>> 32));
        hash = hash * 31 + (int) (m_totalPhysicalMemory ^ (m_totalPhysicalMemory >>> 32));
        hash = hash * 31 + (int) (m_freePhysicalMemory ^ (m_freePhysicalMemory >>> 32));
        hash = hash * 31 + (int) (m_committedVirtualMemory ^ (m_committedVirtualMemory >>> 32));
        hash = hash * 31 + (int) (m_totalSwapSpace ^ (m_totalSwapSpace >>> 32));
        hash = hash * 31 + (int) (m_freeSwapSpace ^ (m_freeSwapSpace >>> 32));

        return hash;
    }

    public void mergeAttributes(OsInfo other) {
        if (other.getName() != null) {
            m_name = other.getName();
        }

        if (other.getArch() != null) {
            m_arch = other.getArch();
        }

        if (other.getVersion() != null) {
            m_version = other.getVersion();
        }

        m_availableProcessors = other.getAvailableProcessors();

        m_systemLoadAverage = other.getSystemLoadAverage();

        m_processTime = other.getProcessTime();

        m_totalPhysicalMemory = other.getTotalPhysicalMemory();

        m_freePhysicalMemory = other.getFreePhysicalMemory();

        m_committedVirtualMemory = other.getCommittedVirtualMemory();

        m_totalSwapSpace = other.getTotalSwapSpace();

        m_freeSwapSpace = other.getFreeSwapSpace();
    }

    public void setArch(String arch) {
        m_arch = arch;
    }

    public void setAvailableProcessors(int availableProcessors) {
        m_availableProcessors = availableProcessors;
    }

    public void setCommittedVirtualMemory(long committedVirtualMemory) {
        m_committedVirtualMemory = committedVirtualMemory;
    }

    public void setFreePhysicalMemory(long freePhysicalMemory) {
        m_freePhysicalMemory = freePhysicalMemory;
    }

    public void setFreeSwapSpace(long freeSwapSpace) {
        m_freeSwapSpace = freeSwapSpace;
    }

    public void setName(String name) {
        m_name = name;
    }

    public void setProcessTime(long processTime) {
        m_processTime = processTime;
    }

    public void setSystemLoadAverage(double systemLoadAverage) {
        m_systemLoadAverage = systemLoadAverage;
    }

    public void setTotalPhysicalMemory(long totalPhysicalMemory) {
        m_totalPhysicalMemory = totalPhysicalMemory;
    }

    public void setTotalSwapSpace(long totalSwapSpace) {
        m_totalSwapSpace = totalSwapSpace;
    }

    public void setVersion(String version) {
        m_version = version;
    }

}
