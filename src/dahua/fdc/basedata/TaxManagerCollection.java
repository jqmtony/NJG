package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaxManagerCollection extends AbstractObjectCollection 
{
    public TaxManagerCollection()
    {
        super(TaxManagerInfo.class);
    }
    public boolean add(TaxManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaxManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaxManagerInfo item)
    {
        return removeObject(item);
    }
    public TaxManagerInfo get(int index)
    {
        return(TaxManagerInfo)getObject(index);
    }
    public TaxManagerInfo get(Object key)
    {
        return(TaxManagerInfo)getObject(key);
    }
    public void set(int index, TaxManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaxManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaxManagerInfo item)
    {
        return super.indexOf(item);
    }
}