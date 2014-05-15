package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WinInviteReportJudgeCollection extends AbstractObjectCollection 
{
    public WinInviteReportJudgeCollection()
    {
        super(WinInviteReportJudgeInfo.class);
    }
    public boolean add(WinInviteReportJudgeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WinInviteReportJudgeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WinInviteReportJudgeInfo item)
    {
        return removeObject(item);
    }
    public WinInviteReportJudgeInfo get(int index)
    {
        return(WinInviteReportJudgeInfo)getObject(index);
    }
    public WinInviteReportJudgeInfo get(Object key)
    {
        return(WinInviteReportJudgeInfo)getObject(key);
    }
    public void set(int index, WinInviteReportJudgeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WinInviteReportJudgeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WinInviteReportJudgeInfo item)
    {
        return super.indexOf(item);
    }
}