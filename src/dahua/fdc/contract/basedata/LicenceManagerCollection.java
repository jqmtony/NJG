package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LicenceManagerCollection extends AbstractObjectCollection 
{
    public LicenceManagerCollection()
    {
        super(LicenceManagerInfo.class);
    }
    public boolean add(LicenceManagerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LicenceManagerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LicenceManagerInfo item)
    {
        return removeObject(item);
    }
    public LicenceManagerInfo get(int index)
    {
        return(LicenceManagerInfo)getObject(index);
    }
    public LicenceManagerInfo get(Object key)
    {
        return(LicenceManagerInfo)getObject(key);
    }
    public void set(int index, LicenceManagerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LicenceManagerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LicenceManagerInfo item)
    {
        return super.indexOf(item);
    }
}