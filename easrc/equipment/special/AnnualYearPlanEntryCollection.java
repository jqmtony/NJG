package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AnnualYearPlanEntryCollection extends AbstractObjectCollection 
{
    public AnnualYearPlanEntryCollection()
    {
        super(AnnualYearPlanEntryInfo.class);
    }
    public boolean add(AnnualYearPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AnnualYearPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AnnualYearPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public AnnualYearPlanEntryInfo get(int index)
    {
        return(AnnualYearPlanEntryInfo)getObject(index);
    }
    public AnnualYearPlanEntryInfo get(Object key)
    {
        return(AnnualYearPlanEntryInfo)getObject(key);
    }
    public void set(int index, AnnualYearPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AnnualYearPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AnnualYearPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}