package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearInvestPlanCollection extends AbstractObjectCollection 
{
    public YearInvestPlanCollection()
    {
        super(YearInvestPlanInfo.class);
    }
    public boolean add(YearInvestPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearInvestPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearInvestPlanInfo item)
    {
        return removeObject(item);
    }
    public YearInvestPlanInfo get(int index)
    {
        return(YearInvestPlanInfo)getObject(index);
    }
    public YearInvestPlanInfo get(Object key)
    {
        return(YearInvestPlanInfo)getObject(key);
    }
    public void set(int index, YearInvestPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(YearInvestPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearInvestPlanInfo item)
    {
        return super.indexOf(item);
    }
}