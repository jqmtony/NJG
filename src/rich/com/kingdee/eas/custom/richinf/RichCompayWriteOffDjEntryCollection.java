package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCompayWriteOffDjEntryCollection extends AbstractObjectCollection 
{
    public RichCompayWriteOffDjEntryCollection()
    {
        super(RichCompayWriteOffDjEntryInfo.class);
    }
    public boolean add(RichCompayWriteOffDjEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCompayWriteOffDjEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCompayWriteOffDjEntryInfo item)
    {
        return removeObject(item);
    }
    public RichCompayWriteOffDjEntryInfo get(int index)
    {
        return(RichCompayWriteOffDjEntryInfo)getObject(index);
    }
    public RichCompayWriteOffDjEntryInfo get(Object key)
    {
        return(RichCompayWriteOffDjEntryInfo)getObject(key);
    }
    public void set(int index, RichCompayWriteOffDjEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCompayWriteOffDjEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCompayWriteOffDjEntryInfo item)
    {
        return super.indexOf(item);
    }
}