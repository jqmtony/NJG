package com.kingdee.eas.port.pm.projectauditsettlement;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectAuditSettlementCollection extends AbstractObjectCollection 
{
    public ProjectAuditSettlementCollection()
    {
        super(ProjectAuditSettlementInfo.class);
    }
    public boolean add(ProjectAuditSettlementInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectAuditSettlementCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectAuditSettlementInfo item)
    {
        return removeObject(item);
    }
    public ProjectAuditSettlementInfo get(int index)
    {
        return(ProjectAuditSettlementInfo)getObject(index);
    }
    public ProjectAuditSettlementInfo get(Object key)
    {
        return(ProjectAuditSettlementInfo)getObject(key);
    }
    public void set(int index, ProjectAuditSettlementInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectAuditSettlementInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectAuditSettlementInfo item)
    {
        return super.indexOf(item);
    }
}