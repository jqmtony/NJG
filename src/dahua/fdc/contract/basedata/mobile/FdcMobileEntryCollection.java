package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcMobileEntryCollection extends AbstractObjectCollection 
{
    public FdcMobileEntryCollection()
    {
        super(FdcMobileEntryInfo.class);
    }
    public boolean add(FdcMobileEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcMobileEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcMobileEntryInfo item)
    {
        return removeObject(item);
    }
    public FdcMobileEntryInfo get(int index)
    {
        return(FdcMobileEntryInfo)getObject(index);
    }
    public FdcMobileEntryInfo get(Object key)
    {
        return(FdcMobileEntryInfo)getObject(key);
    }
    public void set(int index, FdcMobileEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcMobileEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcMobileEntryInfo item)
    {
        return super.indexOf(item);
    }
}