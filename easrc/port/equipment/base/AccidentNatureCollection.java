package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AccidentNatureCollection extends AbstractObjectCollection 
{
    public AccidentNatureCollection()
    {
        super(AccidentNatureInfo.class);
    }
    public boolean add(AccidentNatureInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AccidentNatureCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AccidentNatureInfo item)
    {
        return removeObject(item);
    }
    public AccidentNatureInfo get(int index)
    {
        return(AccidentNatureInfo)getObject(index);
    }
    public AccidentNatureInfo get(Object key)
    {
        return(AccidentNatureInfo)getObject(key);
    }
    public void set(int index, AccidentNatureInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AccidentNatureInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AccidentNatureInfo item)
    {
        return super.indexOf(item);
    }
}