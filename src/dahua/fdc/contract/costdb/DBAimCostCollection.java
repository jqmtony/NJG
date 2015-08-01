package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DBAimCostCollection extends AbstractObjectCollection 
{
    public DBAimCostCollection()
    {
        super(DBAimCostInfo.class);
    }
    public boolean add(DBAimCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DBAimCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DBAimCostInfo item)
    {
        return removeObject(item);
    }
    public DBAimCostInfo get(int index)
    {
        return(DBAimCostInfo)getObject(index);
    }
    public DBAimCostInfo get(Object key)
    {
        return(DBAimCostInfo)getObject(key);
    }
    public void set(int index, DBAimCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DBAimCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DBAimCostInfo item)
    {
        return super.indexOf(item);
    }
}