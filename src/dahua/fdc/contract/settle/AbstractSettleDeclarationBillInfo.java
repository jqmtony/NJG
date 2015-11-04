package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleDeclarationBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSettleDeclarationBillInfo()
    {
        this("id");
    }
    protected AbstractSettleDeclarationBillInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Collection());
        put("E3", new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Collection());
        put("E1", new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Collection());
    }
    /**
     * Object:结算申报单's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:结算申报单's 申报金额property 
     */
    public java.math.BigDecimal getDecAmount()
    {
        return getBigDecimal("decAmount");
    }
    public void setDecAmount(java.math.BigDecimal item)
    {
        setBigDecimal("decAmount", item);
    }
    /**
     * Object:结算申报单's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:结算申报单's 审定金额property 
     */
    public java.math.BigDecimal getApprovalAmount()
    {
        return getBigDecimal("approvalAmount");
    }
    public void setApprovalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("approvalAmount", item);
    }
    /**
     * Object:结算申报单's 实际施工人property 
     */
    public String getActualConstruction()
    {
        return getString("actualConstruction");
    }
    public void setActualConstruction(String item)
    {
        setString("actualConstruction", item);
    }
    /**
     * Object:结算申报单's 结算负责人property 
     */
    public String getSettleHead()
    {
        return getString("settleHead");
    }
    public void setSettleHead(String item)
    {
        setString("settleHead", item);
    }
    /**
     * Object:结算申报单's 电话号码property 
     */
    public String getLinkTel()
    {
        return getString("linkTel");
    }
    public void setLinkTel(String item)
    {
        setString("linkTel", item);
    }
    /**
     * Object:结算申报单's 开工时间property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:结算申报单's 竣工时间property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
    }
    /**
     * Object:结算申报单's 报送时间property 
     */
    public java.util.Date getSubTime()
    {
        return getDate("subTime");
    }
    public void setSubTime(java.util.Date item)
    {
        setDate("subTime", item);
    }
    /**
     * Object:结算申报单's 审价单位property 
     */
    public String getUnitPrice()
    {
        return getString("unitPrice");
    }
    public void setUnitPrice(String item)
    {
        setString("unitPrice", item);
    }
    /**
     * Object:结算申报单's 结算完成时间要求property 
     */
    public java.util.Date getJswcsjyq()
    {
        return getDate("jswcsjyq");
    }
    public void setJswcsjyq(java.util.Date item)
    {
        setDate("jswcsjyq", item);
    }
    /**
     * Object:结算申报单's 单据状态property 
     */
    public com.kingdee.eas.fdc.contract.settle.app.BillStateEnum getBillState()
    {
        return com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.getEnum(getInt("billState"));
    }
    public void setBillState(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum item)
    {
		if (item != null) {
        setInt("billState", item.getValue());
		}
    }
    /**
     * Object:结算申报单's 工作交界面/供货范围描述：property 
     */
    public String getGzjjmghfwms()
    {
        return getString("gzjjmghfwms");
    }
    public void setGzjjmghfwms(String item)
    {
        setString("gzjjmghfwms", item);
    }
    /**
     * Object:结算申报单's 资料复核意见：property 
     */
    public String getZlfhyj()
    {
        return getString("zlfhyj");
    }
    public void setZlfhyj(String item)
    {
        setString("zlfhyj", item);
    }
    /**
     * Object:结算申报单's 商品砼使用范围：property 
     */
    public String getSptsyfw()
    {
        return getString("sptsyfw");
    }
    public void setSptsyfw(String item)
    {
        setString("sptsyfw", item);
    }
    /**
     * Object:结算申报单's 甲供材料范围：property 
     */
    public String getJgclfw()
    {
        return getString("jgclfw");
    }
    public void setJgclfw(String item)
    {
        setString("jgclfw", item);
    }
    /**
     * Object:结算申报单's 违约处罚提示：property 
     */
    public String getWycfts()
    {
        return getString("wycfts");
    }
    public void setWycfts(String item)
    {
        setString("wycfts", item);
    }
    /**
     * Object:结算申报单's 水电费缴纳情况property 
     */
    public String getSdfjnqk()
    {
        return getString("sdfjnqk");
    }
    public void setSdfjnqk(String item)
    {
        setString("sdfjnqk", item);
    }
    /**
     * Object:结算申报单's 质量、安全、工期及其他说明：property 
     */
    public String getZlaqgq()
    {
        return getString("zlaqgq");
    }
    public void setZlaqgq(String item)
    {
        setString("zlaqgq", item);
    }
    /**
     * Object:结算申报单's 砌筑property 
     */
    public boolean isQizhu()
    {
        return getBoolean("qizhu");
    }
    public void setQizhu(boolean item)
    {
        setBoolean("qizhu", item);
    }
    /**
     * Object:结算申报单's 粉刷property 
     */
    public boolean isFenshua()
    {
        return getBoolean("fenshua");
    }
    public void setFenshua(boolean item)
    {
        setBoolean("fenshua", item);
    }
    /**
     * Object:结算申报单's 地面property 
     */
    public boolean isDimian()
    {
        return getBoolean("dimian");
    }
    public void setDimian(boolean item)
    {
        setBoolean("dimian", item);
    }
    /**
     * Object: 结算申报单 's 合同编号 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractNumber()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractNumber");
    }
    public void setContractNumber(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractNumber", item);
    }
    /**
     * Object: 结算申报单 's 第1个表体 property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Collection getE1()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Collection)get("E1");
    }
    /**
     * Object: 结算申报单 's 第2个表体 property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Collection getE2()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Collection)get("E2");
    }
    /**
     * Object: 结算申报单 's 第3个表体 property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Collection getE3()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Collection)get("E3");
    }
    /**
     * Object:结算申报单's 结算资料查验情况property 
     */
    public String getJszlcyqk()
    {
        return getString("jszlcyqk");
    }
    public void setJszlcyqk(String item)
    {
        setString("jszlcyqk", item);
    }
    /**
     * Object:结算申报单's 结算要点提示property 
     */
    public String getJsydti()
    {
        return getString("jsydti");
    }
    public void setJsydti(String item)
    {
        setString("jsydti", item);
    }
    /**
     * Object:结算申报单's 是否最新版本property 
     */
    public boolean isIsVersion()
    {
        return getBoolean("isVersion");
    }
    public void setIsVersion(boolean item)
    {
        setBoolean("isVersion", item);
    }
    /**
     * Object:结算申报单's 送审状态property 
     */
    public com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum getState()
    {
        return com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("19B1A7B4");
    }
}