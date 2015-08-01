package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetInfoEntryCollection extends AbstractObjectCollection 
{
    public TargetInfoEntryCollection()
    {
        super(TargetInfoEntryInfo.class);
    }
    public boolean add(TargetInfoEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetInfoEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetInfoEntryInfo item)
    {
        return removeObject(item);
    }
    public TargetInfoEntryInfo get(int index)
    {
        return(TargetInfoEntryInfo)getObject(index);
    }
    public TargetInfoEntryInfo get(Object key)
    {
        return(TargetInfoEntryInfo)getObject(key);
    }
    public void set(int index, TargetInfoEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetInfoEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetInfoEntryInfo item)
    {
        return super.indexOf(item);
    }
}