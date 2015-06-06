package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgUnitEntryCollection extends AbstractObjectCollection 
{
    public OrgUnitEntryCollection()
    {
        super(OrgUnitEntryInfo.class);
    }
    public boolean add(OrgUnitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgUnitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgUnitEntryInfo item)
    {
        return removeObject(item);
    }
    public OrgUnitEntryInfo get(int index)
    {
        return(OrgUnitEntryInfo)getObject(index);
    }
    public OrgUnitEntryInfo get(Object key)
    {
        return(OrgUnitEntryInfo)getObject(key);
    }
    public void set(int index, OrgUnitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgUnitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgUnitEntryInfo item)
    {
        return super.indexOf(item);
    }
}