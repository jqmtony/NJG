package com.kingdee.eas.bpm.common;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.RowSet;

import org.json.XML;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.viewpz.PzViewFactory;
import com.kingdee.eas.bpm.viewpz.PzViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fi.arap.util.DBUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ViewXmlUtil {
	/***
	 * ҳǩ
	 * */
	public static String[] getViewXmlString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "�ɹ���";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",δ�ҵ�������д���ã��������Ӧ����";
			str[2] = "����ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",��ͼΪ�գ�����д���ݶ�Ӧ��ͼ";
			str[2] = "����ID-BOID:"+BOID;
		}
		try {
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			int colunmCount;
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
		        xml.append("<billEntries>");
		        while(rs.next()){
		          xml.append("<item>");
		        	for (int i = 0; i < colunmCount; i++) {  
			        	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
			        	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
			        	xml.append("<"+colNameArr[i]+">");
			        	xml.append(rs.getObject(i+1));
			        	xml.append("</"+colNameArr[i]+">\n");
			        }
		          xml.append("</item>\n");
		        }
		        xml.append("</billEntries>");
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
				str[2] = "����ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
			str[2] = "����ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS���:"+str[0]);
				log.setDescription("������Ϣ"+str[2]);
				log.setBeizhu("���ýӿڷ�����getViewXmlString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	public static String[] getViewXmlHTString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "�ɹ���";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",δ�ҵ�������д���ã��������Ӧ����";
			str[2] = "����ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",��ͼΪ�գ�����д���ݶ�Ӧ��ͼ";
			str[2] = "����ID-BOID:"+BOID;
		}
		try {
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			int colunmCount;
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
		        while(rs.next()){
		        	for (int i = 0; i < colunmCount; i++) {  
			        	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
			        	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
			        	xml.append("<"+colNameArr[i]+">");
			        	xml.append(rs.getObject(i+1));
			        	xml.append("</"+colNameArr[i]+">\n");
			        }
		        }
		        xml.append(getViewXmlHTXXXXString(ctx,"HTXXXX",BOID));
		        xml.append(getViewXmlHTXXSXString(ctx,"HTXXSX",BOID));
		        xml.append(getViewXmlHTXXLYString(ctx,"HTXXLY",BOID));
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
				str[2] = "����ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
			str[2] = "����ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS���:"+str[0]);
				log.setDescription("������Ϣ"+str[2]);
				log.setBeizhu("���ýӿڷ�����getViewXmlString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
	public static String getViewXmlHTXXXXString(Context ctx,String BTID,String BOID){
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String view = info.getSqlView();
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
		    RowSet rs = null;
			try {
				rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int colunmCount = 0;
				try {
					colunmCount = rs.getMetaData().getColumnCount();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount]; 
		        xml.append("<billEntries>");
		        try {
					while(rs.next()){
						xml.append("<item>");
						for (int i = 0; i < colunmCount; i++) {  
					    	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
					    	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
					    	xml.append("<"+colNameArr[i]+">");
					    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					    	String sql="select FNumber from T_CON_ContractBill where FID='"+rs.getObject(i+1)+"'";
				    		builder.appendSql(sql);
			                IRowSet Rowset = null;
							try {
								Rowset = builder.executeQuery();
							} catch (BOSException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			                  if(Rowset.size()==1)
			                  {
			                   Rowset.next();
			                   xml.append(Rowset.getString("FNumber"));
			                  }
			                  else
			                  {
					    	   xml.append(rs.getObject(i+1));
			                  }
					    	//xml.append(rs.getObject(i+1));
					    	xml.append("</"+colNameArr[i]+">\n");
					    }
						xml.append("</item>\n");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				xml.append("</billEntries>");
		return xml.toString();
	}
	
	
	
	public static String getViewXmlHTXXLYString(Context ctx,String BTID,String BOID){
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String view = info.getSqlView();
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
		    RowSet rs = null;
			try {
				rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int colunmCount = 0;
				try {
					colunmCount = rs.getMetaData().getColumnCount();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount]; 
		        xml.append("<ContractBailEntry>");
		        try {
					while(rs.next()){
						xml.append("<item>");
						for (int i = 0; i < colunmCount; i++) {  
					    	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
					    	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
					    	xml.append("<"+colNameArr[i]+">");
					    	xml.append(rs.getObject(i+1));
					    	xml.append("</"+colNameArr[i]+">\n");
					    }
						xml.append("</item>\n");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				xml.append("</ContractBailEntry>");
		return xml.toString();
	}
	
	
	
	public static String getViewXmlHTXXSXString(Context ctx,String BTID,String BOID){
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String view = info.getSqlView();
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
		    RowSet rs = null;
			try {
				rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int colunmCount = 0;
				try {
					colunmCount = rs.getMetaData().getColumnCount();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount]; 
		        xml.append("<ContractPayItem>");
		        try {
					while(rs.next()){
						xml.append("<item>");
						for (int i = 0; i < colunmCount; i++) {  
					    	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
					    	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
					    	xml.append("<"+colNameArr[i]+">");
					    	xml.append(rs.getObject(i+1));
					    	xml.append("</"+colNameArr[i]+">\n");
					    }
						xml.append("</item>\n");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				xml.append("</ContractPayItem>");
		return xml.toString();
	}
	
	
	
	/*ֻ���ڱ�ͷ����Ϣ*/
	public static String[] getViewXmlBTString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "�ɹ���";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",δ�ҵ�������д���ã��������Ӧ����";
			str[2] = "����ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",��ͼΪ�գ�����д���ݶ�Ӧ��ͼ";
			str[2] = "����ID-BOID:"+BOID;
		}
		try {
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			int colunmCount;
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
		        while(rs.next()){
		         // xml.append("<item>");
		        	for (int i = 0; i < colunmCount; i++) {  
			        	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
			        	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
			        	xml.append("<"+colNameArr[i]+">");
			        	xml.append(rs.getObject(i+1));
			        	xml.append("</"+colNameArr[i]+">\n");
			        }
		         // xml.append("</item>\n");
		        }
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
				str[2] = "����ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
			str[2] = "����ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS���:"+str[0]);
				log.setDescription("������Ϣ"+str[2]);
				log.setBeizhu("���ýӿڷ�����getViewXmlBTString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
	
	 /*��ͷ�ͷ�¼*/
	public static String[] getViewXmlBTANDFLString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "�ɹ���";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---��ȡ������д���ö���ʧ�ܣ������ã�";
			str[2] = "����ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",δ�ҵ�������д���ã��������Ӧ����";
			str[2] = "����ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",��ͼΪ�գ�����д���ݶ�Ӧ��ͼ";
			str[2] = "����ID-BOID:"+BOID;
		}
		try {
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			int colunmCount;
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
		        while(rs.next()){
		         // xml.append("<item>");
		        	for (int i = 0; i < colunmCount; i++) {  
			        	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
			        	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
			        	xml.append("<"+colNameArr[i]+">");
			        	xml.append(rs.getObject(i+1));
			        	xml.append("</"+colNameArr[i]+">\n");
			        }
		         // xml.append("</item>\n");
		        }
		        xml.append(getViewXmlFLXXLYString(ctx,"BBCBTZentry",BOID));
		        xml.append(getViewXmlFLXXLYString(ctx,"KKDENTRY",BOID));
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
				str[2] = "����ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "���ݱ�ʾ-BTID:"+BTID+",ִ����ͼ���ʧ�ܣ���view��"+view;
			str[2] = "����ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS���:"+str[0]);
				log.setDescription("������Ϣ"+str[2]);
				log.setBeizhu("���ýӿڷ�����getViewXmlBTString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
   
	public static String getViewXmlFLXXLYString(Context ctx,String BTID,String BOID){
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String view = info.getSqlView();
			String where = " where fid='"+BOID+"'";
			StringBuffer xml = new StringBuffer();
		    RowSet rs = null;
			try {
				rs = DBUtil.executeQuery(ctx,"select * from "+view+where );
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int colunmCount = 0;
				try {
					colunmCount = rs.getMetaData().getColumnCount();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount]; 
		        xml.append("<billEntries>");
		        try {
					while(rs.next()){
						xml.append("<item>");
						for (int i = 0; i < colunmCount; i++) {  
					    	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
					    	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
					    	xml.append("<"+colNameArr[i]+">");
					    	xml.append(rs.getObject(i+1));
					    	xml.append("</"+colNameArr[i]+">\n");
					    }
						xml.append("</item>\n");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				xml.append("</billEntries>");
		return xml.toString();
	}
	
	
	
}
