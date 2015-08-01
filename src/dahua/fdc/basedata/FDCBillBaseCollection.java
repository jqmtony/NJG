package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBillBaseCollection extends AbstractObjectCollection 
{
    public FDCBillBaseCollection()
    {
        super(FDCBillBaseInfo.class);
    }
    public boolean add(FDCBillBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBillBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBillBaseInfo item)
    {
        return removeObject(item);
    }
    public FDCBillBaseInfo get(int index)
    {
        return(FDCBillBaseInfo)getObject(index);
    }
    public FDCBillBaseInfo get(Object key)
    {
        return(FDCBillBaseInfo)getObject(key);
    }
    public void set(int index, FDCBillBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBillBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBillBaseInfo item)
    {
        return super.indexOf(item);
    }
}