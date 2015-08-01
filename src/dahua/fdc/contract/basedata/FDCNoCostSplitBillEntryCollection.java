package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCNoCostSplitBillEntryCollection extends AbstractObjectCollection 
{
    public FDCNoCostSplitBillEntryCollection()
    {
        super(FDCNoCostSplitBillEntryInfo.class);
    }
    public boolean add(FDCNoCostSplitBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCNoCostSplitBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCNoCostSplitBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCNoCostSplitBillEntryInfo get(int index)
    {
        return(FDCNoCostSplitBillEntryInfo)getObject(index);
    }
    public FDCNoCostSplitBillEntryInfo get(Object key)
    {
        return(FDCNoCostSplitBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCNoCostSplitBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCNoCostSplitBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCNoCostSplitBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}