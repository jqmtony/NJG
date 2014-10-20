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
            return (new String[] {  "N", e.getTypeNumber(), "XML格式错误！" });
        }
        Map head = builder.getHeadProperties("DATA");//读取表头
        List entries = builder.getEntriesProperties("//billEntries/entry"); //读取分录
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
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
			str[2] = "单据ID-BOID:"+BOID;
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
				str[1] = "单据标示-BTID:"+BTID+",  单据ID-BOID:"+BOID;
				str[2] = "审批反写配置分录中字段配置为空，或者字段配置错误，字段名称应该是实体字段对应属性名称，请检查！";
			}
			if(i>0){
				sql.append(" and " );
				if(head.get(entry.getField())!=null && !"".equals(head.get(entry.getField())))
					sql.append(" F"+entry.getField() +" ='"+head.get(entry.getField())+"' " );
				else{
					str[0] = "N";
					str[1] = "单据标示-BTID:"+BTID+",   单据ID-BOID:"+BOID;
					str[2] = "审批反写配置分录中字段配置为空，或者字段配置错误，字段名称应该是实体字段对应属性名称，请检查！";
				}
			}
		}
		sql.append(" where  fid='"+BOID+"' " );
		try {
			com.kingdee.eas.fi.arap.util.DBUtil.execute(ctx, sql.toString());
			str[0] = "Y";
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",  单据ID-BOID:"+BOID;
			str[2] = "更新失败，请检查SQL语句，更新字段名称是否正确；【sql】"+sql.toString();
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName(str[1]);
				log.setDescription("EAS结果:"+str[0]+"; 错误信息"+str[2]);
				log.setBeizhu("调用接口方法：ApproveBack");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return str;
	}
	
}
