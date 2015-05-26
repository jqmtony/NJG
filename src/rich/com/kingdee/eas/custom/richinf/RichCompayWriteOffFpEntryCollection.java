package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCompayWriteOffFpEntryCollection extends AbstractObjectCollection 
{
    public RichCompayWriteOffFpEntryCollection()
    {
        super(RichCompayWriteOffFpEntryInfo.class);
    }
    public boolean add(RichCompayWriteOffFpEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCompayWriteOffFpEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCompayWriteOffFpEntryInfo item)
    {
        return removeObject(item);
    }
    public RichCompayWriteOffFpEntryInfo get(int index)
    {
        return(RichCompayWriteOffFpEntryInfo)getObject(index);
    }
    public RichCompayWriteOffFpEntryInfo get(Object key)
    {
        return(RichCompayWriteOffFpEntryInfo)getObject(key);
    }
    public void set(int index, RichCompayWriteOffFpEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCompayWriteOffFpEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCompayWriteOffFpEntryInfo item)
    {
        return super.indexOf(item);
    }
}