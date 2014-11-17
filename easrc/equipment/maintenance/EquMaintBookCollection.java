package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquMaintBookCollection extends AbstractObjectCollection 
{
    public EquMaintBookCollection()
    {
        super(EquMaintBookInfo.class);
    }
    public boolean add(EquMaintBookInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquMaintBookCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquMaintBookInfo item)
    {
        return removeObject(item);
    }
    public EquMaintBookInfo get(int index)
    {
        return(EquMaintBookInfo)getObject(index);
    }
    public EquMaintBookInfo get(Object key)
    {
        return(EquMaintBookInfo)getObject(key);
    }
    public void set(int index, EquMaintBookInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquMaintBookInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquMaintBookInfo item)
    {
        return super.indexOf(item);
    }
}