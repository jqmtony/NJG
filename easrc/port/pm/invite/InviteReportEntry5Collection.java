package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportEntry5Collection extends AbstractObjectCollection 
{
    public InviteReportEntry5Collection()
    {
        super(InviteReportEntry5Info.class);
    }
    public boolean add(InviteReportEntry5Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportEntry5Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportEntry5Info item)
    {
        return removeObject(item);
    }
    public InviteReportEntry5Info get(int index)
    {
        return(InviteReportEntry5Info)getObject(index);
    }
    public InviteReportEntry5Info get(Object key)
    {
        return(InviteReportEntry5Info)getObject(key);
    }
    public void set(int index, InviteReportEntry5Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportEntry5Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportEntry5Info item)
    {
        return super.indexOf(item);
    }
}