package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearInvestPlanE3Collection extends AbstractObjectCollection 
{
    public YearInvestPlanE3Collection()
    {
        super(YearInvestPlanE3Info.class);
    }
    public boolean add(YearInvestPlanE3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearInvestPlanE3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearInvestPlanE3Info item)
    {
        return removeObject(item);
    }
    public YearInvestPlanE3Info get(int index)
    {
        return(YearInvestPlanE3Info)getObject(index);
    }
    public YearInvestPlanE3Info get(Object key)
    {
        return(YearInvestPlanE3Info)getObject(key);
    }
    public void set(int index, YearInvestPlanE3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(YearInvestPlanE3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearInvestPlanE3Info item)
    {
        return super.indexOf(item);
    }
}