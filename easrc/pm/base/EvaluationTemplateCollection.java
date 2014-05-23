package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationTemplateCollection extends AbstractObjectCollection 
{
    public EvaluationTemplateCollection()
    {
        super(EvaluationTemplateInfo.class);
    }
    public boolean add(EvaluationTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationTemplateInfo item)
    {
        return removeObject(item);
    }
    public EvaluationTemplateInfo get(int index)
    {
        return(EvaluationTemplateInfo)getObject(index);
    }
    public EvaluationTemplateInfo get(Object key)
    {
        return(EvaluationTemplateInfo)getObject(key);
    }
    public void set(int index, EvaluationTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationTemplateInfo item)
    {
        return super.indexOf(item);
    }
}