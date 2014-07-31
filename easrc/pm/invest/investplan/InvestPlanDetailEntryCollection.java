package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestPlanDetailEntryCollection extends AbstractObjectCollection 
{
    public InvestPlanDetailEntryCollection()
    {
        super(InvestPlanDetailEntryInfo.class);
    }
    public boolean add(InvestPlanDetailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestPlanDetailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestPlanDetailEntryInfo item)
    {
        return removeObject(item);
    }
    public InvestPlanDetailEntryInfo get(int index)
    {
        return(InvestPlanDetailEntryInfo)getObject(index);
    }
    public InvestPlanDetailEntryInfo get(Object key)
    {
        return(InvestPlanDetailEntryInfo)getObject(key);
    }
    public void set(int index, InvestPlanDetailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestPlanDetailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestPlanDetailEntryInfo item)
    {
        return super.indexOf(item);
    }
}