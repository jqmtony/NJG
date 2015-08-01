package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcDataTableCollection extends AbstractObjectCollection 
{
    public FdcDataTableCollection()
    {
        super(FdcDataTableInfo.class);
    }
    public boolean add(FdcDataTableInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcDataTableCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcDataTableInfo item)
    {
        return removeObject(item);
    }
    public FdcDataTableInfo get(int index)
    {
        return(FdcDataTableInfo)getObject(index);
    }
    public FdcDataTableInfo get(Object key)
    {
        return(FdcDataTableInfo)getObject(key);
    }
    public void set(int index, FdcDataTableInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcDataTableInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcDataTableInfo item)
    {
        return super.indexOf(item);
    }
}