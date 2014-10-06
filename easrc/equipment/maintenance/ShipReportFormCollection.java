package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShipReportFormCollection extends AbstractObjectCollection 
{
    public ShipReportFormCollection()
    {
        super(ShipReportFormInfo.class);
    }
    public boolean add(ShipReportFormInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShipReportFormCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShipReportFormInfo item)
    {
        return removeObject(item);
    }
    public ShipReportFormInfo get(int index)
    {
        return(ShipReportFormInfo)getObject(index);
    }
    public ShipReportFormInfo get(Object key)
    {
        return(ShipReportFormInfo)getObject(key);
    }
    public void set(int index, ShipReportFormInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShipReportFormInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShipReportFormInfo item)
    {
        return super.indexOf(item);
    }
}