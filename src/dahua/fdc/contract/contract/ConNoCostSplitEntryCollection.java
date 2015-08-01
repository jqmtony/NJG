package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConNoCostSplitEntryCollection extends AbstractObjectCollection 
{
    public ConNoCostSplitEntryCollection()
    {
        super(ConNoCostSplitEntryInfo.class);
    }
    public boolean add(ConNoCostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConNoCostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConNoCostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ConNoCostSplitEntryInfo get(int index)
    {
        return(ConNoCostSplitEntryInfo)getObject(index);
    }
    public ConNoCostSplitEntryInfo get(Object key)
    {
        return(ConNoCostSplitEntryInfo)getObject(key);
    }
    public void set(int index, ConNoCostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConNoCostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConNoCostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}