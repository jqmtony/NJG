package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostAccountPriceIndexInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCostAccountPriceIndexInfo()
    {
        this("id");
    }
    protected AbstractCostAccountPriceIndexInfo(String pkField)
    {
        super(pkField);
        put("Entrys", new com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryCollection());
    }
    /**
     * Object: 成本科目与造价指标关联表 's 指标类别 property 
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
     * Object: 成本科目与造价指标关联表 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:成本科目与造价指标关联表's 描述property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object: 成本科目与造价指标关联表 's 配置分录 property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexEntryCollection)get("Entrys");
    }
    /**
     * Object: 成本科目与造价指标关联表 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F4C16702");
    }
}