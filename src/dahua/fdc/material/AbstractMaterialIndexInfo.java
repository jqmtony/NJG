package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialIndexInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMaterialIndexInfo()
    {
        this("id");
    }
    protected AbstractMaterialIndexInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ָ�����'s ��Դproperty 
     */
    public com.kingdee.eas.fdc.material.MaterialIndexEnum getSource()
    {
        return com.kingdee.eas.fdc.material.MaterialIndexEnum.getEnum(getString("source"));
    }
    public void setSource(com.kingdee.eas.fdc.material.MaterialIndexEnum item)
    {
		if (item != null) {
        setString("source", item.getValue());
		}
    }
    /**
     * Object: ����ָ����� 's ���Ϸ��� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialGroupInfo getMaterialGroup()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialGroupInfo)get("materialGroup");
    }
    public void setMaterialGroup(com.kingdee.eas.basedata.master.material.MaterialGroupInfo item)
    {
        put("materialGroup", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09E88265");
    }
}