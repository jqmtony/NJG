package com.kingdee.eas.bpm.common;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.DBUtil;
import com.kingdee.eas.bpm.ApproveBackSetEntryInfo;
import com.kingdee.eas.bpm.ApproveBackSetFactory;
import com.kingdee.eas.bpm.ApproveBackSetInfo;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.ar.app.webservice.util.WrongArgumentException;

public class UpdateUtil {

	public static String[]  updateValue(Context ctx,String BTID,String BOID,String xml) {
		String[] str = new String[3];
		XMLUtil builder = null;
        try
        {
            try {
				builder = new XMLUtil(xml, "");
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        catch(WrongArgumentException e)
        {
            return (new String[] {  "N", e.getTypeNumber(), "XML��ʽ����" });
        }
        Map head = builder.getHeadProperties("DATA");//��ȡ��ͷ
        List entries = builder.getEntriesProperties("//billEntries/entry"); //��ȡ��¼
		ApproveBackSetInfo info = null;
		try {
			info = ApproveBackSetFactory.getLocalInstance(ctx).getApproveBackSetInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",δ�ҵ�������д���ã��������Ӧ����";
			str[2] = "����ID-BOID:"+BOID;
		}
		String table = info.getName();
		StringBuffer sql = new StringBuffer();
		sql.append(" update  "+table +" set " );
		for(int i=0;i<info.getEntry().size();i++){
			ApproveBackSetEntryInfo entry = info.getEntry().get(i);
			if(head.get(entry.getField())!=null && !"".equals(head.get(entry.getField())))
				sql.append(" F"+entry.getField() +" ='"+head.get(entry.getField())+"' " );
			else{
				str[0] = "N";
				str[1] = "���ݱ�ʾ-BTID:"+BTID+",  ����ID-BOID:"+BOID;
				str[2] = "������д���÷�¼���ֶ�����Ϊ�գ������ֶ����ô����ֶ�����Ӧ����ʵ���ֶζ�Ӧ�������ƣ����飡";
			}
			if(i>0){
				sql.append(" and " );
				if(head.get(entry.getField())!=null && !"".equals(head.get(entry.getField())))
					sql.append(" F"+entry.getField() +" ='"+head.get(entry.getField())+"' " );
				else{
					str[0] = "N";
					str[1] = "���ݱ�ʾ-BTID:"+BTID+",   ����ID-BOID:"+BOID;
					str[2] = "������д���÷�¼���ֶ�����Ϊ�գ������ֶ����ô����ֶ�����Ӧ����ʵ���ֶζ�Ӧ�������ƣ����飡";
				}
			}
		}
		sql.append(" where  fid='"+BOID+"' " );
		try {
			com.kingdee.eas.fi.arap.util.DBUtil.execute(ctx, sql.toString());
			str[0] = "Y";
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",  ����ID-BOID:"+BOID;
			str[2] = "����ʧ�ܣ�����SQL��䣬�����ֶ������Ƿ���ȷ����sql��"+sql.toString();
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName(str[1]);
				log.setDescription("EAS���:"+str[0]+"; ������Ϣ"+str[2]);
				log.setBeizhu("���ýӿڷ�����ApproveBack");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return str;
	}
	
}
