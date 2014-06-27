package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestPlanDetailCollection extends AbstractObjectCollection 
{
    public InvestPlanDetailCollection()
    {
        super(InvestPlanDetailInfo.class);
    }
    public boolean add(InvestPlanDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestPlanDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestPlanDetailInfo item)
    {
        return removeObject(item);
    }
    public InvestPlanDetailInfo get(int index)
    {
        return(InvestPlanDetailInfo)getObject(index);
    }
    public InvestPlanDetailInfo get(Object key)
    {
        return(InvestPlanDetailInfo)getObject(key);
    }
    public void set(int index, InvestPlanDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestPlanDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestPlanDetailInfo item)
    {
        return super.indexOf(item);
    }
}