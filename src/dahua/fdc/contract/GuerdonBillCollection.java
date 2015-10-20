package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GuerdonBillCollection extends AbstractObjectCollection 
{
    public GuerdonBillCollection()
    {
        super(GuerdonBillInfo.class);
    }
    public boolean add(GuerdonBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GuerdonBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GuerdonBillInfo item)
    {
        return removeObject(item);
    }
    public GuerdonBillInfo get(int index)
    {
        return(GuerdonBillInfo)getObject(index);
    }
    public GuerdonBillInfo get(Object key)
    {
        return(GuerdonBillInfo)getObject(key);
    }
    public void set(int index, GuerdonBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GuerdonBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GuerdonBillInfo item)
    {
        return super.indexOf(item);
    }
}