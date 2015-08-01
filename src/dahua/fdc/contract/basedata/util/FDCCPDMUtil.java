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
 * ���ز�-Эͬƽ̨-�ĵ����� ������
 * <p>
 * ���� Эͬ�ĵ��ϴ������ء����¡�ɾ���Ȳ���
 * 
 * �汾��<br>
 * 1.0 ʵ��Эͬ�ĵ������ϴ� 2011-07-01<br>
 * 1.1 �����ϴ����ĵ����� 2011-7-10<br>
 * 1.2 ��Ӹ������Ѵ����ĵ���ɾ������ 2011-7-29<br>
 * 
 * @author emanon
 * @version 1.2
 * @createTime 2011-07-01(��������ร���)
 * @lastUpdate 1011-07-29
 * 
 */
public class FDCCPDMUtil {
	private static Logger logger = Logger
			.getLogger(FDCCPDMUtil.class.getName());

	/**
	 * ����һ���ĵ�������ϢInfo
	 * 
	 * @param bizInfo
	 *            ҵ�񵥾�Info�������ǵ��ݻ�������ϣ����������ĵ���š������ĵ��ȣ�
	 * @param title
	 *            �ĵ�����
	 * @param mark
	 *            �ĵ���ʶ���ɿգ����������ĵ���������ͬһ��ҵ�񵥾����ϴ���ͬ���õ��ĵ���
	 * @param category
	 *            �ĵ��ϴ�����Ŀ
	 * @param contentType
	 *            �ĵ����ͣ�����word��excel��PDF��HTML����
	 * @return
	 * @throws EASBizException
	 */
	public static ArchiveDocumentParamsInfo createParams(
			ObjectBaseInfo bizInfo, String title, String mark,
			CategoryInfo category, ContentTypeEnum contentType)
			throws EASBizException {
		if (bizInfo == null) {
			logger.error("ҵ��ʵ��Ϊ�գ��޷�����Эͬ�ĵ�");
			throw new EASBizException(new NumericExceptionSubItem("000",
					"ҵ��ʵ������Ϊ��"));
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
		// Ĭ������
		params.setCreateTime(new Timestamp(SysUtil.getAppServerTime(null)
				.getTime()));
		params.setModuleName("���ز�_���ȹ���");
		params.setVersion("sch");
		params.setArchive(true);
		return params;
	}

	/**
	 * �ϴ�һ���ļ���Эͬƽ̨
	 * <p>
	 * �ᵥ������һ�����ĵ����ļ���Ϊ���ĵ��ĸ���
	 * 
	 * @param paramsInfo
	 *            �ĵ�������Ϣ
	 * @param file
	 *            ���ϴ��ļ�
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			File file) throws BOSException, IOException, EASBizException {
		upLoadFileToCP(paramsInfo, null, file);
	}

	/**
	 * �ϴ�һ���ļ���Эͬƽ̨
	 * <p>
	 * �ᵥ������һ�����ĵ����ļ���Ϊ���ĵ��ĸ����������ĵ�����
	 * 
	 * @param paramsInfo
	 *            �ĵ�������Ϣ
	 * @param content
	 *            �ĵ�����
	 * @param file
	 *            ���ϴ��ļ�
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			String content, File file) throws BOSException, IOException,
			EASBizException {
		logger.info("��ʼ�ϴ�");
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
		logger.info("�ϴ���������ʱ" + (end - start) + "����");
	}

	/**
	 * �ϴ�һ���ļ���Эͬƽ̨
	 * <p>
	 * �ᵥ������һ�����ĵ����ļ���Ϊ���ĵ��ĸ���
	 * 
	 * @param paramsInfo
	 *            �ĵ�������Ϣ
	 * @param file
	 *            ���ϴ��ļ�
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			File[] file) throws BOSException, IOException, EASBizException {
		upLoadFileToCP(paramsInfo, null, file);
	}

	/**
	 * �ϴ�һ���ļ���Эͬƽ̨
	 * <p>
	 * �ᵥ������һ�����ĵ����ļ���Ϊ���ĵ��ĸ���
	 * 
	 * @param paramsInfo
	 *            �ĵ�������Ϣ
	 * @param content
	 *            �ĵ�����
	 * @param file
	 *            ���ϴ��ļ�
	 * @throws BOSException
	 * @throws IOException
	 * @throws EASBizException
	 */
	public static void upLoadFileToCP(ArchiveDocumentParamsInfo paramsInfo,
			String content, File[] file) throws BOSException, IOException,
			EASBizException {
		logger.info("��ʼ�����ϴ�");
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
		logger.info("�ϴ���������ʱ" + (end - start) + "����");
	}

	/**
	 * ���һ���������Ѵ��ڵ�Эͬ�ĵ�
	 * 
	 * @param bizID
	 *            Эͬ�ĵ�ID
	 * @param file
	 *            ���ϴ�����
	 * @throws IOException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void addFileToCP(String bizID, File file) throws IOException,
			BOSException, EASBizException {
		if (FDCHelper.isEmpty(bizID) || file == null || !file.exists()) {
			logger.error("�����أ����յĽ���");
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
	 * ��Эͬƽ̨����ָ���ĵ��ĸ���
	 * <p>
	 * ��浽������ʱĿ¼������һ��File[]����ָ����Щ�ļ�
	 * 
	 * @param bizID
	 *            ҵ�񵥾�ID
	 * @param fileName
	 *            �ļ���
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
	 * ɾ��Эͬ����
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
	 * ����Эͬ�ĵ�ID���Լ��������ļ���������׺����ȡ�ø�����FTP�ϵ�ID
	 * 
	 * @param bizID
	 *            Эͬ�ĵ�ID
	 * @param fileName
	 *            �����ļ���
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
