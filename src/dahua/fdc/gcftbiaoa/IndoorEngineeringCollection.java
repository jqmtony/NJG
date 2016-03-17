package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IndoorEngineeringCollection extends AbstractObjectCollection 
{
    public IndoorEngineeringCollection()
    {
        super(IndoorEngineeringInfo.class);
    }
    public boolean add(IndoorEngineeringInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IndoorEngineeringCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IndoorEngineeringInfo item)
    {
        return removeObject(item);
    }
    public IndoorEngineeringInfo get(int index)
    {
        return(IndoorEngineeringInfo)getObject(index);
    }
    public IndoorEngineeringInfo get(Object key)
    {
        return(IndoorEngineeringInfo)getObject(key);
    }
    public void set(int index, IndoorEngineeringInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IndoorEngineeringInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IndoorEngineeringInfo item)
    {
        return super.indexOf(item);
    }
}