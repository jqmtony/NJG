package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartAConfmOfPayReqBillCollection extends AbstractObjectCollection 
{
    public PartAConfmOfPayReqBillCollection()
    {
        super(PartAConfmOfPayReqBillInfo.class);
    }
    public boolean add(PartAConfmOfPayReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartAConfmOfPayReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartAConfmOfPayReqBillInfo item)
    {
        return removeObject(item);
    }
    public PartAConfmOfPayReqBillInfo get(int index)
    {
        return(PartAConfmOfPayReqBillInfo)getObject(index);
    }
    public PartAConfmOfPayReqBillInfo get(Object key)
    {
        return(PartAConfmOfPayReqBillInfo)getObject(key);
    }
    public void set(int index, PartAConfmOfPayReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartAConfmOfPayReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartAConfmOfPayReqBillInfo item)
    {
        return super.indexOf(item);
    }
}