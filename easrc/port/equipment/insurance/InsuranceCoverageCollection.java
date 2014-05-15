package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsuranceCoverageCollection extends AbstractObjectCollection 
{
    public InsuranceCoverageCollection()
    {
        super(InsuranceCoverageInfo.class);
    }
    public boolean add(InsuranceCoverageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsuranceCoverageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsuranceCoverageInfo item)
    {
        return removeObject(item);
    }
    public InsuranceCoverageInfo get(int index)
    {
        return(InsuranceCoverageInfo)getObject(index);
    }
    public InsuranceCoverageInfo get(Object key)
    {
        return(InsuranceCoverageInfo)getObject(key);
    }
    public void set(int index, InsuranceCoverageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InsuranceCoverageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsuranceCoverageInfo item)
    {
        return super.indexOf(item);
    }
}