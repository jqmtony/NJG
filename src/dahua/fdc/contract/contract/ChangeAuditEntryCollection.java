package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeAuditEntryCollection extends AbstractObjectCollection 
{
    public ChangeAuditEntryCollection()
    {
        super(ChangeAuditEntryInfo.class);
    }
    public boolean add(ChangeAuditEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeAuditEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeAuditEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeAuditEntryInfo get(int index)
    {
        return(ChangeAuditEntryInfo)getObject(index);
    }
    public ChangeAuditEntryInfo get(Object key)
    {
        return(ChangeAuditEntryInfo)getObject(key);
    }
    public void set(int index, ChangeAuditEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeAuditEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeAuditEntryInfo item)
    {
        return super.indexOf(item);
    }
}