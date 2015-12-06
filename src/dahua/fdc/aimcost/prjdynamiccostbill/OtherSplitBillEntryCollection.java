package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherSplitBillEntryCollection extends AbstractObjectCollection 
{
    public OtherSplitBillEntryCollection()
    {
        super(OtherSplitBillEntryInfo.class);
    }
    public boolean add(OtherSplitBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherSplitBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherSplitBillEntryInfo item)
    {
        return removeObject(item);
    }
    public OtherSplitBillEntryInfo get(int index)
    {
        return(OtherSplitBillEntryInfo)getObject(index);
    }
    public OtherSplitBillEntryInfo get(Object key)
    {
        return(OtherSplitBillEntryInfo)getObject(key);
    }
    public void set(int index, OtherSplitBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherSplitBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherSplitBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}