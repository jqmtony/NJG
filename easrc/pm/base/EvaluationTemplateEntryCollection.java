package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationTemplateEntryCollection extends AbstractObjectCollection 
{
    public EvaluationTemplateEntryCollection()
    {
        super(EvaluationTemplateEntryInfo.class);
    }
    public boolean add(EvaluationTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public EvaluationTemplateEntryInfo get(int index)
    {
        return(EvaluationTemplateEntryInfo)getObject(index);
    }
    public EvaluationTemplateEntryInfo get(Object key)
    {
        return(EvaluationTemplateEntryInfo)getObject(key);
    }
    public void set(int index, EvaluationTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}