package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearPlanApplyCollection extends AbstractObjectCollection 
{
    public YearPlanApplyCollection()
    {
        super(YearPlanApplyInfo.class);
    }
    public boolean add(YearPlanApplyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearPlanApplyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearPlanApplyInfo item)
    {
        return removeObject(item);
    }
    public YearPlanApplyInfo get(int index)
    {
        return(YearPlanApplyInfo)getObject(index);
    }
    public YearPlanApplyInfo get(Object key)
    {
        return(YearPlanApplyInfo)getObject(key);
    }
    public void set(int index, YearPlanApplyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(YearPlanApplyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearPlanApplyInfo item)
    {
        return super.indexOf(item);
    }
}