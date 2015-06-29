package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichExamCheckTabInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRichExamCheckTabInfo()
    {
        this("id");
    }
    protected AbstractRichExamCheckTabInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����ϸУ���'s ҵ�񵥾ݱ��property 
     */
    public String getYwdjbh()
    {
        return getString("ywdjbh");
    }
    public void setYwdjbh(String item)
    {
        setString("ywdjbh", item);
    }
    /**
     * Object:�����ϸУ���'s ҵ������property 
     */
    public java.util.Date getBizdate()
    {
        return getDate("bizdate");
    }
    public void setBizdate(java.util.Date item)
    {
        setDate("bizdate", item);
    }
    /**
     * Object:�����ϸУ���'s �䵥��property 
     */
    public String getLdh()
    {
        return getString("ldh");
    }
    public void setLdh(String item)
    {
        setString("ldh", item);
    }
    /**
     * Object:�����ϸУ���'s ǩԼ��λ����ͬ��λ��property 
     */
    public String getQydw()
    {
        return getString("qydw");
    }
    public void setQydw(String item)
    {
        setString("qydw", item);
    }
    /**
     * Object:�����ϸУ���'s ���쵥λ���ܼ쵥λ��property 
     */
    public String getDjdw()
    {
        return getString("djdw");
    }
    public void setDjdw(String item)
    {
        setString("djdw", item);
    }
    /**
     * Object:�����ϸУ���'s ��Ʊ��λproperty 
     */
    public String getKpdw()
    {
        return getString("kpdw");
    }
    public void setKpdw(String item)
    {
        setString("kpdw", item);
    }
    /**
     * Object:�����ϸУ���'s �տλproperty 
     */
    public String getSkdw()
    {
        return getString("skdw");
    }
    public void setSkdw(String item)
    {
        setString("skdw", item);
    }
    /**
     * Object:�����ϸУ���'s ����ƾ֤��property 
     */
    public String getDjpzh()
    {
        return getString("djpzh");
    }
    public void setDjpzh(String item)
    {
        setString("djpzh", item);
    }
    /**
     * Object:�����ϸУ���'s ��Ʊƾ֤��property 
     */
    public String getKppzh()
    {
        return getString("kppzh");
    }
    public void setKppzh(String item)
    {
        setString("kppzh", item);
    }
    /**
     * Object:�����ϸУ���'s �տ�ƾ֤��property 
     */
    public String getSkpzh()
    {
        return getString("skpzh");
    }
    public void setSkpzh(String item)
    {
        setString("skpzh", item);
    }
    /**
     * Object:�����ϸУ���'s �������ݺ�property 
     */
    public String getBiznumber()
    {
        return getString("biznumber");
    }
    public void setBiznumber(String item)
    {
        setString("biznumber", item);
    }
    /**
     * Object:�����ϸУ���'s ��Ʊ��property 
     */
    public String getFph()
    {
        return getString("fph");
    }
    public void setFph(String item)
    {
        setString("fph", item);
    }
    /**
     * Object:�����ϸУ���'s �ܽ��property 
     */
    public String getZje()
    {
        return getString("zje");
    }
    public void setZje(String item)
    {
        setString("zje", item);
    }
    /**
     * Object:�����ϸУ���'s ����Աproperty 
     */
    public String getXsy()
    {
        return getString("xsy");
    }
    public void setXsy(String item)
    {
        setString("xsy", item);
    }
    /**
     * Object:�����ϸУ���'s ������property 
     */
    public String getTjlb()
    {
        return getString("tjlb");
    }
    public void setTjlb(String item)
    {
        setString("tjlb", item);
    }
    /**
     * Object:�����ϸУ���'s ����״̬property 
     */
    public String getBizState()
    {
        return getString("bizState");
    }
    public void setBizState(String item)
    {
        setString("bizState", item);
    }
    /**
     * Object:�����ϸУ���'s ��עproperty 
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
     * Object:�����ϸУ���'s �����ײ�����property 
     */
    public String getDjtcmc()
    {
        return getString("djtcmc");
    }
    public void setDjtcmc(String item)
    {
        setString("djtcmc", item);
    }
    /**
     * Object:�����ϸУ���'s ������property 
     */
    public String getDjr()
    {
        return getString("djr");
    }
    public void setDjr(String item)
    {
        setString("djr", item);
    }
    /**
     * Object:�����ϸУ���'s �����ײͱ���property 
     */
    public String getDjtcbm()
    {
        return getString("djtcbm");
    }
    public void setDjtcbm(String item)
    {
        setString("djtcbm", item);
    }
    /**
     * Object:�����ϸУ���'s ������Ŀ����property 
     */
    public String getDjxmbm()
    {
        return getString("djxmbm");
    }
    public void setDjxmbm(String item)
    {
        setString("djxmbm", item);
    }
    /**
     * Object:�����ϸУ���'s ������Ŀ����property 
     */
    public String getDjxmmc()
    {
        return getString("djxmmc");
    }
    public void setDjxmmc(String item)
    {
        setString("djxmmc", item);
    }
    /**
     * Object:�����ϸУ���'s �������property 
     */
    public String getXslb()
    {
        return getString("xslb");
    }
    public void setXslb(String item)
    {
        setString("xslb", item);
    }
    /**
     * Object:�����ϸУ���'s �տ����property 
     */
    public String getSklb()
    {
        return getString("sklb");
    }
    public void setSklb(String item)
    {
        setString("sklb", item);
    }
    /**
     * Object:�����ϸУ���'s �����ʶproperty 
     */
    public String getJxbs()
    {
        return getString("jxbs");
    }
    public void setJxbs(String item)
    {
        setString("jxbs", item);
    }
    /**
     * Object:�����ϸУ���'s ������property 
     */
    public String getKlj()
    {
        return getString("klj");
    }
    public void setKlj(String item)
    {
        setString("klj", item);
    }
    /**
     * Object:�����ϸУ���'s �ۿ���property 
     */
    public String getZkl()
    {
        return getString("zkl");
    }
    public void setZkl(String item)
    {
        setString("zkl", item);
    }
    /**
     * Object:�����ϸУ���'s ������property 
     */
    public String getJsje()
    {
        return getString("jsje");
    }
    public void setJsje(String item)
    {
        setString("jsje", item);
    }
    /**
     * Object:�����ϸУ���'s ˰��property 
     */
    public String getSe()
    {
        return getString("se");
    }
    public void setSe(String item)
    {
        setString("se", item);
    }
    /**
     * Object:�����ϸУ���'s ��˰�ϼ�property 
     */
    public String getJshj()
    {
        return getString("jshj");
    }
    public void setJshj(String item)
    {
        setString("jshj", item);
    }
    /**
     * Object:�����ϸУ���'s �������property 
     */
    public String getDjjg()
    {
        return getString("djjg");
    }
    public void setDjjg(String item)
    {
        setString("djjg", item);
    }
    /**
     * Object:�����ϸУ���'s ��Ʊ����property 
     */
    public String getKpjg()
    {
        return getString("kpjg");
    }
    public void setKpjg(String item)
    {
        setString("kpjg", item);
    }
    /**
     * Object:�����ϸУ���'s ��������property 
     */
    public java.util.Date getDjrq()
    {
        return getDate("djrq");
    }
    public void setDjrq(java.util.Date item)
    {
        setDate("djrq", item);
    }
    /**
     * Object:�����ϸУ���'s ��ʶproperty 
     */
    public boolean isFlag()
    {
        return getBoolean("flag");
    }
    public void setFlag(boolean item)
    {
        setBoolean("flag", item);
    }
    /**
     * Object:�����ϸУ���'s ��ֵ����property 
     */
    public String getKh()
    {
        return getString("kh");
    }
    public void setKh(String item)
    {
        setString("kh", item);
    }
    /**
     * Object:�����ϸУ���'s �н����property 
     */
    public String getZjjg()
    {
        return getString("zjjg");
    }
    public void setZjjg(String item)
    {
        setString("zjjg", item);
    }
    /**
     * Object:�����ϸУ���'s ���ɵ��쵥property 
     */
    public boolean isDjd()
    {
        return getBoolean("djd");
    }
    public void setDjd(boolean item)
    {
        setBoolean("djd", item);
    }
    /**
     * Object:�����ϸУ���'s У����Ϣproperty 
     */
    public String getCheckInfo()
    {
        return getString("checkInfo");
    }
    public void setCheckInfo(String item)
    {
        setString("checkInfo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("26353EB6");
    }
}