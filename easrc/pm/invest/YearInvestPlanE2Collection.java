package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearInvestPlanE2Collection extends AbstractObjectCollection 
{
    public YearInvestPlanE2Collection()
    {
        super(YearInvestPlanE2Info.class);
    }
    public boolean add(YearInvestPlanE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearInvestPlanE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearInvestPlanE2Info item)
    {
        return removeObject(item);
    }
    public YearInvestPlanE2Info get(int index)
    {
        return(YearInvestPlanE2Info)getObject(index);
    }
    public YearInvestPlanE2Info get(Object key)
    {
        return(YearInvestPlanE2Info)getObject(key);
    }
    public void set(int index, YearInvestPlanE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(YearInvestPlanE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearInvestPlanE2Info item)
    {
        return super.indexOf(item);
    }
}