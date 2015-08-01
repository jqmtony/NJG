package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcPropertyCollection extends AbstractObjectCollection 
{
    public FdcPropertyCollection()
    {
        super(FdcPropertyInfo.class);
    }
    public boolean add(FdcPropertyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcPropertyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcPropertyInfo item)
    {
        return removeObject(item);
    }
    public FdcPropertyInfo get(int index)
    {
        return(FdcPropertyInfo)getObject(index);
    }
    public FdcPropertyInfo get(Object key)
    {
        return(FdcPropertyInfo)getObject(key);
    }
    public void set(int index, FdcPropertyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcPropertyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcPropertyInfo item)
    {
        return super.indexOf(item);
    }
}