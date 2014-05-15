package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonMainPlanE1Collection extends AbstractObjectCollection 
{
    public MonMainPlanE1Collection()
    {
        super(MonMainPlanE1Info.class);
    }
    public boolean add(MonMainPlanE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonMainPlanE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonMainPlanE1Info item)
    {
        return removeObject(item);
    }
    public MonMainPlanE1Info get(int index)
    {
        return(MonMainPlanE1Info)getObject(index);
    }
    public MonMainPlanE1Info get(Object key)
    {
        return(MonMainPlanE1Info)getObject(key);
    }
    public void set(int index, MonMainPlanE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MonMainPlanE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonMainPlanE1Info item)
    {
        return super.indexOf(item);
    }
}