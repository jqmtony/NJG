package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportEntry2Collection extends AbstractObjectCollection 
{
    public InviteReportEntry2Collection()
    {
        super(InviteReportEntry2Info.class);
    }
    public boolean add(InviteReportEntry2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportEntry2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportEntry2Info item)
    {
        return removeObject(item);
    }
    public InviteReportEntry2Info get(int index)
    {
        return(InviteReportEntry2Info)getObject(index);
    }
    public InviteReportEntry2Info get(Object key)
    {
        return(InviteReportEntry2Info)getObject(key);
    }
    public void set(int index, InviteReportEntry2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportEntry2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportEntry2Info item)
    {
        return super.indexOf(item);
    }
}