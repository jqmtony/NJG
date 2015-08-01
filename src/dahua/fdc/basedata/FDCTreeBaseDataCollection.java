package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCTreeBaseDataCollection extends AbstractObjectCollection 
{
    public FDCTreeBaseDataCollection()
    {
        super(FDCTreeBaseDataInfo.class);
    }
    public boolean add(FDCTreeBaseDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCTreeBaseDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCTreeBaseDataInfo item)
    {
        return removeObject(item);
    }
    public FDCTreeBaseDataInfo get(int index)
    {
        return(FDCTreeBaseDataInfo)getObject(index);
    }
    public FDCTreeBaseDataInfo get(Object key)
    {
        return(FDCTreeBaseDataInfo)getObject(key);
    }
    public void set(int index, FDCTreeBaseDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCTreeBaseDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCTreeBaseDataInfo item)
    {
        return super.indexOf(item);
    }
}