package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DBCostEntryCollection extends AbstractObjectCollection 
{
    public DBCostEntryCollection()
    {
        super(DBCostEntryInfo.class);
    }
    public boolean add(DBCostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DBCostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DBCostEntryInfo item)
    {
        return removeObject(item);
    }
    public DBCostEntryInfo get(int index)
    {
        return(DBCostEntryInfo)getObject(index);
    }
    public DBCostEntryInfo get(Object key)
    {
        return(DBCostEntryInfo)getObject(key);
    }
    public void set(int index, DBCostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DBCostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DBCostEntryInfo item)
    {
        return super.indexOf(item);
    }
}