package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractProgrammingTemplateInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTemplateInfo(String pkField)
    {
        super(pkField);
        put("Entires", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireCollection());
    }
    /**
     * Object: 投资规划模板 's 合约框架 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireCollection getEntires()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireCollection)get("Entires");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D383F146");
    }
}