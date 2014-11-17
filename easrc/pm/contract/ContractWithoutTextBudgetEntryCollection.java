package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWithoutTextBudgetEntryCollection extends AbstractObjectCollection 
{
    public ContractWithoutTextBudgetEntryCollection()
    {
        super(ContractWithoutTextBudgetEntryInfo.class);
    }
    public boolean add(ContractWithoutTextBudgetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWithoutTextBudgetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWithoutTextBudgetEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractWithoutTextBudgetEntryInfo get(int index)
    {
        return(ContractWithoutTextBudgetEntryInfo)getObject(index);
    }
    public ContractWithoutTextBudgetEntryInfo get(Object key)
    {
        return(ContractWithoutTextBudgetEntryInfo)getObject(key);
    }
    public void set(int index, ContractWithoutTextBudgetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWithoutTextBudgetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWithoutTextBudgetEntryInfo item)
    {
        return super.indexOf(item);
    }
}