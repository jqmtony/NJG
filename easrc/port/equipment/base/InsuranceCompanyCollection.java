package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsuranceCompanyCollection extends AbstractObjectCollection 
{
    public InsuranceCompanyCollection()
    {
        super(InsuranceCompanyInfo.class);
    }
    public boolean add(InsuranceCompanyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsuranceCompanyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsuranceCompanyInfo item)
    {
        return removeObject(item);
    }
    public InsuranceCompanyInfo get(int index)
    {
        return(InsuranceCompanyInfo)getObject(index);
    }
    public InsuranceCompanyInfo get(Object key)
    {
        return(InsuranceCompanyInfo)getObject(key);
    }
    public void set(int index, InsuranceCompanyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InsuranceCompanyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsuranceCompanyInfo item)
    {
        return super.indexOf(item);
    }
}