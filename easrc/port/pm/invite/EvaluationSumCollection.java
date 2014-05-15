package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationSumCollection extends AbstractObjectCollection 
{
    public EvaluationSumCollection()
    {
        super(EvaluationSumInfo.class);
    }
    public boolean add(EvaluationSumInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationSumCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationSumInfo item)
    {
        return removeObject(item);
    }
    public EvaluationSumInfo get(int index)
    {
        return(EvaluationSumInfo)getObject(index);
    }
    public EvaluationSumInfo get(Object key)
    {
        return(EvaluationSumInfo)getObject(key);
    }
    public void set(int index, EvaluationSumInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationSumInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationSumInfo item)
    {
        return super.indexOf(item);
    }
}