package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractRevLandDeveloperCollection extends AbstractObjectCollection 
{
    public ContractRevLandDeveloperCollection()
    {
        super(ContractRevLandDeveloperInfo.class);
    }
    public boolean add(ContractRevLandDeveloperInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractRevLandDeveloperCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractRevLandDeveloperInfo item)
    {
        return removeObject(item);
    }
    public ContractRevLandDeveloperInfo get(int index)
    {
        return(ContractRevLandDeveloperInfo)getObject(index);
    }
    public ContractRevLandDeveloperInfo get(Object key)
    {
        return(ContractRevLandDeveloperInfo)getObject(key);
    }
    public void set(int index, ContractRevLandDeveloperInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractRevLandDeveloperInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractRevLandDeveloperInfo item)
    {
        return super.indexOf(item);
    }
}