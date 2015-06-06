/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.custom.richfacade.EASRichFacadeFactory;
import com.kingdee.eas.custom.richfacade.IEASRichFacade;
import com.kingdee.eas.custom.richfacade.serviceclient.EASLoginProxy;
import com.kingdee.eas.custom.richfacade.serviceclient.EASLoginProxyServiceLocator;
import com.kingdee.eas.custom.richfacade.serviceclient.WSContext;
import com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxy;
import com.kingdee.eas.custom.richfacade.serviceclient.WSEASRichFacadeSrvProxyServiceLocator;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RichExamTempTabListUI extends AbstractRichExamTempTabListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichExamTempTabListUI.class);
    
    /**
     * output class constructor
     */
    public RichExamTempTabListUI() throws Exception
    {
        super();
    }
    String getString (){
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < 2; i++) {
    		String str = "<TempTable>" +
							"<billHead>"
							+"<ywdjbh>0001</ywdjbh>"
							+"<bizdate>2015-05-13</bizdate>"
							+"<ldh>HT0002</ldh>"
							+"<qydw>K000000</qydw>"// K000000  0002500470
							+"<djdw>K000000</djdw>"
							+"<kpdw>K000000</kpdw>"
							+"<skdw>K000000</skdw>"
							+"<zjjg>K000000</zjjg>"
							+"<fph>1111</fph>"
							+"<xsy>N000003</xsy>"//    N000003  020034
							+"<tjlb>001|团检</tjlb>"
							+"<beizhu>111</beizhu>"
							+"<djr>李四</djr>"
							+"<djtcbm>TC0001</djtcbm>"
							+"<djtcmc>A套餐</djtcmc>"
							+"<djxmbm>XM0001</djxmbm>"
							+"<djxmmc>A项目</djxmmc>"
							+"<xslb>001|销售员</xslb>"
							+"<sklb>001|现金</sklb>"
							+"<jxbs>0</jxbs>"
							+"<klj>1</klj>"
							+"<zkl>10</zkl>"
							+"<jsje>2000</jsje>"
							+"<se>0</se>"
							+"<jshj>2000</jshj>"
							+"<djjg>NJP</djjg>"  // 01  NJP
							+"<kpjg>NJP</kpjg>"
							+"<kh>9999999</kh>"
							+"<flag>0</flag >"
						+"</billHead>"
						+"<billEntries>"
						+"   <entry>"
						+"   </entry>"
						+"</billEntries>"
					+"</TempTable>";
    		
    		String str2 = "<TempTable>" +
						"<billHead>"
    					+"<end>1</end>"
						+"<ywdjbh>0001</ywdjbh>"
						+"<bizdate>2015-06-2</bizdate>"
						+"<ldh>HT0002</ldh>"
						+"<qydw>0002500470|江苏千年珠宝有限公司</qydw>"// K000000  0002500470
						+"<djdw>0002500470|江苏千年珠宝有限公司</djdw>"
						+"<kpdw>0002500470|江苏千年珠宝有限公司</kpdw>"
						+"<skdw>0002500470|江苏千年珠宝有限公司</skdw>"
						+"<zjjg>0002500470|江苏千年珠宝有限公司</zjjg>"
						+"<fph>1111</fph>"
						+"<xsy>020034|祝涛斋</xsy>"//    N000003  020034
						+"<tjlb>001|团检</tjlb>"
						+"<beizhu>111</beizhu>"
						+"<djr>李四</djr>"
						+"<djtcbm>TC0001</djtcbm>"
						+"<djtcmc>A套餐</djtcmc>"
						+"<djxmbm>XM0001</djxmbm>"
						+"<djxmmc>A项目</djxmmc>"
						+"<xslb>001|销售员</xslb>"
						+"<sklb>001|现收</sklb>"
						+"<jxbs>0</jxbs>"
						+"<klj>1</klj>"
						+"<zkl>10</zkl>"
						+"<jsje>2000</jsje>"
						+"<se>0</se>"
						+"<jshj>2000</jshj>"
						+"<djjg>3|上海瑞慈瑞宁门诊部有限公司</djjg>"  // 01  NJP
						+"<kpjg>3|上海瑞慈瑞宁门诊部有限公司</kpjg>"
						+"<kh>9999999</kh>"
						+"<flag>0</flag >"
					+"</billHead>"
					+"<billEntries>"
					+"   <entry>"
					+"   </entry>"
					+"</billEntries>"
				+"</TempTable>";
    		String str3 ="<TempTable><billHead><end>0</end><ywdjbh>001505203107</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15004572</ldh><kh></kh><qydw>0001003|上海天服三悦服装有限公司</qydw><djdw>0001003|上海天服三悦服装有限公司</djdw><kpdw>0001003|上海天服三悦服装有限公司</kpdw><skdw>0001003|上海天服三悦服装有限公司</skdw><fph>0200044073</fph><xsy>020021|周瑾</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>江金凤</djr><djtcbm>1500457201</djtcbm><djtcmc>2015年天服三悦女已婚套餐</djtcmc><djxmbm>100018</djxmbm><djxmmc>血脂四项</djxmmc><xslb>003|非销售</xslb><sklb>001</sklb><jxbs>0</jxbs><klj>40.00</klj><zkl>0.4425</zkl><jsje>17.70</jsje><se></se><jshj>17.70</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>001505203107</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15004572</ldh><kh></kh><qydw>0001003|上海天服三悦服装有限公司</qydw><djdw>0001003|上海天服三悦服装有限公司</djdw><kpdw>0001003|上海天服三悦服装有限公司</kpdw><skdw>0001003|上海天服三悦服装有限公司</skdw><fph>0200044073</fph><xsy>020021|周瑾</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>江金凤</djr><djtcbm>1500457201</djtcbm><djtcmc>2015年天服三悦女已婚套餐</djtcmc><djxmbm>035001</djxmbm><djxmmc>胸片</djxmmc><xslb>003|非销售</xslb><sklb>001</sklb><jxbs>0</jxbs><klj>60.00</klj><zkl>0.4425</zkl><jsje>26.55</jsje><se></se><jshj>26.55</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>001505203108</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15004572</ldh><kh></kh><qydw>0001003|上海天服三悦服装有限公司</qydw><djdw>0001003|上海天服三悦服装有限公司</djdw><kpdw>0001003|上海天服三悦服装有限公司</kpdw><skdw>0001003|上海天服三悦服装有限公司</skdw><fph></fph><xsy>020021|周瑾</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>李钟奇</djr><djtcbm>1500457203</djtcbm><djtcmc>2015年天服三悦男性套餐</djtcmc><djxmbm>037001</djxmbm><djxmmc>静态心电图检查（ECG）</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>30.00</klj><zkl>0.4894</zkl><jsje>14.68</jsje><se></se><jshj>14.68</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>001505203109</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15004572</ldh><kh></kh><qydw>0001003|上海天服三悦服装有限公司</qydw><djdw>0001003|上海天服三悦服装有限公司</djdw><kpdw>0001003|上海天服三悦服装有限公司</kpdw><skdw>0001003|上海天服三悦服装有限公司</skdw><fph></fph><xsy>020021|周瑾</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>黄平</djr><djtcbm>1500457203</djtcbm><djtcmc>2015年天服三悦男性套餐</djtcmc><djxmbm>011001</djxmbm><djxmmc>空腹血糖（GLU）</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.4894</zkl><jsje>4.89</jsje><se></se><jshj>4.89</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>021505240005</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15004572</ldh><kh></kh><qydw>0001003|上海天服三悦服装有限公司</qydw><djdw>0001003|上海天服三悦服装有限公司</djdw><kpdw>0001003|上海天服三悦服装有限公司</kpdw><skdw>0001003|上海天服三悦服装有限公司</skdw><fph>0200044075</fph><xsy>020021|周瑾</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>佟民凤</djr><djtcbm>1500457201</djtcbm><djtcmc>2015年天服三悦女已婚套餐</djtcmc><djxmbm>100008</djxmbm><djxmmc>眼科检查B</djxmmc><xslb>003|非销售</xslb><sklb>001</sklb><jxbs>0</jxbs><klj>15.00</klj><zkl>0.4425</zkl><jsje>6.64</jsje><se></se><jshj>6.64</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>001505200151</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15005998</ldh><kh></kh><qydw>0400427|上海招商银行（融信）</qydw><djdw>0400427|上海招商银行（融信）</djdw><kpdw>0400427|上海招商银行（融信）</kpdw><skdw>0400427|上海招商银行（融信）</skdw><fph></fph><xsy>020055|姚霞</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>马昉</djr><djtcbm>1500599804</djtcbm><djtcmc>2015年招行正式员工男基础+B1+B2</djtcmc><djxmbm>017013</djxmbm><djxmmc>血清γ-谷氨酰基转移酶（GGT）</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.5051</zkl><jsje>5.05</jsje><se></se><jshj>5.05</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>001505201738</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15005998</ldh><kh></kh><qydw>0400427|上海招商银行（融信）</qydw><djdw>0400427|上海招商银行（融信）</djdw><kpdw>0400427|上海招商银行（融信）</kpdw><skdw>0400427|上海招商银行（融信）</skdw><fph></fph><xsy>020055|姚霞</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>钟黎</djr><djtcbm>1500599816</djtcbm><djtcmc>2015年招行正式员工女已婚基础+A2+B1+B2</djtcmc><djxmbm>017006</djxmbm><djxmmc>血清天冬氨酸氨基转移酶（AST）</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.4924</zkl><jsje>4.92</jsje><se></se><jshj>4.92</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>021505240001</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15002936</ldh><kh></kh><qydw>0000153715|农业部东海区渔政局中国渔政东海总队</qydw><djdw>0000153715|农业部东海区渔政局中国渔政东海总队</djdw><kpdw>0000153715|农业部东海区渔政局中国渔政东海总队</kpdw><skdw>0000153715|农业部东海区渔政局中国渔政东海总队</skdw><fph></fph><xsy>030094|吕媛媛</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>陆强</djr><djtcbm>1500293601</djtcbm><djtcmc>2015年渔政东海总队男套餐</djtcmc><djxmbm>040005</djxmbm><djxmmc>甲状腺超声检查</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>50.00</klj><zkl>0.9237</zkl><jsje>46.19</jsje><se></se><jshj>46.19</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>021505240002</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15002936</ldh><kh></kh><qydw>0000153715|农业部东海区渔政局中国渔政东海总队</qydw><djdw>0000153715|农业部东海区渔政局中国渔政东海总队</djdw><kpdw>0000153715|农业部东海区渔政局中国渔政东海总队</kpdw><skdw>0000153715|农业部东海区渔政局中国渔政东海总队</skdw><fph></fph><xsy>030094|吕媛媛</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>王祚龙</djr><djtcbm>1500293601</djtcbm><djtcmc>2015年渔政东海总队男套餐</djtcmc><djxmbm>100021</djxmbm><djxmmc>血粘度</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>52.00</klj><zkl>0.9237</zkl><jsje>48.03</jsje><se></se><jshj>48.03</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>0</end><ywdjbh>021505240003</ywdjbh><bizdate>2015-05-24</bizdate><ldh>15002936</ldh><kh></kh><qydw>0000153715|农业部东海区渔政局中国渔政东海总队</qydw><djdw>0000153715|农业部东海区渔政局中国渔政东海总队</djdw><kpdw>0000153715|农业部东海区渔政局中国渔政东海总队</kpdw><skdw>0000153715|农业部东海区渔政局中国渔政东海总队</skdw><fph></fph><xsy>030094|吕媛媛</xsy><tjlb>001|团检</tjlb><beizhu></beizhu><djr>曲伟峰</djr><djtcbm>1500293601</djtcbm><djtcmc>2015年渔政东海总队男套餐</djtcmc><djxmbm>040003</djxmbm><djxmmc>双肾超声检查</djxmmc><xslb>003|非销售</xslb><sklb>002</sklb><jxbs>0</jxbs><klj>20.00</klj><zkl>0.9237</zkl><jsje>18.47</jsje><se></se><jshj>18.47</jshj><djjg>2|上海瑞慈门诊部</djjg><kpjg>2|上海瑞慈门诊部</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable>";
			sb.append(str3);
		}
    	
    	String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    			"<TempTables>" +
    				sb.toString()
			+"</TempTables>";
    	return str;
    }
    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionPrintPreview_actionPerformed(e);
    	String[] str = new String[3];
		EASLoginProxy login;
		try {
			login = new EASLoginProxyServiceLocator().getEASLogin( new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//			WSContext  ws = login.login("user", "", "eas", "njp", "l2", 1);
//			login = new EASLoginProxyServiceLocator().getEASLogin( new URL("http://172.3.1.105:6888/ormrpc/services/EASLogin"));
			WSContext  ws = login.login("user", "kingdee", "eas", "TEST201501020", "l2", 1);
		   	if(ws.getSessionId()!=null){
//		   		WSEASRichFacadeSrvProxy pay = new WSEASRichFacadeSrvProxyServiceLocator().getWSEASRichFacade(new URL("http://172.3.1.105:6888/ormrpc/services/WSEASRichFacade"));
		  		IEASRichFacade pay = EASRichFacadeFactory.getRemoteInstance();
		   		str = pay.saveTempData(getString());
		  	}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		MsgBox.showInfo(str[0]+str[1]+str[2]);
    }
    
    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionPrint_actionPerformed(e);
    	EASRichFacadeFactory.getRemoteInstance().saveExamBill(new Date(), "");
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

 


    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichExamTempTabFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichExamTempTabInfo objectValue = new com.kingdee.eas.custom.richinf.RichExamTempTabInfo();
		
        return objectValue;
    }

}