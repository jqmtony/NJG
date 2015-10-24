package com.kingdee.eas.fdc.photomanager;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PhotoAuditCollection extends AbstractObjectCollection 
{
    public PhotoAuditCollection()
    {
        super(PhotoAuditInfo.class);
    }
    public boolean add(PhotoAuditInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PhotoAuditCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PhotoAuditInfo item)
    {
        return removeObject(item);
    }
    public PhotoAuditInfo get(int index)
    {
        return(PhotoAuditInfo)getObject(index);
    }
    public PhotoAuditInfo get(Object key)
    {
        return(PhotoAuditInfo)getObject(key);
    }
    public void set(int index, PhotoAuditInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PhotoAuditInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PhotoAuditInfo item)
    {
        return super.indexOf(item);
    }
}