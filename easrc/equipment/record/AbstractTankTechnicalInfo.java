package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTankTechnicalInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractTankTechnicalInfo()
    {
        this("id");
    }
    protected AbstractTankTechnicalInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���޼������Ա�'s ��������property 
     */
    public String getTankCapacity()
    {
        return getString("tankCapacity");
    }
    public void setTankCapacity(String item)
    {
        setString("tankCapacity", item);
    }
    /**
     * Object:���޼������Ա�'s ����ֱ��property 
     */
    public String getTankDiameter()
    {
        return getString("tankDiameter");
    }
    public void setTankDiameter(String item)
    {
        setString("tankDiameter", item);
    }
    /**
     * Object:���޼������Ա�'s �ޱڸ߶�property 
     */
    public String getTankWallHeight()
    {
        return getString("tankWallHeight");
    }
    public void setTankWallHeight(String item)
    {
        setString("tankWallHeight", item);
    }
    /**
     * Object:���޼������Ա�'s ���ѹ��property 
     */
    public String getShejiyali()
    {
        return getString("shejiyali");
    }
    public void setShejiyali(String item)
    {
        setString("shejiyali", item);
    }
    /**
     * Object:���޼������Ա�'s ����ѹ��property 
     */
    public String getGongzuoyali()
    {
        return getString("gongzuoyali");
    }
    public void setGongzuoyali(String item)
    {
        setString("gongzuoyali", item);
    }
    /**
     * Object:���޼������Ա�'s ����¶�property 
     */
    public String getShejiwendu()
    {
        return getString("shejiwendu");
    }
    public void setShejiwendu(String item)
    {
        setString("shejiwendu", item);
    }
    /**
     * Object:���޼������Ա�'s �����¶�property 
     */
    public String getGongzuowendu()
    {
        return getString("gongzuowendu");
    }
    public void setGongzuowendu(String item)
    {
        setString("gongzuowendu", item);
    }
    /**
     * Object:���޼������Ա�'s ��ʴ����property 
     */
    public String getFushiyuliang()
    {
        return getString("fushiyuliang");
    }
    public void setFushiyuliang(String item)
    {
        setString("fushiyuliang", item);
    }
    /**
     * Object:���޼������Ա�'s ����ܶ�property 
     */
    public String getShejimidu()
    {
        return getString("shejimidu");
    }
    public void setShejimidu(String item)
    {
        setString("shejimidu", item);
    }
    /**
     * Object:���޼������Ա�'s ��ȫ�߶�property 
     */
    public String getAnquangaodu()
    {
        return getString("anquangaodu");
    }
    public void setAnquangaodu(String item)
    {
        setString("anquangaodu", item);
    }
    /**
     * Object:���޼������Ա�'s �������property 
     */
    public String getHuanremianji()
    {
        return getString("huanremianji");
    }
    public void setHuanremianji(String item)
    {
        setString("huanremianji", item);
    }
    /**
     * Object:���޼������Ա�'s �ڰ�ߴ�property 
     */
    public String getBibanchicun()
    {
        return getString("bibanchicun");
    }
    public void setBibanchicun(String item)
    {
        setString("bibanchicun", item);
    }
    /**
     * Object:���޼������Ա�'s �ޱڰ�1property 
     */
    public String getGuanbibanone()
    {
        return getString("guanbibanone");
    }
    public void setGuanbibanone(String item)
    {
        setString("guanbibanone", item);
    }
    /**
     * Object:���޼������Ա�'s �ޱڰ�2property 
     */
    public String getGuanbibantwo()
    {
        return getString("guanbibantwo");
    }
    public void setGuanbibantwo(String item)
    {
        setString("guanbibantwo", item);
    }
    /**
     * Object:���޼������Ա�'s �޶���1property 
     */
    public String getGuandingbanone()
    {
        return getString("guandingbanone");
    }
    public void setGuandingbanone(String item)
    {
        setString("guandingbanone", item);
    }
    /**
     * Object:���޼������Ա�'s �޶���2property 
     */
    public String getGuandingbantwo()
    {
        return getString("guandingbantwo");
    }
    public void setGuandingbantwo(String item)
    {
        setString("guandingbantwo", item);
    }
    /**
     * Object:���޼������Ա�'s �޶���3property 
     */
    public String getGuandingbanthree()
    {
        return getString("guandingbanthree");
    }
    public void setGuandingbanthree(String item)
    {
        setString("guandingbanthree", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ե��1property 
     */
    public String getBianyuanbanone()
    {
        return getString("bianyuanbanone");
    }
    public void setBianyuanbanone(String item)
    {
        setString("bianyuanbanone", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ե��2property 
     */
    public String getBianyuanbantwo()
    {
        return getString("bianyuanbantwo");
    }
    public void setBianyuanbantwo(String item)
    {
        setString("bianyuanbantwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ե��3property 
     */
    public String getBianyuanbanthree()
    {
        return getString("bianyuanbanthree");
    }
    public void setBianyuanbanthree(String item)
    {
        setString("bianyuanbanthree", item);
    }
    /**
     * Object:���޼������Ա�'s �з���1property 
     */
    public String getZhongfubanone()
    {
        return getString("zhongfubanone");
    }
    public void setZhongfubanone(String item)
    {
        setString("zhongfubanone", item);
    }
    /**
     * Object:���޼������Ա�'s �з���2property 
     */
    public String getZhongfubantwo()
    {
        return getString("zhongfubantwo");
    }
    public void setZhongfubantwo(String item)
    {
        setString("zhongfubantwo", item);
    }
    /**
     * Object:���޼������Ա�'s �з���3property 
     */
    public String getZhongfubanthree()
    {
        return getString("zhongfubanthree");
    }
    public void setZhongfubanthree(String item)
    {
        setString("zhongfubanthree", item);
    }
    /**
     * Object:���޼������Ա�'s ���͹�property 
     */
    public String getJingyouguan()
    {
        return getString("jingyouguan");
    }
    public void setJingyouguan(String item)
    {
        setString("jingyouguan", item);
    }
    /**
     * Object:���޼������Ա�'s ���ĸ߽���property 
     */
    public String getZhongxingaojy()
    {
        return getString("zhongxingaojy");
    }
    public void setZhongxingaojy(String item)
    {
        setString("zhongxingaojy", item);
    }
    /**
     * Object:���޼������Ա�'s �����ӿڽ���property 
     */
    public String getFalanjiekoujy()
    {
        return getString("falanjiekoujy");
    }
    public void setFalanjiekoujy(String item)
    {
        setString("falanjiekoujy", item);
    }
    /**
     * Object:���޼������Ա�'s ��˨����property 
     */
    public String getLuoshuanjy()
    {
        return getString("luoshuanjy");
    }
    public void setLuoshuanjy(String item)
    {
        setString("luoshuanjy", item);
    }
    /**
     * Object:���޼������Ա�'s ���͹�property 
     */
    public String getChuyouguan()
    {
        return getString("chuyouguan");
    }
    public void setChuyouguan(String item)
    {
        setString("chuyouguan", item);
    }
    /**
     * Object:���޼������Ա�'s ���ĸ߳���property 
     */
    public String getZhongxingaocy()
    {
        return getString("zhongxingaocy");
    }
    public void setZhongxingaocy(String item)
    {
        setString("zhongxingaocy", item);
    }
    /**
     * Object:���޼������Ա�'s �����ӿڳ���property 
     */
    public String getFalanjiekoucy()
    {
        return getString("falanjiekoucy");
    }
    public void setFalanjiekoucy(String item)
    {
        setString("falanjiekoucy", item);
    }
    /**
     * Object:���޼������Ա�'s ��˨����property 
     */
    public String getLuoshuancy()
    {
        return getString("luoshuancy");
    }
    public void setLuoshuancy(String item)
    {
        setString("luoshuancy", item);
    }
    /**
     * Object:���޼������Ա�'s ���property 
     */
    public String getRukong()
    {
        return getString("rukong");
    }
    public void setRukong(String item)
    {
        setString("rukong", item);
    }
    /**
     * Object:���޼������Ա�'s ���ĸ����property 
     */
    public String getZhongxingaork()
    {
        return getString("zhongxingaork");
    }
    public void setZhongxingaork(String item)
    {
        setString("zhongxingaork", item);
    }
    /**
     * Object:���޼������Ա�'s �������property 
     */
    public java.math.BigDecimal getRukongshuliang()
    {
        return getBigDecimal("rukongshuliang");
    }
    public void setRukongshuliang(java.math.BigDecimal item)
    {
        setBigDecimal("rukongshuliang", item);
    }
    /**
     * Object:���޼������Ա�'s ��˨���property 
     */
    public String getLuoshuanrk()
    {
        return getString("luoshuanrk");
    }
    public void setLuoshuanrk(String item)
    {
        setString("luoshuanrk", item);
    }
    /**
     * Object:���޼������Ա�'s ͸���property 
     */
    public String getTouguangkong()
    {
        return getString("touguangkong");
    }
    public void setTouguangkong(String item)
    {
        setString("touguangkong", item);
    }
    /**
     * Object:���޼������Ա�'s ͸�������property 
     */
    public java.math.BigDecimal getTouguangkongsl()
    {
        return getBigDecimal("touguangkongsl");
    }
    public void setTouguangkongsl(java.math.BigDecimal item)
    {
        setBigDecimal("touguangkongsl", item);
    }
    /**
     * Object:���޼������Ա�'s ��˨͸���property 
     */
    public String getLuoshuantgk()
    {
        return getString("luoshuantgk");
    }
    public void setLuoshuantgk(String item)
    {
        setString("luoshuantgk", item);
    }
    /**
     * Object:���޼������Ա�'s Һλ�ƽӿڷ���1property 
     */
    public String getYeweijifljkone()
    {
        return getString("yeweijifljkone");
    }
    public void setYeweijifljkone(String item)
    {
        setString("yeweijifljkone", item);
    }
    /**
     * Object:���޼������Ա�'s Һλ�ƽӿڷ���2property 
     */
    public String getYeweijijkfltwo()
    {
        return getString("yeweijijkfltwo");
    }
    public void setYeweijijkfltwo(String item)
    {
        setString("yeweijijkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s Һλ����˨property 
     */
    public String getYeweijils()
    {
        return getString("yeweijils");
    }
    public void setYeweijils(String item)
    {
        setString("yeweijils", item);
    }
    /**
     * Object:���޼������Ա�'s Һλ�ƹ��property 
     */
    public String getYeweijiguige()
    {
        return getString("yeweijiguige");
    }
    public void setYeweijiguige(String item)
    {
        setString("yeweijiguige", item);
    }
    /**
     * Object:���޼������Ա�'s Һλ�Ƴ���property 
     */
    public String getYeweijicd()
    {
        return getString("yeweijicd");
    }
    public void setYeweijicd(String item)
    {
        setString("yeweijicd", item);
    }
    /**
     * Object:���޼������Ա�'s Һλ�����ĸ�1property 
     */
    public String getYeweijizxgone()
    {
        return getString("yeweijizxgone");
    }
    public void setYeweijizxgone(String item)
    {
        setString("yeweijizxgone", item);
    }
    /**
     * Object:���޼������Ա�'s ����ʱ��property 
     */
    public java.util.Date getJianzaoTime()
    {
        return getDate("jianzaoTime");
    }
    public void setJianzaoTime(java.util.Date item)
    {
        setDate("jianzaoTime", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƶ�λproperty 
     */
    public String getShejidanwei()
    {
        return getString("shejidanwei");
    }
    public void setShejidanwei(String item)
    {
        setString("shejidanwei", item);
    }
    /**
     * Object:���޼������Ա�'s ʩ����λ����property 
     */
    public String getShigongdanweione()
    {
        return getString("shigongdanweione");
    }
    public void setShigongdanweione(String item)
    {
        setString("shigongdanweione", item);
    }
    /**
     * Object:���޼������Ա�'s ʱ��property 
     */
    public java.util.Date getTime()
    {
        return getDate("time");
    }
    public void setTime(java.util.Date item)
    {
        setDate("time", item);
    }
    /**
     * Object:���޼������Ա�'s ����property 
     */
    public String getGuanrong()
    {
        return getString("guanrong");
    }
    public void setGuanrong(String item)
    {
        setString("guanrong", item);
    }
    /**
     * Object:���޼������Ա�'s ����ʱ��property 
     */
    public java.util.Date getDxtime()
    {
        return getDate("dxtime");
    }
    public void setDxtime(java.util.Date item)
    {
        setDate("dxtime", item);
    }
    /**
     * Object:���޼������Ա�'s ʩ�����ڴ���property 
     */
    public java.util.Date getDaxiushigongriqi()
    {
        return getDate("daxiushigongriqi");
    }
    public void setDaxiushigongriqi(java.util.Date item)
    {
        setDate("daxiushigongriqi", item);
    }
    /**
     * Object:���޼������Ա�'s ʩ����λ����property 
     */
    public String getShigongdanweitwo()
    {
        return getString("shigongdanweitwo");
    }
    public void setShigongdanweitwo(String item)
    {
        setString("shigongdanweitwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��������property 
     */
    public String getDaxiuneirong()
    {
        return getString("daxiuneirong");
    }
    public void setDaxiuneirong(String item)
    {
        setString("daxiuneirong", item);
    }
    /**
     * Object:���޼������Ա�'s �������ӿڷ���1property 
     */
    public String getJiaobanqijkflone()
    {
        return getString("jiaobanqijkflone");
    }
    public void setJiaobanqijkflone(String item)
    {
        setString("jiaobanqijkflone", item);
    }
    /**
     * Object:���޼������Ա�'s �������ӿڷ���2property 
     */
    public String getJiaobanqijkfltwo()
    {
        return getString("jiaobanqijkfltwo");
    }
    public void setJiaobanqijkfltwo(String item)
    {
        setString("jiaobanqijkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��������˨property 
     */
    public String getJiaobanqils()
    {
        return getString("jiaobanqils");
    }
    public void setJiaobanqils(String item)
    {
        setString("jiaobanqils", item);
    }
    /**
     * Object:���޼������Ա�'s ���������property 
     */
    public String getJiaobanqiguige()
    {
        return getString("jiaobanqiguige");
    }
    public void setJiaobanqiguige(String item)
    {
        setString("jiaobanqiguige", item);
    }
    /**
     * Object:���޼������Ա�'s ����������property 
     */
    public String getJiaobanqicd()
    {
        return getString("jiaobanqicd");
    }
    public void setJiaobanqicd(String item)
    {
        setString("jiaobanqicd", item);
    }
    /**
     * Object:���޼������Ա�'s ���������ĸ�property 
     */
    public String getJiaobanqizxg()
    {
        return getString("jiaobanqizxg");
    }
    public void setJiaobanqizxg(String item)
    {
        setString("jiaobanqizxg", item);
    }
    /**
     * Object:���޼������Ա�'s ��ĭ�������ӿڷ���1property 
     */
    public String getPaomojkflone()
    {
        return getString("paomojkflone");
    }
    public void setPaomojkflone(String item)
    {
        setString("paomojkflone", item);
    }
    /**
     * Object:���޼������Ա�'s ��ĭ�������ӿڷ���2property 
     */
    public String getPaomojkfltwo()
    {
        return getString("paomojkfltwo");
    }
    public void setPaomojkfltwo(String item)
    {
        setString("paomojkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��ĭ��������˨property 
     */
    public String getPaomols()
    {
        return getString("paomols");
    }
    public void setPaomols(String item)
    {
        setString("paomols", item);
    }
    /**
     * Object:���޼������Ա�'s ��ĭ���������property 
     */
    public String getPaomoguige()
    {
        return getString("paomoguige");
    }
    public void setPaomoguige(String item)
    {
        setString("paomoguige", item);
    }
    /**
     * Object:���޼������Ա�'s ��ĭ����������property 
     */
    public String getPaomocd()
    {
        return getString("paomocd");
    }
    public void setPaomocd(String item)
    {
        setString("paomocd", item);
    }
    /**
     * Object:���޼������Ա�'s ��ĭ���������ĸ�property 
     */
    public String getPaomozxg()
    {
        return getString("paomozxg");
    }
    public void setPaomozxg(String item)
    {
        setString("paomozxg", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܽӿڷ���1property 
     */
    public String getYouliangguanjkflone()
    {
        return getString("youliangguanjkflone");
    }
    public void setYouliangguanjkflone(String item)
    {
        setString("youliangguanjkflone", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܽӿڷ���2property 
     */
    public String getYouliangguanjkfltwo()
    {
        return getString("youliangguanjkfltwo");
    }
    public void setYouliangguanjkfltwo(String item)
    {
        setString("youliangguanjkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��������˨property 
     */
    public String getYouliangguanls()
    {
        return getString("youliangguanls");
    }
    public void setYouliangguanls(String item)
    {
        setString("youliangguanls", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܹ��property 
     */
    public String getYouliangguanguige()
    {
        return getString("youliangguanguige");
    }
    public void setYouliangguanguige(String item)
    {
        setString("youliangguanguige", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܳ���property 
     */
    public String getYouliangguancd()
    {
        return getString("youliangguancd");
    }
    public void setYouliangguancd(String item)
    {
        setString("youliangguancd", item);
    }
    /**
     * Object:���޼������Ա�'s ���������ĸ�property 
     */
    public String getYouliangguanzxg()
    {
        return getString("youliangguanzxg");
    }
    public void setYouliangguanzxg(String item)
    {
        setString("youliangguanzxg", item);
    }
    /**
     * Object:���޼������Ա�'s ͸���1property 
     */
    public String getTouguangkongone()
    {
        return getString("touguangkongone");
    }
    public void setTouguangkongone(String item)
    {
        setString("touguangkongone", item);
    }
    /**
     * Object:���޼������Ա�'s ͸���2property 
     */
    public String getTouguangkongthree()
    {
        return getString("touguangkongthree");
    }
    public void setTouguangkongthree(String item)
    {
        setString("touguangkongthree", item);
    }
    /**
     * Object:���޼������Ա�'s ��ɨ��1property 
     */
    public String getQingsaokongone()
    {
        return getString("qingsaokongone");
    }
    public void setQingsaokongone(String item)
    {
        setString("qingsaokongone", item);
    }
    /**
     * Object:���޼������Ա�'s ��ɨ��2property 
     */
    public String getQingsaokongtwo()
    {
        return getString("qingsaokongtwo");
    }
    public void setQingsaokongtwo(String item)
    {
        setString("qingsaokongtwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��ɨ��3property 
     */
    public String getQingsaokongthree()
    {
        return getString("qingsaokongthree");
    }
    public void setQingsaokongthree(String item)
    {
        setString("qingsaokongthree", item);
    }
    /**
     * Object:���޼������Ա�'s ����¼property 
     */
    public String getCehoujilu()
    {
        return getString("cehoujilu");
    }
    public void setCehoujilu(String item)
    {
        setString("cehoujilu", item);
    }
    /**
     * Object:���޼������Ա�'s ���property 
     */
    public String getWaiguan()
    {
        return getString("waiguan");
    }
    public void setWaiguan(String item)
    {
        setString("waiguan", item);
    }
    /**
     * Object:���޼������Ա�'s �������property 
     */
    public java.math.BigDecimal getJianzaofeiyong()
    {
        return getBigDecimal("jianzaofeiyong");
    }
    public void setJianzaofeiyong(java.math.BigDecimal item)
    {
        setBigDecimal("jianzaofeiyong", item);
    }
    /**
     * Object:���޼������Ա�'s ��ɨ��4property 
     */
    public String getQingsaokongfour()
    {
        return getString("qingsaokongfour");
    }
    public void setQingsaokongfour(String item)
    {
        setString("qingsaokongfour", item);
    }
    /**
     * Object:���޼������Ա�'s ��ɨ��5property 
     */
    public String getQingsaokongfive()
    {
        return getString("qingsaokongfive");
    }
    public void setQingsaokongfive(String item)
    {
        setString("qingsaokongfive", item);
    }
    /**
     * Object:���޼������Ա�'s ��˨��ɨproperty 
     */
    public String getLuoshuanqs()
    {
        return getString("luoshuanqs");
    }
    public void setLuoshuanqs(String item)
    {
        setString("luoshuanqs", item);
    }
    /**
     * Object:���޼������Ա�'s �������ӿڷ���1property 
     */
    public String getHuxifajkflone()
    {
        return getString("huxifajkflone");
    }
    public void setHuxifajkflone(String item)
    {
        setString("huxifajkflone", item);
    }
    /**
     * Object:���޼������Ա�'s �������ӿڷ���2property 
     */
    public String getHuxifajkfltwo()
    {
        return getString("huxifajkfltwo");
    }
    public void setHuxifajkfltwo(String item)
    {
        setString("huxifajkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��������˨property 
     */
    public String getHuxifals()
    {
        return getString("huxifals");
    }
    public void setHuxifals(String item)
    {
        setString("huxifals", item);
    }
    /**
     * Object:���޼������Ա�'s ���������property 
     */
    public String getHuxifagg()
    {
        return getString("huxifagg");
    }
    public void setHuxifagg(String item)
    {
        setString("huxifagg", item);
    }
    /**
     * Object:���޼������Ա�'s ���²�ӿڷ���1property 
     */
    public String getBwcjkflone()
    {
        return getString("bwcjkflone");
    }
    public void setBwcjkflone(String item)
    {
        setString("bwcjkflone", item);
    }
    /**
     * Object:���޼������Ա�'s ���²�ӿڷ���2property 
     */
    public String getBwcjkfltwo()
    {
        return getString("bwcjkfltwo");
    }
    public void setBwcjkfltwo(String item)
    {
        setString("bwcjkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ����������property 
     */
    public String getHuxifaCd()
    {
        return getString("huxifaCd");
    }
    public void setHuxifaCd(String item)
    {
        setString("huxifaCd", item);
    }
    /**
     * Object:���޼������Ա�'s ���������ĸ�property 
     */
    public String getHuxifazxg()
    {
        return getString("huxifazxg");
    }
    public void setHuxifazxg(String item)
    {
        setString("huxifazxg", item);
    }
    /**
     * Object:���޼������Ա�'s ���²���˨property 
     */
    public String getBwcls()
    {
        return getString("bwcls");
    }
    public void setBwcls(String item)
    {
        setString("bwcls", item);
    }
    /**
     * Object:���޼������Ա�'s ���²���property 
     */
    public String getBaowecenggg()
    {
        return getString("baowecenggg");
    }
    public void setBaowecenggg(String item)
    {
        setString("baowecenggg", item);
    }
    /**
     * Object:���޼������Ա�'s ���²㳤��property 
     */
    public String getBaowencengcd()
    {
        return getString("baowencengcd");
    }
    public void setBaowencengcd(String item)
    {
        setString("baowencengcd", item);
    }
    /**
     * Object:���޼������Ա�'s ���²����ĸ�property 
     */
    public String getBaowencengzxg()
    {
        return getString("baowencengzxg");
    }
    public void setBaowencengzxg(String item)
    {
        setString("baowencengzxg", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƥ�ӿڷ���1property 
     */
    public String getTiebijkfaone()
    {
        return getString("tiebijkfaone");
    }
    public void setTiebijkfaone(String item)
    {
        setString("tiebijkfaone", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƥ�����ӿ�2property 
     */
    public String getTiepijkfltwo()
    {
        return getString("tiepijkfltwo");
    }
    public void setTiepijkfltwo(String item)
    {
        setString("tiepijkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƥ��˨property 
     */
    public String getTeipils()
    {
        return getString("teipils");
    }
    public void setTeipils(String item)
    {
        setString("teipils", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƥ���property 
     */
    public String getTiepigg()
    {
        return getString("tiepigg");
    }
    public void setTiepigg(String item)
    {
        setString("tiepigg", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƥ����property 
     */
    public String getTiepicd()
    {
        return getString("tiepicd");
    }
    public void setTiepicd(String item)
    {
        setString("tiepicd", item);
    }
    /**
     * Object:���޼������Ա�'s ��Ƥ���ĸ�property 
     */
    public String getTiepizxg()
    {
        return getString("tiepizxg");
    }
    public void setTiepizxg(String item)
    {
        setString("tiepizxg", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܽӿڷ���1property 
     */
    public String getDaoboguanjkflone()
    {
        return getString("daoboguanjkflone");
    }
    public void setDaoboguanjkflone(String item)
    {
        setString("daoboguanjkflone", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܽӿڷ���2property 
     */
    public String getDaoboguanjkfltwo()
    {
        return getString("daoboguanjkfltwo");
    }
    public void setDaoboguanjkfltwo(String item)
    {
        setString("daoboguanjkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ��������˨property 
     */
    public String getDaoboguanls()
    {
        return getString("daoboguanls");
    }
    public void setDaoboguanls(String item)
    {
        setString("daoboguanls", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܹ��property 
     */
    public String getDaoboguangg()
    {
        return getString("daoboguangg");
    }
    public void setDaoboguangg(String item)
    {
        setString("daoboguangg", item);
    }
    /**
     * Object:���޼������Ա�'s �����ܳ���property 
     */
    public String getDaoboguancd()
    {
        return getString("daoboguancd");
    }
    public void setDaoboguancd(String item)
    {
        setString("daoboguancd", item);
    }
    /**
     * Object:���޼������Ա�'s ���������ĸ�property 
     */
    public String getDaoboguanzxg()
    {
        return getString("daoboguanzxg");
    }
    public void setDaoboguanzxg(String item)
    {
        setString("daoboguanzxg", item);
    }
    /**
     * Object:���޼������Ա�'s �������ӿڷ���1property 
     */
    public String getJiareqijkfaone()
    {
        return getString("jiareqijkfaone");
    }
    public void setJiareqijkfaone(String item)
    {
        setString("jiareqijkfaone", item);
    }
    /**
     * Object:���޼������Ա�'s �������ӿڷ���2property 
     */
    public String getJiareqijkfltwo()
    {
        return getString("jiareqijkfltwo");
    }
    public void setJiareqijkfltwo(String item)
    {
        setString("jiareqijkfltwo", item);
    }
    /**
     * Object:���޼������Ա�'s ����������property 
     */
    public String getJiarqichang()
    {
        return getString("jiarqichang");
    }
    public void setJiarqichang(String item)
    {
        setString("jiarqichang", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3760E11");
    }
}