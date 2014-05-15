package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WinInviteReportCollection extends AbstractObjectCollection 
{
    public WinInviteReportCollection()
    {
        super(WinInviteReportInfo.class);
    }
    public boolean add(WinInviteReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WinInviteReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WinInviteReportInfo item)
    {
        return removeObject(item);
    }
    public WinInviteReportInfo get(int index)
    {
        return(WinInviteReportInfo)getObject(index);
    }
    public WinInviteReportInfo get(Object key)
    {
        return(WinInviteReportInfo)getObject(key);
    }
    public void set(int index, WinInviteReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WinInviteReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WinInviteReportInfo item)
    {
        return super.indexOf(item);
    }
}