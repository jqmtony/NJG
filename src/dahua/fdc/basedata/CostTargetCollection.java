package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostTargetCollection extends AbstractObjectCollection 
{
    public CostTargetCollection()
    {
        super(CostTargetInfo.class);
    }
    public boolean add(CostTargetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostTargetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostTargetInfo item)
    {
        return removeObject(item);
    }
    public CostTargetInfo get(int index)
    {
        return(CostTargetInfo)getObject(index);
    }
    public CostTargetInfo get(Object key)
    {
        return(CostTargetInfo)getObject(key);
    }
    public void set(int index, CostTargetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostTargetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostTargetInfo item)
    {
        return super.indexOf(item);
    }
}