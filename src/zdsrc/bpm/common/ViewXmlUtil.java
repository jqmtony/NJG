package com.kingdee.eas.bpm.common;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import org.json.XML;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.bpm.BPMLogFactory;
import com.kingdee.eas.bpm.BPMLogInfo;
import com.kingdee.eas.bpm.viewpz.PzViewFactory;
import com.kingdee.eas.bpm.viewpz.PzViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.PayReqTableHelper;
import com.kingdee.eas.fi.arap.util.DBUtil;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class ViewXmlUtil {
	/***
	 * 页签
	 * */
	public static String[] getViewXmlString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
			str[2] = "单据ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
			str[2] = "单据ID-BOID:"+BOID;
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
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				str[2] = "单据ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			str[2] = "单据ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlString");
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
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
			str[2] = "单据ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
			str[2] = "单据ID-BOID:"+BOID;
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
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				str[2] = "单据ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			str[2] = "单据ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlString");
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
//		        if(colNameArr[0]!=null)
//		        {
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
//		        }
		return xml.toString();
	}

	
	
	
	
	
	
	public static String getViewXmlSUPPYEARYString(Context ctx,String BTID,String BOID){
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
//		        if(colNameArr[0]!=null)
//		        {
		        xml.append("<YearEntry>");
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
				xml.append("</YearEntry>");
//		        }
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
//		        if(colNameArr[0]!=null)
//		        {
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
//		        }
		return xml.toString();
	}
	
	
	
	/*只存在表头的信息*/
	public static String[] getViewXmlBTString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
			str[2] = "单据ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
			str[2] = "单据ID-BOID:"+BOID;
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
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				str[2] = "单据ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			str[2] = "单据ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlBTString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
	
	 /*表头和分录*/
	public static String[] getViewXmlBTANDFLString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			str[2] = "单据ID-BOID:"+BOID;
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
			str[2] = "单据ID-BOID:"+BOID;
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
			str[2] = "单据ID-BOID:"+BOID;
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
//		        xml.append(getViewXmlFLXXLYString(ctx,"BBCBTZentry",BOID));
		        xml.append(getViewXmlFLXXLYString(ctx,"KKDENTRY",BOID));
//		        xml.append(getViewXmlFLXXLYString(ctx,"MainPla",BOID));
		        
		        /*供应商*/
		       // xml.append(getViewXmlFLXXLYString(ctx,"SupplierEntry",BOID));
