package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialCheckItemCollection extends AbstractObjectCollection 
{
    public SpecialCheckItemCollection()
    {
        super(SpecialCheckItemInfo.class);
    }
    public boolean add(SpecialCheckItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialCheckItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialCheckItemInfo item)
    {
        return removeObject(item);
    }
    public SpecialCheckItemInfo get(int index)
    {
        return(SpecialCheckItemInfo)getObject(index);
    }
    public SpecialCheckItemInfo get(Object key)
    {
        return(SpecialCheckItemInfo)getObject(key);
    }
    public void set(int index, SpecialCheckItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialCheckItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialCheckItemInfo item)
    {
        return super.indexOf(item);
    }
}