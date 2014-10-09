package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeVisaAppAssEntryCollection extends AbstractObjectCollection 
{
    public ChangeVisaAppAssEntryCollection()
    {
        super(ChangeVisaAppAssEntryInfo.class);
    }
    public boolean add(ChangeVisaAppAssEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeVisaAppAssEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeVisaAppAssEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeVisaAppAssEntryInfo get(int index)
    {
        return(ChangeVisaAppAssEntryInfo)getObject(index);
    }
    public ChangeVisaAppAssEntryInfo get(Object key)
    {
        return(ChangeVisaAppAssEntryInfo)getObject(key);
    }
    public void set(int index, ChangeVisaAppAssEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeVisaAppAssEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeVisaAppAssEntryInfo item)
    {
        return super.indexOf(item);
    }
}