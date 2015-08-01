package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class UnitDataManagerCollection extends AbstractObjectCollection 
{
    public UnitDataManagerCollection()
    {
        super(UnitDataManagerInfo.class);
    }
    public boolean add(UnitDataManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(UnitDataManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(UnitDataManagerInfo item)
    {
        return removeObject(item);
    }
    public UnitDataManagerInfo get(int index)
    {
        return(UnitDataManagerInfo)getObject(index);
    }
    public UnitDataManagerInfo get(Object key)
    {
        return(UnitDataManagerInfo)getObject(key);
    }
    public void set(int index, UnitDataManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(UnitDataManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(UnitDataManagerInfo item)
    {
        return super.indexOf(item);
    }
}