package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupEvaluateTemplateEntryCollection extends AbstractObjectCollection 
{
    public SupEvaluateTemplateEntryCollection()
    {
        super(SupEvaluateTemplateEntryInfo.class);
    }
    public boolean add(SupEvaluateTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupEvaluateTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupEvaluateTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public SupEvaluateTemplateEntryInfo get(int index)
    {
        return(SupEvaluateTemplateEntryInfo)getObject(index);
    }
    public SupEvaluateTemplateEntryInfo get(Object key)
    {
        return(SupEvaluateTemplateEntryInfo)getObject(key);
    }
    public void set(int index, SupEvaluateTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupEvaluateTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupEvaluateTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}