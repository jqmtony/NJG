package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PressurePipingCollection extends AbstractObjectCollection 
{
    public PressurePipingCollection()
    {
        super(PressurePipingInfo.class);
    }
    public boolean add(PressurePipingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PressurePipingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PressurePipingInfo item)
    {
        return removeObject(item);
    }
    public PressurePipingInfo get(int index)
    {
        return(PressurePipingInfo)getObject(index);
    }
    public PressurePipingInfo get(Object key)
    {
        return(PressurePipingInfo)getObject(key);
    }
    public void set(int index, PressurePipingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PressurePipingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PressurePipingInfo item)
    {
        return super.indexOf(item);
    }
}