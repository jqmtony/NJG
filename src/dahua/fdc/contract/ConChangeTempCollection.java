package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeTempCollection extends AbstractObjectCollection 
{
    public ConChangeTempCollection()
    {
        super(ConChangeTempInfo.class);
    }
    public boolean add(ConChangeTempInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeTempCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeTempInfo item)
    {
        return removeObject(item);
    }
    public ConChangeTempInfo get(int index)
    {
        return(ConChangeTempInfo)getObject(index);
    }
    public ConChangeTempInfo get(Object key)
    {
        return(ConChangeTempInfo)getObject(key);
    }
    public void set(int index, ConChangeTempInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeTempInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeTempInfo item)
    {
        return super.indexOf(item);
    }
}