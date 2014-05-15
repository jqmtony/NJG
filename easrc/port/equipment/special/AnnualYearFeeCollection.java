package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AnnualYearFeeCollection extends AbstractObjectCollection 
{
    public AnnualYearFeeCollection()
    {
        super(AnnualYearFeeInfo.class);
    }
    public boolean add(AnnualYearFeeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AnnualYearFeeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AnnualYearFeeInfo item)
    {
        return removeObject(item);
    }
    public AnnualYearFeeInfo get(int index)
    {
        return(AnnualYearFeeInfo)getObject(index);
    }
    public AnnualYearFeeInfo get(Object key)
    {
        return(AnnualYearFeeInfo)getObject(key);
    }
    public void set(int index, AnnualYearFeeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AnnualYearFeeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AnnualYearFeeInfo item)
    {
        return super.indexOf(item);
    }
}