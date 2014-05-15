package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostTypeTreeCollection extends AbstractObjectCollection 
{
    public CostTypeTreeCollection()
    {
        super(CostTypeTreeInfo.class);
    }
    public boolean add(CostTypeTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostTypeTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostTypeTreeInfo item)
    {
        return removeObject(item);
    }
    public CostTypeTreeInfo get(int index)
    {
        return(CostTypeTreeInfo)getObject(index);
    }
    public CostTypeTreeInfo get(Object key)
    {
        return(CostTypeTreeInfo)getObject(key);
    }
    public void set(int index, CostTypeTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostTypeTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostTypeTreeInfo item)
    {
        return super.indexOf(item);
    }
}