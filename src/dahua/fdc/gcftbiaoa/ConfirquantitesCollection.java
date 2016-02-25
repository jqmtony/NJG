package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConfirquantitesCollection extends AbstractObjectCollection 
{
    public ConfirquantitesCollection()
    {
        super(ConfirquantitesInfo.class);
    }
    public boolean add(ConfirquantitesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConfirquantitesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConfirquantitesInfo item)
    {
        return removeObject(item);
    }
    public ConfirquantitesInfo get(int index)
    {
        return(ConfirquantitesInfo)getObject(index);
    }
    public ConfirquantitesInfo get(Object key)
    {
        return(ConfirquantitesInfo)getObject(key);
    }
    public void set(int index, ConfirquantitesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConfirquantitesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConfirquantitesInfo item)
    {
        return super.indexOf(item);
    }
}