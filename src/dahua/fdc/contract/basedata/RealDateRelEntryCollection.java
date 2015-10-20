package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RealDateRelEntryCollection extends AbstractObjectCollection 
{
    public RealDateRelEntryCollection()
    {
        super(RealDateRelEntryInfo.class);
    }
    public boolean add(RealDateRelEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RealDateRelEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RealDateRelEntryInfo item)
    {
        return removeObject(item);
    }
    public RealDateRelEntryInfo get(int index)
    {
        return(RealDateRelEntryInfo)getObject(index);
    }
    public RealDateRelEntryInfo get(Object key)
    {
        return(RealDateRelEntryInfo)getObject(key);
    }
    public void set(int index, RealDateRelEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RealDateRelEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RealDateRelEntryInfo item)
    {
        return super.indexOf(item);
    }
}