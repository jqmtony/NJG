package com.kingdee.eas.port.pm.acceptance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompleteAcceptE1Collection extends AbstractObjectCollection 
{
    public CompleteAcceptE1Collection()
    {
        super(CompleteAcceptE1Info.class);
    }
    public boolean add(CompleteAcceptE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompleteAcceptE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompleteAcceptE1Info item)
    {
        return removeObject(item);
    }
    public CompleteAcceptE1Info get(int index)
    {
        return(CompleteAcceptE1Info)getObject(index);
    }
    public CompleteAcceptE1Info get(Object key)
    {
        return(CompleteAcceptE1Info)getObject(key);
    }
    public void set(int index, CompleteAcceptE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(CompleteAcceptE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompleteAcceptE1Info item)
    {
        return super.indexOf(item);
    }
}