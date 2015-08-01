package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDataBaseCollection extends AbstractObjectCollection 
{
    public FDCDataBaseCollection()
    {
        super(FDCDataBaseInfo.class);
    }
    public boolean add(FDCDataBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDataBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDataBaseInfo item)
    {
        return removeObject(item);
    }
    public FDCDataBaseInfo get(int index)
    {
        return(FDCDataBaseInfo)getObject(index);
    }
    public FDCDataBaseInfo get(Object key)
    {
        return(FDCDataBaseInfo)getObject(key);
    }
    public void set(int index, FDCDataBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDataBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDataBaseInfo item)
    {
        return super.indexOf(item);
    }
}