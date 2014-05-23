package com.kingdee.eas.port.pm.acceptance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompleteAcceptCollection extends AbstractObjectCollection 
{
    public CompleteAcceptCollection()
    {
        super(CompleteAcceptInfo.class);
    }
    public boolean add(CompleteAcceptInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompleteAcceptCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompleteAcceptInfo item)
    {
        return removeObject(item);
    }
    public CompleteAcceptInfo get(int index)
    {
        return(CompleteAcceptInfo)getObject(index);
    }
    public CompleteAcceptInfo get(Object key)
    {
        return(CompleteAcceptInfo)getObject(key);
    }
    public void set(int index, CompleteAcceptInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompleteAcceptInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompleteAcceptInfo item)
    {
        return super.indexOf(item);
    }
}