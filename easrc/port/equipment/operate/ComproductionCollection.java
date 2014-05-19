package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ComproductionCollection extends AbstractObjectCollection 
{
    public ComproductionCollection()
    {
        super(ComproductionInfo.class);
    }
    public boolean add(ComproductionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ComproductionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ComproductionInfo item)
    {
        return removeObject(item);
    }
    public ComproductionInfo get(int index)
    {
        return(ComproductionInfo)getObject(index);
    }
    public ComproductionInfo get(Object key)
    {
        return(ComproductionInfo)getObject(key);
    }
    public void set(int index, ComproductionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ComproductionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ComproductionInfo item)
    {
        return super.indexOf(item);
    }
}