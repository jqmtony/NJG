package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCustomWriteOffE2Collection extends AbstractObjectCollection 
{
    public RichCustomWriteOffE2Collection()
    {
        super(RichCustomWriteOffE2Info.class);
    }
    public boolean add(RichCustomWriteOffE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCustomWriteOffE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCustomWriteOffE2Info item)
    {
        return removeObject(item);
    }
    public RichCustomWriteOffE2Info get(int index)
    {
        return(RichCustomWriteOffE2Info)getObject(index);
    }
    public RichCustomWriteOffE2Info get(Object key)
    {
        return(RichCustomWriteOffE2Info)getObject(key);
    }
    public void set(int index, RichCustomWriteOffE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCustomWriteOffE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCustomWriteOffE2Info item)
    {
        return super.indexOf(item);
    }
}