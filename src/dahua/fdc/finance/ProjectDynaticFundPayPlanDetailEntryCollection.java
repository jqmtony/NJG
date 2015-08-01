package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynaticFundPayPlanDetailEntryCollection extends AbstractObjectCollection 
{
    public ProjectDynaticFundPayPlanDetailEntryCollection()
    {
        super(ProjectDynaticFundPayPlanDetailEntryInfo.class);
    }
    public boolean add(ProjectDynaticFundPayPlanDetailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynaticFundPayPlanDetailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynaticFundPayPlanDetailEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynaticFundPayPlanDetailEntryInfo get(int index)
    {
        return(ProjectDynaticFundPayPlanDetailEntryInfo)getObject(index);
    }
    public ProjectDynaticFundPayPlanDetailEntryInfo get(Object key)
    {
        return(ProjectDynaticFundPayPlanDetailEntryInfo)getObject(key);
    }
    public void set(int index, ProjectDynaticFundPayPlanDetailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynaticFundPayPlanDetailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynaticFundPayPlanDetailEntryInfo item)
    {
        return super.indexOf(item);
    }
}