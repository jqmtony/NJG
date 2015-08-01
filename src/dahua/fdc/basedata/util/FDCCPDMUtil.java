package com.kingdee.eas.fdc.basedata.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.dm.CategoryInfo;
import com.kingdee.eas.cp.dm.ContentTypeEnum;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import com.kingdee.eas.fdc.basedata.FDCCPDMFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 房地产-协同平台-文档管理 工具类
 * <p>
 * 包含 协同文档上传、下载、更新、删除等操作
 * 
 * 版本：<br>
 * 1.0 实现协同文档单个上传 2011-07-01<br>
 * 1.1 批量上传，文档下载 2011-7-10<br>
 * 1.2 添加附件到已存在文档、删除附件 2011-7-29<br>
 * 
 * @author emanon
 * @version 1.2
 * @createTime 2011-07-01(党的生日喔，亲)
 * @lastUpdate 1011-07-29
 * 
 */
public class FDCCPDMUtil {
	private static Logger logger = Logger
			.getLogger(FDCCPDMUtil.class.getName());

	/**
	 * 创建一个文档基本信息Info
	 * 
	 * @param bizInfo
	 *            业务单据Info，可以是单据或基础资料（用于设置文档编号、下载文档等）
	 * @param title
	 *            文档标题
	 * @param mark
	 *            文档标识，可空（用于区分文档，适用于同一个业务单据下上传不同作用的文档）
	 * @param category
	 *            文档上传的栏目
	 * @param contentType
	 *            文档类型，包括word、excel、PDF、HTML四种
	 * @return
	 * @throws EASBizException
	 */
	public static ArchiveDocumentParamsInfo createParams(
			ObjectBaseInfo bizInfo, String title, String mark,
			CategoryInfo category, ContentTypeEnum contentType)
			throws EASBizException {
		if (bizInfo == null) {
			logger.error("业务实例为空，无法构造协同文档");
			throw new EASBizException(new NumericExceptionSubItem("000",
					"业务实例不能为空"));
		}
		ArchiveDocumentParamsInfo params = new ArchiveDocumentParamsInfo();
		params.setBizID(bizInfo.getId().toString());
		params.setNumber(bizInfo.getString("number"));
		params.setTitle(title);
		params.setMark(mark);
		params.setCategoryId(category.getId().toString());
		if (contentType != null) {
			params.setContentType(contentType);
		} else {
			params.setContentType(ContentTypeEnum.HTML);
		}
		// 默认属性
		params.setCreateTime(new Timestamp(SysUtil.getAppServerTime(null)
				.getTime()));
		params.setModuleName("房地产_进度管理");
		params.setVersion("sch");
		params.setArchive(true);
		return params;
	}

