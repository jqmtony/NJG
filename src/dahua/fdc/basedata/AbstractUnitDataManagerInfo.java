package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractUnitDataManagerInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractUnitDataManagerInfo()
    {
        this("id");
    }
    protected AbstractUnitDataManagerInfo(String pkField)
    {
        super(pkField);
        put("watcher", new com.kingdee.eas.fdc.basedata.WatcherCollection());
        put("selfDirector", new com.kingdee.eas.fdc.basedata.SelfDirectorCollection());
        put("taxManager", new com.kingdee.eas.fdc.basedata.TaxManagerCollection());
        put("rightManager", new com.kingdee.eas.fdc.basedata.RightManagerCollection());
        put("director", new com.kingdee.eas.fdc.basedata.DirectorCollection());
    }
    /**
     * Object: ��֯���ݹ��� 's ʵ�ʷ�����֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: ��֯���ݹ��� 's Ӫҵִ����Ϣ property 
     */
    public com.kingdee.eas.fdc.basedata.LicenceManagerInfo getLicenceManager()
    {
        return (com.kingdee.eas.fdc.basedata.LicenceManagerInfo)get("licenceManager");
    }
    public void setLicenceManager(com.kingdee.eas.fdc.basedata.LicenceManagerInfo item)
    {
        put("licenceManager", item);
    }
    /**
     * Object: ��֯���ݹ��� 's ˰��Ǽ���Ϣ property 
     */
    public com.kingdee.eas.fdc.basedata.TaxManagerCollection getTaxManager()
    {
        return (com.kingdee.eas.fdc.basedata.TaxManagerCollection)get("taxManager");
    }
    /**
     * Object:��֯���ݹ���'s ��֯��������property 
     */
    public String getUnitNumber()
    {
        return getUnitNumber((Locale)null);
    }
    public void setUnitNumber(String item)
    {
		setUnitNumber(item,(Locale)null);
    }
    public String getUnitNumber(Locale local)
    {
        return TypeConversionUtils.objToString(get("unitNumber", local));
    }
    public void setUnitNumber(String item, Locale local)
    {
        put("unitNumber", item, local);
    }
    /**
     * Object: ��֯���ݹ��� 's �ڲ�������Ϣ property 
     */
    public com.kingdee.eas.fdc.basedata.InnerManagerInfo getInnerManager()
    {
        return (com.kingdee.eas.fdc.basedata.InnerManagerInfo)get("innerManager");
    }
    public void setInnerManager(com.kingdee.eas.fdc.basedata.InnerManagerInfo item)
    {
        put("innerManager", item);
    }
    /**
     * Object:��֯���ݹ���'s ���������˻��˴���property 
     */
    public String getArtificialPerson()
    {
        return getArtificialPerson((Locale)null);
    }
    public void setArtificialPerson(String item)
    {
		setArtificialPerson(item,(Locale)null);
    }
    public String getArtificialPerson(Locale local)
    {
        return TypeConversionUtils.objToString(get("artificialPerson", local));
    }
    public void setArtificialPerson(String item, Locale local)
    {
        put("artificialPerson", item, local);
    }
    /**
     * Object:��֯���ݹ���'s ��һ�����˻�������property 
     */
    public String getDutyPerson()
    {
        return getDutyPerson((Locale)null);
    }
    public void setDutyPerson(String item)
    {
		setDutyPerson(item,(Locale)null);
    }
    public String getDutyPerson(Locale local)
    {
        return TypeConversionUtils.objToString(get("dutyPerson", local));
    }
    public void setDutyPerson(String item, Locale local)
    {
        put("dutyPerson", item, local);
    }
    /**
     * Object: ��֯���ݹ��� 's ���� property 
     */
    public com.kingdee.eas.fdc.basedata.DirectorCollection getDirector()
    {
        return (com.kingdee.eas.fdc.basedata.DirectorCollection)get("director");
    }
    /**
     * Object: ��֯���ݹ��� 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.SelfDirectorCollection getSelfDirector()
    {
        return (com.kingdee.eas.fdc.basedata.SelfDirectorCollection)get("selfDirector");
    }
    /**
     * Object: ��֯���ݹ��� 's ���� property 
     */
    public com.kingdee.eas.fdc.basedata.WatcherCollection getWatcher()
    {
        return (com.kingdee.eas.fdc.basedata.WatcherCollection)get("watcher");
    }
    /**
     * Object: ��֯���ݹ��� 's Ȩ�������Ϣ property 
     */
    public com.kingdee.eas.fdc.basedata.RightManagerCollection getRightManager()
    {
        return (com.kingdee.eas.fdc.basedata.RightManagerCollection)get("rightManager");
    }
    /**
     * Object:��֯���ݹ���'s �ļ�1property 
     */
    public byte[] getFile1()
    {
        return (byte[])get("file1");
    }
    public void setFile1(byte[] item)
    {
        put("file1", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����1property 
     */
    public String getFileType1()
    {
        return getString("fileType1");
    }
    public void setFileType1(String item)
    {
        setString("fileType1", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�2property 
     */
    public byte[] getFile2()
    {
        return (byte[])get("file2");
    }
    public void setFile2(byte[] item)
    {
        put("file2", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����2property 
     */
    public String getFileType2()
    {
        return getString("fileType2");
    }
    public void setFileType2(String item)
    {
        setString("fileType2", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�3property 
     */
    public byte[] getFile3()
    {
        return (byte[])get("file3");
    }
    public void setFile3(byte[] item)
    {
        put("file3", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����3property 
     */
    public String getFileType3()
    {
        return getString("fileType3");
    }
    public void setFileType3(String item)
    {
        setString("fileType3", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�4property 
     */
    public byte[] getFile4()
    {
        return (byte[])get("file4");
    }
    public void setFile4(byte[] item)
    {
        put("file4", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����4property 
     */
    public String getFileType4()
    {
        return getString("fileType4");
    }
    public void setFileType4(String item)
    {
        setString("fileType4", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�5property 
     */
    public byte[] getFile5()
    {
        return (byte[])get("file5");
    }
    public void setFile5(byte[] item)
    {
        put("file5", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����5property 
     */
    public String getFileType5()
    {
        return getString("fileType5");
    }
    public void setFileType5(String item)
    {
        setString("fileType5", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�6property 
     */
    public byte[] getFile6()
    {
        return (byte[])get("file6");
    }
    public void setFile6(byte[] item)
    {
        put("file6", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����6property 
     */
    public String getFileType6()
    {
        return getString("fileType6");
    }
    public void setFileType6(String item)
    {
        setString("fileType6", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�7property 
     */
    public byte[] getFile7()
    {
        return (byte[])get("file7");
    }
    public void setFile7(byte[] item)
    {
        put("file7", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����7property 
     */
    public String getFileType7()
    {
        return getString("fileType7");
    }
    public void setFileType7(String item)
    {
        setString("fileType7", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�8property 
     */
    public byte[] getFile8()
    {
        return (byte[])get("file8");
    }
    public void setFile8(byte[] item)
    {
        put("file8", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����8property 
     */
    public String getFileType8()
    {
        return getString("fileType8");
    }
    public void setFileType8(String item)
    {
        setString("fileType8", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�9property 
     */
    public byte[] getFile9()
    {
        return (byte[])get("file9");
    }
    public void setFile9(byte[] item)
    {
        put("file9", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����9property 
     */
    public String getFileType9()
    {
        return getString("fileType9");
    }
    public void setFileType9(String item)
    {
        setString("fileType9", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�10property 
     */
    public byte[] getFile10()
    {
        return (byte[])get("file10");
    }
    public void setFile10(byte[] item)
    {
        put("file10", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����10property 
     */
    public String getFileType10()
    {
        return getString("fileType10");
    }
    public void setFileType10(String item)
    {
        setString("fileType10", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�11property 
     */
    public byte[] getFile11()
    {
        return (byte[])get("file11");
    }
    public void setFile11(byte[] item)
    {
        put("file11", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����11property 
     */
    public String getFileType11()
    {
        return getString("fileType11");
    }
    public void setFileType11(String item)
    {
        setString("fileType11", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�12property 
     */
    public byte[] getFile12()
    {
        return (byte[])get("file12");
    }
    public void setFile12(byte[] item)
    {
        put("file12", item);
    }
    /**
     * Object:��֯���ݹ���'s �ļ�����12property 
     */
    public String getFileType12()
    {
        return getString("fileType12");
    }
    public void setFileType12(String item)
    {
        setString("fileType12", item);
    }
    /**
     * Object:��֯���ݹ���'s ��ַproperty 
     */
    public String getAddress()
    {
        return getAddress((Locale)null);
    }
    public void setAddress(String item)
    {
		setAddress(item,(Locale)null);
    }
    public String getAddress(Locale local)
    {
        return TypeConversionUtils.objToString(get("address", local));
    }
    public void setAddress(String item, Locale local)
    {
        put("address", item, local);
    }
    /**
     * Object:��֯���ݹ���'s �ʱ�property 
     */
    public String getPostalCode()
    {
        return getString("postalCode");
    }
    public void setPostalCode(String item)
    {
        setString("postalCode", item);
    }
    /**
     * Object:��֯���ݹ���'s �绰property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:��֯���ݹ���'s ����property 
     */
    public String getFax()
    {
        return getString("fax");
    }
    public void setFax(String item)
    {
        setString("fax", item);
    }
    /**
     * Object:��֯���ݹ���'s �����ʼ�property 
     */
    public String getEip()
    {
        return getString("eip");
    }
    public void setEip(String item)
    {
        setString("eip", item);
    }
    /**
     * Object:��֯���ݹ���'s ��ϵ��property 
     */
    public String getLinkMan()
    {
        return getLinkMan((Locale)null);
    }
    public void setLinkMan(String item)
    {
		setLinkMan(item,(Locale)null);
    }
    public String getLinkMan(Locale local)
    {
        return TypeConversionUtils.objToString(get("linkMan", local));
    }
    public void setLinkMan(String item, Locale local)
    {
        put("linkMan", item, local);
    }
    /**
     * Object:��֯���ݹ���'s ��ϵ�ֻ�property 
     */
    public String getLinkMobile()
    {
        return getString("linkMobile");
    }
    public void setLinkMobile(String item)
    {
        setString("linkMobile", item);
    }
    /**
     * Object:��֯���ݹ���'s ֧���ļ�property 
     */
    public byte[] getPayFile()
    {
        return (byte[])get("payFile");
    }
    public void setPayFile(byte[] item)
    {
        put("payFile", item);
    }
    /**
     * Object:��֯���ݹ���'s ֧���ļ�����property 
     */
    public String getPayFileType()
    {
        return getString("payFileType");
    }
    public void setPayFileType(String item)
    {
        setString("payFileType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8E92FF4D");
    }
}