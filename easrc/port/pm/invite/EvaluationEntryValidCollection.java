package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationEntryValidCollection extends AbstractObjectCollection 
{
    public EvaluationEntryValidCollection()
    {
        super(EvaluationEntryValidInfo.class);
    }
    public boolean add(EvaluationEntryValidInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationEntryValidCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationEntryValidInfo item)
    {
        return removeObject(item);
    }
    public EvaluationEntryValidInfo get(int index)
    {
        return(EvaluationEntryValidInfo)getObject(index);
    }
    public EvaluationEntryValidInfo get(Object key)
    {
        return(EvaluationEntryValidInfo)getObject(key);
    }
    public void set(int index, EvaluationEntryValidInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationEntryValidInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationEntryValidInfo item)
    {
        return super.indexOf(item);
    }
}