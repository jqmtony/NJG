package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetWarningCollection extends AbstractObjectCollection 
{
    public TargetWarningCollection()
    {
        super(TargetWarningInfo.class);
    }
    public boolean add(TargetWarningInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetWarningCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetWarningInfo item)
    {
        return removeObject(item);
    }
    public TargetWarningInfo get(int index)
    {
        return(TargetWarningInfo)getObject(index);
    }
    public TargetWarningInfo get(Object key)
    {
        return(TargetWarningInfo)getObject(key);
    }
    public void set(int index, TargetWarningInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetWarningInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetWarningInfo item)
    {
        return super.indexOf(item);
    }
}