package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OutdoorEngineeringCollection extends AbstractObjectCollection 
{
    public OutdoorEngineeringCollection()
    {
        super(OutdoorEngineeringInfo.class);
    }
    public boolean add(OutdoorEngineeringInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OutdoorEngineeringCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OutdoorEngineeringInfo item)
    {
        return removeObject(item);
    }
    public OutdoorEngineeringInfo get(int index)
    {
        return(OutdoorEngineeringInfo)getObject(index);
    }
    public OutdoorEngineeringInfo get(Object key)
    {
        return(OutdoorEngineeringInfo)getObject(key);
    }
    public void set(int index, OutdoorEngineeringInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OutdoorEngineeringInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OutdoorEngineeringInfo item)
    {
        return super.indexOf(item);
    }
}