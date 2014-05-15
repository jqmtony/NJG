package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsuranceDeclarationStateCollection extends AbstractObjectCollection 
{
    public InsuranceDeclarationStateCollection()
    {
        super(InsuranceDeclarationStateInfo.class);
    }
    public boolean add(InsuranceDeclarationStateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsuranceDeclarationStateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsuranceDeclarationStateInfo item)
    {
        return removeObject(item);
    }
    public InsuranceDeclarationStateInfo get(int index)
    {
        return(InsuranceDeclarationStateInfo)getObject(index);
    }
    public InsuranceDeclarationStateInfo get(Object key)
    {
        return(InsuranceDeclarationStateInfo)getObject(key);
    }
    public void set(int index, InsuranceDeclarationStateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InsuranceDeclarationStateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsuranceDeclarationStateInfo item)
    {
        return super.indexOf(item);
    }
}