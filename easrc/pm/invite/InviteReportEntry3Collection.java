package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportEntry3Collection extends AbstractObjectCollection 
{
    public InviteReportEntry3Collection()
    {
        super(InviteReportEntry3Info.class);
    }
    public boolean add(InviteReportEntry3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportEntry3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportEntry3Info item)
    {
        return removeObject(item);
    }
    public InviteReportEntry3Info get(int index)
    {
        return(InviteReportEntry3Info)getObject(index);
    }
    public InviteReportEntry3Info get(Object key)
    {
        return(InviteReportEntry3Info)getObject(key);
    }
    public void set(int index, InviteReportEntry3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportEntry3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportEntry3Info item)
    {
        return super.indexOf(item);
    }
}