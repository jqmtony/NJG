package com.kingdee.eas.fdc.basedata.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 附件工具类
 * 
 * @author liupd
 * 
 */
public class AttachmentUtils {

	/** 文件扩展名 map key */
	public static final String FILE_EXT = "file_ext";

	/** 文件数据 map key */
	public static final String FILE_DATA = "file_data";

	/**
	 * 选择文件
	 * 
	 * @param comp
	 *            当前UI对象
	 * @return map{FILE_EXT, FILE_DATA}, 文件扩展名，文件数据
	 */
	public static Map chooseFile(CoreUIObject comp) {

		Map rtnMap = new HashMap();
		File file = AttachmentUtils.chooseFileByDialog(comp);
		if (file == null) {
			return null;
		}

		if (!file.canRead()) {
			MsgBox.showWarning(comp, ContractClientUtils
					.getRes("readFileError"));
			SysUtil.abort();
		}

		String fullname = file.getName();
		String extName = StringUtil4File.getExtendedFileName(fullname);
		byte[] content = null;
		try {
			content = FileGetter.getBytesFromFile(file);
		} catch (Exception ex) {
			MsgBox.showWarning(comp, ContractClientUtils
					.getRes("readFileError"));
			SysUtil.abort();
		}

		rtnMap.put(FILE_EXT, extName);
		rtnMap.put(FILE_DATA, content);

		return rtnMap;
	}

	/**
	 * 查看文件
	 * 
	 * @param fileExt
	 *            文件扩展名
	 * @param fileData
	 *            文件数据
	 */
	public static void viewFile(String fileExt, byte[] fileData) {
		try {
			File file = File.createTempFile("temp", "." + fileExt);
			String fullname = file.getPath();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(fileData);
			fos.close();

			File tempbat = File.createTempFile("tempbat", ".bat");
			FileWriter fw = new FileWriter(tempbat);
			fw.write("start " + fullname);
			fw.close();
			String tempbatFullname = tempbat.getPath();
			Process process = Runtime.getRuntime().exec(tempbatFullname);
			process.toString();
		} catch (IOException e) {
			// @AbortException
			ExceptionHandler.handle(e);
		}
	}

	private static File chooseFileByDialog(CoreUIObject comp) {
		File retFile = null;
		int retVal;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);

		retVal = fc.showOpenDialog(comp);
		if (retVal == KDFileChooser.CANCEL_OPTION)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > StringUtil4File.FILE_BYTES_LIMIT_SINGLE) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		}
		return retFile;
	}
	
	/**
	 * 描述：根据单据ID获取附件列表
	 * @return
	 * @Author：jian_cao
	 * @CreateTime：2013-1-10
	 */
	public static List getAttachmentListByBillID(String boID) throws BOSException {

		List attachmentList = new ArrayList();
		if (boID != null) {
			EntityViewInfo viewInfo = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.id"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boID));
			viewInfo.setSelector(sic);
			viewInfo.setFilter(filter);
			SorterItemInfo sort = new SorterItemInfo("attachment.createTime");
			sort.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(sort);
			BoAttchAssoCollection cols = null;

			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewInfo);
				if (cols.size() > 0) {
					for (Iterator it = cols.iterator(); it.hasNext();) {
						AttachmentInfo info = ((BoAttchAssoInfo) it.next()).getAttachment();
						attachmentList.add(info);
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return attachmentList;
	}

	/**
	 * 
	 * 描述：从金地项目迁过来的方法
	 * @param boID 业务单据ID
	 * @return 附件集合
	 * @Author：owen_wen
	 * @CreateTime：2013-4-12
	 */
	public static AttachmentCollection getAttachmentsByBoID(String boID) throws BOSException {
		if (boID == null) {
			return null;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", boID));

		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("attachment.createTime");
		sort.setSortType(SortType.DESCEND);
		sorts.add(sort);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		sels.add("attachment.number");
		sels.add("attachment.createTime");

		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.setSelector(sels);
		viewInfo.setSorter(sorts);
		viewInfo.setFilter(filter);
		BoAttchAssoCollection attch = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewInfo);
		AttachmentCollection attachColl = new AttachmentCollection();
		for (Iterator it = attch.iterator(); it.hasNext();) {
			BoAttchAssoInfo tmp = (BoAttchAssoInfo) it.next();
			attachColl.add(tmp.getAttachment());
		}

		return attachColl;
	}
}
