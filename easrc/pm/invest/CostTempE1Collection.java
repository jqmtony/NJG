package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostTempE1Collection extends AbstractObjectCollection 
{
    public CostTempE1Collection()
    {
        super(CostTempE1Info.class);
    }
    public boolean add(CostTempE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostTempE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostTempE1Info item)
    {
        return removeObject(item);
    }
    public CostTempE1Info get(int index)
    {
        return(CostTempE1Info)getObject(index);
    }
    public CostTempE1Info get(Object key)
    {
        return(CostTempE1Info)getObject(key);
    }
    public void set(int index, CostTempE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(CostTempE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostTempE1Info item)
    {
        return super.indexOf(item);
    }
}