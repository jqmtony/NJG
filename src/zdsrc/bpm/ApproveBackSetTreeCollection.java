package com.kingdee.eas.bpm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ApproveBackSetTreeCollection extends AbstractObjectCollection 
{
    public ApproveBackSetTreeCollection()
    {
        super(ApproveBackSetTreeInfo.class);
    }
    public boolean add(ApproveBackSetTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ApproveBackSetTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ApproveBackSetTreeInfo item)
    {
        return removeObject(item);
    }
    public ApproveBackSetTreeInfo get(int index)
    {
        return(ApproveBackSetTreeInfo)getObject(index);
    }
    public ApproveBackSetTreeInfo get(Object key)
    {
        return(ApproveBackSetTreeInfo)getObject(key);
    }
    public void set(int index, ApproveBackSetTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ApproveBackSetTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ApproveBackSetTreeInfo item)
    {
        return super.indexOf(item);
    }
}