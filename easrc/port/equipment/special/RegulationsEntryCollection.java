package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RegulationsEntryCollection extends AbstractObjectCollection 
{
    public RegulationsEntryCollection()
    {
        super(RegulationsEntryInfo.class);
    }
    public boolean add(RegulationsEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RegulationsEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RegulationsEntryInfo item)
    {
        return removeObject(item);
    }
    public RegulationsEntryInfo get(int index)
    {
        return(RegulationsEntryInfo)getObject(index);
    }
    public RegulationsEntryInfo get(Object key)
    {
        return(RegulationsEntryInfo)getObject(key);
    }
    public void set(int index, RegulationsEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RegulationsEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RegulationsEntryInfo item)
    {
        return super.indexOf(item);
    }
}