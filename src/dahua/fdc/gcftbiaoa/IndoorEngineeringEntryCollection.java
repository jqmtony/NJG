package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IndoorEngineeringEntryCollection extends AbstractObjectCollection 
{
    public IndoorEngineeringEntryCollection()
    {
        super(IndoorEngineeringEntryInfo.class);
    }
    public boolean add(IndoorEngineeringEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IndoorEngineeringEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IndoorEngineeringEntryInfo item)
    {
        return removeObject(item);
    }
    public IndoorEngineeringEntryInfo get(int index)
    {
        return(IndoorEngineeringEntryInfo)getObject(index);
    }
    public IndoorEngineeringEntryInfo get(Object key)
    {
        return(IndoorEngineeringEntryInfo)getObject(key);
    }
    public void set(int index, IndoorEngineeringEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IndoorEngineeringEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IndoorEngineeringEntryInfo item)
    {
        return super.indexOf(item);
    }
}