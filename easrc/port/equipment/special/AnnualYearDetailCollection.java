package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AnnualYearDetailCollection extends AbstractObjectCollection 
{
    public AnnualYearDetailCollection()
    {
        super(AnnualYearDetailInfo.class);
    }
    public boolean add(AnnualYearDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AnnualYearDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AnnualYearDetailInfo item)
    {
        return removeObject(item);
    }
    public AnnualYearDetailInfo get(int index)
    {
        return(AnnualYearDetailInfo)getObject(index);
    }
    public AnnualYearDetailInfo get(Object key)
    {
        return(AnnualYearDetailInfo)getObject(key);
    }
    public void set(int index, AnnualYearDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AnnualYearDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AnnualYearDetailInfo item)
    {
        return super.indexOf(item);
    }
}