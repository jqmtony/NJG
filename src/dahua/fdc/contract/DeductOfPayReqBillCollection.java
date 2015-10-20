package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeductOfPayReqBillCollection extends AbstractObjectCollection 
{
    public DeductOfPayReqBillCollection()
    {
        super(DeductOfPayReqBillInfo.class);
    }
    public boolean add(DeductOfPayReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeductOfPayReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeductOfPayReqBillInfo item)
    {
        return removeObject(item);
    }
    public DeductOfPayReqBillInfo get(int index)
    {
        return(DeductOfPayReqBillInfo)getObject(index);
    }
    public DeductOfPayReqBillInfo get(Object key)
    {
        return(DeductOfPayReqBillInfo)getObject(key);
    }
    public void set(int index, DeductOfPayReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeductOfPayReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeductOfPayReqBillInfo item)
    {
        return super.indexOf(item);
    }
}