package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBillWFAuditInfoCollection extends AbstractObjectCollection 
{
    public FDCBillWFAuditInfoCollection()
    {
        super(FDCBillWFAuditInfoInfo.class);
    }
    public boolean add(FDCBillWFAuditInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBillWFAuditInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBillWFAuditInfoInfo item)
    {
        return removeObject(item);
    }
    public FDCBillWFAuditInfoInfo get(int index)
    {
        return(FDCBillWFAuditInfoInfo)getObject(index);
    }
    public FDCBillWFAuditInfoInfo get(Object key)
    {
        return(FDCBillWFAuditInfoInfo)getObject(key);
    }
    public void set(int index, FDCBillWFAuditInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBillWFAuditInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBillWFAuditInfoInfo item)
    {
        return super.indexOf(item);
    }
}