package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillPayItemCollection extends AbstractObjectCollection 
{
    public ContractBillPayItemCollection()
    {
        super(ContractBillPayItemInfo.class);
    }
    public boolean add(ContractBillPayItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillPayItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillPayItemInfo item)
    {
        return removeObject(item);
    }
    public ContractBillPayItemInfo get(int index)
    {
        return(ContractBillPayItemInfo)getObject(index);
    }
    public ContractBillPayItemInfo get(Object key)
    {
        return(ContractBillPayItemInfo)getObject(key);
    }
    public void set(int index, ContractBillPayItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillPayItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillPayItemInfo item)
    {
        return super.indexOf(item);
    }
}