package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ComproductionEntryCollection extends AbstractObjectCollection 
{
    public ComproductionEntryCollection()
    {
        super(ComproductionEntryInfo.class);
    }
    public boolean add(ComproductionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ComproductionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ComproductionEntryInfo item)
    {
        return removeObject(item);
    }
    public ComproductionEntryInfo get(int index)
    {
        return(ComproductionEntryInfo)getObject(index);
    }
    public ComproductionEntryInfo get(Object key)
    {
        return(ComproductionEntryInfo)getObject(key);
    }
    public void set(int index, ComproductionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ComproductionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ComproductionEntryInfo item)
    {
        return super.indexOf(item);
    }
}