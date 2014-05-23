package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsuranceCoverageE1Collection extends AbstractObjectCollection 
{
    public InsuranceCoverageE1Collection()
    {
        super(InsuranceCoverageE1Info.class);
    }
    public boolean add(InsuranceCoverageE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsuranceCoverageE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsuranceCoverageE1Info item)
    {
        return removeObject(item);
    }
    public InsuranceCoverageE1Info get(int index)
    {
        return(InsuranceCoverageE1Info)getObject(index);
    }
    public InsuranceCoverageE1Info get(Object key)
    {
        return(InsuranceCoverageE1Info)getObject(key);
    }
    public void set(int index, InsuranceCoverageE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InsuranceCoverageE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsuranceCoverageE1Info item)
    {
        return super.indexOf(item);
    }
}