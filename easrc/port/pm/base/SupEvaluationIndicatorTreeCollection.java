package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupEvaluationIndicatorTreeCollection extends AbstractObjectCollection 
{
    public SupEvaluationIndicatorTreeCollection()
    {
        super(SupEvaluationIndicatorTreeInfo.class);
    }
    public boolean add(SupEvaluationIndicatorTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupEvaluationIndicatorTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupEvaluationIndicatorTreeInfo item)
    {
        return removeObject(item);
    }
    public SupEvaluationIndicatorTreeInfo get(int index)
    {
        return(SupEvaluationIndicatorTreeInfo)getObject(index);
    }
    public SupEvaluationIndicatorTreeInfo get(Object key)
    {
        return(SupEvaluationIndicatorTreeInfo)getObject(key);
    }
    public void set(int index, SupEvaluationIndicatorTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupEvaluationIndicatorTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupEvaluationIndicatorTreeInfo item)
    {
        return super.indexOf(item);
    }
}