package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractLandDeveloperCollection extends AbstractObjectCollection 
{
    public ContractLandDeveloperCollection()
    {
        super(ContractLandDeveloperInfo.class);
    }
    public boolean add(ContractLandDeveloperInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractLandDeveloperCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractLandDeveloperInfo item)
    {
        return removeObject(item);
    }
    public ContractLandDeveloperInfo get(int index)
    {
        return(ContractLandDeveloperInfo)getObject(index);
    }
    public ContractLandDeveloperInfo get(Object key)
    {
        return(ContractLandDeveloperInfo)getObject(key);
    }
    public void set(int index, ContractLandDeveloperInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractLandDeveloperInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractLandDeveloperInfo item)
    {
        return super.indexOf(item);
    }
}