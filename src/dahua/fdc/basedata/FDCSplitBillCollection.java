package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplitBillCollection extends AbstractObjectCollection 
{
    public FDCSplitBillCollection()
    {
        super(FDCSplitBillInfo.class);
    }
    public boolean add(FDCSplitBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplitBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplitBillInfo item)
    {
        return removeObject(item);
    }
    public FDCSplitBillInfo get(int index)
    {
        return(FDCSplitBillInfo)getObject(index);
    }
    public FDCSplitBillInfo get(Object key)
    {
        return(FDCSplitBillInfo)getObject(key);
    }
    public void set(int index, FDCSplitBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplitBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplitBillInfo item)
    {
        return super.indexOf(item);
    }
}