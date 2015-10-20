// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ContractContentUI.java

package com.kingdee.eas.fdc.contract.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.framework.client.AbstractBillEditUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

// Referenced classes of package com.kingdee.eas.fdc.contract.client:
//            AbstractContractContentUI, ContractClientUtils

public class ContractModelUI {


	public ContractModelUI(ContractBillEditUI editUI,String id,String mid){
		ui=editUI;
		contractId = id;
		modelId = mid;
		threadMap = new HashMap();
	}

	public ContractContentInfo getContentInfo() throws Exception{
		ContractContentInfo contentInfo = null;
		
		FilterInfo filter = new FilterInfo();
		boolean isExist = ContractContentFactory.getRemoteInstance().exists(filter);
		if (isExist) {
			
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("id");
			viewInfo.getSelector().add("version");
			viewInfo.getSelector().add("fileType");
			viewInfo.getSelector().add("createTime");
			viewInfo.getSelector().add("creator.*");
			viewInfo.getSelector().add("model");
			viewInfo.getSelector().add("*");
			SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
			sorterItemInfo.setSortType(SortType.ASCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			sorterItemInfo = new SorterItemInfo("version");
			sorterItemInfo.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("parent", contractId);
			filterInfo.appendFilterItem("model", modelId);
			viewInfo.setFilter(filterInfo);
			ContractContentCollection contentCollection = ContractContentFactory
					.getRemoteInstance().getContractContentCollection(viewInfo);
			if(contentCollection!=null&&contentCollection.size()>0){
				contentInfo = contentCollection.get(0);
			}
			
		}
		if(contentInfo==null){
			filter.getFilterItems().clear();
			filter.getFilterItems().remove(new FilterItemInfo("id",modelId));
			isExist = ContractContentFactory.getRemoteInstance().exists(filter);
			if (isExist) {
				contentInfo = ContractContentFactory
				.getRemoteInstance().getContractContentInfo(
						"select * where id='"+modelId+"'");
			}
			contentInfo.put("isNew", "isNew");
			
		}
		return contentInfo;
	}
	
	private File chooseFileByDialog() {
		File retFile = null;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		int retVal = fc.showOpenDialog(ui);
		if (retVal == 1)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > 0x3200000L) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		} else {
			return retFile;
		}
	}

	public void editModel() throws Exception {
		LongTimeDialog dialog = UITools.getDialog(ui);		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				
				ContractContentInfo contentInfo = getContentInfo();
					
					File file = editModel(contentInfo);
					boolean isAdd=false;
					if(contentInfo.get("isNew")!=null){
						isAdd=true;
					}
					FileModifyThread fileModifyThread = new FileModifyThread(file, contentInfo.getFileType());
					fileModifyThread.start();
					threadMap.put(file.getAbsolutePath(), fileModifyThread);
					
					return null;
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		if (dialog != null)
			dialog.show();
	}
	

	public  File editModel(ContractContentInfo contentInfo)
			throws BOSException, IOException, FileNotFoundException,
			EASBizException, InterruptedException {
		String type = contentInfo.getFileType();
		String name = "";
		String dat = "";
		if (type.indexOf(".") != -1) {
			name = type.substring(0, type.lastIndexOf("."));
			name = name.replaceAll(" ", "");
			dat = type.substring(type.lastIndexOf("."), type.length());
		}
		File file = File.createTempFile("KDTF-" + name, dat);
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(contentInfo.getContentFile());
		fos.close();
		File tempbat = File.createTempFile("tempbat", ".bat");
		FileWriter fw = new FileWriter(tempbat);
		String fullname = file.getPath();
		StringBuffer sb = new StringBuffer(fullname);
		sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
		sb.append("\"");
		fullname = sb.toString();
		fw.write("start " + fullname);
		fw.close();
		String tempbatFullname = tempbat.getPath();
		Runtime.getRuntime().exec(tempbatFullname);
		File newFile = new File(file.getAbsolutePath());
		
		//合同单据状态
		FDCBillStateEnum state = ((ContractBillInfo) ui.getEditData()).getState();
		//如果 单据状态是审核中
		if (state == FDCBillStateEnum.AUDITTING) {
			//审核中状态的单据如果不是从工作流界面点的查看正文就把文件置为只读模式
			if (!AbstractBillEditUI.STATUS_FINDVIEW.equals(ui.getOprtState())) {
				file.setReadOnly();
			}
		} else {

			//其它状态的单据，如果是编辑界面是查看状态也把文件置为只读模式
			if (OprtState.VIEW.equals(ui.getOprtState())) {
				file.setReadOnly();
			}
		}
		
		for(int i = 0; i < 100; ++i){
			Thread.sleep(200);
			if(!file.renameTo(newFile)){
				break;
			}
		}
			return file;
	}

	public File editContent(ContractContentInfo contentInfo)
			throws BOSException, IOException, FileNotFoundException,
			EASBizException, InterruptedException {
		String type = contentInfo.getFileType();
		String name = "";
		String dat = "";
		if (type.indexOf(".") != -1) {
			name = type.substring(0, type.lastIndexOf("."));
			name = name.replaceAll(" ", "");
			dat = type.substring(type.lastIndexOf("."), type.length());
		}
		File file = File.createTempFile("KDTF-" + name, dat);
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(contentInfo.getContentFile());
		fos.close();
		
		File tempbat = File.createTempFile("tempbat", ".bat");
		FileWriter fw = new FileWriter(tempbat);
		String fullname = file.getPath();
		StringBuffer sb = new StringBuffer(fullname);
		sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
		sb.append("\"");
		fullname = sb.toString();
		fw.write("start " + fullname);
		fw.close();
		String tempbatFullname = tempbat.getPath();
		Runtime.getRuntime().exec(tempbatFullname);
		//
		File newFile = new File(file.getAbsolutePath());
		for(int i = 0; i < 100; ++i){
			Thread.sleep(200);
			if(!file.renameTo(newFile)){
				break;
			}
		}
		return file;
	}

	private static final Logger logger;

	ContractBillEditUI ui;
	String contractId;
	String modelId;
	FDCBillInfo billInfo;

	boolean isModify;

	Map threadMap;

	static {
		logger = CoreUIObject
				.getLogger(ContractModelUI.class);
	}

	class FileModifyThread extends Thread {

		public void run() {
			while (run)
				try {
					sleep(1000L);
					if (file != null && file.exists()) {
						File newFile = new File(file.getAbsolutePath());
						boolean isFree = file.renameTo(newFile);
						if (isFree) {
							if (newFile.lastModified() != oriModifyTime) {
								String sql = "select max(fversion) as version from T_CON_ContractContent where FParent='"
										+ contractId
										+ "' and FfileType='"
										+ fileName + "'";
								BigDecimal version = FDCHelper.ZERO;
								ISQLExecutor executor = SQLExecutorFactory
										.getRemoteInstance(sql);
								try {
									IRowSet rowset = executor.executeSQL();
									if (rowset.next()) {
										Collection collection = rowset
												.toCollection();
										Iterator iterator = collection
												.iterator();
										if (iterator.hasNext()) {
											Vector vector = (Vector) iterator
													.next();
											if (vector.get(0) != null)
												version = new BigDecimal(vector
														.get(0).toString());
										}
									}
								} catch (Exception e) {
									logger.error("@@@TEST@@@获取正文异常 ", e);
									e.printStackTrace();
								}
								if (version.floatValue() == 0.0F){
									version = new BigDecimal("1.0");
								} else{
									version = version
											.add(new BigDecimal("0.1"));
								}
								ContractContentInfo contentInfo = new ContractContentInfo();
								contentInfo.setVersion(version);
								contentInfo.setParent(contractId);
								contentInfo.setFileType(fileName);
								contentInfo.setModel(modelId);
								byte content[] = (byte[]) null;
								try {
									content = FileGetter.getBytesFromFile(file);
									contentInfo.setContentFile(content);
									ContractContentFactory.getRemoteInstance()
											.addnew(contentInfo);
								} catch (Exception e) {
									logger.error("@@@TEST@@@保存记录时出现异常 ", e);
									e.printStackTrace();
								}
							}
							logger.error("@@@TEST@@@oriModifyTime = " + oriModifyTime);
							logger.error("@@@TEST@@@newFile.lastModified() = " + newFile.lastModified());
							run = false;
						}
					} else {
						logger.error("@@@TEST@@@文件不存在！" + file.getAbsolutePath());
						run = false;
					}
				} catch (InterruptedException e) {
					logger.error("@@@TEST@@@线程出现异常 ", e);
					e.printStackTrace();
				}
		}

		public void setRun(boolean myRun) {
			run = myRun;
		}

		boolean run;

		File file;

		String fileName;

		long oriModifyTime;

		public FileModifyThread(File myFile, String myFileName) {
			super();
			run = true;
			fileName = "";
			file = myFile;
			fileName = myFileName;
			if (file != null && file.exists())
				oriModifyTime = file.lastModified();
		}
	}

}