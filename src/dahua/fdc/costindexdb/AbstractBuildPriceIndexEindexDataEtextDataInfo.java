package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEindexDataEtextDataInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEindexDataEtextDataInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEindexDataEtextDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ı����� 's null property 
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
     * Object:�ı�����'s ��¼��property 
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
     * Object:�ı�����'s �ı�property 
     */
    public String getTextData()
    {
        return getString("textData");
    }
    public void setTextData(String item)
    {
        setString("textData", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("608038D4");
    }
}