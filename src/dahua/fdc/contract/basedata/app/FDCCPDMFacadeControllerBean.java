package com.kingdee.eas.fdc.basedata.app;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.fme.service.ServiceResult;
import com.kingdee.eas.base.fme.uimodel.FMEParameter;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.dm.CategoryFactory;
import com.kingdee.eas.cp.dm.CategoryInfo;
import com.kingdee.eas.cp.dm.DMArchiveException;
import com.kingdee.eas.cp.dm.DmState;
import com.kingdee.eas.cp.dm.DocWfState;
import com.kingdee.eas.cp.dm.DocumentFactory;
import com.kingdee.eas.cp.dm.DocumentInfo;
import com.kingdee.eas.cp.dm.ICategory;
import com.kingdee.eas.cp.dm.IDocument;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import com.kingdee.eas.cp.dm.archive.ArchiveResultInfo;
import com.kingdee.eas.cp.dm.archive.ArchiveUtil;
import com.kingdee.eas.cp.dm.archive.AttachHandleHttp;
import com.kingdee.eas.fdc.basedata.FDCFMEService;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * ���ز�Эͬ����
 * <p>
 * ��Ҫ����Эͬ�ĵ����ϴ�������
 * 
 * @author emanon
 * 
 */
public class FDCCPDMFacadeControllerBean extends
		AbstractFDCCPDMFacadeControllerBean {
	private static final long serialVersionUID = -2725518960009743173L;

	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.FDCCPDMFacadeControllerBean");

	/**
	 * �ϴ��ļ���Эͬ
	 * <p>
	 * ��Ϊ������<br>
	 * 1������ftp�����ϴ��ĵ���ftp������������ftp�ļ���Эͬ�ĵ��Ĺ�ϵ<br>
	 * 2������Эͬ�ĵ���Ϣ
	 */
	protected ArchiveResultInfo _upLoadFileToCP(Context ctx,
			ArchiveDocumentParamsInfo params, String content,
			String[] fileNames, List fileBytes) throws BOSException,
			EASBizException {
		try {
			DocumentInfo docInfo = null;
			// 1������ftp�����ϴ��ĵ���ftp������������ftp�ļ���Эͬ�ĵ��Ĺ�ϵ
			docInfo = contructDocument(ctx, params);
			BOSUuid id = docInfo.getId();
			if (id == null) {
				id = BOSUuid.create(docInfo.getBOSType());
				docInfo.setId(id);
			}
			InputStream[] fileStream = new ByteArrayInputStream[fileBytes
					.size()];
			for (int i = 0; i < fileBytes.size(); i++) {
				byte[] bytes = (byte[]) fileBytes.get(i);
				fileStream[i] = new ByteArrayInputStream(bytes);
			}
			// ���渽����ftp��
			if (fileStream != null && fileStream.length > 0) {
				FMEParameter fmeParameter = new FMEParameter();
				initFMEParameter(fmeParameter, docInfo);
				FDCFMEService fdcfme = new FDCFMEService(ctx);
				fdcfme.uploadFile(fileStream, fileNames, id.toString(),
						fmeParameter);
			} else {
				logger.info("executeArchiveDocument: no attach,�޸���");
			}

			// 2������Эͬ�ĵ���Ϣ
			if (!FDCHelper.isEmpty(content)) {
				// ��������
				InputStream bodyStream = new ByteArrayInputStream(content
						.getBytes());
				if (bodyStream != null) {
					addAttache(ctx, id.toString(), bodyStream, docInfo
							.getContentType().getName().toLowerCase());
				} else {
					logger.info("executeArchiveDocument:no body attach,������");
				}
			}
			// �����ĵ�ʵ��
			IDocument idoc = DocumentFactory.getLocalInstance(ctx);
			idoc.dmSubmit(docInfo);
			// �ϴ����ɽ��
			ArchiveResultInfo resultInfo = new ArchiveResultInfo();
			resultInfo.setDocId(id.toString());
			return contructResult(ctx, id.toString(), resultInfo);
		} catch (EASBizException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * ������Эͬ�ĵ���Ӹ���
	 */
	protected void _addFileToCP(Context ctx, String bizID, String fileName,
			byte[] fileStream) throws BOSException, EASBizException {
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("category.id");
			sic.add("category.docArea.ftpPath");
			DocumentInfo docInfo = DocumentFactory.getLocalInstance(ctx)
					.getDocumentInfo(new ObjectUuidPK(bizID), sic);
			if (docInfo != null) {
				InputStream in = new ByteArrayInputStream(fileStream);
				if (fileStream != null && fileStream.length > 0) {
					FMEParameter fmeParameter = new FMEParameter();
					initFMEParameter(fmeParameter, docInfo);
					FDCFMEService fdcfme = new FDCFMEService(ctx);
					ServiceResult result = fdcfme.uploadFile(in, fileName,
							bizID, fmeParameter);
					if (result.isResult()) {
						logger.info("�ϴ��ɹ�");
					} else {
						logger.info("�ϴ�ʧ��");
						throw new EASBizException(new NumericExceptionSubItem(
								"101", "�ϴ�ʧ��" + result.getResultMessge()));
					}
					logger.info("" + result.getResultFlag());
				} else {
					logger.info("executeArchiveDocument: no attach,�޸���");
				}
			}
		} catch (EASBizException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * ����ָ��id��Эͬ����
	 */
	protected List _downLoadFileFromCP(Context ctx, String bizID, String docID)
			throws BOSException, EASBizException {
		List downFiles = new ArrayList();
		FDCFMEService service = new FDCFMEService(ctx);
		String tmpPath = System.getenv("TMP") + "/" + bizID + "server";
		File file = new File(tmpPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql.appendSql(" select FDisplayName as docName from T_FME_BusiDoc ");
		sql.appendSql(" where FBusinessID = ? and FDocumentID = ?");
		sql.addParam(bizID);
		sql.addParam(docID);
		IRowSet rs = sql.executeQuery();
		String docName = null;
		try {
			while (rs.next()) {
				docName = rs.getString("docName");
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tmpPath = tmpPath + "/" + docName;
		service.downloadFile(bizID, docID, tmpPath);

		File rt = new File(tmpPath);
		try {
			byte[] fileByte = new byte[(int) rt.length()];
			FileInputStream in = new FileInputStream(rt);
			in.read(fileByte);
			in.close();
			Map tmp = new HashMap();
			tmp.put("fileName", docName);
			tmp.put("fileByte", fileByte);
			downFiles.add(tmp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return downFiles;
	}

	/**
	 * ɾ��Эͬ����
	 */
	protected void _deleteCPFile(Context ctx, String bizID, String docID)
			throws BOSException, EASBizException {
		FDCFMEService service = new FDCFMEService(ctx);
		service.deleteFile(bizID, docID, 0);
	}

	/**
	 * ���������ݹ鵵���������ĵ�����
	 * 
	 * @param paramsInfo
	 *            ��¼����鵵��Ϣ���ĵ�����
	 * @return �ĵ���Ϣ
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private DocumentInfo contructDocument(Context ctx,
			ArchiveDocumentParamsInfo paramsInfo) throws EASBizException,
			BOSException {

		DocumentInfo docInfo = new DocumentInfo();

		if (paramsInfo == null)
			throw new DMArchiveException(
					DMArchiveException.ARCHIVE_SOURCECE_IS_NULL);

		// ��֤������׼ȷ��
		paramsInfo.valid(ctx);

		String categoryId = paramsInfo.getCategoryId();
		if (categoryId != null) {
			CategoryInfo categoryInfo = initCategory(ctx, categoryId);
			if (categoryInfo == null) {
				throw new DMArchiveException(
						DMArchiveException.CATEGORY_PATH_NOT_EXIST);
			}
			docInfo.setCategory(categoryInfo);
		} else {
			throw new DMArchiveException(DMArchiveException.CATEGORY_ID_NULL);
		}

		docInfo.setNumber(paramsInfo.getNumber());
		docInfo.setTitle(paramsInfo.getTitle());
		docInfo.setVersion(paramsInfo.getVersion());
		docInfo.setDocMarkNumber(paramsInfo.getDescription());
		docInfo.setCreateTime(paramsInfo.getCreateTime());
		docInfo.setTag(paramsInfo.getTag());
		docInfo.setSummary(paramsInfo.getSummary());
		docInfo.setIsTop(false);
		docInfo.setIsRed(false);
		docInfo.setIsLatest(false);
		docInfo.setReadCount(0);
		docInfo.setCommentCount(0);
		docInfo.setPublishCount(0);
		docInfo.setState(DmState.ENABLE);
		docInfo.setWfState(DocWfState.PUBLISH);
		docInfo.setDocMarkNumber(paramsInfo.getDocNumber());
		//docInfo.setArchivedModule(paramsInfo.getModuleName());
		if (paramsInfo.getAuthor() == null) {
			docInfo.setAuthor(ContextUtil.getCurrentUserInfo(ctx).getPerson());
		} else {
			docInfo.setAuthor(paramsInfo.getAuthor());
		}

		docInfo
				.setCreatePerson(ContextUtil.getCurrentUserInfo(ctx)
						.getPerson());
		docInfo.setCanCopy(paramsInfo.isCanCopy());
		docInfo.setIsHistory(false);
		docInfo.setDocDept(ContextUtil.getCurrentAdminUnit(ctx));
		docInfo.setIsArchive(true);
		docInfo.setBizID(paramsInfo.getBizID());
		docInfo.setContentType(paramsInfo.getContentType());
		docInfo.setDisplayAttachSize("");

		return docInfo;
	}

	/**
	 * ��ʼ����Ŀ
	 * 
	 * @param ctx
	 * @param categoryId
	 * @return
	 * @throws BOSException
	 * @throws DMArchiveException
	 * @throws EASBizException
	 */
	private CategoryInfo initCategory(Context ctx, String categoryId)
			throws BOSException, DMArchiveException, EASBizException {

		CategoryInfo cateInfo = null;

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isArchive"));
		sic.add(new SelectorItemInfo("isVerSet"));
		sic.add(new SelectorItemInfo("attachSet"));
		sic.add(new SelectorItemInfo("attachTypes"));
		sic.add(new SelectorItemInfo("attachSizes"));
		sic.add(new SelectorItemInfo("isAnonymous"));
		sic.add(new SelectorItemInfo("docArea.id"));
		sic.add(new SelectorItemInfo("docArea.ftpPath"));
		sic.add(new SelectorItemInfo("docArea.state"));
		sic.add(new SelectorItemInfo("category.canReSelectTemplate"));

		ICategory iCategory = CategoryFactory.getLocalInstance(ctx);
		if (iCategory.exists(new ObjectUuidPK(categoryId))) {
			cateInfo = iCategory.getCategoryInfo(new ObjectUuidPK(categoryId),
					sic);
			if (cateInfo != null) {
				if (!DmState.ENABLE.equals(cateInfo.getState())
						|| !cateInfo.isIsArchive()) {
					throw new DMArchiveException(
							DMArchiveException.CATEGORY_ID_NOT_ENABLE);
				}
			} else {
				throw new DMArchiveException(
						DMArchiveException.CATEGORY_PATH_NOT_EXIST);
			}
		} else {
			throw new DMArchiveException(
					DMArchiveException.CATEGORY_PATH_NOT_EXIST);
		}

		return cateInfo;
	}

	/**
	 * ��������ʼ���ĵ�����·���������
	 * 
	 * @param fmeParams
	 * @param docInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private FMEParameter initFMEParameter(FMEParameter fmeParams,
			DocumentInfo docInfo) throws EASBizException, BOSException {

		FMEParameter params = fmeParams;
		if (params == null) {
			params = new FMEParameter();
		}
		params.setNewPathID(docInfo.getCategory().getDocArea().getFtpPath());
		params.setNewCategoryInfo(docInfo.getCategory().getId().toString());

		return params;
	}

	/**
	 * �����ϴ����
	 * 
	 * @param ctx
	 * @param docId
	 * @param resultInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private ArchiveResultInfo contructResult(Context ctx, String docId,
			ArchiveResultInfo resultInfo) throws BOSException, EASBizException {

		ArchiveResultInfo tempResult = resultInfo;
		if (tempResult == null) {
			tempResult = new ArchiveResultInfo();
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("title"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("category.name"));
		sic.add(new SelectorItemInfo("category.longNumber"));
		sic.add(new SelectorItemInfo("category.docArea.name"));

		IDocument iDoc = DocumentFactory.getLocalInstance(ctx);
		DocumentInfo docInfo = iDoc.getDocumentInfo(new ObjectUuidPK(docId),
				sic);
		if (docInfo != null) {
			resultInfo.setDocId(docId);
			resultInfo.setTitle(docInfo.getTitle());
			resultInfo.setDocNumber(docInfo.getNumber());
			resultInfo.setCategoryName(docInfo.getCategory().getName());
			resultInfo
					.setAreaName(docInfo.getCategory().getDocArea().getName());
			resultInfo.setSuccess(true);
		}

		return tempResult;
	}

	/**
	 * �������ϴ�����������http��ʽ
	 * 
	 * @param ctx
	 * @param bizId
	 * @param bodyStream
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws Exception
	 */
	private String addAttache(Context ctx, String bizId,
			InputStream bodyStream, String attachType) throws EASBizException,
			BOSException, Exception {
		String fileName = ArchiveUtil.getRandomFileName(attachType);
		AttachHandleHttp httpSave = new AttachHandleHttp();
		return httpSave.saveAttach(ctx, fileName, bizId, bodyStream);
	}
}