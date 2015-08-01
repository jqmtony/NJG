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
			log.info("创建Web GUIFMEServiceImpl");
		}
		this.ctx = ctx;
		initDaoAndFtpInterface();
	}

	public void initDaoAndFtpInterface() {
		// TODO 自动生成方法存根
		if (log.isInfoEnabled()) {
			log.info("创建的Context is======================================:"
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
			result.setResult(false, "上传输入流为null,或者上传的文件名称为null,获取大小不一致");
		} else {

			try {
				String newPathID = "";
				String newCategoryInfo = "";
				if (param != null) {
					newPathID = param.getNewPathID();
					newCategoryInfo = param.getNewCategoryInfo();
					log.info("=新增NewPathID======================"
							+ newPathID);
					log.info("=新增newCategoryInfo======================"
									+ newCategoryInfo);
				} else {
					log.info("传入的FMEParameter 为null");
				}

				log.info("获取NewPathID 对应的=========:" + newPathID);

				try {
					log.info("============>>>===================one time query savepath ,get ftpconfig");
					// lazyLoadFtpServiceInstanaceByPathID(newPathID);

				} catch (Exception sse) {
					log.info("===============================加载保存路径抛出异常："
									+ sse.getMessage());
				}

				DocumentBO[] docBO = new DocumentBO[uploadCount];
				// 增加对文件ID的保存
				String[] documentIDArray = new String[uploadCount];

				/**
				 * 第一次集中DAO处理
				 **/

				for (int i = 0; i < is.length; i++) {

					// 上传后的文件名
					String remoteFileName = FMEUIUtils
							.getRandomFileName(FMEUIUtils
									.getFileExtName(fileName[i]));
					// 上传后的显示名
					String displayName = fileName[i];

					DocumentBO oneBO = new DocumentBO();
					oneBO.setDisplayName(displayName);
					oneBO.setName(remoteFileName);
					// 从流中获取代码
					int fileSize = is[i].available();
					allFileSize += fileSize;
					oneBO.setSize(fileSize);
					oneBO.setStatus(FMEDaoRes.DOCUMENT_STATUS_UNULOAD);
					oneBO.setBusinessID(businessID);
					// 没有设置描述，设置缺省文件上传内容
					if (fileName[i].length() >= 255) {
						oneBO.setDescription(fileName[i].substring(0, 255));
					} else {
						oneBO.setDescription(fileName[i]);
					}

					// 1.1版本增加、文件存储路径ID,以及对应分类信息 gpx 2009-3-27
					oneBO.setNewPathID(newPathID);
					oneBO.setNewCategoryInfo(newCategoryInfo);

					docBO[i] = oneBO;
				}

				// 调用DAO
				log.info("============>>>==>>>==============second time ,insert waiting upload record");
				List insertResultList = null;
				try {
					insertResultList = dao.insertUnloadDocument(docBO);
				} catch (Exception insertUE) {
					log.info("插入待上传文件记录抛出异常：" + insertUE.getMessage());
				}

				int insertListSize = 0;
				if (insertResultList != null) {
					insertListSize = insertResultList.size();
				}

				int[] updateFileResult = new int[insertListSize];

				boolean uploadFileOK = true;

				/***
				 * 执行上传文件处理
				 * */
				for (int k = 0; k < insertListSize; k++) {

					String[] oneInsertResult = (String[]) insertResultList
							.get(k);

					String documentID = oneInsertResult[0];
					String uploadPath = oneInsertResult[1];
					log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&获取上传文件"
									+ k + "Document id:" + documentID);

					documentIDArray[k] = documentID;

					log.info("获取上传文件保存路径 :" + uploadPath);

					DocumentBO oneBO = docBO[k];

					oneBO.setPath(uploadPath);
					oneBO.setDocumentID(documentID);

					// 上传文件
					if (documentID != null && documentID.trim().length() > 0) {
						// 上传Ftp文件
						String remoteFileName = oneBO.getName();
						int uploadResult = ftp.uploadStream(is[k], uploadPath
								+ remoteFileName);
						updateFileResult[k] = uploadResult;
						if (uploadResult == 0) {
							// 调整上传记录信息
							// 需要调整为正常状态
							oneBO.setStatus(FMEDaoRes.DOCUMENT_STATUS_NORMAL);
						} else {
							// 文件上传过程被中止,或者异常状态，都不能够返回正常的DocumentID
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

					// 调用DAO
					log.info("============>>>==>>>==>>>============third time ,update docuent status,save docInfo");

					String[] uploadDocumentID = dao.insert(docBO);

					if (uploadDocumentID.length == documentIDArray.length) {
						log.info("update query document info==================================");
						result.setResult(true);
					} else {
						log.info("update query document info fail:==================================预上传的文件中，更新插入BusiDoc记录失败");
						result.setResult(false, "预上传的文件中，更新插入BusiDoc记录失败");
					}
				} else {
					log.info("===================上传文件中，出现上传失败的文件，抛出异常信息"
									+ result.getResultDetail());
				}
				result.setResultID(documentIDArray);
				result.setFileSize(allFileSize);
			} catch (Exception se) {
				se.printStackTrace();
				log.info("上传文件操作抛出异常:" + se.getMessage());
				String exMessage = se.getMessage();
				if (exMessage.indexOf("java.io.FilePermission") != -1) {
					result.setResult(false, "上传文件异常，客户端JRE权限限制");
				} else {
					result.setResult(false, "上传文件操作抛出异常");
				}
				// 增加上传错误详细信息
				result.setResultDetail(se.getMessage());
			}
		}
		return result;
	}

}
