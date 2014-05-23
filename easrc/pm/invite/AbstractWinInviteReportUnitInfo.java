package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWinInviteReportUnitInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWinInviteReportUnitInfo()
    {
        this("id");
    }
    protected AbstractWinInviteReportUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ͷ�굥λ 's null property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.WinInviteReportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:Ͷ�굥λ's ����property 
     */
    public String getQuality()
    {
        return getString("quality");
    }
    public void setQuality(String item)
    {
        setString("quality", item);
    }
    /**
     * Object:Ͷ�굥λ's ���ձ���property 
     */
    public java.math.BigDecimal getInviteAmount()
    {
        return getBigDecimal("inviteAmount");
    }
    public void setInviteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("inviteAmount", item);
    }
    /**
     * Object:Ͷ�굥λ's �����б�property 
     */
    public boolean isWin()
    {
        return getBoolean("win");
    }
    public void setWin(boolean item)
    {
        setBoolean("win", item);
    }
    /**
     * Object:Ͷ�굥λ's ��עproperty 
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
     * Object: Ͷ�굥λ 's Ͷ�굥λ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo getUnitName()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)get("unitName");
    }
    public void setUnitName(com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo item)
    {
        put("unitName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("441A2CD4");
    }
}