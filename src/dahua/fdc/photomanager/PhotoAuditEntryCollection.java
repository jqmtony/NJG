package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PhotoAuditEntryCollection extends AbstractObjectCollection 
{
    public PhotoAuditEntryCollection()
    {
        super(PhotoAuditEntryInfo.class);
    }
    public boolean add(PhotoAuditEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PhotoAuditEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PhotoAuditEntryInfo item)
    {
        return removeObject(item);
    }
    public PhotoAuditEntryInfo get(int index)
    {
        return(PhotoAuditEntryInfo)getObject(index);
    }
    public PhotoAuditEntryInfo get(Object key)
    {
        return(PhotoAuditEntryInfo)getObject(key);
    }
    public void set(int index, PhotoAuditEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PhotoAuditEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PhotoAuditEntryInfo item)
    {
        return super.indexOf(item);
    }
}