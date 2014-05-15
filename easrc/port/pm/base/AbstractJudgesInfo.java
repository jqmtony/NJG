package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgesInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractJudgesInfo()
    {
        this("id");
    }
    protected AbstractJudgesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ר�ҿ� 's ��� property 
     */
    public com.kingdee.eas.port.pm.base.JudgesTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.JudgesTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.JudgesTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object: ר�ҿ� 's ���� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getJuName()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("juName");
    }
    public void setJuName(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("juName", item);
    }
    /**
     * Object:ר�ҿ�'s �Ա�property 
     */
    public com.kingdee.eas.basedata.person.Genders getSex()
    {
        return com.kingdee.eas.basedata.person.Genders.getEnum(getInt("sex"));
    }
    public void setSex(com.kingdee.eas.basedata.person.Genders item)
    {
		if (item != null) {
        setInt("sex", item.getValue());
		}
    }
    /**
     * Object:ר�ҿ�'s ��������property 
     */
    public java.util.Date getBirthday()
    {
        return getDate("birthday");
    }
    public void setBirthday(java.util.Date item)
    {
        setDate("birthday", item);
    }
    /**
     * Object: ר�ҿ� 's ѧ�� property 
     */
    public com.kingdee.eas.basedata.hraux.DiplomaInfo getEducation()
    {
        return (com.kingdee.eas.basedata.hraux.DiplomaInfo)get("education");
    }
    public void setEducation(com.kingdee.eas.basedata.hraux.DiplomaInfo item)
    {
        put("education", item);
    }
    /**
     * Object:ר�ҿ�'s ��λ�绰property 
     */
    public String getTelephone()
    {
        return getString("telephone");
    }
    public void setTelephone(String item)
    {
        setString("telephone", item);
    }
    /**
     * Object:ר�ҿ�'s �ֻ�property 
     */
    public String getMobile()
    {
        return getString("mobile");
    }
    public void setMobile(String item)
    {
        setString("mobile", item);
    }
    /**
     * Object:ר�ҿ�'s �ִ���רҵproperty 
     */
    public String getProfession()
    {
        return getString("profession");
    }
    public void setProfession(String item)
    {
        setString("profession", item);
    }
    /**
     * Object:ר�ҿ�'s רҵ����property 
     */
    public String getYears()
    {
        return getString("years");
    }
    public void setYears(String item)
    {
        setString("years", item);
    }
    /**
     * Object: ר�ҿ� 's �ֲ��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCurDep()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("curDep");
    }
    public void setCurDep(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("curDep", item);
    }
    /**
     * Object:ר�ҿ�'s �ָ�λproperty 
     */
    public String getCurPost()
    {
        return getString("curPost");
    }
    public void setCurPost(String item)
    {
        setString("curPost", item);
    }
    /**
     * Object:ר�ҿ�'s ����ְ��property 
     */
    public String getTechLevel()
    {
        return getString("techLevel");
    }
    public void setTechLevel(String item)
    {
        setString("techLevel", item);
    }
    /**
     * Object: ר�ҿ� 's ר����� property 
     */
    public com.kingdee.eas.port.pm.base.JudgesTreeInfo getJudgeType()
    {
        return (com.kingdee.eas.port.pm.base.JudgesTreeInfo)get("judgeType");
    }
    public void setJudgeType(com.kingdee.eas.port.pm.base.JudgesTreeInfo item)
    {
        put("judgeType", item);
    }
    /**
     * Object:ר�ҿ�'s ����property 
     */
    public boolean isIsUse()
    {
        return getBoolean("isUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("isUse", item);
    }
    /**
     * Object:ר�ҿ�'s ����property 
     */
    public String getPersonName()
    {
        return getString("personName");
    }
    public void setPersonName(String item)
    {
        setString("personName", item);
    }
    /**
     * Object:ר�ҿ�'s �Ƿ��ⲿר��property 
     */
    public boolean isIsOuter()
    {
        return getBoolean("isOuter");
    }
    public void setIsOuter(boolean item)
    {
        setBoolean("isOuter", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("18EA4FBD");
    }
}