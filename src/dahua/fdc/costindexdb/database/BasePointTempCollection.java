package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BasePointTempCollection extends AbstractObjectCollection 
{
    public BasePointTempCollection()
    {
        super(BasePointTempInfo.class);
    }
    public boolean add(BasePointTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BasePointTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BasePointTempInfo item)
    {
        return removeObject(item);
    }
    public BasePointTempInfo get(int index)
    {
        return(BasePointTempInfo)getObject(index);
    }
    public BasePointTempInfo get(Object key)
    {
        return(BasePointTempInfo)getObject(key);
    }
    public void set(int index, BasePointTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BasePointTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BasePointTempInfo item)
    {
        return super.indexOf(item);
    }
}