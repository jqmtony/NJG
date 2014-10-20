package com.kingdee.eas.bpm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ApproveBackSetEntryCollection extends AbstractObjectCollection 
{
    public ApproveBackSetEntryCollection()
    {
        super(ApproveBackSetEntryInfo.class);
    }
    public boolean add(ApproveBackSetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ApproveBackSetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ApproveBackSetEntryInfo item)
    {
        return removeObject(item);
    }
    public ApproveBackSetEntryInfo get(int index)
    {
        return(ApproveBackSetEntryInfo)getObject(index);
    }
    public ApproveBackSetEntryInfo get(Object key)
    {
        return(ApproveBackSetEntryInfo)getObject(key);
    }
    public void set(int index, ApproveBackSetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ApproveBackSetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ApproveBackSetEntryInfo item)
    {
        return super.indexOf(item);
    }
}