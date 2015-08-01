package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBillEntryCollection extends AbstractObjectCollection 
{
    public FDCBillEntryCollection()
    {
        super(FDCBillEntryInfo.class);
    }
    public boolean add(FDCBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCBillEntryInfo get(int index)
    {
        return(FDCBillEntryInfo)getObject(index);
    }
    public FDCBillEntryInfo get(Object key)
    {
        return(FDCBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}