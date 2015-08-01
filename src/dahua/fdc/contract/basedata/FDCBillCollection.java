package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBillCollection extends AbstractObjectCollection 
{
    public FDCBillCollection()
    {
        super(FDCBillInfo.class);
    }
    public boolean add(FDCBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBillInfo item)
    {
        return removeObject(item);
    }
    public FDCBillInfo get(int index)
    {
        return(FDCBillInfo)getObject(index);
    }
    public FDCBillInfo get(Object key)
    {
        return(FDCBillInfo)getObject(key);
    }
    public void set(int index, FDCBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBillInfo item)
    {
        return super.indexOf(item);
    }
}