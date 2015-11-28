package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildSplitBillCollection extends AbstractObjectCollection 
{
    public BuildSplitBillCollection()
    {
        super(BuildSplitBillInfo.class);
    }
    public boolean add(BuildSplitBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildSplitBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildSplitBillInfo item)
    {
        return removeObject(item);
    }
    public BuildSplitBillInfo get(int index)
    {
        return(BuildSplitBillInfo)getObject(index);
    }
    public BuildSplitBillInfo get(Object key)
    {
        return(BuildSplitBillInfo)getObject(key);
    }
    public void set(int index, BuildSplitBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildSplitBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildSplitBillInfo item)
    {
        return super.indexOf(item);
    }
}