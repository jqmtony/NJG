package com.kingdee.eas.port.pm.evaluation;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationCollection extends AbstractObjectCollection 
{
    public EvaluationCollection()
    {
        super(EvaluationInfo.class);
    }
    public boolean add(EvaluationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationInfo item)
    {
        return removeObject(item);
    }
    public EvaluationInfo get(int index)
    {
        return(EvaluationInfo)getObject(index);
    }
    public EvaluationInfo get(Object key)
    {
        return(EvaluationInfo)getObject(key);
    }
    public void set(int index, EvaluationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationInfo item)
    {
        return super.indexOf(item);
    }
}