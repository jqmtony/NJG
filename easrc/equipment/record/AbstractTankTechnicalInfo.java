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
     * Object:储罐技术特性表's 储罐容量property 
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
     * Object:储罐技术特性表's 储罐直径property 
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
     * Object:储罐技术特性表's 罐壁高度property 
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
     * Object:储罐技术特性表's 设计压力property 
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
     * Object:储罐技术特性表's 工作压力property 
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
     * Object:储罐技术特性表's 设计温度property 
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
     * Object:储罐技术特性表's 工作温度property 
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
     * Object:储罐技术特性表's 腐蚀余量property 
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
     * Object:储罐技术特性表's 设计密度property 
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
     * Object:储罐技术特性表's 安全高度property 
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
     * Object:储罐技术特性表's 换热面积property 
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
     * Object:储罐技术特性表's 壁板尺寸property 
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
     * Object:储罐技术特性表's 罐壁板1property 
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
     * Object:储罐技术特性表's 罐壁板2property 
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
     * Object:储罐技术特性表's 罐顶板1property 
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
     * Object:储罐技术特性表's 罐顶板2property 
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
     * Object:储罐技术特性表's 罐顶板3property 
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
     * Object:储罐技术特性表's 边缘板1property 
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
     * Object:储罐技术特性表's 边缘板2property 
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
     * Object:储罐技术特性表's 边缘板3property 
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
     * Object:储罐技术特性表's 中幅板1property 
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
     * Object:储罐技术特性表's 中幅板2property 
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
     * Object:储罐技术特性表's 中幅板3property 
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
     * Object:储罐技术特性表's 进油管property 
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
     * Object:储罐技术特性表's 中心高进油property 
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
     * Object:储罐技术特性表's 法兰接口进油property 
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
     * Object:储罐技术特性表's 螺栓进油property 
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
     * Object:储罐技术特性表's 出油管property 
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
     * Object:储罐技术特性表's 中心高出油property 
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
     * Object:储罐技术特性表's 法兰接口出油property 
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
     * Object:储罐技术特性表's 螺栓出油property 
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
     * Object:储罐技术特性表's 入孔property 
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
     * Object:储罐技术特性表's 中心高入孔property 
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
     * Object:储罐技术特性表's 入孔数量property 
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
     * Object:储罐技术特性表's 螺栓入孔property 
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
     * Object:储罐技术特性表's 透光孔property 
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
     * Object:储罐技术特性表's 透光孔数量property 
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
     * Object:储罐技术特性表's 螺栓透光孔property 
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
     * Object:储罐技术特性表's 液位计接口法兰1property 
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
     * Object:储罐技术特性表's 液位计接口法兰2property 
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
     * Object:储罐技术特性表's 液位计螺栓property 
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
     * Object:储罐技术特性表's 液位计规格property 
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
     * Object:储罐技术特性表's 液位计长度property 
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
     * Object:储罐技术特性表's 液位计中心高1property 
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
     * Object:储罐技术特性表's 建造时间property 
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
     * Object:储罐技术特性表's 设计单位property 
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
     * Object:储罐技术特性表's 施工单位建造property 
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
     * Object:储罐技术特性表's 时间property 
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
     * Object:储罐技术特性表's 罐容property 
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
     * Object:储罐技术特性表's 大修时间property 
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
     * Object:储罐技术特性表's 施工日期大修property 
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
     * Object:储罐技术特性表's 施工单位大修property 
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
     * Object:储罐技术特性表's 大修内容property 
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
     * Object:储罐技术特性表's 搅拌器接口法兰1property 
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
     * Object:储罐技术特性表's 搅拌器接口法兰2property 
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
     * Object:储罐技术特性表's 搅拌器螺栓property 
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
     * Object:储罐技术特性表's 搅拌器规格property 
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
     * Object:储罐技术特性表's 搅拌器长度property 
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
     * Object:储罐技术特性表's 搅拌器中心高property 
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
     * Object:储罐技术特性表's 泡沫发生器接口法兰1property 
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
     * Object:储罐技术特性表's 泡沫发生器接口法兰2property 
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
     * Object:储罐技术特性表's 泡沫发生器螺栓property 
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
     * Object:储罐技术特性表's 泡沫发生器规格property 
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
     * Object:储罐技术特性表's 泡沫发生器长度property 
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
     * Object:储罐技术特性表's 泡沫发生器中心高property 
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
     * Object:储罐技术特性表's 油量管接口法兰1property 
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
     * Object:储罐技术特性表's 油量管接口法兰2property 
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
     * Object:储罐技术特性表's 油量管螺栓property 
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
     * Object:储罐技术特性表's 油量管规格property 
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
     * Object:储罐技术特性表's 油量管长度property 
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
     * Object:储罐技术特性表's 油量管中心高property 
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
     * Object:储罐技术特性表's 透光孔1property 
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
     * Object:储罐技术特性表's 透光孔2property 
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
     * Object:储罐技术特性表's 清扫孔1property 
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
     * Object:储罐技术特性表's 清扫孔2property 
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
     * Object:储罐技术特性表's 清扫孔3property 
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
     * Object:储罐技术特性表's 侧厚记录property 
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
     * Object:储罐技术特性表's 外观property 
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
     * Object:储罐技术特性表's 建造费用property 
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
     * Object:储罐技术特性表's 清扫孔4property 
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
     * Object:储罐技术特性表's 清扫孔5property 
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
     * Object:储罐技术特性表's 螺栓清扫property 
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
     * Object:储罐技术特性表's 呼吸阀接口法兰1property 
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
     * Object:储罐技术特性表's 呼吸阀接口法兰2property 
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
     * Object:储罐技术特性表's 呼吸阀螺栓property 
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
     * Object:储罐技术特性表's 呼吸阀规格property 
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
     * Object:储罐技术特性表's 保温层接口法兰1property 
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
     * Object:储罐技术特性表's 保温层接口法兰2property 
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
     * Object:储罐技术特性表's 呼吸阀长度property 
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
     * Object:储罐技术特性表's 呼吸阀中心高property 
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
     * Object:储罐技术特性表's 保温层螺栓property 
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
     * Object:储罐技术特性表's 保温层规格property 
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
     * Object:储罐技术特性表's 保温层长度property 
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
     * Object:储罐技术特性表's 保温层中心高property 
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
     * Object:储罐技术特性表's 铁皮接口法兰1property 
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
     * Object:储罐技术特性表's 铁皮法兰接口2property 
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
     * Object:储罐技术特性表's 铁皮螺栓property 
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
     * Object:储罐技术特性表's 铁皮规格property 
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
     * Object:储罐技术特性表's 铁皮长度property 
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
     * Object:储罐技术特性表's 铁皮中心高property 
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
     * Object:储罐技术特性表's 导波管接口法兰1property 
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
     * Object:储罐技术特性表's 导波管接口法兰2property 
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
     * Object:储罐技术特性表's 导波管螺栓property 
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
     * Object:储罐技术特性表's 导波管规格property 
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
     * Object:储罐技术特性表's 导波管长度property 
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
     * Object:储罐技术特性表's 导波管中心高property 
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
     * Object:储罐技术特性表's 加热器接口法兰1property 
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
     * Object:储罐技术特性表's 加热器接口法兰2property 
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
     * Object:储罐技术特性表's 加热器长格property 
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