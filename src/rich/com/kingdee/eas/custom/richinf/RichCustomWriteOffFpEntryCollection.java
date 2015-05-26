package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCustomWriteOffFpEntryCollection extends AbstractObjectCollection 
{
    public RichCustomWriteOffFpEntryCollection()
    {
        super(RichCustomWriteOffFpEntryInfo.class);
    }
    public boolean add(RichCustomWriteOffFpEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCustomWriteOffFpEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCustomWriteOffFpEntryInfo item)
    {
        return removeObject(item);
    }
    public RichCustomWriteOffFpEntryInfo get(int index)
    {
        return(RichCustomWriteOffFpEntryInfo)getObject(index);
    }
    public RichCustomWriteOffFpEntryInfo get(Object key)
    {
        return(RichCustomWriteOffFpEntryInfo)getObject(key);
    }
    public void set(int index, RichCustomWriteOffFpEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCustomWriteOffFpEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCustomWriteOffFpEntryInfo item)
    {
        return super.indexOf(item);
    }
}