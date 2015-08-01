package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeductAccountEntrysCollection extends AbstractObjectCollection 
{
    public DeductAccountEntrysCollection()
    {
        super(DeductAccountEntrysInfo.class);
    }
    public boolean add(DeductAccountEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeductAccountEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeductAccountEntrysInfo item)
    {
        return removeObject(item);
    }
    public DeductAccountEntrysInfo get(int index)
    {
        return(DeductAccountEntrysInfo)getObject(index);
    }
    public DeductAccountEntrysInfo get(Object key)
    {
        return(DeductAccountEntrysInfo)getObject(key);
    }
    public void set(int index, DeductAccountEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeductAccountEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeductAccountEntrysInfo item)
    {
        return super.indexOf(item);
    }
}