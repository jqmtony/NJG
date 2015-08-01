package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetDescCollection extends AbstractObjectCollection 
{
    public TargetDescCollection()
    {
        super(TargetDescInfo.class);
    }
    public boolean add(TargetDescInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetDescCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetDescInfo item)
    {
        return removeObject(item);
    }
    public TargetDescInfo get(int index)
    {
        return(TargetDescInfo)getObject(index);
    }
    public TargetDescInfo get(Object key)
    {
        return(TargetDescInfo)getObject(key);
    }
    public void set(int index, TargetDescInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetDescInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetDescInfo item)
    {
        return super.indexOf(item);
    }
}