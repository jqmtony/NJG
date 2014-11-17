package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillBudgetEntryCollection extends AbstractObjectCollection 
{
    public ContractBillBudgetEntryCollection()
    {
        super(ContractBillBudgetEntryInfo.class);
    }
    public boolean add(ContractBillBudgetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillBudgetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillBudgetEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillBudgetEntryInfo get(int index)
    {
        return(ContractBillBudgetEntryInfo)getObject(index);
    }
    public ContractBillBudgetEntryInfo get(Object key)
    {
        return(ContractBillBudgetEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillBudgetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillBudgetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillBudgetEntryInfo item)
    {
        return super.indexOf(item);
    }
}