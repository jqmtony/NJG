package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OverhaulNoticeEntryCollection extends AbstractObjectCollection 
{
    public OverhaulNoticeEntryCollection()
    {
        super(OverhaulNoticeEntryInfo.class);
    }
    public boolean add(OverhaulNoticeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OverhaulNoticeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OverhaulNoticeEntryInfo item)
    {
        return removeObject(item);
    }
    public OverhaulNoticeEntryInfo get(int index)
    {
        return(OverhaulNoticeEntryInfo)getObject(index);
    }
    public OverhaulNoticeEntryInfo get(Object key)
    {
        return(OverhaulNoticeEntryInfo)getObject(key);
    }
    public void set(int index, OverhaulNoticeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OverhaulNoticeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OverhaulNoticeEntryInfo item)
    {
        return super.indexOf(item);
    }
}