package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherSplitBillCollection extends AbstractObjectCollection 
{
    public OtherSplitBillCollection()
    {
        super(OtherSplitBillInfo.class);
    }
    public boolean add(OtherSplitBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherSplitBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherSplitBillInfo item)
    {
        return removeObject(item);
    }
    public OtherSplitBillInfo get(int index)
    {
        return(OtherSplitBillInfo)getObject(index);
    }
    public OtherSplitBillInfo get(Object key)
    {
        return(OtherSplitBillInfo)getObject(key);
    }
    public void set(int index, OtherSplitBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherSplitBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherSplitBillInfo item)
    {
        return super.indexOf(item);
    }
}