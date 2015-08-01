package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractSignPlanCollection extends AbstractObjectCollection 
{
    public ContractSignPlanCollection()
    {
        super(ContractSignPlanInfo.class);
    }
    public boolean add(ContractSignPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractSignPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractSignPlanInfo item)
    {
        return removeObject(item);
    }
    public ContractSignPlanInfo get(int index)
    {
        return(ContractSignPlanInfo)getObject(index);
    }
    public ContractSignPlanInfo get(Object key)
    {
        return(ContractSignPlanInfo)getObject(key);
    }
    public void set(int index, ContractSignPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractSignPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractSignPlanInfo item)
    {
        return super.indexOf(item);
    }
}