package com.storm.monitor.core.entity;

public class RuntimeInfo {

    private long m_startTime;

    private long m_upTime;

    private String m_javaVersion;

    private String m_userName;

    private String m_userDir;

    private String m_javaClasspath;

    public RuntimeInfo() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RuntimeInfo) {
            RuntimeInfo _o = (RuntimeInfo) obj;
            long startTime = _o.getStartTime();
            long upTime = _o.getUpTime();
            String javaVersion = _o.getJavaVersion();
            String userName = _o.getUserName();
            String userDir = _o.getUserDir();
            String javaClasspath = _o.getJavaClasspath();
            boolean result = true;

            result &= (m_startTime == startTime);
            result &= (m_upTime == upTime);
            result &= (m_javaVersion == javaVersion || m_javaVersion != null && m_javaVersion.equals(javaVersion));
            result &= (m_userName == userName || m_userName != null && m_userName.equals(userName));
            result &= (m_userDir == userDir || m_userDir != null && m_userDir.equals(userDir));
            result &= (m_javaClasspath == javaClasspath || m_javaClasspath != null && m_javaClasspath.equals(javaClasspath));

            return result;
        }

        return false;
    }

    public String getJavaClasspath() {
        return m_javaClasspath;
    }

    public String getJavaVersion() {
        return m_javaVersion;
    }

    public long getStartTime() {
        return m_startTime;
    }

    public long getUpTime() {
        return m_upTime;
    }

    public String getUserDir() {
        return m_userDir;
    }

    public String getUserName() {
        return m_userName;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash = hash * 31 + (int) (m_startTime ^ (m_startTime >>> 32));
        hash = hash * 31 + (int) (m_upTime ^ (m_upTime >>> 32));
        hash = hash * 31 + (m_javaVersion == null ? 0 : m_javaVersion.hashCode());
        hash = hash * 31 + (m_userName == null ? 0 : m_userName.hashCode());
        hash = hash * 31 + (m_userDir == null ? 0 : m_userDir.hashCode());
        hash = hash * 31 + (m_javaClasspath == null ? 0 : m_javaClasspath.hashCode());

        return hash;
    }

    public void mergeAttributes(RuntimeInfo other) {
        m_startTime = other.getStartTime();

        m_upTime = other.getUpTime();

        if (other.getJavaVersion() != null) {
            m_javaVersion = other.getJavaVersion();
        }

        if (other.getUserName() != null) {
            m_userName = other.getUserName();
        }
    }

    public void setJavaClasspath(String javaClasspath) {
        m_javaClasspath = javaClasspath;
    }

    public void setJavaVersion(String javaVersion) {
        m_javaVersion = javaVersion;
    }

    public void setStartTime(long startTime) {
        m_startTime = startTime;
    }

    public void setUpTime(long upTime) {
        m_upTime = upTime;
    }

    public void setUserDir(String userDir) {
        m_userDir = userDir;
    }

    public void setUserName(String userName) {
        m_userName = userName;
    }

}
