package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildSplitBillEntryCollection extends AbstractObjectCollection 
{
    public BuildSplitBillEntryCollection()
    {
        super(BuildSplitBillEntryInfo.class);
    }
    public boolean add(BuildSplitBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildSplitBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildSplitBillEntryInfo item)
    {
        return removeObject(item);
    }
    public BuildSplitBillEntryInfo get(int index)
    {
        return(BuildSplitBillEntryInfo)getObject(index);
    }
    public BuildSplitBillEntryInfo get(Object key)
    {
        return(BuildSplitBillEntryInfo)getObject(key);
    }
    public void set(int index, BuildSplitBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildSplitBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildSplitBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}