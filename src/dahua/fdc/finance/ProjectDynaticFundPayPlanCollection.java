package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynaticFundPayPlanCollection extends AbstractObjectCollection 
{
    public ProjectDynaticFundPayPlanCollection()
    {
        super(ProjectDynaticFundPayPlanInfo.class);
    }
    public boolean add(ProjectDynaticFundPayPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynaticFundPayPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynaticFundPayPlanInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynaticFundPayPlanInfo get(int index)
    {
        return(ProjectDynaticFundPayPlanInfo)getObject(index);
    }
    public ProjectDynaticFundPayPlanInfo get(Object key)
    {
        return(ProjectDynaticFundPayPlanInfo)getObject(key);
    }
    public void set(int index, ProjectDynaticFundPayPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynaticFundPayPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynaticFundPayPlanInfo item)
    {
        return super.indexOf(item);
    }
}