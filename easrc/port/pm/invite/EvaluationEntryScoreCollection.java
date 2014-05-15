package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationEntryScoreCollection extends AbstractObjectCollection 
{
    public EvaluationEntryScoreCollection()
    {
        super(EvaluationEntryScoreInfo.class);
    }
    public boolean add(EvaluationEntryScoreInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationEntryScoreCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationEntryScoreInfo item)
    {
        return removeObject(item);
    }
    public EvaluationEntryScoreInfo get(int index)
    {
        return(EvaluationEntryScoreInfo)getObject(index);
    }
    public EvaluationEntryScoreInfo get(Object key)
    {
        return(EvaluationEntryScoreInfo)getObject(key);
    }
    public void set(int index, EvaluationEntryScoreInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationEntryScoreInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationEntryScoreInfo item)
    {
        return super.indexOf(item);
    }
}