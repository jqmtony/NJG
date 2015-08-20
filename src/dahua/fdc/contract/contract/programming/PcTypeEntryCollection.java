package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PcTypeEntryCollection extends AbstractObjectCollection 
{
    public PcTypeEntryCollection()
    {
        super(PcTypeEntryInfo.class);
    }
    public boolean add(PcTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PcTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PcTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public PcTypeEntryInfo get(int index)
    {
        return(PcTypeEntryInfo)getObject(index);
    }
    public PcTypeEntryInfo get(Object key)
    {
        return(PcTypeEntryInfo)getObject(key);
    }
    public void set(int index, PcTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PcTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PcTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}