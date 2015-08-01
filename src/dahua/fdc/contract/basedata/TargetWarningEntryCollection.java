package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetWarningEntryCollection extends AbstractObjectCollection 
{
    public TargetWarningEntryCollection()
    {
        super(TargetWarningEntryInfo.class);
    }
    public boolean add(TargetWarningEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetWarningEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetWarningEntryInfo item)
    {
        return removeObject(item);
    }
    public TargetWarningEntryInfo get(int index)
    {
        return(TargetWarningEntryInfo)getObject(index);
    }
    public TargetWarningEntryInfo get(Object key)
    {
        return(TargetWarningEntryInfo)getObject(key);
    }
    public void set(int index, TargetWarningEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetWarningEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetWarningEntryInfo item)
    {
        return super.indexOf(item);
    }
}