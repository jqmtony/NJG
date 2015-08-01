package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcSqlExeLogCollection extends AbstractObjectCollection 
{
    public FdcSqlExeLogCollection()
    {
        super(FdcSqlExeLogInfo.class);
    }
    public boolean add(FdcSqlExeLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcSqlExeLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcSqlExeLogInfo item)
    {
        return removeObject(item);
    }
    public FdcSqlExeLogInfo get(int index)
    {
        return(FdcSqlExeLogInfo)getObject(index);
    }
    public FdcSqlExeLogInfo get(Object key)
    {
        return(FdcSqlExeLogInfo)getObject(key);
    }
    public void set(int index, FdcSqlExeLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcSqlExeLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcSqlExeLogInfo item)
    {
        return super.indexOf(item);
    }
}