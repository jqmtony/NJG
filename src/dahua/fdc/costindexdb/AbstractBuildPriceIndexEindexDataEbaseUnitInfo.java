package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEindexDataEbaseUnitInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEindexDataEbaseUnitInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEindexDataEbaseUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������λ���� 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo getParent1()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:������λ����'s ��¼��property 
     */
    public String getRecordSeq()
    {
        return getString("recordSeq");
    }
    public void setRecordSeq(String item)
    {
        setString("recordSeq", item);
    }
    /**
     * Object: ������λ���� 's ������λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getBaseUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("baseUnit");
    }
    public void setBaseUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("baseUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("35BE7252");
    }
}