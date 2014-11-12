package com.kingdee.eas.fi.cas;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentBillInfo extends com.kingdee.eas.fi.cas.RecPayBillInfo implements Serializable 
{
    public AbstractPaymentBillInfo()
    {
        this("id");
    }
    protected AbstractPaymentBillInfo(String pkField)
    {
        super(pkField);
        put("fdcPayVoucherEntry", new com.kingdee.eas.fdc.finance.FDCPayVoucherEntryCollection());
        put("fdcCostVoucherEntry", new com.kingdee.eas.fdc.finance.FDCCostVoucherEntryCollection());
        put("assItems", new com.kingdee.eas.fi.cas.AssItemsForCashPayCollection());
        put("entries", new com.kingdee.eas.fi.cas.PaymentBillEntryCollection());
    }
    /**
     * Object:���'s �������ͣ��������ã�����ɾ����property 
     */
    public com.kingdee.eas.fi.cas.RecPayBillTypeEnum getPayType()
    {
        return com.kingdee.eas.fi.cas.RecPayBillTypeEnum.getEnum(getInt("payType"));
    }
    public void setPayType(com.kingdee.eas.fi.cas.RecPayBillTypeEnum item)
    {
		if (item != null) {
        setInt("payType", item.getValue());
		}
    }
    /**
     * Object:���'s ʵ�����ϼ�property 
     */
    public java.math.BigDecimal getActPayAmt()
    {
        return getBigDecimal("actPayAmt");
    }
    public void setActPayAmt(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmt", item);
    }
    /**
     * Object:���'s ʵ������ۼƺ���property 
     */
    public java.math.BigDecimal getActPayAmtVc()
    {
        return getBigDecimal("actPayAmtVc");
    }
    public void setActPayAmtVc(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmtVc", item);
    }
    /**
     * Object:���'s ʵ����λ�ҽ��ϼ�property 
     */
    public java.math.BigDecimal getActPayLocAmt()
    {
        return getBigDecimal("actPayLocAmt");
    }
    public void setActPayLocAmt(java.math.BigDecimal item)
    {
        setBigDecimal("actPayLocAmt", item);
    }
    /**
     * Object:���'s ʵ����λ�ҽ���ۼƺ���property 
     */
    public java.math.BigDecimal getActPayLocAmtVc()
    {
        return getBigDecimal("actPayLocAmtVc");
    }
    public void setActPayLocAmtVc(java.math.BigDecimal item)
    {
        setBigDecimal("actPayLocAmtVc", item);
    }
    /**
     * Object: ��� 's �������� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getPayerBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("payerBank");
    }
    public void setPayerBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("payerBank", item);
    }
    /**
     * Object: ��� 's �����˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPayerAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("payerAccountBank");
    }
    public void setPayerAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("payerAccountBank", item);
    }
    /**
     * Object: ��� 's �����Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getPayerAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("payerAccount");
    }
    public void setPayerAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("payerAccount", item);
    }
    /**
     * Object: ��� 's ���������� property 
     */
    public com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo getPayeeType()
    {
        return (com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo)get("payeeType");
    }
    public void setPayeeType(com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo item)
    {
        put("payeeType", item);
    }
    /**
     * Object:���'s ������IDproperty 
     */
    public String getPayeeID()
    {
        return getString("payeeID");
    }
    public void setPayeeID(String item)
    {
        setString("payeeID", item);
    }
    /**
     * Object:���'s ����������property 
     */
    public String getPayeeNumber()
    {
        return getString("payeeNumber");
    }
    public void setPayeeNumber(String item)
    {
        setString("payeeNumber", item);
    }
    /**
     * Object:���'s ����������property 
     */
    public String getPayeeName()
    {
        return getString("payeeName");
    }
    public void setPayeeName(String item)
    {
        setString("payeeName", item);
    }
    /**
     * Object:���'s �տ�����property 
     */
    public String getPayeeBank()
    {
        return getString("payeeBank");
    }
    public void setPayeeBank(String item)
    {
        setString("payeeBank", item);
    }
    /**
     * Object:���'s �տ��˺�property 
     */
    public String getPayeeAccountBank()
    {
        return getString("payeeAccountBank");
    }
    public void setPayeeAccountBank(String item)
    {
        setString("payeeAccountBank", item);
    }
    /**
     * Object: ��� 's �տ���� property 
     */
    public com.kingdee.eas.fm.be.OpenAreaInfo getPayeeArea()
    {
        return (com.kingdee.eas.fm.be.OpenAreaInfo)get("payeeArea");
    }
    public void setPayeeArea(com.kingdee.eas.fm.be.OpenAreaInfo item)
    {
        put("payeeArea", item);
    }
    /**
     * Object: ��� 's �����¼ property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillEntryCollection)get("entries");
    }
    /**
     * Object:���'s ��������property 
     */
    public java.util.Date getPayDate()
    {
        return getDate("payDate");
    }
    public void setPayDate(java.util.Date item)
    {
        setDate("payDate", item);
    }
    /**
     * Object:���'s �Ƿ�Ʊproperty 
     */
    public boolean isIsRelateCheque()
    {
        return getBoolean("isRelateCheque");
    }
    public void setIsRelateCheque(boolean item)
    {
        setBoolean("isRelateCheque", item);
    }
    /**
     * Object: ��� 's ����֧Ʊ��id property 
     */
    public com.kingdee.eas.fm.nt.ChequeInfo getCheque()
    {
        return (com.kingdee.eas.fm.nt.ChequeInfo)get("cheque");
    }
    public void setCheque(com.kingdee.eas.fm.nt.ChequeInfo item)
    {
        put("cheque", item);
    }
    /**
     * Object: ��� 's �ÿ�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepartment");
    }
    public void setUseDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepartment", item);
    }
    /**
     * Object:���'s �����̶�property 
     */
    public com.kingdee.eas.fi.cas.UrgentDegreeEnum getUrgentDegree()
    {
        return com.kingdee.eas.fi.cas.UrgentDegreeEnum.getEnum(getString("urgentDegree"));
    }
    public void setUrgentDegree(com.kingdee.eas.fi.cas.UrgentDegreeEnum item)
    {
		if (item != null) {
        setString("urgentDegree", item.getValue());
		}
    }
    /**
     * Object:���'s ���ڼƻ�����property 
     */
    public java.math.BigDecimal getCurPlannedPayment()
    {
        return getBigDecimal("curPlannedPayment");
    }
    public void setCurPlannedPayment(java.math.BigDecimal item)
    {
        setBigDecimal("curPlannedPayment", item);
    }
    /**
     * Object:���'s ����Ƿ����property 
     */
    public java.math.BigDecimal getCurBackPay()
    {
        return getBigDecimal("curBackPay");
    }
    public void setCurBackPay(java.math.BigDecimal item)
    {
        setBigDecimal("curBackPay", item);
    }
    /**
     * Object:���'s ����ƻ�property 
     */
    public java.math.BigDecimal getPaymentPlan()
    {
        return getBigDecimal("paymentPlan");
    }
    public void setPaymentPlan(java.math.BigDecimal item)
    {
        setBigDecimal("paymentPlan", item);
    }
    /**
     * Object:���'s ��������%property 
     */
    public java.math.BigDecimal getCurReqPercent()
    {
        return getBigDecimal("curReqPercent");
    }
    public void setCurReqPercent(java.math.BigDecimal item)
    {
        setBigDecimal("curReqPercent", item);
    }
    /**
     * Object:���'s �ۼ�����%property 
     */
    public java.math.BigDecimal getAllReqPercent()
    {
        return getBigDecimal("allReqPercent");
    }
    public void setAllReqPercent(java.math.BigDecimal item)
    {
        setBigDecimal("allReqPercent", item);
    }
    /**
     * Object:���'s �������property 
     */
    public java.math.BigDecimal getImageSchedule()
    {
        return getBigDecimal("imageSchedule");
    }
    public void setImageSchedule(java.math.BigDecimal item)
    {
        setBigDecimal("imageSchedule", item);
    }
    /**
     * Object: ��� 's �ڲ��տ��˻� property 
     */
    public com.kingdee.eas.fm.fs.InnerAccountInfo getOppInnerAcct()
    {
        return (com.kingdee.eas.fm.fs.InnerAccountInfo)get("oppInnerAcct");
    }
    public void setOppInnerAcct(com.kingdee.eas.fm.fs.InnerAccountInfo item)
    {
        put("oppInnerAcct", item);
    }
    /**
     * Object:���'s �Ƿ�Ӽ�property 
     */
    public com.kingdee.eas.fi.cas.IsMergencyEnum getIsEmergency()
    {
        return com.kingdee.eas.fi.cas.IsMergencyEnum.getEnum(getInt("isEmergency"));
    }
    public void setIsEmergency(com.kingdee.eas.fi.cas.IsMergencyEnum item)
    {
		if (item != null) {
        setInt("isEmergency", item.getValue());
		}
    }
    /**
     * Object:���'s Ԥ���׼���property 
     */
    public java.math.BigDecimal getBgAmount()
    {
        return getBigDecimal("bgAmount");
    }
    public void setBgAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bgAmount", item);
    }
    /**
     * Object: ��� 's �������� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillTypeInfo getPayBillType()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillTypeInfo)get("payBillType");
    }
    public void setPayBillType(com.kingdee.eas.fi.cas.PaymentBillTypeInfo item)
    {
        put("payBillType", item);
    }
    /**
     * Object:���'s �Ƿ��ѵǼ�Ӧ��Ʊ��property 
     */
    public boolean isIsRelatePayBook()
    {
        return getBoolean("isRelatePayBook");
    }
    public void setIsRelatePayBook(boolean item)
    {
        setBoolean("isRelatePayBook", item);
    }
    /**
     * Object: ��� 's �����տ��˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPayeeAccountBankO()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("payeeAccountBankO");
    }
    public void setPayeeAccountBankO(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("payeeAccountBankO", item);
    }
    /**
     * Object:���'s �Ƿ����property 
     */
    public com.kingdee.eas.fi.cas.DifPlaceEnum getIsDifferPlace()
    {
        return com.kingdee.eas.fi.cas.DifPlaceEnum.getEnum(getInt("isDifferPlace"));
    }
    public void setIsDifferPlace(com.kingdee.eas.fi.cas.DifPlaceEnum item)
    {
		if (item != null) {
        setInt("isDifferPlace", item.getValue());
		}
    }
    /**
     * Object:���'s �Ƿ񱻴۸ı�־λproperty 
     */
    public String getEditFlag()
    {
        return getString("editFlag");
    }
    public void setEditFlag(String item)
    {
        setString("editFlag", item);
    }
    /**
     * Object:���'s �Ƿ��ύ������property 
     */
    public boolean isIsCommittoBe()
    {
        return getBoolean("isCommittoBe");
    }
    public void setIsCommittoBe(boolean item)
    {
        setBoolean("isCommittoBe", item);
    }
    /**
     * Object:���'s ��;property 
     */
    public String getUsage()
    {
        return getString("usage");
    }
    public void setUsage(String item)
    {
        setString("usage", item);
    }
    /**
     * Object:���'s �տʡproperty 
     */
    public String getRecProvince()
    {
        return getString("recProvince");
    }
    public void setRecProvince(String item)
    {
        setString("recProvince", item);
    }
    /**
     * Object:���'s �տ����property 
     */
    public String getRecCity()
    {
        return getString("recCity");
    }
    public void setRecCity(String item)
    {
        setString("recCity", item);
    }
    /**
     * Object:���'s ���и��״̬property 
     */
    public com.kingdee.eas.fm.be.BankPayingBillStateEnum getBankPayState()
    {
        return com.kingdee.eas.fm.be.BankPayingBillStateEnum.getEnum(getInt("bankPayState"));
    }
    public void setBankPayState(com.kingdee.eas.fm.be.BankPayingBillStateEnum item)
    {
		if (item != null) {
        setInt("bankPayState", item.getValue());
		}
    }
    /**
     * Object:���'s ���з�����Ϣproperty 
     */
    public String getBankReturnInfo()
    {
        return getString("bankReturnInfo");
    }
    public void setBankReturnInfo(String item)
    {
        setString("bankReturnInfo", item);
    }
    /**
     * Object: ��� 's �Է���Ŀ������Ŀ property 
     */
    public com.kingdee.eas.fi.cas.AssItemsForCashPayCollection getAssItems()
    {
        return (com.kingdee.eas.fi.cas.AssItemsForCashPayCollection)get("assItems");
    }
    /**
     * Object: ��� 's �����˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getAgentPayCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("agentPayCompany");
    }
    public void setAgentPayCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("agentPayCompany", item);
    }
    /**
     * Object:���'s �������ʵ�ʸ��IDproperty 
     */
    public String getSrcAgenPaymentBillID()
    {
        return getString("srcAgenPaymentBillID");
    }
    public void setSrcAgenPaymentBillID(String item)
    {
        setString("srcAgenPaymentBillID", item);
    }
    /**
     * Object:���'s �����IDproperty 
     */
    public String getAgentPaymentBillID()
    {
        return getString("agentPaymentBillID");
    }
    public void setAgentPaymentBillID(String item)
    {
        setString("agentPaymentBillID", item);
    }
    /**
     * Object:���'s �������property 
     */
    public com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum getPaymentBillType()
    {
        return com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum.getEnum(getInt("paymentBillType"));
    }
    public void setPaymentBillType(com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum item)
    {
		if (item != null) {
        setInt("paymentBillType", item.getValue());
		}
    }
    /**
     * Object: ��� 's ԭ���������� property 
     */
    public com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo getLastPayeeType()
    {
        return (com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo)get("lastPayeeType");
    }
    public void setLastPayeeType(com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo item)
    {
        put("lastPayeeType", item);
    }
    /**
     * Object:���'s ԭ���������property 
     */
    public String getLastPayeeNumber()
    {
        return getString("lastPayeeNumber");
    }
    public void setLastPayeeNumber(String item)
    {
        setString("lastPayeeNumber", item);
    }
    /**
     * Object:���'s ԭ����������property 
     */
    public String getLastPayeeName()
    {
        return getString("lastPayeeName");
    }
    public void setLastPayeeName(String item)
    {
        setString("lastPayeeName", item);
    }
    /**
     * Object:���'s ԭ������IDproperty 
     */
    public String getLastPayeeID()
    {
        return getString("lastPayeeID");
    }
    public void setLastPayeeID(String item)
    {
        setString("lastPayeeID", item);
    }
    /**
     * Object:���'s �Ƿ���Ҫ֧��property 
     */
    public boolean isIsNeedPay()
    {
        return getBoolean("isNeedPay");
    }
    public void setIsNeedPay(boolean item)
    {
        setBoolean("isNeedPay", item);
    }
    /**
     * Object: ��� 's �տ���� property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getFRecCountry()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("FRecCountry");
    }
    public void setFRecCountry(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("FRecCountry", item);
    }
    /**
     * Object:���'s �Ƿ�д�������property 
     */
    public boolean isIsReverseLockAmount()
    {
        return getBoolean("isReverseLockAmount");
    }
    public void setIsReverseLockAmount(boolean item)
    {
        setBoolean("isReverseLockAmount", item);
    }
    /**
     * Object:���'s �ϻ�����¼Idproperty 
     */
    public String getTransUpBillEntryId()
    {
        return getString("transUpBillEntryId");
    }
    public void setTransUpBillEntryId(String item)
    {
        setString("transUpBillEntryId", item);
    }
    /**
     * Object:���'s ͬ�п���property 
     */
    public com.kingdee.eas.fi.cas.DifBankEnum getIsDifBank()
    {
        return com.kingdee.eas.fi.cas.DifBankEnum.getEnum(getInt("isDifBank"));
    }
    public void setIsDifBank(com.kingdee.eas.fi.cas.DifBankEnum item)
    {
		if (item != null) {
        setInt("isDifBank", item.getValue());
		}
    }
    /**
     * Object: ��� 's ����ƻ���Ŀ property 
     */
    public com.kingdee.eas.fm.fpl.FpItemInfo getOppFpItem()
    {
        return (com.kingdee.eas.fm.fpl.FpItemInfo)get("oppFpItem");
    }
    public void setOppFpItem(com.kingdee.eas.fm.fpl.FpItemInfo item)
    {
        put("oppFpItem", item);
    }
    /**
     * Object:���'s ����Ԥ����Ŀ����property 
     */
    public String getOppBgItemName()
    {
        return getString("oppBgItemName");
    }
    public void setOppBgItemName(String item)
    {
        setString("oppBgItemName", item);
    }
    /**
     * Object:���'s ����Ԥ����Ŀ����property 
     */
    public String getOppBgItemNumber()
    {
        return getString("oppBgItemNumber");
    }
    public void setOppBgItemNumber(String item)
    {
        setString("oppBgItemNumber", item);
    }
    /**
     * Object:���'s ����Ԥ����ĿIdproperty 
     */
    public String getOppBgItemId()
    {
        return getString("oppBgItemId");
    }
    public void setOppBgItemId(String item)
    {
        setString("oppBgItemId", item);
    }
    /**
     * Object: ��� 's ����ʵ���˺� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getActRecAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("actRecAccountBank");
    }
    public void setActRecAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("actRecAccountBank", item);
    }
    /**
     * Object: ��� 's ����ڲ��˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPayerInAcctID()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("payerInAcctID");
    }
    public void setPayerInAcctID(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("payerInAcctID", item);
    }
    /**
     * Object:���'s �ύ������ʱ��property 
     */
    public java.util.Date getCommitBeTime()
    {
        return getDate("commitBeTime");
    }
    public void setCommitBeTime(java.util.Date item)
    {
        setDate("commitBeTime", item);
    }
    /**
     * Object:���'s ���������ѷ�ʽproperty 
     */
    public com.kingdee.eas.fi.cas.SettleFeeTypeEnum getSettleFeeType()
    {
        return com.kingdee.eas.fi.cas.SettleFeeTypeEnum.getEnum(getInt("settleFeeType"));
    }
    public void setSettleFeeType(com.kingdee.eas.fi.cas.SettleFeeTypeEnum item)
    {
		if (item != null) {
        setInt("settleFeeType", item.getValue());
		}
    }
    /**
     * Object:���'s ��������ʱ��property 
     */
    public java.sql.Timestamp getExpectDealTime()
    {
        return getTimestamp("expectDealTime");
    }
    public void setExpectDealTime(java.sql.Timestamp item)
    {
        setTimestamp("expectDealTime", item);
    }
    /**
     * Object:���'s ǿ�����property 
     */
    public boolean isIsLanding()
    {
        return getBoolean("isLanding");
    }
    public void setIsLanding(boolean item)
    {
        setBoolean("isLanding", item);
    }
    /**
     * Object:���'s SWIFT Codeproperty 
     */
    public String getSwiftcode()
    {
        return getString("swiftcode");
    }
    public void setSwiftcode(String item)
    {
        setString("swiftcode", item);
    }
    /**
     * Object: ��� 's ���ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.basedata.assistant.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.basedata.assistant.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    /**
     * Object:���'s ��ӡ����property 
     */
    public int getPrintCount()
    {
        return getInt("printCount");
    }
    public void setPrintCount(int item)
    {
        setInt("printCount", item);
    }
    /**
     * Object:���'s �տ���ʵ��property 
     */
    public String getBankAcctName()
    {
        return getString("bankAcctName");
    }
    public void setBankAcctName(String item)
    {
        setString("bankAcctName", item);
    }
    /**
     * Object:���'s �տ����к�property 
     */
    public String getBankNumber()
    {
        return getString("bankNumber");
    }
    public void setBankNumber(String item)
    {
        setString("bankNumber", item);
    }
    /**
     * Object: ��� 's ��������(���ز�) property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getFdcPayType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("fdcPayType");
    }
    public void setFdcPayType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("fdcPayType", item);
    }
    /**
     * Object: ��� 's �տλȫ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getFdcPayeeName()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("fdcPayeeName");
    }
    public void setFdcPayeeName(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("fdcPayeeName", item);
    }
    /**
     * Object:���'s �տ�������property 
     */
    public com.kingdee.eas.fi.cas.FdcPayeeTypeEnum getFdcPayeeType()
    {
        return com.kingdee.eas.fi.cas.FdcPayeeTypeEnum.getEnum(getString("fdcPayeeType"));
    }
    public void setFdcPayeeType(com.kingdee.eas.fi.cas.FdcPayeeTypeEnum item)
    {
		if (item != null) {
        setString("fdcPayeeType", item.getValue());
		}
    }
    /**
     * Object: ��� 's Ӧ�ۿ��� property 
     */
    public com.kingdee.eas.fdc.basedata.DeductTypeInfo getDeductMoneyType()
    {
        return (com.kingdee.eas.fdc.basedata.DeductTypeInfo)get("deductMoneyType");
    }
    public void setDeductMoneyType(com.kingdee.eas.fdc.basedata.DeductTypeInfo item)
    {
        put("deductMoneyType", item);
    }
    /**
     * Object:���'s �������property 
     */
    public java.math.BigDecimal getLatestPrice()
    {
        return getBigDecimal("latestPrice");
    }
    public void setLatestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("latestPrice", item);
    }
    /**
     * Object:���'s ���ӱ��ڷ������̿�property 
     */
    public java.math.BigDecimal getAddProjectAmt()
    {
        return getBigDecimal("addProjectAmt");
    }
    public void setAddProjectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("addProjectAmt", item);
    }
    /**
     * Object:���'s ���ǩ֤���property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:���'s �����뵥�Ѹ����property 
     */
    public java.math.BigDecimal getPayedAmt()
    {
        return getBigDecimal("payedAmt");
    }
    public void setPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmt", item);
    }
    /**
     * Object:���'s Ӧ�ۼ׹����Ͽ���ڷ�����property 
     */
    public java.math.BigDecimal getPayPartAMatlAmt()
    {
        return getBigDecimal("payPartAMatlAmt");
    }
    public void setPayPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlAmt", item);
    }
    /**
     * Object:���'s �������property 
     */
    public int getPayTimes()
    {
        return getInt("payTimes");
    }
    public void setPayTimes(int item)
    {
        setInt("payTimes", item);
    }
    /**
     * Object:���'s ��ͬ�ڹ��̿���ڷ�����property 
     */
    public java.math.BigDecimal getProjectPriceInContract()
    {
        return getBigDecimal("projectPriceInContract");
    }
    public void setProjectPriceInContract(java.math.BigDecimal item)
    {
        setBigDecimal("projectPriceInContract", item);
    }
    /**
     * Object:���'s ���ȿ�property 
     */
    public java.math.BigDecimal getScheduleAmt()
    {
        return getBigDecimal("scheduleAmt");
    }
    public void setScheduleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("scheduleAmt", item);
    }
    /**
     * Object:���'s ������property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object:���'s ʵ����ڷ�����property 
     */
    public java.math.BigDecimal getCurPaid()
    {
        return getBigDecimal("curPaid");
    }
    public void setCurPaid(java.math.BigDecimal item)
    {
        setBigDecimal("curPaid", item);
    }
    /**
     * Object:���'s ��ͬ�ڹ����ۼ�����property 
     */
    public java.math.BigDecimal getPrjAllReqAmt()
    {
        return getBigDecimal("prjAllReqAmt");
    }
    public void setPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("prjAllReqAmt", item);
    }
    /**
     * Object:���'s ���ӹ��̿��ۼ�����property 
     */
    public java.math.BigDecimal getAddPrjAllReqAmt()
    {
        return getBigDecimal("addPrjAllReqAmt");
    }
    public void setAddPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("addPrjAllReqAmt", item);
    }
    /**
     * Object:���'s �׹����ۼ������property 
     */
    public java.math.BigDecimal getPayPartAMatlAllReqAmt()
    {
        return getBigDecimal("payPartAMatlAllReqAmt");
    }
    public void setPayPartAMatlAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlAllReqAmt", item);
    }
    /**
     * Object:���'s ��ͬ�ڹ��̿������ۼ�����property 
     */
    public java.math.BigDecimal getLstPrjAllReqAmt()
    {
        return getBigDecimal("lstPrjAllReqAmt");
    }
    public void setLstPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstPrjAllReqAmt", item);
    }
    /**
     * Object:���'s ���ӹ��̿������ۼ�property 
     */
    public java.math.BigDecimal getLstAddPrjAllReqAmt()
    {
        return getBigDecimal("lstAddPrjAllReqAmt");
    }
    public void setLstAddPrjAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAddPrjAllReqAmt", item);
    }
    /**
     * Object:���'s �׹��������ۼ�����property 
     */
    public java.math.BigDecimal getLstAMatlAllReqAmt()
    {
        return getBigDecimal("lstAMatlAllReqAmt");
    }
    public void setLstAMatlAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAMatlAllReqAmt", item);
    }
    /**
     * Object: ��� 's ʵ���տλȫ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getActFdcPayeeName()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("actFdcPayeeName");
    }
    public void setActFdcPayeeName(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("actFdcPayeeName", item);
    }
    /**
     * Object:���'s �������뵥���ݱ��property 
     */
    public String getFdcPayReqNumber()
    {
        return getString("fdcPayReqNumber");
    }
    public void setFdcPayReqNumber(String item)
    {
        setString("fdcPayReqNumber", item);
    }
    /**
     * Object:���'s ���ں�ͬ���ۼ�ʵ��property 
     */
    public java.math.BigDecimal getLstPrjAllPaidAmt()
    {
        return getBigDecimal("lstPrjAllPaidAmt");
    }
    public void setLstPrjAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstPrjAllPaidAmt", item);
    }
    /**
     * Object:���'s ���ӹ��̿������ۼ�ʵ��property 
     */
    public java.math.BigDecimal getLstAddPrjAllPaidAmt()
    {
        return getBigDecimal("lstAddPrjAllPaidAmt");
    }
    public void setLstAddPrjAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAddPrjAllPaidAmt", item);
    }
    /**
     * Object:���'s �׹��������ۼ�ʵ��property 
     */
    public java.math.BigDecimal getLstAMatlAllPaidAmt()
    {
        return getBigDecimal("lstAMatlAllPaidAmt");
    }
    public void setLstAMatlAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("lstAMatlAllPaidAmt", item);
    }
    /**
     * Object:���'s �������뵥property 
     */
    public String getFdcPayReqID()
    {
        return getString("fdcPayReqID");
    }
    public void setFdcPayReqID(String item)
    {
        setString("fdcPayReqID", item);
    }
    /**
     * Object: ��� 's ���ز��ɱ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCCostVoucherEntryCollection getFdcCostVoucherEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCCostVoucherEntryCollection)get("fdcCostVoucherEntry");
    }
    /**
     * Object: ��� 's ���ز������¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCPayVoucherEntryCollection getFdcPayVoucherEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCPayVoucherEntryCollection)get("fdcPayVoucherEntry");
    }
    /**
     * Object: ��� 's ��ͬ�������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBase()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBase");
    }
    public void setContractBase(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBase", item);
    }
    /**
     * Object: ��� 's ������̸�������� property 
     */
    public com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo getPrjPayEntry()
    {
        return (com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo)get("prjPayEntry");
    }
    public void setPrjPayEntry(com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo item)
    {
        put("prjPayEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("40284E81");
    }
}