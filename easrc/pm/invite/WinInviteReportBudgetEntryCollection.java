package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WinInviteReportBudgetEntryCollection extends AbstractObjectCollection 
{
    public WinInviteReportBudgetEntryCollection()
    {
        super(WinInviteReportBudgetEntryInfo.class);
    }
    public boolean add(WinInviteReportBudgetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WinInviteReportBudgetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WinInviteReportBudgetEntryInfo item)
    {
        return removeObject(item);
    }
    public WinInviteReportBudgetEntryInfo get(int index)
    {
        return(WinInviteReportBudgetEntryInfo)getObject(index);
    }
    public WinInviteReportBudgetEntryInfo get(Object key)
    {
        return(WinInviteReportBudgetEntryInfo)getObject(key);
    }
    public void set(int index, WinInviteReportBudgetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WinInviteReportBudgetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WinInviteReportBudgetEntryInfo item)
    {
        return super.indexOf(item);
    }
}