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
    	for (int i = 0; i < 1; i++) {
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
							+"<tjlb>001|�ż�</tjlb>"
							+"<beizhu>111</beizhu>"
							+"<djr>����</djr>"
							+"<djtcbm>TC0001</djtcbm>"
							+"<djtcmc>A�ײ�</djtcmc>"
							+"<djxmbm>XM0001</djxmbm>"
							+"<djxmmc>A��Ŀ</djxmmc>"
							+"<xslb>001|����Ա</xslb>"
							+"<sklb>001|�ֽ�</sklb>"
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
						+"<bizdate>2015-06-29</bizdate>"
						+"<djrq>2015-06-28</djrq>"
						+"<ldh>HT0002</ldh>"
						+"<qydw>00025004701|����ǧ���鱦���޹�˾</qydw>"// K000000  0002500470
						+"<djdw>0002500470|����ǧ���鱦���޹�˾</djdw>"
						+"<kpdw>0002500470|����ǧ���鱦���޹�˾</kpdw>"
						+"<skdw>0002500470|����ǧ���鱦���޹�˾</skdw>"
						+"<zjjg>0002500470|����ǧ���鱦���޹�˾</zjjg>"
						+"<fph>1111</fph>"
						+"<xsy>020034|ף��ի</xsy>"//    N000003  020034
						+"<tjlb>001|�ż�</tjlb>"
						+"<beizhu>111</beizhu>"
						+"<djr>����</djr>"
						+"<djtcbm>TC0001</djtcbm>"
						+"<djtcmc>A�ײ�</djtcmc>"
						+"<djxmbm>XM0001</djxmbm>"
						+"<djxmmc>A��Ŀ</djxmmc>"
						+"<xslb>001|����Ա</xslb>"
						+"<sklb>001|����</sklb>"
						+"<jxbs>0</jxbs>"
						+"<klj>1</klj>"
						+"<zkl>10</zkl>"
						+"<jsje>2000</jsje>"
						+"<se>0</se>"
						+"<jshj>2000</jshj>"
						+"<djjg>3|�Ϻ�����������ﲿ���޹�˾</djjg>"  // 01  NJP
						+"<kpjg>3|�Ϻ�����������ﲿ���޹�˾</kpjg>"
						+"<kh>9999999</kh>"
						+"<flag>0</flag >"
					+"</billHead>"
					+"<billEntries>"
					+"   <entry>"
					+"   </entry>"
					+"</billEntries>"
				+"</TempTable>";
    		String str3 ="<TempTable><billHead><end>1</end><ywdjbh>001506256298</ywdjbh><bizdate>2015-07-01</bizdate><ldh>10003673</ldh><kh></kh><qydw>0512000432|�����Ϻ����ü���������˾</qydw><djdw>0512000432|�����Ϻ����ü���������˾</djdw><kpdw>0512000432|�����Ϻ����ü���������˾</kpdw><skdw>0512000432|�����Ϻ����ü���������˾</skdw><fph></fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>�˴�Ӣ</djr><djtcbm>1000367306</djtcbm><djtcmc>2012������������Ů�ѻ�B�ײ�</djtcmc><djxmbm>100012</djxmbm><djxmmc>���Ƽ��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.3740</zkl><jsje>3.74</jsje><se></se><jshj>3.74</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506181308</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15008660</ldh><kh></kh><qydw>11|11</qydw><djdw>11|11</djdw><kpdw>11|11</kpdw><skdw>11|11</skdw><fph></fph><xsy>040039|Ԭ�˷�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500866003</djtcbm><djtcmc>2015��Ϋ��������ְŮ�ѻ�</djtcmc><djxmbm>100008</djxmbm><djxmmc>�ۿƼ��B</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>15.00</klj><zkl>0.8092</zkl><jsje>12.14</jsje><se></se><jshj>12.14</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271952</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>֣�ľ�</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>049003</djxmbm><djxmmc>���---���챨��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>7.00</klj><zkl>1.0000</zkl><jsje>7.00</jsje><se></se><jshj>7.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271952</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>֣�ľ�</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>100031</djxmbm><djxmmc>�򳣹�</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>15.00</klj><zkl>0.5956</zkl><jsje>8.93</jsje><se></se><jshj>8.93</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271953</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>�ͺ�Ӣ</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>100040</djxmbm><djxmmc>�״�����</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.5956</zkl><jsje>5.96</jsje><se></se><jshj>5.96</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506256297</ywdjbh><bizdate>2015-07-01</bizdate><ldh>10003673</ldh><kh></kh><qydw>0512000432|�����Ϻ����ü���������˾</qydw><djdw>0512000432|�����Ϻ����ü���������˾</djdw><kpdw>0512000432|�����Ϻ����ü���������˾</kpdw><skdw>0512000432|�����Ϻ����ü���������˾</skdw><fph></fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1000367306</djtcbm><djtcmc>2012������������Ů�ѻ�B�ײ�</djtcmc><djxmbm>035001</djxmbm><djxmmc>��Ƭ</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>50.00</klj><zkl>0.3740</zkl><jsje>18.70</jsje><se></se><jshj>18.70</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506255955</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15007178</ldh><kh></kh><qydw>0021003626|�й����������Ϻ��ֶ�����</qydw><djdw>0021003626|�й����������Ϻ��ֶ�����</djdw><kpdw>0021003626|�й����������Ϻ��ֶ�����</kpdw><skdw>0021003626|�й����������Ϻ��ֶ�����</skdw><fph>0200045204</fph><xsy>110014|��ǧ��</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500717801</djtcbm><djtcmc>2015�꽨���ֶ�����������Ա���ײ�</djtcmc><djxmbm>040011</djxmbm><djxmmc>ǰ���ٳ������</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>50.00</klj><zkl>1.0680</zkl><jsje>53.40</jsje><se></se><jshj>53.40</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010001</ywdjbh><bizdate>2015-07-01</bizdate><ldh>14013675</ldh><kh></kh><qydw></qydw><djdw></djdw><kpdw></kpdw><skdw></skdw><fph></fph><xsy>020049|����</xsy><tjlb>002|����</tjlb><beizhu></beizhu><djr>����</djr><djtcbm>1401367501</djtcbm><djtcmc>2015�����ʶ���Ů�ѻ���ְ�ײ�</djtcmc><djxmbm>035003</djxmbm><djxmmc>��׵X����Ӱ��飨��λ��</djxmmc><xslb>001|����Ա</xslb><sklb>001|����</sklb><jxbs>0</jxbs><klj>50.00</klj><zkl>0.6478</zkl><jsje>32.39</jsje><se></se><jshj>32.39</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506181191</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15008660</ldh><kh></kh><qydw>1|1</qydw><djdw>1|1</djdw><kpdw>1|1</kpdw><skdw>1|1</skdw><fph></fph><xsy>040039|Ԭ�˷�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>����ٻ</djr><djtcbm>1500866003</djtcbm><djtcmc>2015��Ϋ��������ְŮ�ѻ�</djtcmc><djxmbm>049006</djxmbm><djxmmc>���---�ż�1Ԫ����</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>1.00</klj><zkl>1.0000</zkl><jsje>1.00</jsje><se></se><jshj>1.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010006</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15007176</ldh><kh></kh><qydw>0021003677|����������ѯ���Ϻ������޹�˾</qydw><djdw>0021003677|����������ѯ���Ϻ������޹�˾</djdw><kpdw>0021003677|����������ѯ���Ϻ������޹�˾</kpdw><skdw>0021003677|����������ѯ���Ϻ������޹�˾</skdw><fph>0200045205</fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>ëѩ��</djr><djtcbm>1500717603</djtcbm><djtcmc>2015���������Ůδ���ײ�</djtcmc><djxmbm>048003</djxmbm><djxmmc>�������A</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>6.00</klj><zkl>1.0000</zkl><jsje>6.00</jsje><se></se><jshj>6.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010006</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15007176</ldh><kh></kh><qydw>0021003677|����������ѯ���Ϻ������޹�˾</qydw><djdw>0021003677|����������ѯ���Ϻ������޹�˾</djdw><kpdw>0021003677|����������ѯ���Ϻ������޹�˾</kpdw><skdw>0021003677|����������ѯ���Ϻ������޹�˾</skdw><fph>0200045205</fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>ëѩ��</djr><djtcbm>1500717603</djtcbm><djtcmc>2015���������Ůδ���ײ�</djtcmc><djxmbm>040002</djxmbm><djxmmc>�Ρ������ȡ�Ƣ�������</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>80.00</klj><zkl>0.5214</zkl><jsje>41.71</jsje><se></se><jshj>41.71</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506256298</ywdjbh><bizdate>2015-07-01</bizdate><ldh>10003673</ldh><kh></kh><qydw>0512000432|�����Ϻ����ü���������˾</qydw><djdw>0512000432|�����Ϻ����ü���������˾</djdw><kpdw>0512000432|�����Ϻ����ü���������˾</kpdw><skdw>0512000432|�����Ϻ����ü���������˾</skdw><fph></fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>�˴�Ӣ</djr><djtcbm>1000367306</djtcbm><djtcmc>2012������������Ů�ѻ�B�ײ�</djtcmc><djxmbm>017005</djxmbm><djxmmc>Ѫ������ᰱ��ת��ø��ALT��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.3740</zkl><jsje>3.74</jsje><se></se><jshj>3.74</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271951</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>007012</djxmbm><djxmmc>�״����棨���ƣ�</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>0.00</klj><zkl>0.5956</zkl><jsje>0.00</jsje><se></se><jshj>0.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271951</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>100031</djxmbm><djxmmc>�򳣹�</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>15.00</klj><zkl>0.5956</zkl><jsje>8.93</jsje><se></se><jshj>8.93</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506256297</ywdjbh><bizdate>2015-07-01</bizdate><ldh>10003673</ldh><kh></kh><qydw>0512000432|�����Ϻ����ü���������˾</qydw><djdw>0512000432|�����Ϻ����ü���������˾</djdw><kpdw>0512000432|�����Ϻ����ü���������˾</kpdw><skdw>0512000432|�����Ϻ����ü���������˾</skdw><fph></fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1000367306</djtcbm><djtcmc>2012������������Ů�ѻ�B�ײ�</djtcmc><djxmbm>017005</djxmbm><djxmmc>Ѫ������ᰱ��ת��ø��ALT��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.3740</zkl><jsje>3.74</jsje><se></se><jshj>3.74</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506292633</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15001134</ldh><kh></kh><qydw>0021001725|�Ϻ��ֶ���˼�����پƵ� </qydw><djdw>0021001725|�Ϻ��ֶ���˼�����پƵ�</djdw><kpdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </kpdw><skdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </skdw><fph></fph><xsy>020055|Ҧϼ</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>�ѩӨ</djr><djtcbm>1500113402</djtcbm><djtcmc>2015����˼������Ů�ѻ�38������ײ�</djtcmc><djxmbm>040002</djxmbm><djxmmc>�Ρ������ȡ�Ƣ�������</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>80.00</klj><zkl>0.4899</zkl><jsje>39.19</jsje><se></se><jshj>39.19</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506292632</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15001134</ldh><kh></kh><qydw>0021001725|�Ϻ��ֶ���˼�����پƵ� </qydw><djdw>0021001725|�Ϻ��ֶ���˼�����پƵ�</djdw><kpdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </kpdw><skdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </skdw><fph></fph><xsy>020055|Ҧϼ</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500113402</djtcbm><djtcmc>2015����˼������Ů�ѻ�38������ײ�</djtcmc><djxmbm>048003</djxmbm><djxmmc>�������A</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>6.00</klj><zkl>1.0000</zkl><jsje>6.00</jsje><se></se><jshj>6.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506292632</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15001134</ldh><kh></kh><qydw>0021001725|�Ϻ��ֶ���˼�����پƵ� </qydw><djdw>0021001725|�Ϻ��ֶ���˼�����پƵ�</djdw><kpdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </kpdw><skdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </skdw><fph></fph><xsy>020055|Ҧϼ</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500113402</djtcbm><djtcmc>2015����˼������Ů�ѻ�38������ײ�</djtcmc><djxmbm>007012</djxmbm><djxmmc>�״����棨���ƣ�</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>0.00</klj><zkl>0.4899</zkl><jsje>0.00</jsje><se></se><jshj>0.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506292632</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15001134</ldh><kh></kh><qydw>0021001725|�Ϻ��ֶ���˼�����پƵ� </qydw><djdw>0021001725|�Ϻ��ֶ���˼�����پƵ�</djdw><kpdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </kpdw><skdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </skdw><fph></fph><xsy>020055|Ҧϼ</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500113402</djtcbm><djtcmc>2015����˼������Ů�ѻ�38������ײ�</djtcmc><djxmbm>100031</djxmbm><djxmmc>�򳣹�</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>15.00</klj><zkl>0.4899</zkl><jsje>7.35</jsje><se></se><jshj>7.35</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010008</ywdjbh><bizdate>2015-07-01</bizdate><ldh></ldh><kh></kh><qydw></qydw><djdw></djdw><kpdw></kpdw><skdw></skdw><fph>0200045203</fph><xsy>999997|ҽ��</xsy><tjlb>002|����</tjlb><beizhu></beizhu><djr>�����</djr><djtcbm>354</djtcbm><djtcmc>��ҵ��Ӣ���ǣ�Ůδ�飩</djtcmc><djxmbm>100018</djxmbm><djxmmc>Ѫ֬����</djxmmc><xslb>001|����Ա</xslb><sklb>001|����</sklb><jxbs>0</jxbs><klj>40.00</klj><zkl>0.6000</zkl><jsje>24.00</jsje><se></se><jshj>24.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001505191071</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15005865</ldh><kh></kh><qydw>0021004089|���������׶�԰</qydw><djdw>0021004089|���������׶�԰</djdw><kpdw>0021004089|���������׶�԰</kpdw><skdw>0021004089|���������׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500586502</djtcbm><djtcmc>2015�궫�������׶�԰Ů�ѻ�A�ײ�</djtcmc><djxmbm>022022</djxmbm><djxmmc>θ����øԭ�ⶨ</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>180.00</klj><zkl>0.5995</zkl><jsje>107.91</jsje><se></se><jshj>107.91</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010011</ywdjbh><bizdate>2015-07-01</bizdate><ldh>13009002</ldh><kh></kh><qydw>0021000597|�Ϻ��ж���������޹�˾</qydw><djdw>0021000597|�Ϻ��ж���������޹�˾</djdw><kpdw>0021000597|�Ϻ��ж���������޹�˾</kpdw><skdw>0021000597|�Ϻ��ж���������޹�˾</skdw><fph>0200045207</fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1300900207</djtcbm><djtcmc>2013���Ϻ����Ů��ְ�ײ�(�ֽ�270)</djtcmc><djxmbm>100057</djxmbm><djxmmc>ѪҺ����(������)</djxmmc><xslb>001|����Ա</xslb><sklb>001|����</sklb><jxbs>0</jxbs><klj>20.00</klj><zkl>0.6633</zkl><jsje>13.27</jsje><se></se><jshj>13.27</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271953</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>�ͺ�Ӣ</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>076001</djxmbm><djxmmc>ͬ�Ͱ��װ��ᣨHCY��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>120.00</klj><zkl>0.5956</zkl><jsje>71.47</jsje><se></se><jshj>71.47</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271951</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>100020</djxmbm><djxmmc>�ļ�ø������</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>55.00</klj><zkl>0.5956</zkl><jsje>32.76</jsje><se></se><jshj>32.76</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506256297</ywdjbh><bizdate>2015-07-01</bizdate><ldh>10003673</ldh><kh></kh><qydw>0512000432|�����Ϻ����ü���������˾</qydw><djdw>0512000432|�����Ϻ����ü���������˾</djdw><kpdw>0512000432|�����Ϻ����ü���������˾</kpdw><skdw>0512000432|�����Ϻ����ü���������˾</skdw><fph></fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>������</djr><djtcbm>1000367306</djtcbm><djtcmc>2012������������Ů�ѻ�B�ײ�</djtcmc><djxmbm>100012</djxmbm><djxmmc>���Ƽ��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.3740</zkl><jsje>3.74</jsje><se></se><jshj>3.74</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506292633</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15001134</ldh><kh></kh><qydw>0021001725|�Ϻ��ֶ���˼�����پƵ� </qydw><djdw>0021001725|�Ϻ��ֶ���˼�����پƵ�</djdw><kpdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </kpdw><skdw>0021001725|�Ϻ��ֶ���˼�����پƵ� </skdw><fph></fph><xsy>020055|Ҧϼ</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>�ѩӨ</djr><djtcbm>1500113402</djtcbm><djtcmc>2015����˼������Ů�ѻ�38������ײ�</djtcmc><djxmbm>100057</djxmbm><djxmmc>ѪҺ����(������)</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>20.00</klj><zkl>0.4899</zkl><jsje>9.80</jsje><se></se><jshj>9.80</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506255838</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15007178</ldh><kh></kh><qydw>0021003626|�й����������Ϻ��ֶ�����</qydw><djdw>0021003626|�й����������Ϻ��ֶ�����</djdw><kpdw>0021003626|�й����������Ϻ��ֶ�����</kpdw><skdw>0021003626|�й����������Ϻ��ֶ�����</skdw><fph></fph><xsy>110014|��ǧ��</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>ʱ��</djr><djtcbm>1500717802</djtcbm><djtcmc>2015�꽨���ֶ���������Ů�ѻ�Ա���ײ�</djtcmc><djxmbm>049003</djxmbm><djxmmc>���---���챨��</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>7.00</klj><zkl>1.0000</zkl><jsje>7.00</jsje><se></se><jshj>7.00</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506181354</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15008660</ldh><kh></kh><qydw>1|1</qydw><djdw>1|1</djdw><kpdw>1|1</kpdw><skdw>1|1</skdw><fph></fph><xsy>040039|Ԭ�˷�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>ׯ��</djr><djtcbm>1500866002</djtcbm><djtcmc>2015��Ϋ��������ְŮδ��</djtcmc><djxmbm>100031</djxmbm><djxmmc>�򳣹�</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>15.00</klj><zkl>0.8185</zkl><jsje>12.28</jsje><se></se><jshj>12.28</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010003</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15008901</ldh><kh></kh><qydw>1|1</qydw><djdw>1|1</djdw><kpdw>1|1</kpdw><skdw>1|1</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>��־��</djr><djtcbm>1500890101</djtcbm><djtcmc>2015�������������ó����������ײ�</djtcmc><djxmbm>100010</djxmbm><djxmmc>���Ǻ���</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.6500</zkl><jsje>6.50</jsje><se></se><jshj>6.50</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010004</ywdjbh><bizdate>2015-07-01</bizdate><ldh></ldh><kh></kh><qydw>0513001334|�����è�콢��</qydw><djdw>0513001334|�����è�콢��</djdw><kpdw>0513001334|�����è�콢��</kpdw><skdw>0513001334|�����è�콢��</skdw><fph></fph><xsy>020190|���</xsy><tjlb>002|����</tjlb><beizhu></beizhu><djr>��־��</djr><djtcbm>375</djtcbm><djtcmc>2013��������ѻ�Ů���Ϻ���</djtcmc><djxmbm>100010</djxmbm><djxmmc>���Ǻ���</djxmmc><xslb>001|����Ա</xslb><sklb>001|����</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.5797</zkl><jsje>5.80</jsje><se></se><jshj>5.80</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>021507010006</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15007176</ldh><kh></kh><qydw>0021003677|����������ѯ���Ϻ������޹�˾</qydw><djdw>0021003677|����������ѯ���Ϻ������޹�˾</djdw><kpdw>0021003677|����������ѯ���Ϻ������޹�˾</kpdw><skdw>0021003677|����������ѯ���Ϻ������޹�˾</skdw><fph>0200045205</fph><xsy>020022|��ѩ�</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>ëѩ��</djr><djtcbm>1500717603</djtcbm><djtcmc>2015���������Ůδ���ײ�</djtcmc><djxmbm>100001</djxmbm><djxmmc>һ����A</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.5214</zkl><jsje>5.20</jsje><se></se><jshj>5.20</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506253874</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15009276</ldh><kh></kh><qydw>0021005574|�Ϻ������ɹ������޹�˾</qydw><djdw>0021005574|�Ϻ������ɹ������޹�˾</djdw><kpdw>0021005574|�Ϻ������ɹ������޹�˾</kpdw><skdw>0021005574|�Ϻ������ɹ������޹�˾</skdw><fph></fph><xsy>030001|��·ң</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>Ѧƽ</djr><djtcbm>1500927603</djtcbm><djtcmc>2015������Ů�ѻ鸣���ײ�</djtcmc><djxmbm>100001</djxmbm><djxmmc>һ����A</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>10.00</klj><zkl>0.4458</zkl><jsje>4.50</jsje><se></se><jshj>4.50</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506253874</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15009276</ldh><kh></kh><qydw>0021005574|�Ϻ������ɹ������޹�˾</qydw><djdw>0021005574|�Ϻ������ɹ������޹�˾</djdw><kpdw>0021005574|�Ϻ������ɹ������޹�˾</kpdw><skdw>0021005574|�Ϻ������ɹ������޹�˾</skdw><fph></fph><xsy>030001|��·ң</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>Ѧƽ</djr><djtcbm>1500927603</djtcbm><djtcmc>2015������Ů�ѻ鸣���ײ�</djtcmc><djxmbm>100023</djxmbm><djxmmc>�ι���ʮ����</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>110.00</klj><zkl>0.4458</zkl><jsje>49.04</jsje><se></se><jshj>49.04</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable><TempTable><billHead><end>1</end><ywdjbh>001506271952</ywdjbh><bizdate>2015-07-01</bizdate><ldh>15004438</ldh><kh></kh><qydw>0021001249|�����׶�԰</qydw><djdw>0021001249|�����׶�԰</djdw><kpdw>0021001249|�����׶�԰</kpdw><skdw>0021001249|�����׶�԰</skdw><fph></fph><xsy>020034|ף��ի</xsy><tjlb>001|�ż�</tjlb><beizhu></beizhu><djr>֣�ľ�</djr><djtcbm>1500443813</djtcbm><djtcmc>2015�갮���׶�԰Ů�ѻ������ײ�</djtcmc><djxmbm>040005</djxmbm><djxmmc>��״�ٳ������</djxmmc><xslb>001|����Ա</xslb><sklb>002|������</sklb><jxbs>0</jxbs><klj>50.00</klj><zkl>0.5956</zkl><jsje>29.78</jsje><se></se><jshj>29.78</jshj><djjg>2|�Ϻ�������ﲿ</djjg><kpjg>2|�Ϻ�������ﲿ</kpjg><flag>0</flag></billHead><billEntries><entry></entry></billEntries></TempTable>";
			//sb.append(str3);
			
			//by shilei
    		String newstr = "<TempTable>" +
						"<billHead>"
						+"<ywdjbh>0001</ywdjbh>"
						+"<bizdate>2015-06-06</bizdate>"
						+"<ldh>HT0002</ldh>"
						+"<qydw>1.01.001|�����ű���</qydw>"// K000000  0002500470//ǩԼ��λ
						+"<djdw>1.01.001|�����ű���</djdw>"//���쵥λ
						+"<kpdw>1.01.001|�����ű���</kpdw>"//��Ʊ��λ
						+"<skdw>1.01.001|�����ű���</skdw>"//���λ
						+"<zjjg>1.01.001|�����ű���</zjjg>"//�н鵥λ
						+"<fph>1111</fph>"//��Ʊ��
						+"<xsy>dlz|������</xsy>"//    N000003  020034 //����Ա
						+"<tjlb>001|ȫ�����</tjlb>"//������
						+"<beizhu>111</beizhu>"// ��ע
						+"<djr>����</djr>"//������
						+"<djtcbm>TC0001</djtcbm>"//�����ײͱ���
						+"<djtcmc>A�ײ�</djtcmc>"//�����ײ�����
						+"<djxmbm>XM0001</djxmbm>"//������Ŀ����
						+"<djxmmc>A��Ŀ</djxmmc>"//��Ŀ����
						+"<xslb>001|һ��������</xslb>"//�������
						+"<sklb>001|Ӧ�տ�</sklb>"//�տ����
						+"<jxbs>0</jxbs>"//
						+"<klj>1</klj>"//�����
						+"<zkl>10</zkl>"//
						+"<jsje>2000</jsje>"
						+"<se>0</se>"
						+"<jshj>2000</jshj>"
						+"<djjg>010000|�����ű���</djjg>"  // 01  NJP//�������
						+"<kpjg>010000|�����ű���</kpjg>"//��Ʊ����
						+"<kh>9999999</kh>"
						+"<end>0</end>"
						+"<flag>0</flag >"//�Ƿ��޸�����//�޸�����Ա�����쵥λ�������ɸ����м�������������
					+"</billHead>"
					+"<billEntries>"
					+"   <entry>"
					+"   </entry>"
					+"</billEntries>"
				+"</TempTable>";
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
//			login = new EASLoginProxyServiceLocator().getEASLogin( new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//			WSContext  ws = login.login("user", "", "eas", "njp", "l2", 1);
//			login = new EASLoginProxyServiceLocator().getEASLogin( new URL("http://172.3.1.105:6888/ormrpc/services/EASLogin"));
//			WSContext  ws = login.login("user", "kingdee1234a", "eas", "TEXT20150630", "l2", 1);
//		   	if(ws.getSessionId()!=null){
//		   		WSEASRichFacadeSrvProxy pay = new WSEASRichFacadeSrvProxyServiceLocator().getWSEASRichFacade(new URL("http://172.3.1.105:6888/ormrpc/services/WSEASRichFacade"));
		  		IEASRichFacade pay = EASRichFacadeFactory.getRemoteInstance();
		   		str = pay.saveTempData(getString());
//		  	}
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
    	String[] str = new String[3];
    	str = EASRichFacadeFactory.getRemoteInstance().saveExamBill(Utils.parseCustomDateString("2015-06-06", "yyyy-MM-dd"), "");
    	MsgBox.showInfo(str[0]+str[1]+str[2]);
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