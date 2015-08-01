package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBillEntryBaseCollection extends AbstractObjectCollection 
{
    public FDCBillEntryBaseCollection()
    {
        super(FDCBillEntryBaseInfo.class);
    }
    public boolean add(FDCBillEntryBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBillEntryBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBillEntryBaseInfo item)
    {
        return removeObject(item);
    }
    public FDCBillEntryBaseInfo get(int index)
    {
        return(FDCBillEntryBaseInfo)getObject(index);
    }
    public FDCBillEntryBaseInfo get(Object key)
    {
        return(FDCBillEntryBaseInfo)getObject(key);
    }
    public void set(int index, FDCBillEntryBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBillEntryBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBillEntryBaseInfo item)
    {
        return super.indexOf(item);
    }
}