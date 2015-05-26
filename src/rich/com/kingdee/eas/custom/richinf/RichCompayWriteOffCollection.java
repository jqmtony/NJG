package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCompayWriteOffCollection extends AbstractObjectCollection 
{
    public RichCompayWriteOffCollection()
    {
        super(RichCompayWriteOffInfo.class);
    }
    public boolean add(RichCompayWriteOffInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCompayWriteOffCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCompayWriteOffInfo item)
    {
        return removeObject(item);
    }
    public RichCompayWriteOffInfo get(int index)
    {
        return(RichCompayWriteOffInfo)getObject(index);
    }
    public RichCompayWriteOffInfo get(Object key)
    {
        return(RichCompayWriteOffInfo)getObject(key);
    }
    public void set(int index, RichCompayWriteOffInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCompayWriteOffInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCompayWriteOffInfo item)
    {
        return super.indexOf(item);
    }
}