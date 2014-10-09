package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeVisaAppEntryCollection extends AbstractObjectCollection 
{
    public ChangeVisaAppEntryCollection()
    {
        super(ChangeVisaAppEntryInfo.class);
    }
    public boolean add(ChangeVisaAppEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeVisaAppEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeVisaAppEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeVisaAppEntryInfo get(int index)
    {
        return(ChangeVisaAppEntryInfo)getObject(index);
    }
    public ChangeVisaAppEntryInfo get(Object key)
    {
        return(ChangeVisaAppEntryInfo)getObject(key);
    }
    public void set(int index, ChangeVisaAppEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeVisaAppEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeVisaAppEntryInfo item)
    {
        return super.indexOf(item);
    }
}