package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractDemoCollection extends AbstractObjectCollection 
{
    public ContractDemoCollection()
    {
        super(ContractDemoInfo.class);
    }
    public boolean add(ContractDemoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractDemoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractDemoInfo item)
    {
        return removeObject(item);
    }
    public ContractDemoInfo get(int index)
    {
        return(ContractDemoInfo)getObject(index);
    }
    public ContractDemoInfo get(Object key)
    {
        return(ContractDemoInfo)getObject(key);
    }
    public void set(int index, ContractDemoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractDemoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractDemoInfo item)
    {
        return super.indexOf(item);
    }
}