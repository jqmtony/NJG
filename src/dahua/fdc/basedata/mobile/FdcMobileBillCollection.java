package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcMobileBillCollection extends AbstractObjectCollection 
{
    public FdcMobileBillCollection()
    {
        super(FdcMobileBillInfo.class);
    }
    public boolean add(FdcMobileBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcMobileBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcMobileBillInfo item)
    {
        return removeObject(item);
    }
    public FdcMobileBillInfo get(int index)
    {
        return(FdcMobileBillInfo)getObject(index);
    }
    public FdcMobileBillInfo get(Object key)
    {
        return(FdcMobileBillInfo)getObject(key);
    }
    public void set(int index, FdcMobileBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcMobileBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcMobileBillInfo item)
    {
        return super.indexOf(item);
    }
}