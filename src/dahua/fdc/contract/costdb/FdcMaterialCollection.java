package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcMaterialCollection extends AbstractObjectCollection 
{
    public FdcMaterialCollection()
    {
        super(FdcMaterialInfo.class);
    }
    public boolean add(FdcMaterialInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcMaterialCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcMaterialInfo item)
    {
        return removeObject(item);
    }
    public FdcMaterialInfo get(int index)
    {
        return(FdcMaterialInfo)getObject(index);
    }
    public FdcMaterialInfo get(Object key)
    {
        return(FdcMaterialInfo)getObject(key);
    }
    public void set(int index, FdcMaterialInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcMaterialInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcMaterialInfo item)
    {
        return super.indexOf(item);
    }
}