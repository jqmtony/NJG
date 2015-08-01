package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetInfoCollection extends AbstractObjectCollection 
{
    public TargetInfoCollection()
    {
        super(TargetInfoInfo.class);
    }
    public boolean add(TargetInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetInfoInfo item)
    {
        return removeObject(item);
    }
    public TargetInfoInfo get(int index)
    {
        return(TargetInfoInfo)getObject(index);
    }
    public TargetInfoInfo get(Object key)
    {
        return(TargetInfoInfo)getObject(key);
    }
    public void set(int index, TargetInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetInfoInfo item)
    {
        return super.indexOf(item);
    }
}