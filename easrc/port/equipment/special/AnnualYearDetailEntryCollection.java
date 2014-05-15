package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AnnualYearDetailEntryCollection extends AbstractObjectCollection 
{
    public AnnualYearDetailEntryCollection()
    {
        super(AnnualYearDetailEntryInfo.class);
    }
    public boolean add(AnnualYearDetailEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AnnualYearDetailEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AnnualYearDetailEntryInfo item)
    {
        return removeObject(item);
    }
    public AnnualYearDetailEntryInfo get(int index)
    {
        return(AnnualYearDetailEntryInfo)getObject(index);
    }
    public AnnualYearDetailEntryInfo get(Object key)
    {
        return(AnnualYearDetailEntryInfo)getObject(key);
    }
    public void set(int index, AnnualYearDetailEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AnnualYearDetailEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AnnualYearDetailEntryInfo item)
    {
        return super.indexOf(item);
    }
}