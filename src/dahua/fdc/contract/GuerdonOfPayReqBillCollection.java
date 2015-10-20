package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GuerdonOfPayReqBillCollection extends AbstractObjectCollection 
{
    public GuerdonOfPayReqBillCollection()
    {
        super(GuerdonOfPayReqBillInfo.class);
    }
    public boolean add(GuerdonOfPayReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GuerdonOfPayReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GuerdonOfPayReqBillInfo item)
    {
        return removeObject(item);
    }
    public GuerdonOfPayReqBillInfo get(int index)
    {
        return(GuerdonOfPayReqBillInfo)getObject(index);
    }
    public GuerdonOfPayReqBillInfo get(Object key)
    {
        return(GuerdonOfPayReqBillInfo)getObject(key);
    }
    public void set(int index, GuerdonOfPayReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GuerdonOfPayReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GuerdonOfPayReqBillInfo item)
    {
        return super.indexOf(item);
    }
}