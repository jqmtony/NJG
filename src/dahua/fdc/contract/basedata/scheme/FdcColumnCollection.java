package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcColumnCollection extends AbstractObjectCollection 
{
    public FdcColumnCollection()
    {
        super(FdcColumnInfo.class);
    }
    public boolean add(FdcColumnInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcColumnCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcColumnInfo item)
    {
        return removeObject(item);
    }
    public FdcColumnInfo get(int index)
    {
        return(FdcColumnInfo)getObject(index);
    }
    public FdcColumnInfo get(Object key)
    {
        return(FdcColumnInfo)getObject(key);
    }
    public void set(int index, FdcColumnInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcColumnInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcColumnInfo item)
    {
        return super.indexOf(item);
    }
}