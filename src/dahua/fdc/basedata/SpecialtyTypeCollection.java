package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialtyTypeCollection extends AbstractObjectCollection 
{
    public SpecialtyTypeCollection()
    {
        super(SpecialtyTypeInfo.class);
    }
    public boolean add(SpecialtyTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialtyTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialtyTypeInfo item)
    {
        return removeObject(item);
    }
    public SpecialtyTypeInfo get(int index)
    {
        return(SpecialtyTypeInfo)getObject(index);
    }
    public SpecialtyTypeInfo get(Object key)
    {
        return(SpecialtyTypeInfo)getObject(key);
    }
    public void set(int index, SpecialtyTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialtyTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialtyTypeInfo item)
    {
        return super.indexOf(item);
    }
}