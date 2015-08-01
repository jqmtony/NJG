package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BITargetCollection extends AbstractObjectCollection 
{
    public BITargetCollection()
    {
        super(BITargetInfo.class);
    }
    public boolean add(BITargetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BITargetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BITargetInfo item)
    {
        return removeObject(item);
    }
    public BITargetInfo get(int index)
    {
        return(BITargetInfo)getObject(index);
    }
    public BITargetInfo get(Object key)
    {
        return(BITargetInfo)getObject(key);
    }
    public void set(int index, BITargetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BITargetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BITargetInfo item)
    {
        return super.indexOf(item);
    }
}