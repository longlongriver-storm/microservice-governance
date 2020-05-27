package com.storm.monitor.core.entity;


public class MessageInfo {
   private long m_produced;

   private long m_overflowed;

   private long m_bytes;

   public MessageInfo() {
   }


   @Override
   public boolean equals(Object obj) {
      if (obj instanceof MessageInfo) {
         MessageInfo _o = (MessageInfo) obj;
         long produced = _o.getProduced();
         long overflowed = _o.getOverflowed();
         long bytes = _o.getBytes();
         boolean result = true;

         result &= (m_produced == produced);
         result &= (m_overflowed == overflowed);
         result &= (m_bytes == bytes);

         return result;
      }

      return false;
   }

   public long getBytes() {
      return m_bytes;
   }

   public long getOverflowed() {
      return m_overflowed;
   }

   public long getProduced() {
      return m_produced;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (int) (m_produced ^ (m_produced >>> 32));
      hash = hash * 31 + (int) (m_overflowed ^ (m_overflowed >>> 32));
      hash = hash * 31 + (int) (m_bytes ^ (m_bytes >>> 32));

      return hash;
   }

   public void mergeAttributes(MessageInfo other) {
      m_produced = other.getProduced();

      m_overflowed = other.getOverflowed();

      m_bytes = other.getBytes();
   }

   public void setBytes(long bytes) {
      m_bytes = bytes;
   }

   public void setOverflowed(long overflowed) {
      m_overflowed = overflowed;
   }

   public void setProduced(long produced) {
      m_produced = produced;
   }

}
