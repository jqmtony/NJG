package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RepairOrderCollection extends AbstractObjectCollection 
{
    public RepairOrderCollection()
    {
        super(RepairOrderInfo.class);
    }
    public boolean add(RepairOrderInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RepairOrderCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RepairOrderInfo item)
    {
        return removeObject(item);
    }
    public RepairOrderInfo get(int index)
    {
        return(RepairOrderInfo)getObject(index);
    }
    public RepairOrderInfo get(Object key)
    {
        return(RepairOrderInfo)getObject(key);
    }
    public void set(int index, RepairOrderInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RepairOrderInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RepairOrderInfo item)
    {
        return super.indexOf(item);
    }
}