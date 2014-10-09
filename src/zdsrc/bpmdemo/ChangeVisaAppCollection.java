package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeVisaAppCollection extends AbstractObjectCollection 
{
    public ChangeVisaAppCollection()
    {
        super(ChangeVisaAppInfo.class);
    }
    public boolean add(ChangeVisaAppInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeVisaAppCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeVisaAppInfo item)
    {
        return removeObject(item);
    }
    public ChangeVisaAppInfo get(int index)
    {
        return(ChangeVisaAppInfo)getObject(index);
    }
    public ChangeVisaAppInfo get(Object key)
    {
        return(ChangeVisaAppInfo)getObject(key);
    }
    public void set(int index, ChangeVisaAppInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeVisaAppInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeVisaAppInfo item)
    {
        return super.indexOf(item);
    }
}