package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEindexDataInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEindexDataInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEindexDataInfo(String pkField)
    {
        super(pkField);
        put("EbaseUnit", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbaseUnitCollection());
        put("EproductType", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEproductTypeCollection());
        put("EbuildNumber", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbuildNumberCollection());
        put("EdateData", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEdateDataCollection());
        put("EnumberData", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEnumberDataCollection());
        put("EtextData", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEtextDataCollection());
    }
    /**
     * Object: ָ������ 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ָ������ 's ָ����� property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.IndexTypeInfo getIndexType()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IndexTypeInfo)get("indexType");
    }
    public void setIndexType(com.kingdee.eas.fdc.costindexdb.database.IndexTypeInfo item)
    {
        put("indexType", item);
    }
    /**
     * Object:ָ������'s �ɱ���Ŀ����property 
     */
    public String getCostAccountNumber()
    {
        return getString("costAccountNumber");
    }
    public void setCostAccountNumber(String item)
    {
        setString("costAccountNumber", item);
    }
    /**
     * Object:ָ������'s ��¼��property 
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
     * Object:ָ������'s ��¼�к�property 
     */
    public int getRecordIndex()
    {
        return getInt("recordIndex");
    }
    public void setRecordIndex(int item)
    {
        setInt("recordIndex", item);
    }
    /**
     * Object: ָ������ 's �ı����� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEtextDataCollection getEtextData()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEtextDataCollection)get("EtextData");
    }
    /**
     * Object: ָ������ 's �������� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEnumberDataCollection getEnumberData()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEnumberDataCollection)get("EnumberData");
    }
    /**
     * Object: ָ������ 's �������� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEdateDataCollection getEdateData()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEdateDataCollection)get("EdateData");
    }
    /**
     * Object: ָ������ 's ��Ʒ�������� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEproductTypeCollection getEproductType()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEproductTypeCollection)get("EproductType");
    }
    /**
     * Object: ָ������ 's ������λ���� property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbaseUnitCollection getEbaseUnit()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbaseUnitCollection)get("EbaseUnit");
    }
    /**
     * Object: ָ������ 's ¥������ property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbuildNumberCollection getEbuildNumber()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataEbuildNumberCollection)get("EbuildNumber");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8D2BA1E8");
    }
}