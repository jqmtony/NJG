package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationIndicatorsTreeCollection extends AbstractObjectCollection 
{
    public EvaluationIndicatorsTreeCollection()
    {
        super(EvaluationIndicatorsTreeInfo.class);
    }
    public boolean add(EvaluationIndicatorsTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationIndicatorsTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationIndicatorsTreeInfo item)
    {
        return removeObject(item);
    }
    public EvaluationIndicatorsTreeInfo get(int index)
    {
        return(EvaluationIndicatorsTreeInfo)getObject(index);
    }
    public EvaluationIndicatorsTreeInfo get(Object key)
    {
        return(EvaluationIndicatorsTreeInfo)getObject(key);
    }
    public void set(int index, EvaluationIndicatorsTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationIndicatorsTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationIndicatorsTreeInfo item)
    {
        return super.indexOf(item);
    }
}