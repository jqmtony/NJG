package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BasePointTempEntryCollection extends AbstractObjectCollection 
{
    public BasePointTempEntryCollection()
    {
        super(BasePointTempEntryInfo.class);
    }
    public boolean add(BasePointTempEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BasePointTempEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BasePointTempEntryInfo item)
    {
        return removeObject(item);
    }
    public BasePointTempEntryInfo get(int index)
    {
        return(BasePointTempEntryInfo)getObject(index);
    }
    public BasePointTempEntryInfo get(Object key)
    {
        return(BasePointTempEntryInfo)getObject(key);
    }
    public void set(int index, BasePointTempEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BasePointTempEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BasePointTempEntryInfo item)
    {
        return super.indexOf(item);
    }
}