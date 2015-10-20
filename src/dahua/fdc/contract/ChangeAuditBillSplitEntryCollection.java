package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeAuditBillSplitEntryCollection extends AbstractObjectCollection 
{
    public ChangeAuditBillSplitEntryCollection()
    {
        super(ChangeAuditBillSplitEntryInfo.class);
    }
    public boolean add(ChangeAuditBillSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeAuditBillSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeAuditBillSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeAuditBillSplitEntryInfo get(int index)
    {
        return(ChangeAuditBillSplitEntryInfo)getObject(index);
    }
    public ChangeAuditBillSplitEntryInfo get(Object key)
    {
        return(ChangeAuditBillSplitEntryInfo)getObject(key);
    }
    public void set(int index, ChangeAuditBillSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeAuditBillSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeAuditBillSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}