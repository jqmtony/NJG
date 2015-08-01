package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValuationModeCollection extends AbstractObjectCollection 
{
    public ValuationModeCollection()
    {
        super(ValuationModeInfo.class);
    }
    public boolean add(ValuationModeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValuationModeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValuationModeInfo item)
    {
        return removeObject(item);
    }
    public ValuationModeInfo get(int index)
    {
        return(ValuationModeInfo)getObject(index);
    }
    public ValuationModeInfo get(Object key)
    {
        return(ValuationModeInfo)getObject(key);
    }
    public void set(int index, ValuationModeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValuationModeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValuationModeInfo item)
    {
        return super.indexOf(item);
    }
}