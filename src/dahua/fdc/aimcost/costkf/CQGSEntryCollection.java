package com.kingdee.eas.fdc.aimcost.costkf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CQGSEntryCollection extends AbstractObjectCollection 
{
    public CQGSEntryCollection()
    {
        super(CQGSEntryInfo.class);
    }
    public boolean add(CQGSEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CQGSEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CQGSEntryInfo item)
    {
        return removeObject(item);
    }
    public CQGSEntryInfo get(int index)
    {
        return(CQGSEntryInfo)getObject(index);
    }
    public CQGSEntryInfo get(Object key)
    {
        return(CQGSEntryInfo)getObject(key);
    }
    public void set(int index, CQGSEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CQGSEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CQGSEntryInfo item)
    {
        return super.indexOf(item);
    }
}