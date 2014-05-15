package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportEntry4Collection extends AbstractObjectCollection 
{
    public InviteReportEntry4Collection()
    {
        super(InviteReportEntry4Info.class);
    }
    public boolean add(InviteReportEntry4Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportEntry4Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportEntry4Info item)
    {
        return removeObject(item);
    }
    public InviteReportEntry4Info get(int index)
    {
        return(InviteReportEntry4Info)getObject(index);
    }
    public InviteReportEntry4Info get(Object key)
    {
        return(InviteReportEntry4Info)getObject(key);
    }
    public void set(int index, InviteReportEntry4Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportEntry4Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportEntry4Info item)
    {
        return super.indexOf(item);
    }
}