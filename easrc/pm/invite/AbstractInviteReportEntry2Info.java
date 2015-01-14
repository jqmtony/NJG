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
     * Object: �б굥λ��¼ 's null property 
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
     * Object:�б굥λ��¼'s ��עproperty 
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
     * Object: �б굥λ��¼ 's Ͷ�굥λ property 
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
     * Object: �б굥λ��¼ 's �б�˱��� property 
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