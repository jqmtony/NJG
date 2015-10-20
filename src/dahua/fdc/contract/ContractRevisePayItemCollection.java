package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractRevisePayItemCollection extends AbstractObjectCollection 
{
    public ContractRevisePayItemCollection()
    {
        super(ContractRevisePayItemInfo.class);
    }
    public boolean add(ContractRevisePayItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractRevisePayItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractRevisePayItemInfo item)
    {
        return removeObject(item);
    }
    public ContractRevisePayItemInfo get(int index)
    {
        return(ContractRevisePayItemInfo)getObject(index);
    }
    public ContractRevisePayItemInfo get(Object key)
    {
        return(ContractRevisePayItemInfo)getObject(key);
    }
    public void set(int index, ContractRevisePayItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractRevisePayItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractRevisePayItemInfo item)
    {
        return super.indexOf(item);
    }
}