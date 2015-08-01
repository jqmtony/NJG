package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensationBillCollection extends AbstractObjectCollection 
{
    public CompensationBillCollection()
    {
        super(CompensationBillInfo.class);
    }
    public boolean add(CompensationBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensationBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensationBillInfo item)
    {
        return removeObject(item);
    }
    public CompensationBillInfo get(int index)
    {
        return(CompensationBillInfo)getObject(index);
    }
    public CompensationBillInfo get(Object key)
    {
        return(CompensationBillInfo)getObject(key);
    }
    public void set(int index, CompensationBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensationBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensationBillInfo item)
    {
        return super.indexOf(item);
    }
}