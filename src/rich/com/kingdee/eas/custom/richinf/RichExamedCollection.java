package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichExamedCollection extends AbstractObjectCollection 
{
    public RichExamedCollection()
    {
        super(RichExamedInfo.class);
    }
    public boolean add(RichExamedInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichExamedCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichExamedInfo item)
    {
        return removeObject(item);
    }
    public RichExamedInfo get(int index)
    {
        return(RichExamedInfo)getObject(index);
    }
    public RichExamedInfo get(Object key)
    {
        return(RichExamedInfo)getObject(key);
    }
    public void set(int index, RichExamedInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichExamedInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichExamedInfo item)
    {
        return super.indexOf(item);
    }
}