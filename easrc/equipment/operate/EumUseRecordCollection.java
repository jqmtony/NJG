package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EumUseRecordCollection extends AbstractObjectCollection 
{
    public EumUseRecordCollection()
    {
        super(EumUseRecordInfo.class);
    }
    public boolean add(EumUseRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EumUseRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EumUseRecordInfo item)
    {
        return removeObject(item);
    }
    public EumUseRecordInfo get(int index)
    {
        return(EumUseRecordInfo)getObject(index);
    }
    public EumUseRecordInfo get(Object key)
    {
        return(EumUseRecordInfo)getObject(key);
    }
    public void set(int index, EumUseRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EumUseRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EumUseRecordInfo item)
    {
        return super.indexOf(item);
    }
}