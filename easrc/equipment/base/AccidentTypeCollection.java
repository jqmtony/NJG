package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AccidentTypeCollection extends AbstractObjectCollection 
{
    public AccidentTypeCollection()
    {
        super(AccidentTypeInfo.class);
    }
    public boolean add(AccidentTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AccidentTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AccidentTypeInfo item)
    {
        return removeObject(item);
    }
    public AccidentTypeInfo get(int index)
    {
        return(AccidentTypeInfo)getObject(index);
    }
    public AccidentTypeInfo get(Object key)
    {
        return(AccidentTypeInfo)getObject(key);
    }
    public void set(int index, AccidentTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AccidentTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AccidentTypeInfo item)
    {
        return super.indexOf(item);
    }
}