package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostAccountAssignCollection extends AbstractObjectCollection 
{
    public CostAccountAssignCollection()
    {
        super(CostAccountAssignInfo.class);
    }
    public boolean add(CostAccountAssignInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostAccountAssignCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostAccountAssignInfo item)
    {
        return removeObject(item);
    }
    public CostAccountAssignInfo get(int index)
    {
        return(CostAccountAssignInfo)getObject(index);
    }
    public CostAccountAssignInfo get(Object key)
    {
        return(CostAccountAssignInfo)getObject(key);
    }
    public void set(int index, CostAccountAssignInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostAccountAssignInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostAccountAssignInfo item)
    {
        return super.indexOf(item);
    }
}