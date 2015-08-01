package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynaticFundPayPlanEntryCollection extends AbstractObjectCollection 
{
    public ProjectDynaticFundPayPlanEntryCollection()
    {
        super(ProjectDynaticFundPayPlanEntryInfo.class);
    }
    public boolean add(ProjectDynaticFundPayPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynaticFundPayPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynaticFundPayPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynaticFundPayPlanEntryInfo get(int index)
    {
        return(ProjectDynaticFundPayPlanEntryInfo)getObject(index);
    }
    public ProjectDynaticFundPayPlanEntryInfo get(Object key)
    {
        return(ProjectDynaticFundPayPlanEntryInfo)getObject(key);
    }
    public void set(int index, ProjectDynaticFundPayPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynaticFundPayPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynaticFundPayPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}