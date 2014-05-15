package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialChangeCollection extends AbstractObjectCollection 
{
    public SpecialChangeCollection()
    {
        super(SpecialChangeInfo.class);
    }
    public boolean add(SpecialChangeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialChangeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialChangeInfo item)
    {
        return removeObject(item);
    }
    public SpecialChangeInfo get(int index)
    {
        return(SpecialChangeInfo)getObject(index);
    }
    public SpecialChangeInfo get(Object key)
    {
        return(SpecialChangeInfo)getObject(key);
    }
    public void set(int index, SpecialChangeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialChangeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialChangeInfo item)
    {
        return super.indexOf(item);
    }
}