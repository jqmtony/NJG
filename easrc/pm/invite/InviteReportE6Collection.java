package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportE6Collection extends AbstractObjectCollection 
{
    public InviteReportE6Collection()
    {
        super(InviteReportE6Info.class);
    }
    public boolean add(InviteReportE6Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportE6Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportE6Info item)
    {
        return removeObject(item);
    }
    public InviteReportE6Info get(int index)
    {
        return(InviteReportE6Info)getObject(index);
    }
    public InviteReportE6Info get(Object key)
    {
        return(InviteReportE6Info)getObject(key);
    }
    public void set(int index, InviteReportE6Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportE6Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportE6Info item)
    {
        return super.indexOf(item);
    }
}