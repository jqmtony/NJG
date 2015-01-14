package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteReportEntry2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteReportEntry2Info()
    {
        this("id");
    }
    protected AbstractInviteReportEntry2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标单位分录 's null property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:招标单位分录's 备注property 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    /**
     * Object: 招标单位分录 's 投标单位 property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo getEvaEnterprise()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)get("evaEnterprise");
    }
    public void setEvaEnterprise(com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo item)
    {
        put("evaEnterprise", item);
    }
    /**
     * Object: 招标单位分录 's 中标核备表 property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportInfo getSuccTable()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportInfo)get("succTable");
    }
    public void setSuccTable(com.kingdee.eas.port.pm.invite.WinInviteReportInfo item)
    {
        put("succTable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76421706");
    }
}