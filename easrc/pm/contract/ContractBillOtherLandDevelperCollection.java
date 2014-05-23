package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillOtherLandDevelperCollection extends AbstractObjectCollection 
{
    public ContractBillOtherLandDevelperCollection()
    {
        super(ContractBillOtherLandDevelperInfo.class);
    }
    public boolean add(ContractBillOtherLandDevelperInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillOtherLandDevelperCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillOtherLandDevelperInfo item)
    {
        return removeObject(item);
    }
    public ContractBillOtherLandDevelperInfo get(int index)
    {
        return(ContractBillOtherLandDevelperInfo)getObject(index);
    }
    public ContractBillOtherLandDevelperInfo get(Object key)
    {
        return(ContractBillOtherLandDevelperInfo)getObject(key);
    }
    public void set(int index, ContractBillOtherLandDevelperInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillOtherLandDevelperInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillOtherLandDevelperInfo item)
    {
        return super.indexOf(item);
    }
}