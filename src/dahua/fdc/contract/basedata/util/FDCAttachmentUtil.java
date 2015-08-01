package com.kingdee.eas.fdc.basedata.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentException;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IAttachmentFtpFacade;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.common.AttachmentServerManager;
import com.kingdee.eas.base.attachment.common.SimpleAttachmentInfo;
import com.kingdee.eas.base.attachment.ftp.AttachmentDownloadClient;
import com.kingdee.eas.base.attachment.ftp.AttachmentFtpInfo;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.attachment.util.VariousAttachmentInfoMaker;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.IFDCSQLFacade;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.DataZipUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ���ز�������������
 * <p>
 * �����ϴ������ء��鿴���򿪡�ɾ�������ȹ���
 * 
 * @author emanon
 * 
 */
public class FDCAttachmentUtil {
	private static final Logger logger = Logger
			.getLogger(FDCAttachmentUtil.class.getName());

	/**
	 * ȡ��һ��ҵ�񵥾ݹ��������и�����Ϣ����
	 * 
	 * @param billID
	 * @return
	 * @throws BOSException
	 */
	public static AttachmentCollection getAttachmentsInfo(String billID)
			throws BOSException {
		Set attIDs = new HashSet();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select att.FID as attID ");
		sql.appendSql(" from T_BAS_ATTACHMENT as att ");
		sql.appendSql(" left join T_BAS_BoAttchAsso as bot ");
		sql.appendSql(" on bot.FAttachmentID = att.FID ");
		sql.appendSql(" where bot.FBoID = ? ");
		sql.addParam(billID);
		IRowSet rs = sql.executeQuery();
		try {
			while (rs.next()) {
				attIDs.add(rs.getString("attID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("simpleName");
		sic.add("attachID");
		sic.add("type");
		sic.add("isShared");
		sic.add("size");
		sic.add("sizeInByte");
		sic.add("beizhu");
		sic.add("size");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", attIDs, CompareType.INCLUDE));
		view.setFilter(filter);
		SorterItemInfo sorter = new SorterItemInfo("createTime");
		sorter.setSortType(SortType.DESCEND);
		view.getSorter().add(sorter);
		return AttachmentFactory.getRemoteInstance().getAttachmentCollection(
				view);
	}

	/**
	 * �ϴ�һ���ļ�����������������ҵ�񵥾�
	 * 
	 * @param billID
	 *            ҵ�񵥾�ID
	 * @param upAtt
	 *            �ϴ����ļ�
	 * @throws Exception
	 * @return ����ID
	 */
	public static String uploadAtt(String billID, File upAtt) throws Exception {
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		SimpleAttachmentInfo sai = getSimpleFile(upAtt, info);
		IAttachment ia = AttachmentFactory.getRemoteInstance();
		AttachmentClientManager attachmentClientManager = AttachmentManagerFactory
				.getClientManager();
		AttachmentInfo attachmentInfo = VariousAttachmentInfoMaker
				.makeAttachmentInfo(ia, billID, sai);
		return attachmentClientManager.submitAttachment(attachmentInfo);
	}

	/**
	 * ����ָ��ID�ĸ�����ָ���ļ�
	 * <p>
	 * �������ļ�Ϊ�գ�����������ʱ�ļ��в�����·��
	 * 
	 * @param attID
	 *            ����ID
	 * @return ���غ���ļ�
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws IOException
	 */
	public static File downloadAtt(String attID, File saveFile)
			throws EASBizException, BOSException, IOException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("name");
		sic.add("simpleName");
		sic.add("sizeInByte");
		AttachmentInfo att = AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attID), sic);

		return downloadAtt(att,saveFile);
	}
	
	public static File downloadAtt(AttachmentInfo att, File saveFile) throws IOException, BOSException, EASBizException {
		String attID = att.getId().toString();
		String name = att.getName();
		if (saveFile == null) {
			saveFile = File.createTempFile("KDTF-" + name, "." + att.getSimpleName());
		}
		FileOutputStream fos = new FileOutputStream(saveFile);
		IAttachmentFtpFacade iAff = AttachmentFtpFacadeFactory.getRemoteInstance();
		ByteArrayOutputStream dataByte = new ByteArrayOutputStream();
		OutputStream out = dataByte;
		AttachmentDownloadClient downloadClient = new AttachmentDownloadClient(iAff);
		downloadClient.setOutputStream(out);
		downloadClient.downLoad(attID);

		byte[] down = dataByte.toByteArray();
		if (down.length == att.getSizeInByte()) {
			logger.info(name + ": no unpack! ");
			fos.write(down);
		} else {
			logger.info(name + ": unpack! ");
			fos.write(DataZipUtils.unpack(down));
		}
		out.close();
		fos.close();
		return saveFile;
	}

	/**
	 * ��ָ��ID�ĸ���
	 * <p>
	 * ʹ��windowsĬ�ϵ��ļ��򿪷�ʽ��ֻ���ڿͻ���Ϊwindowsʱʹ��<br>
	 * �ǵģ���������������
	 * 
	 * @param attID
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws IOException
	 */
	public static void openAtt(String attID) throws EASBizException,
			BOSException, IOException {
		String path = downloadAtt(attID, null).getPath();
		openFileInWindows(path);
	}

	/**
	 * ɾ��һ��ҵ�񵥾ݹ����ĸ���
	 * <p>
	 * ����ø���������������ҵ�񵥾ݣ���ֻɾ����ǰ������ϵ<br>
	 * ����ø���ֻ������ǰҵ�񵥾ݣ�����ͬ��������һ��ɾ��
	 * 
	 * @param bizID
	 * @param attID
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static void deleteAtt(String bizID, String attID) throws EASBizException, BOSException {
		/* modified by zhaoqin for R140314-0312 on 2014/03/18 start */		
		//if (FdcStringUtil.isNotBlank(bizID) || FdcStringUtil.isNotBlank(attID)) {
		if (FdcStringUtil.isBlank(bizID) || FdcStringUtil.isBlank(attID)) {
			return;
		}
		/* modified by zhaoqin for R140314-0312 on 2014/03/18 end */

		BoAttchAssoInfo boAttchAssoInfo = new BoAttchAssoInfo();
		boAttchAssoInfo.setBoID(bizID);
		AttachmentInfo attachmentInfo = new AttachmentInfo();
		attachmentInfo.setId(BOSUuid.read(attID));
		boAttchAssoInfo.setAttachment(attachmentInfo);

		BoAttchAssoCollection boCol = new BoAttchAssoCollection();
		boCol.add(boAttchAssoInfo);

		BoAttchAssoFactory.getRemoteInstance().deleteByBoList(boCol);
	}

