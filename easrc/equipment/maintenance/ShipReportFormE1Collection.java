package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShipReportFormE1Collection extends AbstractObjectCollection 
{
    public ShipReportFormE1Collection()
    {
        super(ShipReportFormE1Info.class);
    }
    public boolean add(ShipReportFormE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShipReportFormE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShipReportFormE1Info item)
    {
        return removeObject(item);
    }
    public ShipReportFormE1Info get(int index)
    {
        return(ShipReportFormE1Info)getObject(index);
    }
    public ShipReportFormE1Info get(Object key)
    {
        return(ShipReportFormE1Info)getObject(key);
    }
    public void set(int index, ShipReportFormE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ShipReportFormE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShipReportFormE1Info item)
    {
        return super.indexOf(item);
    }
}