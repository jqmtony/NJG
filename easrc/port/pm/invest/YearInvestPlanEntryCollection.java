package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearInvestPlanEntryCollection extends AbstractObjectCollection 
{
    public YearInvestPlanEntryCollection()
    {
        super(YearInvestPlanEntryInfo.class);
    }
    public boolean add(YearInvestPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearInvestPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearInvestPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public YearInvestPlanEntryInfo get(int index)
    {
        return(YearInvestPlanEntryInfo)getObject(index);
    }
    public YearInvestPlanEntryInfo get(Object key)
    {
        return(YearInvestPlanEntryInfo)getObject(key);
    }
    public void set(int index, YearInvestPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(YearInvestPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearInvestPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}