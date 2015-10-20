package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeductOfPayReqBillEntryCollection extends AbstractObjectCollection 
{
    public DeductOfPayReqBillEntryCollection()
    {
        super(DeductOfPayReqBillEntryInfo.class);
    }
    public boolean add(DeductOfPayReqBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeductOfPayReqBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeductOfPayReqBillEntryInfo item)
    {
        return removeObject(item);
    }
    public DeductOfPayReqBillEntryInfo get(int index)
    {
        return(DeductOfPayReqBillEntryInfo)getObject(index);
    }
    public DeductOfPayReqBillEntryInfo get(Object key)
    {
        return(DeductOfPayReqBillEntryInfo)getObject(key);
    }
    public void set(int index, DeductOfPayReqBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeductOfPayReqBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeductOfPayReqBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}