	/**
	 * 上传一个文件到协同平台
	 * <p>
	 * 会单独生成一个新文档，文件作为该文档的附件
	 * 
	 * @param paramsInfo
	 *            文档基本信息
	 * @param file
	 *            待上传文件
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			File file) throws BOSException, IOException, EASBizException {
		upLoadFileToCP(paramsInfo, null, file);
	}

	/**
	 * 上传一个文件到协同平台
	 * <p>
	 * 会单独生成一个新文档，文件作为该文档的附件，包含文档内容
	 * 
	 * @param paramsInfo
	 *            文档基本信息
	 * @param content
	 *            文档内容
	 * @param file
	 *            待上传文件
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			String content, File file) throws BOSException, IOException,
			EASBizException {
		logger.info("开始上传");
		long start = new Date().getTime();
		List fileBytes = new ArrayList();
		String[] fileNames = null;
		if (file != null) {
			fileNames = new String[] { file.getName() };
			InputStream is = new FileInputStream(file);
			byte[] fileByte = new byte[(int) file.length()];
			is.read(fileByte);
			is.close();
			fileBytes.add(fileByte);
		}
		FDCCPDMFacadeFactory.getRemoteInstance().upLoadFileToCP(paramsInfo,
				content, fileNames, fileBytes);
		long end = new Date().getTime();
		logger.info("上传结束，用时" + (end - start) + "毫秒");
	}

	/**
	 * 上传一批文件到协同平台
	 * <p>
	 * 会单独生成一个新文档，文件作为该文档的附件
	 * 
	 * @param paramsInfo
	 *            文档基本信息
	 * @param file
	 *            待上传文件
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			File[] file) throws BOSException, IOException, EASBizException {
		upLoadFileToCP(paramsInfo, null, file);
	}

	/**
	 * 上传一批文件到协同平台
	 * <p>
	 * 会单独生成一个新文档，文件作为该文档的附件
	 * 
	 * @param paramsInfo
	 *            文档基本信息
	 * @param content
	 *            文档内容
	 * @param file
	 *            待上传文件
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			String content, File[] file) throws BOSException, IOException,
			EASBizException {
		logger.info("开始批量上传");
		long start = new Date().getTime();
		List fileBytes = new ArrayList();
		String[] fileNames = null;
		if (file != null && file.length > 0) {
			fileNames = new String[file.length];
			for (int i = 0; i < file.length; i++) {
				if (file[i] != null) {
					fileNames[i] = file[i].getName();
					InputStream is = new FileInputStream(file[i]);
					byte[] fileByte = new byte[(int) file[i].length()];
					is.read(fileByte);
					is.close();
					fileBytes.add(fileByte);
				}
			}
		}
		FDCCPDMFacadeFactory.getRemoteInstance().upLoadFileToCP(paramsInfo,
				content, fileNames, fileBytes);
		long end = new Date().getTime();
		logger.info("上传结束，用时" + (end - start) + "毫秒");
	}

	/**
	 * 添加一个附件到已存在的协同文档
	 * 
	 * @param bizID
	 *            协同文档ID
	 * @param file
	 *            需上传附件
	 * @throws IOException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void addFileToCP(String bizID, File file) throws IOException,
			BOSException, EASBizException {
		if (FDCHelper.isEmpty(bizID) || file == null || !file.exists()) {
			logger.error("玩我呢，传空的进来");
		} else {
			FileInputStream in = new FileInputStream(file);
			byte[] fileBytes = new byte[(int) file.length()];
			in.read(fileBytes);
			in.close();
			FDCCPDMFacadeFactory.getRemoteInstance().addFileToCP(bizID,
					file.getName(), fileBytes);
		}

	}

	/**
	 * 从协同平台下载指定文档的附件
	 * <p>
	 * 会存到本地临时目录，返回一个File[]类型指向这些文件
	 * 
	 * @param bizID
	 *            业务单据ID
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static File[] downLoadFileFromCP(String bizID, String fileName)
			throws BOSException, SQLException, IOException, EASBizException {
		String tmpPath = System.getenv("TMP") + "/" + bizID;
		File file = new File(tmpPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String docID = getDocID(bizID, fileName);
		List files = FDCCPDMFacadeFactory.getRemoteInstance()
				.downLoadFileFromCP(bizID, docID);
		if (files != null) {
			File[] rt = new File[files.size()];
			for (int i = 0; i < files.size(); i++) {
				Map fileMap = (Map) files.get(i);
				String name = (String) fileMap.get("fileName");
				byte[] fileByte = (byte[]) fileMap.get("fileByte");
				File tmpFile = new File(tmpPath + "/" + name);
				FileOutputStream out = new FileOutputStream(tmpFile);
				out.write(fileByte);
				out.close();
				rt[i] = tmpFile;
			}
			return rt;
		}
		return null;
	}

	/**
	 * 删除协同附件
	 * 
	 * @param bizID
	 * @param fileName
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 */
	public static void deleteCPFile(String bizID, String fileName)
			throws BOSException, SQLException, EASBizException {
		String docID = getDocID(bizID, fileName);
		FDCCPDMFacadeFactory.getRemoteInstance().deleteCPFile(bizID, docID);
	}

	/**
	 * 根据协同文档ID，以及附件的文件名（带后缀），取得附件在FTP上的ID
	 * 
	 * @param bizID
	 *            协同文档ID
	 * @param fileName
	 *            附件文件名
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private static String getDocID(String bizID, String fileName)
			throws BOSException, SQLException {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select FDocumentID as docID from T_FME_BusiDoc ");
		sql.appendSql(" where FBusinessID = ? and FDisplayName = ?");
		sql.addParam(bizID);
		sql.addParam(fileName);
		IRowSet rs = sql.executeQuery();
		String docID = null;
		while (rs.next()) {
			docID = rs.getString("docID");
			break;
		}
		return docID;
	}
}
