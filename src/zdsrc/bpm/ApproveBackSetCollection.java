package com.kingdee.eas.bpm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ApproveBackSetCollection extends AbstractObjectCollection 
{
    public ApproveBackSetCollection()
    {
        super(ApproveBackSetInfo.class);
    }
    public boolean add(ApproveBackSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ApproveBackSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ApproveBackSetInfo item)
    {
        return removeObject(item);
    }
    public ApproveBackSetInfo get(int index)
    {
        return(ApproveBackSetInfo)getObject(index);
    }
    public ApproveBackSetInfo get(Object key)
    {
        return(ApproveBackSetInfo)getObject(key);
    }
    public void set(int index, ApproveBackSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ApproveBackSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ApproveBackSetInfo item)
    {
        return super.indexOf(item);
    }
}