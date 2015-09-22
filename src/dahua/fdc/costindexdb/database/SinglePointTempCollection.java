package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SinglePointTempCollection extends AbstractObjectCollection 
{
    public SinglePointTempCollection()
    {
        super(SinglePointTempInfo.class);
    }
    public boolean add(SinglePointTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SinglePointTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SinglePointTempInfo item)
    {
        return removeObject(item);
    }
    public SinglePointTempInfo get(int index)
    {
        return(SinglePointTempInfo)getObject(index);
    }
    public SinglePointTempInfo get(Object key)
    {
        return(SinglePointTempInfo)getObject(key);
    }
    public void set(int index, SinglePointTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SinglePointTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SinglePointTempInfo item)
    {
        return super.indexOf(item);
    }
}