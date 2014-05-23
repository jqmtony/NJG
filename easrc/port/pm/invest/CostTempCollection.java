package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostTempCollection extends AbstractObjectCollection 
{
    public CostTempCollection()
    {
        super(CostTempInfo.class);
    }
    public boolean add(CostTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostTempInfo item)
    {
        return removeObject(item);
    }
    public CostTempInfo get(int index)
    {
        return(CostTempInfo)getObject(index);
    }
    public CostTempInfo get(Object key)
    {
        return(CostTempInfo)getObject(key);
    }
    public void set(int index, CostTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostTempInfo item)
    {
        return super.indexOf(item);
    }
}