package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCNoCostSplitBillCollection extends AbstractObjectCollection 
{
    public FDCNoCostSplitBillCollection()
    {
        super(FDCNoCostSplitBillInfo.class);
    }
    public boolean add(FDCNoCostSplitBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCNoCostSplitBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCNoCostSplitBillInfo item)
    {
        return removeObject(item);
    }
    public FDCNoCostSplitBillInfo get(int index)
    {
        return(FDCNoCostSplitBillInfo)getObject(index);
    }
    public FDCNoCostSplitBillInfo get(Object key)
    {
        return(FDCNoCostSplitBillInfo)getObject(key);
    }
    public void set(int index, FDCNoCostSplitBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCNoCostSplitBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCNoCostSplitBillInfo item)
    {
        return super.indexOf(item);
    }
}