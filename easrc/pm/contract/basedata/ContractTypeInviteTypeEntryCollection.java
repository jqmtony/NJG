package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractTypeInviteTypeEntryCollection extends AbstractObjectCollection 
{
    public ContractTypeInviteTypeEntryCollection()
    {
        super(ContractTypeInviteTypeEntryInfo.class);
    }
    public boolean add(ContractTypeInviteTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractTypeInviteTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractTypeInviteTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractTypeInviteTypeEntryInfo get(int index)
    {
        return(ContractTypeInviteTypeEntryInfo)getObject(index);
    }
    public ContractTypeInviteTypeEntryInfo get(Object key)
    {
        return(ContractTypeInviteTypeEntryInfo)getObject(key);
    }
    public void set(int index, ContractTypeInviteTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractTypeInviteTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractTypeInviteTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}