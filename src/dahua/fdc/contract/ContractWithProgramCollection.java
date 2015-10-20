package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWithProgramCollection extends AbstractObjectCollection 
{
    public ContractWithProgramCollection()
    {
        super(ContractWithProgramInfo.class);
    }
    public boolean add(ContractWithProgramInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWithProgramCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWithProgramInfo item)
    {
        return removeObject(item);
    }
    public ContractWithProgramInfo get(int index)
    {
        return(ContractWithProgramInfo)getObject(index);
    }
    public ContractWithProgramInfo get(Object key)
    {
        return(ContractWithProgramInfo)getObject(key);
    }
    public void set(int index, ContractWithProgramInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWithProgramInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWithProgramInfo item)
    {
        return super.indexOf(item);
    }
}