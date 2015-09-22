package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SinglePointTempEntryCollection extends AbstractObjectCollection 
{
    public SinglePointTempEntryCollection()
    {
        super(SinglePointTempEntryInfo.class);
    }
    public boolean add(SinglePointTempEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SinglePointTempEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SinglePointTempEntryInfo item)
    {
        return removeObject(item);
    }
    public SinglePointTempEntryInfo get(int index)
    {
        return(SinglePointTempEntryInfo)getObject(index);
    }
    public SinglePointTempEntryInfo get(Object key)
    {
        return(SinglePointTempEntryInfo)getObject(key);
    }
    public void set(int index, SinglePointTempEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SinglePointTempEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SinglePointTempEntryInfo item)
    {
        return super.indexOf(item);
    }
}