package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DecorationEngineeringEntryCollection extends AbstractObjectCollection 
{
    public DecorationEngineeringEntryCollection()
    {
        super(DecorationEngineeringEntryInfo.class);
    }
    public boolean add(DecorationEngineeringEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DecorationEngineeringEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DecorationEngineeringEntryInfo item)
    {
        return removeObject(item);
    }
    public DecorationEngineeringEntryInfo get(int index)
    {
        return(DecorationEngineeringEntryInfo)getObject(index);
    }
    public DecorationEngineeringEntryInfo get(Object key)
    {
        return(DecorationEngineeringEntryInfo)getObject(key);
    }
    public void set(int index, DecorationEngineeringEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DecorationEngineeringEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DecorationEngineeringEntryInfo item)
    {
        return super.indexOf(item);
    }
}