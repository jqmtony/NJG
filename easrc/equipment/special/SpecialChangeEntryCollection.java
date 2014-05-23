package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialChangeEntryCollection extends AbstractObjectCollection 
{
    public SpecialChangeEntryCollection()
    {
        super(SpecialChangeEntryInfo.class);
    }
    public boolean add(SpecialChangeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialChangeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialChangeEntryInfo item)
    {
        return removeObject(item);
    }
    public SpecialChangeEntryInfo get(int index)
    {
        return(SpecialChangeEntryInfo)getObject(index);
    }
    public SpecialChangeEntryInfo get(Object key)
    {
        return(SpecialChangeEntryInfo)getObject(key);
    }
    public void set(int index, SpecialChangeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialChangeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialChangeEntryInfo item)
    {
        return super.indexOf(item);
    }
}