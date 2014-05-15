package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupEvaluationIndicatorCollection extends AbstractObjectCollection 
{
    public SupEvaluationIndicatorCollection()
    {
        super(SupEvaluationIndicatorInfo.class);
    }
    public boolean add(SupEvaluationIndicatorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupEvaluationIndicatorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupEvaluationIndicatorInfo item)
    {
        return removeObject(item);
    }
    public SupEvaluationIndicatorInfo get(int index)
    {
        return(SupEvaluationIndicatorInfo)getObject(index);
    }
    public SupEvaluationIndicatorInfo get(Object key)
    {
        return(SupEvaluationIndicatorInfo)getObject(key);
    }
    public void set(int index, SupEvaluationIndicatorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupEvaluationIndicatorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupEvaluationIndicatorInfo item)
    {
        return super.indexOf(item);
    }
}