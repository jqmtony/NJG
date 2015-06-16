package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerSyncLogCollection extends AbstractObjectCollection 
{
    public CustomerSyncLogCollection()
    {
        super(CustomerSyncLogInfo.class);
    }
    public boolean add(CustomerSyncLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerSyncLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerSyncLogInfo item)
    {
        return removeObject(item);
    }
    public CustomerSyncLogInfo get(int index)
    {
        return(CustomerSyncLogInfo)getObject(index);
    }
    public CustomerSyncLogInfo get(Object key)
    {
        return(CustomerSyncLogInfo)getObject(key);
    }
    public void set(int index, CustomerSyncLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerSyncLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerSyncLogInfo item)
    {
        return super.indexOf(item);
    }
}