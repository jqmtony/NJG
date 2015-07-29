package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichExamTempTabInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRichExamTempTabInfo()
    {
        this("id");
    }
    protected AbstractRichExamTempTabInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:体检明细临时表's 业务单据编号property 
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
     * Object:体检明细临时表's 业务日期property 
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
     * Object:体检明细临时表's 落单号property 
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
     * Object:体检明细临时表's 签约单位（合同单位）property 
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
     * Object:体检明细临时表's 到检单位（受检单位）property 
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
     * Object:体检明细临时表's 开票单位property 
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
     * Object:体检明细临时表's 收款单位property 
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
     * Object:体检明细临时表's 到检凭证号property 
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
     * Object:体检明细临时表's 开票凭证号property 
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
     * Object:体检明细临时表's 收款凭证号property 
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
     * Object:体检明细临时表's 关联单据号property 
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
     * Object:体检明细临时表's 发票号property 
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
     * Object:体检明细临时表's 总金额property 
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
     * Object:体检明细临时表's 销售员property 
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
     * Object:体检明细临时表's 体检类别property 
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
     * Object:体检明细临时表's 单据状态property 
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
     * Object:体检明细临时表's 备注property 
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
     * Object:体检明细临时表's 到检套餐名称property 
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
     * Object:体检明细临时表's 到检人property 
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
     * Object:体检明细临时表's 到检套餐编码property 
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
     * Object:体检明细临时表's 到检项目编码property 
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
     * Object:体检明细临时表's 到检项目名称property 
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
     * Object:体检明细临时表's 销售类别property 
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
     * Object:体检明细临时表's 收款类别property 
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
     * Object:体检明细临时表's 加项标识property 
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
     * Object:体检明细临时表's 刊例价property 
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
     * Object:体检明细临时表's 折扣率property 
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
     * Object:体检明细临时表's 结算金额property 
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
     * Object:体检明细临时表's 税额property 
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
     * Object:体检明细临时表's 价税合计property 
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
     * Object:体检明细临时表's 到检机构property 
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
     * Object:体检明细临时表's 开票机构property 
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
     * Object:体检明细临时表's 单据日期property 
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
     * Object:体检明细临时表's 标识property 
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
     * Object:体检明细临时表's 储值卡号property 
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
     * Object:体检明细临时表's 中介机构property 
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
     * Object:体检明细临时表's 生成到检单property 
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
     * Object:体检明细临时表's 原到检编号property 
     */
    public String getYdjbh()
    {
        return getString("ydjbh");
    }
    public void setYdjbh(String item)
    {
        setString("ydjbh", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("90615CB8");
    }
}