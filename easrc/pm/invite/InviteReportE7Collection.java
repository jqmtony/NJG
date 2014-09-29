package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportE7Collection extends AbstractObjectCollection 
{
    public InviteReportE7Collection()
    {
        super(InviteReportE7Info.class);
    }
    public boolean add(InviteReportE7Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportE7Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportE7Info item)
    {
        return removeObject(item);
    }
    public InviteReportE7Info get(int index)
    {
        return(InviteReportE7Info)getObject(index);
    }
    public InviteReportE7Info get(Object key)
    {
        return(InviteReportE7Info)getObject(key);
    }
    public void set(int index, InviteReportE7Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportE7Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportE7Info item)
    {
        return super.indexOf(item);
    }
}