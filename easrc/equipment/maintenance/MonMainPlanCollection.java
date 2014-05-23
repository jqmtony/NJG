package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonMainPlanCollection extends AbstractObjectCollection 
{
    public MonMainPlanCollection()
    {
        super(MonMainPlanInfo.class);
    }
    public boolean add(MonMainPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonMainPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonMainPlanInfo item)
    {
        return removeObject(item);
    }
    public MonMainPlanInfo get(int index)
    {
        return(MonMainPlanInfo)getObject(index);
    }
    public MonMainPlanInfo get(Object key)
    {
        return(MonMainPlanInfo)getObject(key);
    }
    public void set(int index, MonMainPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonMainPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonMainPlanInfo item)
    {
        return super.indexOf(item);
    }
}