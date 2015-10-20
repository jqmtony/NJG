package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensationOfPayReqBillCollection extends AbstractObjectCollection 
{
    public CompensationOfPayReqBillCollection()
    {
        super(CompensationOfPayReqBillInfo.class);
    }
    public boolean add(CompensationOfPayReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensationOfPayReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensationOfPayReqBillInfo item)
    {
        return removeObject(item);
    }
    public CompensationOfPayReqBillInfo get(int index)
    {
        return(CompensationOfPayReqBillInfo)getObject(index);
    }
    public CompensationOfPayReqBillInfo get(Object key)
    {
        return(CompensationOfPayReqBillInfo)getObject(key);
    }
    public void set(int index, CompensationOfPayReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensationOfPayReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensationOfPayReqBillInfo item)
    {
        return super.indexOf(item);
    }
}