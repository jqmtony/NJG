package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCompayWriteOffE2Collection extends AbstractObjectCollection 
{
    public RichCompayWriteOffE2Collection()
    {
        super(RichCompayWriteOffE2Info.class);
    }
    public boolean add(RichCompayWriteOffE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCompayWriteOffE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCompayWriteOffE2Info item)
    {
        return removeObject(item);
    }
    public RichCompayWriteOffE2Info get(int index)
    {
        return(RichCompayWriteOffE2Info)getObject(index);
    }
    public RichCompayWriteOffE2Info get(Object key)
    {
        return(RichCompayWriteOffE2Info)getObject(key);
    }
    public void set(int index, RichCompayWriteOffE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCompayWriteOffE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCompayWriteOffE2Info item)
    {
        return super.indexOf(item);
    }
}