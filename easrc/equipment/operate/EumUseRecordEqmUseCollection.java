package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EumUseRecordEqmUseCollection extends AbstractObjectCollection 
{
    public EumUseRecordEqmUseCollection()
    {
        super(EumUseRecordEqmUseInfo.class);
    }
    public boolean add(EumUseRecordEqmUseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EumUseRecordEqmUseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EumUseRecordEqmUseInfo item)
    {
        return removeObject(item);
    }
    public EumUseRecordEqmUseInfo get(int index)
    {
        return(EumUseRecordEqmUseInfo)getObject(index);
    }
    public EumUseRecordEqmUseInfo get(Object key)
    {
        return(EumUseRecordEqmUseInfo)getObject(key);
    }
    public void set(int index, EumUseRecordEqmUseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EumUseRecordEqmUseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EumUseRecordEqmUseInfo item)
    {
        return super.indexOf(item);
    }
}