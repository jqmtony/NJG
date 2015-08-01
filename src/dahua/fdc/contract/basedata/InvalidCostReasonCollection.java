package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvalidCostReasonCollection extends AbstractObjectCollection 
{
    public InvalidCostReasonCollection()
    {
        super(InvalidCostReasonInfo.class);
    }
    public boolean add(InvalidCostReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvalidCostReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvalidCostReasonInfo item)
    {
        return removeObject(item);
    }
    public InvalidCostReasonInfo get(int index)
    {
        return(InvalidCostReasonInfo)getObject(index);
    }
    public InvalidCostReasonInfo get(Object key)
    {
        return(InvalidCostReasonInfo)getObject(key);
    }
    public void set(int index, InvalidCostReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvalidCostReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvalidCostReasonInfo item)
    {
        return super.indexOf(item);
    }
}