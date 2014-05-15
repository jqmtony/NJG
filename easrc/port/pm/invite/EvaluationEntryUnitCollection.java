package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationEntryUnitCollection extends AbstractObjectCollection 
{
    public EvaluationEntryUnitCollection()
    {
        super(EvaluationEntryUnitInfo.class);
    }
    public boolean add(EvaluationEntryUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationEntryUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationEntryUnitInfo item)
    {
        return removeObject(item);
    }
    public EvaluationEntryUnitInfo get(int index)
    {
        return(EvaluationEntryUnitInfo)getObject(index);
    }
    public EvaluationEntryUnitInfo get(Object key)
    {
        return(EvaluationEntryUnitInfo)getObject(key);
    }
    public void set(int index, EvaluationEntryUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationEntryUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationEntryUnitInfo item)
    {
        return super.indexOf(item);
    }
}