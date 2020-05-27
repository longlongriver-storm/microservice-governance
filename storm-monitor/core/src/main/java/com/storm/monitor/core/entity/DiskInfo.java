/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

import java.util.ArrayList;
import java.util.List;

public class DiskInfo {
   private List<DiskVolumeInfo> m_diskVolumes = new ArrayList<DiskVolumeInfo>();

   public DiskInfo() {
   }

   public DiskInfo addDiskVolume(DiskVolumeInfo diskVolume) {
      m_diskVolumes.add(diskVolume);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof DiskInfo) {
         DiskInfo _o = (DiskInfo) obj;
         List<DiskVolumeInfo> diskVolumes = _o.getDiskVolumes();
         boolean result = true;

         result &= (m_diskVolumes == diskVolumes || m_diskVolumes != null && m_diskVolumes.equals(diskVolumes));

         return result;
      }

      return false;
   }

   public DiskVolumeInfo findDiskVolume(String id) {
      for (DiskVolumeInfo diskVolume : m_diskVolumes) {
         if (!diskVolume.getId().equals(id)) {
            continue;
         }

         return diskVolume;
      }

      return null;
   }

   public List<DiskVolumeInfo> getDiskVolumes() {
      return m_diskVolumes;
   }
   
   public void setDiskVolumes(List<DiskVolumeInfo> diskVolumes){
       m_diskVolumes=diskVolumes;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_diskVolumes == null ? 0 : m_diskVolumes.hashCode());

      return hash;
   }

   public boolean removeDiskVolume(String id) {
      int len = m_diskVolumes.size();

      for (int i = 0; i < len; i++) {
         DiskVolumeInfo diskVolume = m_diskVolumes.get(i);

         if (!diskVolume.getId().equals(id)) {
            continue;
         }

         m_diskVolumes.remove(i);
         return true;
      }

      return false;
   }

}
