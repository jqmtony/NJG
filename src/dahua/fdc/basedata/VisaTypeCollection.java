package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class VisaTypeCollection extends AbstractObjectCollection 
{
    public VisaTypeCollection()
    {
        super(VisaTypeInfo.class);
    }
    public boolean add(VisaTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(VisaTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(VisaTypeInfo item)
    {
        return removeObject(item);
    }
    public VisaTypeInfo get(int index)
    {
        return(VisaTypeInfo)getObject(index);
    }
    public VisaTypeInfo get(Object key)
    {
        return(VisaTypeInfo)getObject(key);
    }
    public void set(int index, VisaTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(VisaTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(VisaTypeInfo item)
    {
        return super.indexOf(item);
    }
}