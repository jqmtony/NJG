package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasurEquipCollection extends AbstractObjectCollection 
{
    public MeasurEquipCollection()
    {
        super(MeasurEquipInfo.class);
    }
    public boolean add(MeasurEquipInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasurEquipCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasurEquipInfo item)
    {
        return removeObject(item);
    }
    public MeasurEquipInfo get(int index)
    {
        return(MeasurEquipInfo)getObject(index);
    }
    public MeasurEquipInfo get(Object key)
    {
        return(MeasurEquipInfo)getObject(key);
    }
    public void set(int index, MeasurEquipInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasurEquipInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasurEquipInfo item)
    {
        return super.indexOf(item);
    }
}