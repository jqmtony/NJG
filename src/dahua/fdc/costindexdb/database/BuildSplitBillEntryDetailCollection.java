package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildSplitBillEntryDetailCollection extends AbstractObjectCollection 
{
    public BuildSplitBillEntryDetailCollection()
    {
        super(BuildSplitBillEntryDetailInfo.class);
    }
    public boolean add(BuildSplitBillEntryDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildSplitBillEntryDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildSplitBillEntryDetailInfo item)
    {
        return removeObject(item);
    }
    public BuildSplitBillEntryDetailInfo get(int index)
    {
        return(BuildSplitBillEntryDetailInfo)getObject(index);
    }
    public BuildSplitBillEntryDetailInfo get(Object key)
    {
        return(BuildSplitBillEntryDetailInfo)getObject(key);
    }
    public void set(int index, BuildSplitBillEntryDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildSplitBillEntryDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildSplitBillEntryDetailInfo item)
    {
        return super.indexOf(item);
    }
}