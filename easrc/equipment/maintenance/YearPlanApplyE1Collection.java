package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearPlanApplyE1Collection extends AbstractObjectCollection 
{
    public YearPlanApplyE1Collection()
    {
        super(YearPlanApplyE1Info.class);
    }
    public boolean add(YearPlanApplyE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearPlanApplyE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearPlanApplyE1Info item)
    {
        return removeObject(item);
    }
    public YearPlanApplyE1Info get(int index)
    {
        return(YearPlanApplyE1Info)getObject(index);
    }
    public YearPlanApplyE1Info get(Object key)
    {
        return(YearPlanApplyE1Info)getObject(key);
    }
    public void set(int index, YearPlanApplyE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(YearPlanApplyE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearPlanApplyE1Info item)
    {
        return super.indexOf(item);
    }
}