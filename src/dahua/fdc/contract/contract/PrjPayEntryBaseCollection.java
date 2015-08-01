package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrjPayEntryBaseCollection extends AbstractObjectCollection 
{
    public PrjPayEntryBaseCollection()
    {
        super(PrjPayEntryBaseInfo.class);
    }
    public boolean add(PrjPayEntryBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrjPayEntryBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrjPayEntryBaseInfo item)
    {
        return removeObject(item);
    }
    public PrjPayEntryBaseInfo get(int index)
    {
        return(PrjPayEntryBaseInfo)getObject(index);
    }
    public PrjPayEntryBaseInfo get(Object key)
    {
        return(PrjPayEntryBaseInfo)getObject(key);
    }
    public void set(int index, PrjPayEntryBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrjPayEntryBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrjPayEntryBaseInfo item)
    {
        return super.indexOf(item);
    }
}