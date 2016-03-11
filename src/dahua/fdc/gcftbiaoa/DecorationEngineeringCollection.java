package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DecorationEngineeringCollection extends AbstractObjectCollection 
{
    public DecorationEngineeringCollection()
    {
        super(DecorationEngineeringInfo.class);
    }
    public boolean add(DecorationEngineeringInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DecorationEngineeringCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DecorationEngineeringInfo item)
    {
        return removeObject(item);
    }
    public DecorationEngineeringInfo get(int index)
    {
        return(DecorationEngineeringInfo)getObject(index);
    }
    public DecorationEngineeringInfo get(Object key)
    {
        return(DecorationEngineeringInfo)getObject(key);
    }
    public void set(int index, DecorationEngineeringInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DecorationEngineeringInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DecorationEngineeringInfo item)
    {
        return super.indexOf(item);
    }
}