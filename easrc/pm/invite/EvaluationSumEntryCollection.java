package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationSumEntryCollection extends AbstractObjectCollection 
{
    public EvaluationSumEntryCollection()
    {
        super(EvaluationSumEntryInfo.class);
    }
    public boolean add(EvaluationSumEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationSumEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationSumEntryInfo item)
    {
        return removeObject(item);
    }
    public EvaluationSumEntryInfo get(int index)
    {
        return(EvaluationSumEntryInfo)getObject(index);
    }
    public EvaluationSumEntryInfo get(Object key)
    {
        return(EvaluationSumEntryInfo)getObject(key);
    }
    public void set(int index, EvaluationSumEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationSumEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationSumEntryInfo item)
    {
        return super.indexOf(item);
    }
}