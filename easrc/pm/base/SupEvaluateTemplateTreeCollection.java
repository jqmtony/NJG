package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupEvaluateTemplateTreeCollection extends AbstractObjectCollection 
{
    public SupEvaluateTemplateTreeCollection()
    {
        super(SupEvaluateTemplateTreeInfo.class);
    }
    public boolean add(SupEvaluateTemplateTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupEvaluateTemplateTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupEvaluateTemplateTreeInfo item)
    {
        return removeObject(item);
    }
    public SupEvaluateTemplateTreeInfo get(int index)
    {
        return(SupEvaluateTemplateTreeInfo)getObject(index);
    }
    public SupEvaluateTemplateTreeInfo get(Object key)
    {
        return(SupEvaluateTemplateTreeInfo)getObject(key);
    }
    public void set(int index, SupEvaluateTemplateTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupEvaluateTemplateTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupEvaluateTemplateTreeInfo item)
    {
        return super.indexOf(item);
    }
}