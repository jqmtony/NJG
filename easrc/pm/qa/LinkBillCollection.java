package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LinkBillCollection extends AbstractObjectCollection 
{
    public LinkBillCollection()
    {
        super(LinkBillInfo.class);
    }
    public boolean add(LinkBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LinkBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LinkBillInfo item)
    {
        return removeObject(item);
    }
    public LinkBillInfo get(int index)
    {
        return(LinkBillInfo)getObject(index);
    }
    public LinkBillInfo get(Object key)
    {
        return(LinkBillInfo)getObject(key);
    }
    public void set(int index, LinkBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LinkBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LinkBillInfo item)
    {
        return super.indexOf(item);
    }
}