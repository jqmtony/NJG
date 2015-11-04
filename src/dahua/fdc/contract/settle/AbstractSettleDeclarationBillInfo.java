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
     * Object:�����걨��'s �Ƿ�����ƾ֤property 
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
     * Object:�����걨��'s �걨���property 
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
     * Object:�����걨��'s �汾��property 
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
     * Object:�����걨��'s �󶨽��property 
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
     * Object:�����걨��'s ʵ��ʩ����property 
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
     * Object:�����걨��'s ���㸺����property 
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
     * Object:�����걨��'s �绰����property 
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
     * Object:�����걨��'s ����ʱ��property 
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
     * Object:�����걨��'s ����ʱ��property 
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
     * Object:�����걨��'s ����ʱ��property 
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
     * Object:�����걨��'s ��۵�λproperty 
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
     * Object:�����걨��'s �������ʱ��Ҫ��property 
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
     * Object:�����걨��'s ����״̬property 
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
     * Object:�����걨��'s ����������/������Χ������property 
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
     * Object:�����걨��'s ���ϸ��������property 
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
     * Object:�����걨��'s ��Ʒ��ʹ�÷�Χ��property 
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
     * Object:�����걨��'s �׹����Ϸ�Χ��property 
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
     * Object:�����걨��'s ΥԼ������ʾ��property 
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
     * Object:�����걨��'s ˮ��ѽ������property 
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
     * Object:�����걨��'s ��������ȫ�����ڼ�����˵����property 
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
     * Object:�����걨��'s ����property 
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
     * Object:�����걨��'s ��ˢproperty 
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
     * Object:�����걨��'s ����property 
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
     * Object: �����걨�� 's ��ͬ��� property 
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
     * Object: �����걨�� 's ��1������ property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Collection getE1()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE1Collection)get("E1");
    }
    /**
     * Object: �����걨�� 's ��2������ property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Collection getE2()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE2Collection)get("E2");
    }
    /**
     * Object: �����걨�� 's ��3������ property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Collection getE3()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillE3Collection)get("E3");
    }
    /**
     * Object:�����걨��'s �������ϲ������property 
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
     * Object:�����걨��'s ����Ҫ����ʾproperty 
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
     * Object:�����걨��'s �Ƿ����°汾property 
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
     * Object:�����걨��'s ����״̬property 
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