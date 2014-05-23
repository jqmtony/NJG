package com.kingdee.eas.port.equipment.insurance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsuranceDeclarationStateE1Collection extends AbstractObjectCollection 
{
    public InsuranceDeclarationStateE1Collection()
    {
        super(InsuranceDeclarationStateE1Info.class);
    }
    public boolean add(InsuranceDeclarationStateE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsuranceDeclarationStateE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsuranceDeclarationStateE1Info item)
    {
        return removeObject(item);
    }
    public InsuranceDeclarationStateE1Info get(int index)
    {
        return(InsuranceDeclarationStateE1Info)getObject(index);
    }
    public InsuranceDeclarationStateE1Info get(Object key)
    {
        return(InsuranceDeclarationStateE1Info)getObject(key);
    }
    public void set(int index, InsuranceDeclarationStateE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InsuranceDeclarationStateE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsuranceDeclarationStateE1Info item)
    {
        return super.indexOf(item);
    }
}