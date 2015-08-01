package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConSpecialtyTypeCollection extends AbstractObjectCollection 
{
    public ConSpecialtyTypeCollection()
    {
        super(ConSpecialtyTypeInfo.class);
    }
    public boolean add(ConSpecialtyTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConSpecialtyTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConSpecialtyTypeInfo item)
    {
        return removeObject(item);
    }
    public ConSpecialtyTypeInfo get(int index)
    {
        return(ConSpecialtyTypeInfo)getObject(index);
    }
    public ConSpecialtyTypeInfo get(Object key)
    {
        return(ConSpecialtyTypeInfo)getObject(key);
    }
    public void set(int index, ConSpecialtyTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConSpecialtyTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConSpecialtyTypeInfo item)
    {
        return super.indexOf(item);
    }
}