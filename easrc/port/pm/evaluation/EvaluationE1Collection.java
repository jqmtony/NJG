package com.kingdee.eas.port.pm.evaluation;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationE1Collection extends AbstractObjectCollection 
{
    public EvaluationE1Collection()
    {
        super(EvaluationE1Info.class);
    }
    public boolean add(EvaluationE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationE1Info item)
    {
        return removeObject(item);
    }
    public EvaluationE1Info get(int index)
    {
        return(EvaluationE1Info)getObject(index);
    }
    public EvaluationE1Info get(Object key)
    {
        return(EvaluationE1Info)getObject(key);
    }
    public void set(int index, EvaluationE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationE1Info item)
    {
        return super.indexOf(item);
    }
}