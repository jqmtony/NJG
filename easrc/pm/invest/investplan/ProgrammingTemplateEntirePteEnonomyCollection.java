package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingTemplateEntirePteEnonomyCollection extends AbstractObjectCollection 
{
    public ProgrammingTemplateEntirePteEnonomyCollection()
    {
        super(ProgrammingTemplateEntirePteEnonomyInfo.class);
    }
    public boolean add(ProgrammingTemplateEntirePteEnonomyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingTemplateEntirePteEnonomyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingTemplateEntirePteEnonomyInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingTemplateEntirePteEnonomyInfo get(int index)
    {
        return(ProgrammingTemplateEntirePteEnonomyInfo)getObject(index);
    }
    public ProgrammingTemplateEntirePteEnonomyInfo get(Object key)
    {
        return(ProgrammingTemplateEntirePteEnonomyInfo)getObject(key);
    }
    public void set(int index, ProgrammingTemplateEntirePteEnonomyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingTemplateEntirePteEnonomyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingTemplateEntirePteEnonomyInfo item)
    {
        return super.indexOf(item);
    }
}