package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AnnualYearPlanCollection extends AbstractObjectCollection 
{
    public AnnualYearPlanCollection()
    {
        super(AnnualYearPlanInfo.class);
    }
    public boolean add(AnnualYearPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AnnualYearPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AnnualYearPlanInfo item)
    {
        return removeObject(item);
    }
    public AnnualYearPlanInfo get(int index)
    {
        return(AnnualYearPlanInfo)getObject(index);
    }
    public AnnualYearPlanInfo get(Object key)
    {
        return(AnnualYearPlanInfo)getObject(key);
    }
    public void set(int index, AnnualYearPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AnnualYearPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AnnualYearPlanInfo item)
    {
        return super.indexOf(item);
    }
}