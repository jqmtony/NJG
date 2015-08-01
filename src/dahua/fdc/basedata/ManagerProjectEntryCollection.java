package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ManagerProjectEntryCollection extends AbstractObjectCollection 
{
    public ManagerProjectEntryCollection()
    {
        super(ManagerProjectEntryInfo.class);
    }
    public boolean add(ManagerProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ManagerProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ManagerProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public ManagerProjectEntryInfo get(int index)
    {
        return(ManagerProjectEntryInfo)getObject(index);
    }
    public ManagerProjectEntryInfo get(Object key)
    {
        return(ManagerProjectEntryInfo)getObject(key);
    }
    public void set(int index, ManagerProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ManagerProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ManagerProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}