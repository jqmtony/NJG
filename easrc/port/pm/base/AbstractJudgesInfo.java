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
     * Object: 专家库 's 组别 property 
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
     * Object: 专家库 's 姓名 property 
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
     * Object:专家库's 性别property 
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
     * Object:专家库's 出生年月property 
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
     * Object: 专家库 's 学历 property 
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
     * Object:专家库's 单位电话property 
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
     * Object:专家库's 手机property 
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
     * Object:专家库's 现从事专业property 
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
     * Object:专家库's 专业年数property 
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
     * Object: 专家库 's 现部门 property 
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
     * Object:专家库's 现岗位property 
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
     * Object:专家库's 技术职称property 
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
     * Object: 专家库 's 专家类别 property 
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
     * Object:专家库's 启用property 
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
     * Object:专家库's 姓名property 
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
     * Object:专家库's 是否外部专家property 
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