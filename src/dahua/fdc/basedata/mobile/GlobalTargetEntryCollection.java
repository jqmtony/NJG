package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GlobalTargetEntryCollection extends AbstractObjectCollection 
{
    public GlobalTargetEntryCollection()
    {
        super(GlobalTargetEntryInfo.class);
    }
    public boolean add(GlobalTargetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GlobalTargetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GlobalTargetEntryInfo item)
    {
        return removeObject(item);
    }
    public GlobalTargetEntryInfo get(int index)
    {
        return(GlobalTargetEntryInfo)getObject(index);
    }
    public GlobalTargetEntryInfo get(Object key)
    {
        return(GlobalTargetEntryInfo)getObject(key);
    }
    public void set(int index, GlobalTargetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GlobalTargetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GlobalTargetEntryInfo item)
    {
        return super.indexOf(item);
    }
}