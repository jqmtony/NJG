package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteReportCollection extends AbstractObjectCollection 
{
    public InviteReportCollection()
    {
        super(InviteReportInfo.class);
    }
    public boolean add(InviteReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteReportInfo item)
    {
        return removeObject(item);
    }
    public InviteReportInfo get(int index)
    {
        return(InviteReportInfo)getObject(index);
    }
    public InviteReportInfo get(Object key)
    {
        return(InviteReportInfo)getObject(key);
    }
    public void set(int index, InviteReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteReportInfo item)
    {
        return super.indexOf(item);
    }
}