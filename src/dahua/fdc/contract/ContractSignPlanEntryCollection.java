package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractSignPlanEntryCollection extends AbstractObjectCollection 
{
    public ContractSignPlanEntryCollection()
    {
        super(ContractSignPlanEntryInfo.class);
    }
    public boolean add(ContractSignPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractSignPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractSignPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractSignPlanEntryInfo get(int index)
    {
        return(ContractSignPlanEntryInfo)getObject(index);
    }
    public ContractSignPlanEntryInfo get(Object key)
    {
        return(ContractSignPlanEntryInfo)getObject(key);
    }
    public void set(int index, ContractSignPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractSignPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractSignPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}