	/**
	 * ʹ��windowsĬ�ϵĴ򿪷�ʽ��һ���ļ�
	 * <p>
	 * ע��ֻ�пͻ���Ϊwindows�����ſ���ʹ��
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void openFileInWindows(String path) throws IOException {
		File tempbat = File.createTempFile("tempbat", ".bat");
		FileWriter fw = new FileWriter(tempbat);

		// ��start�����������ļ��������ţ�֧�ֿո��ļ�����
		int lp = path.lastIndexOf(File.separator);
		if (lp == -1) {
			path = "\"" + path + "\"";
		} else {
			path = path.substring(0, lp + 1) + "\"" + path.substring(lp + 1)
					+ "\"";
		}

		fw.write("start /I " + path); // ���»����´�
		fw.close();
		String tempbatFullname = tempbat.getPath();
		Runtime.getRuntime().exec(tempbatFullname);
	}

	/**
	 * ��������Info
	 * <p>
	 * ��Ϯ��ϵͳ��������
	 * 
	 * @param file
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private static SimpleAttachmentInfo getSimpleFile(File file,
			AttachmentUIContextInfo info) throws Exception {
		try {
			String fullname = file.getName();
			// String mainname =
			// StringUtil4File.getMainFileName(BOSUuid.create("0E9C4124"
			// ).toString());
			String mainname = StringUtil4File.getMainFileName(fullname);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("HHmmss");
			String a = format.format(date);
			mainname += a;
			String extname = StringUtil4File.getExtendedFileName(fullname);
			// ��ֹ�ϴ��ļ������ͺ�׺���д�д�ַ�������ϵͳ��������Ϊδ֪�����ļ���BUG
			extname = extname.toLowerCase();
			byte[] content = FileGetter.getBytesFromFile(file);
			
//			IAttachmentSize iattachmentsize = AttachmentSizeFactory.getRemoteInstance();
//			double fileMaxSize = iattachmentsize.getFileSize();
//			if(FDCHelper.compareValue(content.length / (1024*1024), fileMaxSize) == 1) {
//			//if(content.length>AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH){
//				//FDCMsgBox.showError("�ϴ���������ϵͳԼ���Ĵ�С("+AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH/(1024*1024)+"M)");
//				FDCMsgBox.showError("�ϴ���������ϵͳԼ���Ĵ�С("+ fileMaxSize +"M)");
//				SysUtil.abort();
//			}
			SimpleAttachmentInfo sai = new SimpleAttachmentInfo();
			sai.setMainName(mainname);
			sai.setDescription(mainname);
			sai.setExtName(extname);
			sai.setContent(content);
			sai.setCode(info.getCode());
			sai.setIsShared(false);
			sai.setShareRange(info.getShareRange());
			sai.setBeizhu(info.getBeizhu());
			return sai;
		} catch (FileNotFoundException fe) {
			throw new AttachmentException(AttachmentException.READFILEERROR);
		}
	}
	
	/**
	 * ɾ���������и���
	 * 
	 * @param bizID
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static void deleteAllAtt(Context ctx, String bizID) throws EASBizException, BOSException {
		if (ctx == null) {
			try {
				AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
				attachmentClientManager.deleteAttachemtsByBoID(bizID);
			} catch (ObjectNotFoundException onfe) {
				logger.info("", onfe);
			}
		} else {
			try {
				AttachmentServerManager attachmentServerManager = AttachmentManagerFactory.getServerManager(ctx);
				attachmentServerManager.deleteAttachemtsByBoID(bizID);
			} catch (ObjectNotFoundException onfe) {
				logger.info("", onfe);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ���������"������ҵ��������"ʵ��ʵ��
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @return
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 */
	public static IBoAttchAsso getBoAttchAssoInstance(Context ctx) throws BOSException {
		IBoAttchAsso iBoAttchAsso = null;

		if (null != ctx) {
			iBoAttchAsso = BoAttchAssoFactory.getLocalInstance(ctx);
		} else {
			iBoAttchAsso = BoAttchAssoFactory.getRemoteInstance();
		}

		return iBoAttchAsso;
	}

