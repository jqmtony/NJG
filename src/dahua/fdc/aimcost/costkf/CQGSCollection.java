package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CQGSCollection extends AbstractObjectCollection 
{
    public CQGSCollection()
    {
        super(CQGSInfo.class);
    }
    public boolean add(CQGSInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CQGSCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CQGSInfo item)
    {
        return removeObject(item);
    }
    public CQGSInfo get(int index)
    {
        return(CQGSInfo)getObject(index);
    }
    public CQGSInfo get(Object key)
    {
        return(CQGSInfo)getObject(key);
    }
    public void set(int index, CQGSInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CQGSInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CQGSInfo item)
    {
        return super.indexOf(item);
    }
}