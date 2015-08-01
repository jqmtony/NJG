package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartAOfPayReqBillCollection extends AbstractObjectCollection 
{
    public PartAOfPayReqBillCollection()
    {
        super(PartAOfPayReqBillInfo.class);
    }
    public boolean add(PartAOfPayReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartAOfPayReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartAOfPayReqBillInfo item)
    {
        return removeObject(item);
    }
    public PartAOfPayReqBillInfo get(int index)
    {
        return(PartAOfPayReqBillInfo)getObject(index);
    }
    public PartAOfPayReqBillInfo get(Object key)
    {
        return(PartAOfPayReqBillInfo)getObject(key);
    }
    public void set(int index, PartAOfPayReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartAOfPayReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartAOfPayReqBillInfo item)
    {
        return super.indexOf(item);
    }
}