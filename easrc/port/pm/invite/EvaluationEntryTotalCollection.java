package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationEntryTotalCollection extends AbstractObjectCollection 
{
    public EvaluationEntryTotalCollection()
    {
        super(EvaluationEntryTotalInfo.class);
    }
    public boolean add(EvaluationEntryTotalInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationEntryTotalCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationEntryTotalInfo item)
    {
        return removeObject(item);
    }
    public EvaluationEntryTotalInfo get(int index)
    {
        return(EvaluationEntryTotalInfo)getObject(index);
    }
    public EvaluationEntryTotalInfo get(Object key)
    {
        return(EvaluationEntryTotalInfo)getObject(key);
    }
    public void set(int index, EvaluationEntryTotalInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationEntryTotalInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationEntryTotalInfo item)
    {
        return super.indexOf(item);
    }
}