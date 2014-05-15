package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquInsuranceAccidentCollection extends AbstractObjectCollection 
{
    public EquInsuranceAccidentCollection()
    {
        super(EquInsuranceAccidentInfo.class);
    }
    public boolean add(EquInsuranceAccidentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquInsuranceAccidentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquInsuranceAccidentInfo item)
    {
        return removeObject(item);
    }
    public EquInsuranceAccidentInfo get(int index)
    {
        return(EquInsuranceAccidentInfo)getObject(index);
    }
    public EquInsuranceAccidentInfo get(Object key)
    {
        return(EquInsuranceAccidentInfo)getObject(key);
    }
    public void set(int index, EquInsuranceAccidentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquInsuranceAccidentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquInsuranceAccidentInfo item)
    {
        return super.indexOf(item);
    }
}