	/**
	 * ���������"����"ʵ��ʵ��
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @return
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 */
	public static IAttachment getAttachmentInstance(Context ctx) throws BOSException {
		IAttachment iAttachment = null;

		if (null != ctx) {
			iAttachment = AttachmentFactory.getLocalInstance(ctx);
		} else {
			iAttachment = AttachmentFactory.getRemoteInstance();
		}

		return iAttachment;
	}

	/**
	 * ����������ҵ�񵥾�ID��ȡ"������ҵ��������"�б�
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param boID
	 *            ҵ�񵥾�ID;�ǿ�
	 * @return
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 */
	public static BoAttchAssoCollection getBoAttchAssosByBoID(Context ctx, String boID) throws BOSException {
		BoAttchAssoCollection boAttchAssoCollection = new BoAttchAssoCollection();

		if (FdcStringUtil.isBlank(boID)) {
			return boAttchAssoCollection;
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", boID));

		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("attachment.createTime");
		sort.setSortType(SortType.DESCEND);
		sorts.add(sort);

		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.setFilter(filter);
		viewInfo.setSorter(sorts);
		boAttchAssoCollection = getBoAttchAssoInstance(ctx).getBoAttchAssoCollection(viewInfo);

		return boAttchAssoCollection;
	}

	/**
	 * ����������ҵ�񵥾�ID������������ҵ�����������б�
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param srcBoID
	 *            Դҵ�񵥾�ID;�ǿ�
	 * @param destBoID
	 *            Ŀ��ҵ�񵥾�ID;�ǿ�
	 * @throws BOSException
	 * 
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 */
	public static void copyBoAttchAssosByBoID(Context ctx, String srcBoID, String destBoID) throws BOSException,
			EASBizException {
		if (FdcStringUtil.isBlank(srcBoID) || FdcStringUtil.isBlank(destBoID)) {
			return;
		}
		String destBosTypeStr = BOSUuid.read(destBoID).getType().toString();

		BoAttchAssoCollection boAttchAssoCollection = getBoAttchAssosByBoID(ctx, srcBoID);
		if (FdcObjectCollectionUtil.isNotEmpty(boAttchAssoCollection)) {
			IBoAttchAsso iBoAttchAsso = getBoAttchAssoInstance(ctx);
			BoAttchAssoInfo boAttchAssoInfo = null;

			for (Iterator iterator = boAttchAssoCollection.iterator(); iterator.hasNext();) {
				boAttchAssoInfo = (BoAttchAssoInfo) iterator.next();

				boAttchAssoInfo.setId(null);
				boAttchAssoInfo.setBoID(destBoID);
				boAttchAssoInfo.setAssoBusObjType(destBosTypeStr);

				// ���桰������ҵ����������
				iBoAttchAsso.addnew(boAttchAssoInfo);
			}

			// ����ҵ�񵥾�ID����"����"
			// shareAttachmentsByBoID(ctx, srcBoID);
		}
	}

	/**
	 * ����������ҵ�񵥾�ID����"����"
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param boID
	 *            ҵ�񵥾�ID;�ǿ�
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 */
	public static void shareAttachmentsByBoID(Context ctx, String boID) throws BOSException {
		if (FdcStringUtil.isBlank(boID)) {
			return;
		}

		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	UPDATE T_BAS_Attachment tbsAtt	\r\n");
		sqlSb.append("	   SET FIsShared = ?, FSharedDesc_L1 = ?, FSharedDesc_L2 = ?, FSharedDesc_L3 = ?	\r\n");
		sqlSb.append("	 WHERE EXISTS (SELECT tbsBoAA.FAttachmentID	\r\n");
		sqlSb.append("	          FROM T_BAS_BoAttchAsso tbsBoAA	\r\n");
		sqlSb.append("	         WHERE tbsBoAA.FAttachmentID = tbsAtt.FID	\r\n");
		sqlSb.append("	           AND tbsBoAA.FBoID = ?)	\r\n");

		String sql = sqlSb.toString();

		IFDCSQLFacade iFDCSQLFacade = null;
		if (null != ctx) {
			iFDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		} else {
			iFDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		}

		List params = new ArrayList();
		params.add(BooleanUtils.toIntegerObject(true));
		params.add("��");
		params.add("��");
		params.add("��");
		params.add(boID);

		iFDCSQLFacade.executeUpdate(sql, params);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
}
