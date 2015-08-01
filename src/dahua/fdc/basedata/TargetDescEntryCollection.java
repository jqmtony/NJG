package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetDescEntryCollection extends AbstractObjectCollection 
{
    public TargetDescEntryCollection()
    {
        super(TargetDescEntryInfo.class);
    }
    public boolean add(TargetDescEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetDescEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetDescEntryInfo item)
    {
        return removeObject(item);
    }
    public TargetDescEntryInfo get(int index)
    {
        return(TargetDescEntryInfo)getObject(index);
    }
    public TargetDescEntryInfo get(Object key)
    {
        return(TargetDescEntryInfo)getObject(key);
    }
    public void set(int index, TargetDescEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetDescEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetDescEntryInfo item)
    {
        return super.indexOf(item);
    }
}