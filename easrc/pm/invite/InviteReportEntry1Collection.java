package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportEntry1Collection extends AbstractObjectCollection 
{
    public InviteReportEntry1Collection()
    {
        super(InviteReportEntry1Info.class);
    }
    public boolean add(InviteReportEntry1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportEntry1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportEntry1Info item)
    {
        return removeObject(item);
    }
    public InviteReportEntry1Info get(int index)
    {
        return(InviteReportEntry1Info)getObject(index);
    }
    public InviteReportEntry1Info get(Object key)
    {
        return(InviteReportEntry1Info)getObject(key);
    }
    public void set(int index, InviteReportEntry1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportEntry1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportEntry1Info item)
    {
        return super.indexOf(item);
    }
}