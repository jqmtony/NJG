package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillReviseCollection extends AbstractObjectCollection 
{
    public ContractBillReviseCollection()
    {
        super(ContractBillReviseInfo.class);
    }
    public boolean add(ContractBillReviseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillReviseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillReviseInfo item)
    {
        return removeObject(item);
    }
    public ContractBillReviseInfo get(int index)
    {
        return(ContractBillReviseInfo)getObject(index);
    }
    public ContractBillReviseInfo get(Object key)
    {
        return(ContractBillReviseInfo)getObject(key);
    }
    public void set(int index, ContractBillReviseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillReviseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillReviseInfo item)
    {
        return super.indexOf(item);
    }
}