//		        xml.append(getViewXmlHTXXSXString(ctx,"SupplierDetailFile",BOID));
//		        xml.append(getViewXmlHTXXLYString(ctx,"Supplierdetment",BOID));
//		        xml.append(getViewXmlSUPPYEARYString(ctx,"SupplierYear",BOID));
		        
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				str[2] = "单据ID-BOID:"+BOID;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			str[2] = "单据ID-BOID:"+BOID;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlBTString");
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
		       // if(colNameArr[0]!=null)
		       // {
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
		     //}
		return xml.toString();
	}
	
	
	
	
	
	
	
	public static String[] getViewXmlCurProjectString(Context ctx,String BTID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
		
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
		
		}
		try {
			StringBuffer xml = new StringBuffer();
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from "+view );
			int colunmCount;
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
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
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlBTString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
	
	public static String[] getViewXmlMBString(Context ctx,String BTID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
		
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
		
		}
		try {
			StringBuffer xml = new StringBuffer();
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from "+view );
			int colunmCount;
			String BOID="";
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
		        while(rs.next()){
		          xml.append("<item>");
		        	for (int i = 0; i < colunmCount; i++) {  
			        	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
			        	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
			        	xml.append("<"+colNameArr[i]+">");
			        	xml.append(rs.getObject(i+1));
			        	xml.append("</"+colNameArr[i]+">\n");
			        	BOID=rs.getObject(1).toString();			        	
			        }
		        	xml.append(getViewXmlFLXXLYString(ctx,"MBFL",BOID));
		          xml.append("</item>\n");
		        }
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlMBString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
	
	
	public static String[] getViewXmlPOINTString(Context ctx,String BTID,String BOID){
		String[] str = new String[3];
		str[0] = "Y";
		str[1] = "成功！";
		PzViewInfo info = null;
		try {
			info = PzViewFactory.getLocalInstance(ctx).getPzViewInfo(" where number='"+BTID+"'");
		} catch (EASBizException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			e1.printStackTrace();
		} catch (BOSException e1) {
			str[0] = "N";
			str[1] = BTID+"---获取审批反写设置对象失败，请配置！";
			e1.printStackTrace();
		}
		if(info==null){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",未找到审批反写配置，请进行相应配置";
		
		}
		String view = info.getSqlView();
		if(view==null || "".equals(view)){
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",试图为空，请填写单据对应试图";
		
		}
		try {
			StringBuffer xml = new StringBuffer();
			String where = " where fid='"+BOID+"'";
			xml.append("<DATA>\n");
			RowSet rs = DBUtil.executeQuery(ctx,"select * from (select * from "+view+where+" order by VersionName desc) where rownum=1");
			int colunmCount;
			String BID="";
			try {
				colunmCount = rs.getMetaData().getColumnCount();
		        String[] colNameArr = new String[colunmCount];  
		        String[] colTypeArr = new String[colunmCount];  
		        while(rs.next()){
		          //xml.append("<item>");
		        	for (int i = 0; i < colunmCount; i++) {  
			        	colNameArr[i] = rs.getMetaData().getColumnName(i + 1);  
			        	colTypeArr[i] = rs.getMetaData().getColumnTypeName(i + 1); 
			        	xml.append("<"+colNameArr[i]+">");
			        	xml.append(rs.getObject(i+1));
			        	xml.append("</"+colNameArr[i]+">\n");
			        	BID=rs.getObject(1).toString();			        	
			        }
		        	xml.append(getViewXmlFLXXLYString(ctx,"MainPla",BID));
		          //xml.append("</item>\n");
		        }
		        xml.append("</DATA>\n");
		        str[1]=xml.toString();
			} catch (SQLException e) {
				str[0] = "N";
				str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
				e.printStackTrace();
			}  
		} catch (BOSException e) {
			str[0] = "N";
			str[1] = "单据标示-BTID:"+BTID+",执行试图语句失败！【view】"+view;
			e.printStackTrace();
		}finally{
			BPMLogInfo log = new BPMLogInfo();
			try {
				log.setLogDate(new Date());
				log.setName("EAS结果:"+str[0]);
				log.setDescription("错误信息"+str[2]);
				log.setBeizhu("调用接口方法：getViewXmlMBString");
				BPMLogFactory.getLocalInstance(ctx).save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
//  public static String[] getFKQK(Context ctx,String BTID,String BOID){
//		String[] str = new String[3];
//		BigDecimal tempOri = FDCHelper.ZERO;
//		BigDecimal temp = FDCHelper.ZERO;
//		BigDecimal LstAddPrjAllPaidAmt=FDCHelper.ZERO;
//		BigDecimal LstPrjAllPaidAmt=FDCHelper.ZERO;
//		BigDecimal LstPrjAllPaidAmt2=FDCHelper.ZERO;
//		String PaycontractBillId=null;
//    	IObjectValue billInfo = null;
//		try {
//			billInfo = DynamicObjectFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(BOID).getObjectType(),new ObjectUuidPK(BOID));
//    	}catch (Exception e) {
//			str[2] = "根据单据ID获取对象数据失败,请检查单据ID是否存在，并查看服务器log日志";
//			e.printStackTrace();
//		}
//		Map initparam = new HashMap();
//		ContractBillInfo contractBill = null;
//		PayRequestBillInfo requestBil = null;
//		ContractChangeBillInfo contractchangebill=null;
//		ContractSettlementBillInfo settlementBill=null;
//		
//		if(billInfo instanceof ContractBillInfo){
//			initparam.put("contractBillId", BOID);
//			try {
//				contractBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOID));
//				contractBill.getName();    //合同名称
//				contractBill.getAmount();  //合同造价本币
//				contractBill.getOriginalAmount(); //合同造价原币
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
//		}
//		if(billInfo instanceof PayRequestBillInfo)
//		{
//			initparam.put("id", BOID);
//			try {
//				requestBil = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(BOID));
//				if(requestBil.getPayPartAMatlAmt() != null)
//				{
//				  temp = requestBil.getPayPartAMatlAmt();
//				}
//				  PaycontractBillId=requestBil.getContractId();
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
//		}
//		if(billInfo instanceof ContractChangeBillInfo)
//		{
//			initparam.put("id", BOID);
//			try {
//				contractchangebill = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(BOID));
//				contractchangebill.getBalanceAmount(); //本币
//				contractchangebill.getOriBalanceAmount();  //原币
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
//		}
//		if(billInfo instanceof ContractSettlementBillInfo)
//		{
//			initparam.put("id", BOID);
//			try {
//				settlementBill = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(BOID));
//				settlementBill.getCurOriginalAmount();  //最新造价原币
//				settlementBill.getSettlePrice();    //最新造价本币
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		
//		try {
//			
//			Map initData = PayRequestBillFactory.getLocalInstance(ctx).fetchInitData(initparam);
//			
//			PayRequestBillCollection payReqCol = (PayRequestBillCollection)initData.get("PayRequestBillCollection");
//			if(payReqCol != null && payReqCol.size() > 0)
//			{
//			     SelectorItemCollection sic = new SelectorItemCollection();
//			     sic.add("amount");
//			     sic.add("originalAmount");
//			     try
//			     {
//			    	 PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payReqCol.get(0).getId().toString()), sic);
//			    	 payRequestBillInfo.getAmount();
//			     }
//			     catch(EASBizException e)
//                 {
//                  e.printStackTrace();
//                 }
//			}
//			PayRequestBillInfo info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
//			if(info.getPayPartAMatlOriAmt() != null)
//			{
//				 tempOri = info.getPayPartAMatlOriAmt();
//			}
//			if(FDCHelper.toBigDecimal(temp).compareTo(FDCHelper.ZERO) == 0)
//			{
//				tempOri = FDCHelper.ZERO;
//			}
//			PaymentBillCollection paymentColl = (PaymentBillCollection)initData.get("PaymentBillCollection_paymentColl");
//			ContractBillInfo con;
//			 if(!paymentColl.iterator().hasNext())
//			 {
//				 con = new ContractBillInfo();
//				 String contractBillId =PaycontractBillId;
//				 if(contractBillId != null && contractBillId.trim().length() > 1 && BOSUuid.read(contractBillId).getType().equals(con.getBOSType()))
//				 {
//					 ContractBillInfo contractBill1 = (ContractBillInfo)initData.get("contractBillInfo");
//					 if(contractBill1.getAddPrjAmtPaid() != null)
//						 if(contractBill1.getAddPrjAmtPaid().compareTo(FDCHelper.ZERO) == 0)
//						    {
//						      LstAddPrjAllPaidAmt=null;    //增加工程款 原币
//						    } 
//						 else{
//							   LstAddPrjAllPaidAmt=contractBill1.getAddPrjAmtPaid();
//						     }
//				 }
//				 if(contractBill.getPrjPriceInConPaid() != null)
//				 {
//					 if(contractBill.getPrjPriceInConPaid().compareTo(FDCHelper.ZERO) == 0)
//					 {
//						 LstPrjAllPaidAmt=null;   //合同类工程款原币
//						 LstPrjAllPaidAmt2=null;   //合同类工程款本币
//					 }
//					 else
//					 {
//						 LstPrjAllPaidAmt=contractBill.getPrjPriceInConPaid();
//						 LstPrjAllPaidAmt2=contractBill.getPrjPriceInConPaid();
//						 BigDecimal prjPriceInConPaid = contractBill.getPrjPriceInConPaid();
//						 BigDecimal prjPriceInConOriPaid = FDCHelper.ZERO;
//						 if(prjPriceInConPaid.compareTo(FDCHelper.ZERO) != 0)
//						 prjPriceInConOriPaid = FDCHelper.divide(prjPriceInConPaid, getExeRate());
//						 BigDecimal LstPrjAllPaidOriAmt = contractBill.getPrjPriceInConPaid();
//						 LstPrjAllPaidAmt=(BigDecimal) (prjPriceInConOriPaid.compareTo(FDCHelper.ZERO) != 0 ? ((Object) (prjPriceInConOriPaid)) : null);
//					 }
//				 }
//				
//			 }
//			ContractChangeBillCollection contractChangeBillCollection = null;
//			// 付款单
//			BillBaseCollection paymentBillCollection = null;
//			// 奖励单
//			GuerdonBillCollection guerdonBillCollection = null;
//			// 付款申请单对应的违约金
//			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = null;
//			// 付款申请单对应的甲供材扣款
//			PartAOfPayReqBillCollection partAOfPayReqBillCollection = null;
//			// 付款申请单对应的甲村确认单金额
//			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = null;
//			// 扣款类型
//			DeductTypeCollection deductTypeCollection = null;
//			int payTimes = ((Integer) initData.get("payTimes")).intValue();
//			// 变更单
//			contractChangeBillCollection = (ContractChangeBillCollection) initData.get("ContractChangeBillCollection");
//			// 付款单
//			paymentBillCollection = (BillBaseCollection) initData.get("PaymentBillCollection");
//			// 付款申请单对应的奖励项
//			// 奖励单
//			guerdonBillCollection = (GuerdonBillCollection) initData.get("GuerdonBillCollection");
//			// 付款申请单对应的违约金
//			compensationOfPayReqBillCollection = (CompensationOfPayReqBillCollection) initData.get("CompensationOfPayReqBillCollection");
//			// 付款申请单对应的甲供材扣款
//			partAOfPayReqBillCollection = (PartAOfPayReqBillCollection) initData.get("PartAOfPayReqBillCollection");
//			// 付款申请单对应的甲供材确认单金额
//			partAConfmOfPayReqBillCollection = (PartAConfmOfPayReqBillCollection) initData.get("PartAConfmOfPayReqBillCollection");
//			// 扣款类型
//			deductTypeCollection = (DeductTypeCollection) initData.get("DeductTypeCollection");
//			HashMap bindCellMap = new HashMap();
//			
//
////			PayReqDataHelper.updateDynamicValue(ctx,bindCellMap,contractBill, contractChangeBillCollection, paymentBillCollection);
////			PayReqDataHelper.updateLstAdvanceAmt(ctx, bindCellMap, contractBill);
////			PayReqDataHelper.updateLstReqAmt(ctx, bindCellMap, contractBill);
////			PayReqDataHelper.reloadCompensationValue(ctx, bindCellMap, contractBill, requestBil);
////			PayReqDataHelper.reloadGuerdonValue(ctx, bindCellMap, contractBill, requestBil);
////			PayReqDataHelper.reloadPartAConfmValue(ctx, bindCellMap, contractBill, requestBil);
////			PayReqDataHelper.reloadPartAValue(ctx, bindCellMap, contractBill, requestBil);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return str;
//	}
//
//	
////  private BigDecimal getExeRate(Map initData)
////  {
////       BigDecimal rate = FDCHelper.ONE;
////       if(BOSUuid.read(editData.getContractBillId()).getType().equals((new ContractBillInfo()).getBOSType()))
////       {
////         if(initData.get("contractBillInfo") instanceof ContractBillInfo)
////          {
////               ContractBillInfo contractBillInfo = (ContractBillInfo)initData.get("contractBillInfo");
////             if(contractBillInfo.getExRate() == null)
////                  rate = editData.getExchangeRate() != null ? editData.getExchangeRate() : FDCHelper.ONE;
////            else
////                rate = contractBillInfo.getExRate();
////          }
////      } else
////      {
////        rate = editData.getExchangeRate();
////      }
////      return rate;
////  }
	
}
