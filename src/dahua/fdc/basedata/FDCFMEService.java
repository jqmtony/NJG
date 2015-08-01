package com.kingdee.eas.fdc.basedata;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kingdee.bos.Context;
import com.kingdee.eas.base.fme.bo.DocumentBO;
import com.kingdee.eas.base.fme.dao.FMEDaoFactory;
import com.kingdee.eas.base.fme.dao.FMEDaoRes;
import com.kingdee.eas.base.fme.service.AbstractFMEService;
import com.kingdee.eas.base.fme.service.ServiceResult;
import com.kingdee.eas.base.fme.service.WebFMEServiceImpl;
import com.kingdee.eas.base.fme.uimodel.FMEParameter;
import com.kingdee.eas.base.fme.util.FMECommonRes;
import com.kingdee.eas.base.fme.util.FMEUIUtils;

public class FDCFMEService extends AbstractFMEService {
	private static final Log log = LogFactory.getLog(WebFMEServiceImpl.class);

	private Context ctx = null;

	public FDCFMEService(Context ctx) {
		if (log.isInfoEnabled()) {
			log.info("����Web GUIFMEServiceImpl");
		}
		this.ctx = ctx;
		initDaoAndFtpInterface();
	}

	public void initDaoAndFtpInterface() {
		// TODO �Զ����ɷ������
		if (log.isInfoEnabled()) {
			log.info("������Context is======================================:"
					+ this.ctx);
		}
		this.dao = FMEDaoFactory.getFMEDaoInstance(ctx);
		try {
			this.ftp = new FDCFMEFtpImpl(dao.getDefaultFtpServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServiceResult uploadFile(InputStream[] is, String[] fileName,
			String businessID, FMEParameter param) {
		ServiceResult result = new ServiceResult();
		long allFileSize = 0;
		int uploadCount = fileName.length;
		if (is == null || is.length <= 0 || fileName == null
				|| fileName.length <= 0 || is.length != fileName.length) {
			result.setResult(false, "�ϴ�������Ϊnull,�����ϴ����ļ�����Ϊnull,��ȡ��С��һ��");
		} else {

			try {
				String newPathID = "";
				String newCategoryInfo = "";
				if (param != null) {
					newPathID = param.getNewPathID();
					newCategoryInfo = param.getNewCategoryInfo();
					log.info("=����NewPathID======================"
							+ newPathID);
					log.info("=����newCategoryInfo======================"
									+ newCategoryInfo);
				} else {
					log.info("�����FMEParameter Ϊnull");
				}

				log.info("��ȡNewPathID ��Ӧ��=========:" + newPathID);

				try {
					log.info("============>>>===================one time query savepath ,get ftpconfig");
					// lazyLoadFtpServiceInstanaceByPathID(newPathID);

				} catch (Exception sse) {
					log.info("===============================���ر���·���׳��쳣��"
									+ sse.getMessage());
				}

				DocumentBO[] docBO = new DocumentBO[uploadCount];
				// ���Ӷ��ļ�ID�ı���
				String[] documentIDArray = new String[uploadCount];

				/**
				 * ��һ�μ���DAO����
				 **/

				for (int i = 0; i < is.length; i++) {

					// �ϴ�����ļ���
					String remoteFileName = FMEUIUtils
							.getRandomFileName(FMEUIUtils
									.getFileExtName(fileName[i]));
					// �ϴ������ʾ��
					String displayName = fileName[i];

					DocumentBO oneBO = new DocumentBO();
					oneBO.setDisplayName(displayName);
					oneBO.setName(remoteFileName);
					// �����л�ȡ����
					int fileSize = is[i].available();
					allFileSize += fileSize;
					oneBO.setSize(fileSize);
					oneBO.setStatus(FMEDaoRes.DOCUMENT_STATUS_UNULOAD);
					oneBO.setBusinessID(businessID);
					// û����������������ȱʡ�ļ��ϴ�����
					if (fileName[i].length() >= 255) {
						oneBO.setDescription(fileName[i].substring(0, 255));
					} else {
						oneBO.setDescription(fileName[i]);
					}

					// 1.1�汾���ӡ��ļ��洢·��ID,�Լ���Ӧ������Ϣ gpx 2009-3-27
					oneBO.setNewPathID(newPathID);
					oneBO.setNewCategoryInfo(newCategoryInfo);

					docBO[i] = oneBO;
				}

				// ����DAO
				log.info("============>>>==>>>==============second time ,insert waiting upload record");
				List insertResultList = null;
				try {
					insertResultList = dao.insertUnloadDocument(docBO);
				} catch (Exception insertUE) {
					log.info("������ϴ��ļ���¼�׳��쳣��" + insertUE.getMessage());
				}

				int insertListSize = 0;
				if (insertResultList != null) {
					insertListSize = insertResultList.size();
				}

				int[] updateFileResult = new int[insertListSize];

				boolean uploadFileOK = true;

				/***
				 * ִ���ϴ��ļ�����
				 * */
				for (int k = 0; k < insertListSize; k++) {

					String[] oneInsertResult = (String[]) insertResultList
							.get(k);

					String documentID = oneInsertResult[0];
					String uploadPath = oneInsertResult[1];
					log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&��ȡ�ϴ��ļ�"
									+ k + "Document id:" + documentID);

					documentIDArray[k] = documentID;

					log.info("��ȡ�ϴ��ļ�����·�� :" + uploadPath);

					DocumentBO oneBO = docBO[k];

					oneBO.setPath(uploadPath);
					oneBO.setDocumentID(documentID);

					// �ϴ��ļ�
					if (documentID != null && documentID.trim().length() > 0) {
						// �ϴ�Ftp�ļ�
						String remoteFileName = oneBO.getName();
						int uploadResult = ftp.uploadStream(is[k], uploadPath
								+ remoteFileName);
						updateFileResult[k] = uploadResult;
						if (uploadResult == 0) {
							// �����ϴ���¼��Ϣ
							// ��Ҫ����Ϊ����״̬
							oneBO.setStatus(FMEDaoRes.DOCUMENT_STATUS_NORMAL);
						} else {
							// �ļ��ϴ����̱���ֹ,�����쳣״̬�������ܹ�����������DocumentID
							uploadFileOK = false;
							documentIDArray[k] = "";
							if (uploadResult == 1) {
								result.setResult(false,
										FMECommonRes.UPLOAD_CANCEL_WRONG);
							} else {
								result.setResult(false,
										FMECommonRes.UPLOAD_FTPLINK_WRONG);
							}
						}
					} else {
						uploadFileOK = false;
						result.setResult(false,
								FMECommonRes.UPLOAD_DB_LINK_WRONG);
						break;
					}
				}

				if (uploadFileOK && insertListSize > 0) {

					// ����DAO
					log.info("============>>>==>>>==>>>============third time ,update docuent status,save docInfo");

					String[] uploadDocumentID = dao.insert(docBO);

					if (uploadDocumentID.length == documentIDArray.length) {
						log.info("update query document info==================================");
						result.setResult(true);
					} else {
						log.info("update query document info fail:==================================Ԥ�ϴ����ļ��У����²���BusiDoc��¼ʧ��");
						result.setResult(false, "Ԥ�ϴ����ļ��У����²���BusiDoc��¼ʧ��");
					}
				} else {
					log.info("===================�ϴ��ļ��У������ϴ�ʧ�ܵ��ļ����׳��쳣��Ϣ"
									+ result.getResultDetail());
				}
				result.setResultID(documentIDArray);
				result.setFileSize(allFileSize);
			} catch (Exception se) {
				se.printStackTrace();
				log.info("�ϴ��ļ������׳��쳣:" + se.getMessage());
				String exMessage = se.getMessage();
				if (exMessage.indexOf("java.io.FilePermission") != -1) {
					result.setResult(false, "�ϴ��ļ��쳣���ͻ���JREȨ������");
				} else {
					result.setResult(false, "�ϴ��ļ������׳��쳣");
				}
				// �����ϴ�������ϸ��Ϣ
				result.setResultDetail(se.getMessage());
			}
		}
		return result;
	}

}
