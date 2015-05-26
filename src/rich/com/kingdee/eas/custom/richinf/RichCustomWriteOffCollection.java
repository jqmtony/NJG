package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCustomWriteOffCollection extends AbstractObjectCollection 
{
    public RichCustomWriteOffCollection()
    {
        super(RichCustomWriteOffInfo.class);
    }
    public boolean add(RichCustomWriteOffInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCustomWriteOffCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCustomWriteOffInfo item)
    {
        return removeObject(item);
    }
    public RichCustomWriteOffInfo get(int index)
    {
        return(RichCustomWriteOffInfo)getObject(index);
    }
    public RichCustomWriteOffInfo get(Object key)
    {
        return(RichCustomWriteOffInfo)getObject(key);
    }
    public void set(int index, RichCustomWriteOffInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCustomWriteOffInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCustomWriteOffInfo item)
    {
        return super.indexOf(item);
    }
}