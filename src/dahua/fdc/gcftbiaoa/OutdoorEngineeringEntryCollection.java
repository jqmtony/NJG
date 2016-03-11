package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OutdoorEngineeringEntryCollection extends AbstractObjectCollection 
{
    public OutdoorEngineeringEntryCollection()
    {
        super(OutdoorEngineeringEntryInfo.class);
    }
    public boolean add(OutdoorEngineeringEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OutdoorEngineeringEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OutdoorEngineeringEntryInfo item)
    {
        return removeObject(item);
    }
    public OutdoorEngineeringEntryInfo get(int index)
    {
        return(OutdoorEngineeringEntryInfo)getObject(index);
    }
    public OutdoorEngineeringEntryInfo get(Object key)
    {
        return(OutdoorEngineeringEntryInfo)getObject(key);
    }
    public void set(int index, OutdoorEngineeringEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OutdoorEngineeringEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OutdoorEngineeringEntryInfo item)
    {
        return super.indexOf(item);
    }
}