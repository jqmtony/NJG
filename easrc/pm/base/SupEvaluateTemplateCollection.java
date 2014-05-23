package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupEvaluateTemplateCollection extends AbstractObjectCollection 
{
    public SupEvaluateTemplateCollection()
    {
        super(SupEvaluateTemplateInfo.class);
    }
    public boolean add(SupEvaluateTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupEvaluateTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupEvaluateTemplateInfo item)
    {
        return removeObject(item);
    }
    public SupEvaluateTemplateInfo get(int index)
    {
        return(SupEvaluateTemplateInfo)getObject(index);
    }
    public SupEvaluateTemplateInfo get(Object key)
    {
        return(SupEvaluateTemplateInfo)getObject(key);
    }
    public void set(int index, SupEvaluateTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupEvaluateTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupEvaluateTemplateInfo item)
    {
        return super.indexOf(item);
    }
}