package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialtyTypeEntryCollection extends AbstractObjectCollection 
{
    public SpecialtyTypeEntryCollection()
    {
        super(SpecialtyTypeEntryInfo.class);
    }
    public boolean add(SpecialtyTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialtyTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialtyTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public SpecialtyTypeEntryInfo get(int index)
    {
        return(SpecialtyTypeEntryInfo)getObject(index);
    }
    public SpecialtyTypeEntryInfo get(Object key)
    {
        return(SpecialtyTypeEntryInfo)getObject(key);
    }
    public void set(int index, SpecialtyTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialtyTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialtyTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}