package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WinInviteReportUnitCollection extends AbstractObjectCollection 
{
    public WinInviteReportUnitCollection()
    {
        super(WinInviteReportUnitInfo.class);
    }
    public boolean add(WinInviteReportUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WinInviteReportUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WinInviteReportUnitInfo item)
    {
        return removeObject(item);
    }
    public WinInviteReportUnitInfo get(int index)
    {
        return(WinInviteReportUnitInfo)getObject(index);
    }
    public WinInviteReportUnitInfo get(Object key)
    {
        return(WinInviteReportUnitInfo)getObject(key);
    }
    public void set(int index, WinInviteReportUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WinInviteReportUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WinInviteReportUnitInfo item)
    {
        return super.indexOf(item);
    }
}