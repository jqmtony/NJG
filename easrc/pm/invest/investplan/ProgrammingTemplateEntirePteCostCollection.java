package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingTemplateEntirePteCostCollection extends AbstractObjectCollection 
{
    public ProgrammingTemplateEntirePteCostCollection()
    {
        super(ProgrammingTemplateEntirePteCostInfo.class);
    }
    public boolean add(ProgrammingTemplateEntirePteCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingTemplateEntirePteCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingTemplateEntirePteCostInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingTemplateEntirePteCostInfo get(int index)
    {
        return(ProgrammingTemplateEntirePteCostInfo)getObject(index);
    }
    public ProgrammingTemplateEntirePteCostInfo get(Object key)
    {
        return(ProgrammingTemplateEntirePteCostInfo)getObject(key);
    }
    public void set(int index, ProgrammingTemplateEntirePteCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingTemplateEntirePteCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingTemplateEntirePteCostInfo item)
    {
        return super.indexOf(item);
    }
}