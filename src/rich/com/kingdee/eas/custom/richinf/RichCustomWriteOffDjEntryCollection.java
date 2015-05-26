package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCustomWriteOffDjEntryCollection extends AbstractObjectCollection 
{
    public RichCustomWriteOffDjEntryCollection()
    {
        super(RichCustomWriteOffDjEntryInfo.class);
    }
    public boolean add(RichCustomWriteOffDjEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCustomWriteOffDjEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCustomWriteOffDjEntryInfo item)
    {
        return removeObject(item);
    }
    public RichCustomWriteOffDjEntryInfo get(int index)
    {
        return(RichCustomWriteOffDjEntryInfo)getObject(index);
    }
    public RichCustomWriteOffDjEntryInfo get(Object key)
    {
        return(RichCustomWriteOffDjEntryInfo)getObject(key);
    }
    public void set(int index, RichCustomWriteOffDjEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCustomWriteOffDjEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCustomWriteOffDjEntryInfo item)
    {
        return super.indexOf(item);
    }
}