package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OverhaulNoticeCollection extends AbstractObjectCollection 
{
    public OverhaulNoticeCollection()
    {
        super(OverhaulNoticeInfo.class);
    }
    public boolean add(OverhaulNoticeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OverhaulNoticeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OverhaulNoticeInfo item)
    {
        return removeObject(item);
    }
    public OverhaulNoticeInfo get(int index)
    {
        return(OverhaulNoticeInfo)getObject(index);
    }
    public OverhaulNoticeInfo get(Object key)
    {
        return(OverhaulNoticeInfo)getObject(key);
    }
    public void set(int index, OverhaulNoticeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OverhaulNoticeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OverhaulNoticeInfo item)
    {
        return super.indexOf(item);
    }
}