package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCustomWriteOffEntryFpEntryCollection extends AbstractObjectCollection 
{
    public RichCustomWriteOffEntryFpEntryCollection()
    {
        super(RichCustomWriteOffEntryFpEntryInfo.class);
    }
    public boolean add(RichCustomWriteOffEntryFpEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCustomWriteOffEntryFpEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCustomWriteOffEntryFpEntryInfo item)
    {
        return removeObject(item);
    }
    public RichCustomWriteOffEntryFpEntryInfo get(int index)
    {
        return(RichCustomWriteOffEntryFpEntryInfo)getObject(index);
    }
    public RichCustomWriteOffEntryFpEntryInfo get(Object key)
    {
        return(RichCustomWriteOffEntryFpEntryInfo)getObject(key);
    }
    public void set(int index, RichCustomWriteOffEntryFpEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCustomWriteOffEntryFpEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCustomWriteOffEntryFpEntryInfo item)
    {
        return super.indexOf(item);
    }
}