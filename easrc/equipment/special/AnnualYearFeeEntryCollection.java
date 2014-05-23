package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AnnualYearFeeEntryCollection extends AbstractObjectCollection 
{
    public AnnualYearFeeEntryCollection()
    {
        super(AnnualYearFeeEntryInfo.class);
    }
    public boolean add(AnnualYearFeeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AnnualYearFeeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AnnualYearFeeEntryInfo item)
    {
        return removeObject(item);
    }
    public AnnualYearFeeEntryInfo get(int index)
    {
        return(AnnualYearFeeEntryInfo)getObject(index);
    }
    public AnnualYearFeeEntryInfo get(Object key)
    {
        return(AnnualYearFeeEntryInfo)getObject(key);
    }
    public void set(int index, AnnualYearFeeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AnnualYearFeeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AnnualYearFeeEntryInfo item)
    {
        return super.indexOf(item);
    }
}