package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationIndicatorsCollection extends AbstractObjectCollection 
{
    public EvaluationIndicatorsCollection()
    {
        super(EvaluationIndicatorsInfo.class);
    }
    public boolean add(EvaluationIndicatorsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationIndicatorsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationIndicatorsInfo item)
    {
        return removeObject(item);
    }
    public EvaluationIndicatorsInfo get(int index)
    {
        return(EvaluationIndicatorsInfo)getObject(index);
    }
    public EvaluationIndicatorsInfo get(Object key)
    {
        return(EvaluationIndicatorsInfo)getObject(key);
    }
    public void set(int index, EvaluationIndicatorsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationIndicatorsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationIndicatorsInfo item)
    {
        return super.indexOf(item);
    }
}