package com.storm.monitor.core.entity;


public class GcInfo {

    private String m_name;

    private long m_count;

    private long m_time;

    public GcInfo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GcInfo) {
            GcInfo _o = (GcInfo) obj;
            String name = _o.getName();
            long count = _o.getCount();
            long time = _o.getTime();
            boolean result = true;

            result &= (m_name == name || m_name != null && m_name.equals(name));
            result &= (m_count == count);
            result &= (m_time == time);

            return result;
        }

        return false;
    }

    public long getCount() {
        return m_count;
    }

    public String getName() {
        return m_name;
    }

    public long getTime() {
        return m_time;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
        hash = hash * 31 + (int) (m_count ^ (m_count >>> 32));
        hash = hash * 31 + (int) (m_time ^ (m_time >>> 32));

        return hash;
    }

    public void mergeAttributes(GcInfo other) {
        if (other.getName() != null) {
            m_name = other.getName();
        }

        m_count = other.getCount();

        m_time = other.getTime();
    }

    public GcInfo setCount(long count) {
        m_count = count;
        return this;
    }

    public GcInfo setName(String name) {
        m_name = name;
        return this;
    }

    public GcInfo setTime(long time) {
        m_time = time;
        return this;
    }

}
