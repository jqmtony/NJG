package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationTemplateTreeCollection extends AbstractObjectCollection 
{
    public EvaluationTemplateTreeCollection()
    {
        super(EvaluationTemplateTreeInfo.class);
    }
    public boolean add(EvaluationTemplateTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationTemplateTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationTemplateTreeInfo item)
    {
        return removeObject(item);
    }
    public EvaluationTemplateTreeInfo get(int index)
    {
        return(EvaluationTemplateTreeInfo)getObject(index);
    }
    public EvaluationTemplateTreeInfo get(Object key)
    {
        return(EvaluationTemplateTreeInfo)getObject(key);
    }
    public void set(int index, EvaluationTemplateTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationTemplateTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationTemplateTreeInfo item)
    {
        return super.indexOf(item);
    }
}