package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeSplitEntryTempCollection extends AbstractObjectCollection 
{
    public ConChangeSplitEntryTempCollection()
    {
        super(ConChangeSplitEntryTempInfo.class);
    }
    public boolean add(ConChangeSplitEntryTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeSplitEntryTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeSplitEntryTempInfo item)
    {
        return removeObject(item);
    }
    public ConChangeSplitEntryTempInfo get(int index)
    {
        return(ConChangeSplitEntryTempInfo)getObject(index);
    }
    public ConChangeSplitEntryTempInfo get(Object key)
    {
        return(ConChangeSplitEntryTempInfo)getObject(key);
    }
    public void set(int index, ConChangeSplitEntryTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeSplitEntryTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeSplitEntryTempInfo item)
    {
        return super.indexOf(item);
    }
}