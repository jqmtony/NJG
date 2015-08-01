package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AdjustReasonCollection extends AbstractObjectCollection 
{
    public AdjustReasonCollection()
    {
        super(AdjustReasonInfo.class);
    }
    public boolean add(AdjustReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AdjustReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AdjustReasonInfo item)
    {
        return removeObject(item);
    }
    public AdjustReasonInfo get(int index)
    {
        return(AdjustReasonInfo)getObject(index);
    }
    public AdjustReasonInfo get(Object key)
    {
        return(AdjustReasonInfo)getObject(key);
    }
    public void set(int index, AdjustReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AdjustReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AdjustReasonInfo item)
    {
        return super.indexOf(item);
    }
}