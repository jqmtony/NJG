package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcEntityObjectCollection extends AbstractObjectCollection 
{
    public FdcEntityObjectCollection()
    {
        super(FdcEntityObjectInfo.class);
    }
    public boolean add(FdcEntityObjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcEntityObjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcEntityObjectInfo item)
    {
        return removeObject(item);
    }
    public FdcEntityObjectInfo get(int index)
    {
        return(FdcEntityObjectInfo)getObject(index);
    }
    public FdcEntityObjectInfo get(Object key)
    {
        return(FdcEntityObjectInfo)getObject(key);
    }
    public void set(int index, FdcEntityObjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcEntityObjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcEntityObjectInfo item)
    {
        return super.indexOf(item);
    }
}