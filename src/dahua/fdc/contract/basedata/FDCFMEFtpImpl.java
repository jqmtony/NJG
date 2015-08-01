package com.kingdee.eas.fdc.basedata;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.net.ftp.FileTransferInputStream;
import com.enterprisedt.net.ftp.FileTransferOutputStream;
import com.kingdee.eas.base.fme.ftp.FMEEdFtpImpl;
import com.kingdee.eas.base.fme.ftp.FMEFtpInputStream;
import com.kingdee.eas.base.fme.ftp.FtpConfig;
import com.kingdee.eas.base.fme.service.FtpDocumentInfo;
import com.kingdee.eas.base.fme.util.FMECommonRes;
import com.kingdee.eas.base.fme.util.FMEDataZipUtils;
import com.kingdee.eas.base.fme.util.FMEUIUtils;

/**
 * ����һ���ӵ����࣬�������д�����ȫ���������ĸ���
 * <p>
 * ��Ϊ���ĸ��ඨ��ı���ȫ�ǿӵ���private<br>
 * ��Ȼ�����������ʤ����<br>
 * ����ӵ������븸��Ψһ�Ĳ�ͬ�ǣ��ϴ�ʱ�������<br>
 * �ǵģ�uploadStream(InputStream is, String remoteFileName)�ӵ��ļ���
 * 
 * @author emanon
 * 
 */
public class FDCFMEFtpImpl extends FMEEdFtpImpl {
	private static Logger logger = Logger.getLogger(FDCFMEFtpImpl.class.getName());

	private FileTransferClient ftp;

	private FileTransferClient ftp2;

	private FileInputStream fis = null;

	private FtpConfig config;

	private static int TransferFile_Cache = 1024 * 256;

	public FDCFMEFtpImpl(FtpConfig config) throws Exception {
		super(config);
		logger.info("FMEEdFtpImpl");
		this.config = config;
		try {
			ftp = new FileTransferClient();
			ftp.setRemoteHost(config.getHost());
			ftp.setUserName(config.getUsername());
			ftp.setPassword(config.getPassword());
			ftp.setRemotePort(config.getPort());
			// ���ö����Ʒ�ʽ����
			ftp.setContentType(FTPTransferType.BINARY);
			ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
			logger.info("----------------------------------------------------");
			logger.info("---	Ftp host is:" + config.getHost() + "			 -----");
			logger.info("---	Ftp port is:" + config.getPort() + "             -----");
			logger.info("----------------------------------------------------");

		} catch (FTPException ftpe) {
			logger.info("���������׳��쳣:" + ftpe.getMessage());
			ftpe.printStackTrace();
			logger.info("ʵ����FileTransferClient �׳��쳣" + ftpe.getMessage());
			throw new Exception("ʵ����FileTransferClient �׳��쳣", ftpe);
		}
	}

	public FDCFMEFtpImpl(String host, int port, String username, String password) throws Exception {
		super(host, port, username, password);
		logger.info("FMEEdFtpImpl");
		try {
			ftp = new FileTransferClient();
			config = new FtpConfig();
			ftp.setRemoteHost(host);
			config.setHost(host);

			ftp.setUserName(username);
			config.setUsername(username);
			ftp.setPassword(password);
			config.setPassword(password);
			config.setPort(port);
			ftp.setRemotePort(port);
			// ���ö����Ʒ�ʽ����
			ftp.setContentType(FTPTransferType.BINARY);

		} catch (FTPException ftpe) {
			logger.warn("���������׳��쳣:" + ftpe.getMessage());
			ftpe.printStackTrace();

			throw new Exception("ʵ����FileTransferClient �׳��쳣", ftpe);
		}
	}

	private void createNewFtp() throws Exception {
		try {
			ftp2 = new FileTransferClient();

			ftp2.setRemoteHost(this.config.getHost());

			ftp2.setUserName(this.config.getUsername());

			ftp2.setPassword(this.config.getPassword());

		} catch (FTPException ftpe) {
			logger.error("ʵ����FileTransferClient �׳��쳣" + ftpe.getMessage());
			throw new Exception("ʵ����FileTransferClient �׳��쳣", ftpe);
		}
	}

	private FileTransferClient createExtFtp(FtpConfig extConfig) throws Exception {
		FileTransferClient ftp3 = null;
		try {
			ftp3 = new FileTransferClient();

			ftp3.setRemoteHost(extConfig.getHost());

			ftp3.setUserName(extConfig.getUsername());

			ftp3.setPassword(extConfig.getPassword());

		} catch (FTPException ftpe) {
			logger.error("ʵ����createExtFtp FileTransferClient �׳��쳣" + ftpe.getMessage());
			throw new Exception("ʵ����createExtFtp FileTransferClient �׳��쳣", ftpe);
		}
		return ftp3;
	}

	/*
	 * @TODO ����FTP��������ִ��Ԥ�ȼ���Ƿ������Ѿ�����
	 * 
	 * @see com.kingdee.eas.web.IFtp#connect()
	 */
	public boolean connect() {
		boolean conResult = false;
		try {
			logger.info("��������");
			if (ftp == null) {
				logger.error("FMEEdFtpImplʵ��û�д������޷�������������������");
				conResult = false;
			} else {
				if (!ftp.isConnected()) {
					ftp.connect();
					logger.info("<<<<<<<<<<<<<<<<<<<������FTP������������");
					conResult = true;
				} else {
					logger.info("�Ѿ�������FTP�����������ᣬ�������½�������");
					conResult = true;
				}
			}
		} catch (Exception se) {
			logger.warn("���������׳��쳣:" + se.getMessage());
			se.printStackTrace();
			conResult = false;
		}
		return conResult;
	}

	/*
	 * @TODO �Ͽ����� (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#disconnect()
	 */
	public boolean disconnect() {
		boolean discResult = false;
		try {
			if (ftp.isConnected()) {
				ftp.disconnect();
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>�Ͽ���FTP������������");
				discResult = true;
			} else {
				logger.info("�ļ�������û�н������ᣬ����Ҫ�ر�");
				discResult = true;
			}
		} catch (Exception se) {
			logger.warn("���������׳��쳣:" + se.getMessage());
			se.printStackTrace();
			discResult = false;
		}
		return discResult;
	}

	public void checkConnect() {
		if (!ftp.isConnected()) {
			logger.info("��Ftp������û�н�������");
			connect();
		} else {
			logger.info("��Ftp�������Ѿ���������");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#delete(java.lang.String)
	 */
	public boolean delete(String remoteFileName) throws Exception {
		boolean delResult = false;
		checkConnect();
		if (ftp.exists(remoteFileName)) {
			ftp.deleteFile(remoteFileName);
			delResult = true;
		} else {
			logger.info("Ҫɾ�����ļ�" + remoteFileName + "������ ����Ҫɾ��");
			delResult = true;
		}
		logger.info("ɾ��FTP�����ļ�:" + remoteFileName);
		disconnect();
		return delResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#download(java.lang.String,
	 * java.lang.String)
	 */
	public void download(String localFileName, String remoteFileName) throws Exception {
		checkConnect();
		ftp.downloadFile(localFileName, remoteFileName);

		logger.info("��FTP�������������ļ�:" + remoteFileName);
		logger.info("�����ļ����ر���Ϊ:" + localFileName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#rename(java.lang.String, java.lang.String)
	 */
	public void rename(String ftpName, String newName) {
		checkConnect();
		try {
			ftp.rename(ftpName, newName);
		} catch (Exception se) {
			logger.error("�������ļ�" + ftpName + "Ϊ" + newName + "�׳��쳣" + se.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#upload(java.lang.String, java.lang.String)
	 */
	public String upload(String localFileName, String remoteFileName) throws Exception {
		checkConnect();
		String uploadResult = "";
		try {
			uploadResult = ftp.uploadFile(localFileName, remoteFileName);
			logger.info("�ϴ������ļ�:" + localFileName);
			logger.info("�ϴ���FTP·��:" + remoteFileName);

		} catch (Exception se) {
			logger.error("FMEEdFtpImpl�ϴ��ļ�ʧ�ܣ������ϴ��ļ�������" + se.getMessage());
			throw new Exception("FMEEdFtpImpl�ϴ��ļ�ʧ�ܣ������ϴ��ļ�������", se);
		}
		return uploadResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#upload(java.lang.String, java.lang.String)
	 */
	public void uploadStream(String remoteFileName) throws Exception {
		checkConnect();

		FileTransferOutputStream ftos = ftp.uploadStream(remoteFileName);
		byte[] buffer = new byte[TransferFile_Cache];
		int readV = -1;
		while ((readV = fis.read(buffer)) != -1) {
			ftos.write(buffer, 0, readV);
		}
		ftos.close();
		fis.close();

		logger.info("�ϴ���FTP·��:" + remoteFileName);

	}

	/**
	 * @TODO ��ȡ��Ҫ�ϴ����ļ���·��Ŀ¼��ʵ���ļ�����ɵ�����
	 * @param uploadFileName
	 * @return ָ���ϴ����ļ���·�����ļ�����ɵ�����
	 */
	public static String[] getUploadSaveDirAndName(String uploadFileName) {
		String[] dnArray = new String[2];
		int fpos = uploadFileName.lastIndexOf("/");
		if (fpos != -1) {
			dnArray[0] = uploadFileName.substring(0, fpos);
			dnArray[1] = uploadFileName.substring(fpos + 1);
		} else {
			dnArray[0] = "";
			dnArray[1] = uploadFileName;
		}
		return dnArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#upload(java.lang.String, java.lang.String)
	 */
	public int uploadStream_back(InputStream is, String remoteFileName) throws Exception {
		int uploadResult = 2;
		try {
			checkConnect();

			int isAV = is.available();
			logger.info("���������õ�Ϊ:" + isAV);
			logger.info("Ԥ�ϴ��ļ�Ϊ:" + remoteFileName);

			String[] fileDirNameArray = getUploadSaveDirAndName(remoteFileName);// FtpUtils
			// .
			// getUploadSaveDirAndName
			// (
			// remoteFileName
			// )
			// ;

			logger.info("��ȡ�ϴ����ļ�·��:" + fileDirNameArray[0] + "---" + fileDirNameArray[1]);

			// �ж���Ӧ��Ŀ¼�Ƿ����
			String fileDirs = fileDirNameArray[0];
			String[] dirArray = fileDirs.split("/");
			StringBuffer filePathBuffer = new StringBuffer();
			for (int di = 0; di < dirArray.length; di++) {
				String oneDir = dirArray[di];
				try {
					filePathBuffer.append(oneDir);
					ftp.createDirectory(filePathBuffer.toString());
					logger.info("����Ŀ¼" + oneDir);

				} catch (Exception se) {
					logger.info("Ŀ¼" + filePathBuffer.toString() + "�Ѿ����ڣ�����Ҫ����");
				} finally {
					filePathBuffer.append("/");
				}
			}

			try {
				if (!ftp.exists(fileDirs)) {
					ftp.createDirectory(fileDirs);
					logger.info("����Ŀ¼" + fileDirs);
				}
			} catch (Exception se) {
				logger.info("Ŀ¼" + fileDirs + "�Ѿ�����");
			}

			changeDir(fileDirNameArray[0]);

			logger.info("ִ���ļ��ϴ�");

			FileTransferOutputStream ftos = ftp.uploadStream(fileDirNameArray[1]);
			byte[] buffer = new byte[TransferFile_Cache];
			int readV = -1;
			String blockArray = "";
			// �ж��Ƿ���ֹ

			boolean isCancel = false;
			try {
				while ((readV = is.read(buffer)) != -1) {
					// ftos.write(buffer, 0, readV);
					byte[] temp = FMEDataZipUtils.pack(buffer);
					blockArray += temp.length + ",";
					ftos.write(temp);
				}
				uploadResult = 0;
			} catch (InterruptedIOException iio) {
				logger.warn("�����ϴ�����ֹ" + iio.getMessage());
				isCancel = true;
			} finally {
				ftos.close();
				is.close();
			}

			// ���ɼ��ܸ����ļ�
			String extFileName = fileDirNameArray[1].substring(0, fileDirNameArray[1].indexOf(".") + 1) + FMECommonRes.EXT;
			FileTransferOutputStream ftosExt = ftp.uploadStream(extFileName);
			File extFile = new File(FMEUIUtils.getClientTempDir() + File.separator + extFileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(extFile));
			output.write(blockArray);
			output.close();
			FileInputStream extIs = new FileInputStream(extFile);
			buffer = new byte[TransferFile_Cache];
			readV = -1;
			isCancel = false;
			try {
				while ((readV = extIs.read(buffer)) != -1) {
					ftosExt.write(buffer, 0, readV);
				}
				uploadResult = 0;
			} catch (InterruptedIOException iio) {
				logger.warn("�����ϴ�����ֹ" + iio.getMessage());
				isCancel = true;
			} finally {
				ftosExt.close();
				extIs.close();
			}

			try {
				extFile.delete();
			} catch (Exception e) {
				logger.warn("ɾ�����ܸ����ļ�����!" + e.getMessage());
			}

			// �����ֹ�ˣ����ļ���Ҫɾ��
			if (isCancel) {
				logger.info("�����ϴ����ļ�����ֹ����Ҫ��������ļ�");
				changeDir(PARENT_DIR);
				changeDir(PARENT_DIR);
				boolean deleteCancelResult = this.delete(remoteFileName);
				logger.info("�������ֹ���ļ����Ϊ:" + deleteCancelResult);
				uploadResult = 1;
			} else {
				logger.info("�ļ��ϴ����");
				logger.info("�ϴ���FTP·��:" + remoteFileName);
			}
		} catch (Exception se) {
			se.printStackTrace();
			logger.warn("�ϴ��ļ��׳��쳣:" + se.getMessage());
			uploadResult = 2;
		} finally {
			this.disconnect();
		}
		return uploadResult;
	}

	public static final String EXT_FLAG = "KingdeeFME";

	private boolean fobidZipFileType(String uploadFileName) {
		boolean allowZip = false;
		List fileType = new ArrayList();
		fileType.add("pdf");
		fileType.add("docx");
		fileType.add("doc");
		fileType.add("xlsx");
		fileType.add("pptx");
		fileType.add("wmv");
		fileType.add("asf");
		fileType.add("wma");
		fileType.add("wm");
		fileType.add("avi");
		fileType.add("mpg");
		fileType.add("mpeg");
		fileType.add("mp3");
		fileType.add("mid");
		fileType.add("midi");
		fileType.add("rmi");
		fileType.add("wav");
		fileType.add("vob");
		int index = uploadFileName.lastIndexOf(".");
		String uploadFileType = "";
		if (index != -1) {
			uploadFileType = uploadFileName.substring(index + 1);
			uploadFileType = uploadFileType.toLowerCase();
		}

		if (fileType.indexOf(uploadFileType) != -1) {
			allowZip = true;
		}
		return allowZip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @TODO ���ɼ����ļ���չ����������Ϣ��ϵ�һ���ļ���
	 * 
	 * @see com.kingdee.eas.web.IFtp#upload(java.lang.String, java.lang.String)
	 */
	public int uploadStream(InputStream is, String remoteFileName) throws Exception {
		int uploadResult = 2;
		try {
			checkConnect();

			int isAV = is.available();
			logger.info("���������õ�Ϊ:" + isAV);
			logger.info("Ԥ�ϴ��ļ�Ϊ:" + remoteFileName);

			String[] fileDirNameArray = getUploadSaveDirAndName(remoteFileName);

			logger.info("��ȡ�ϴ����ļ�·��:" + fileDirNameArray[0] + "---" + fileDirNameArray[1]);

			// �ж���Ӧ��Ŀ¼�Ƿ����
			String fileDirs = fileDirNameArray[0];
			String[] dirArray = fileDirs.split("/");
			StringBuffer filePathBuffer = new StringBuffer();
			for (int di = 0; di < dirArray.length; di++) {
				String oneDir = dirArray[di];
				try {
					filePathBuffer.append(oneDir);
					ftp.createDirectory(filePathBuffer.toString());
					logger.info("����Ŀ¼" + oneDir);

				} catch (Exception se) {
					logger.warn("Ŀ¼" + filePathBuffer.toString() + "�Ѿ����ڣ�����Ҫ����");
				} finally {
					filePathBuffer.append("/");
				}
			}

			try {
				if (!ftp.exists(fileDirs)) {
					ftp.createDirectory(fileDirs);
					logger.info("����Ŀ¼" + fileDirs);
				}
			} catch (Exception se) {
				logger.warn("Ŀ¼" + fileDirs + "�Ѿ�����");
			}

			changeDir(fileDirNameArray[0]);

			logger.info("ִ���ļ��ϴ�");

			FileTransferOutputStream ftos = ftp.uploadStream(fileDirNameArray[1]);
			byte[] buffer = new byte[TransferFile_Cache];
			int readV = -1;

			boolean isCancel = false;

			try {

				while ((readV = is.read(buffer)) != -1) {
					ftos.write(buffer, 0, readV);
				}

				uploadResult = 0;
			} catch (InterruptedIOException iio) {
				logger.warn("�����ϴ�����ֹ" + iio.getMessage());
				isCancel = true;
			} finally {
				ftos.close();
				is.close();
			}

			// �����ֹ�ˣ����ļ���Ҫɾ��
			if (isCancel) {
				logger.info("�����ϴ����ļ�����ֹ����Ҫ��������ļ�");
				changeDir(PARENT_DIR);
				changeDir(PARENT_DIR);
				boolean deleteCancelResult = this.delete(remoteFileName);
				logger.info("�������ֹ���ļ����Ϊ:" + deleteCancelResult);
				uploadResult = 1;
			} else {
				logger.info("�ļ��ϴ����");
				logger.info("�ϴ���FTP·��:" + remoteFileName);
			}
		} catch (Exception se) {
			se.printStackTrace();
			logger.warn("�ϴ��ļ��׳��쳣:" + se.getMessage());
			uploadResult = 2;
		} finally {
			this.disconnect();
		}
		return uploadResult;
	}

	public void showDirOrFileInfo(String dir) throws Exception {
		logger.info("Dir " + dir + "file is follows :");
		String[] descriptions = ftp.directoryNameList(dir, true);
		for (int i = 0; i < descriptions.length; i++) {
			logger.info("Dir Or File " + i + " is:" + descriptions[i]);
		}
	}

	public void showFileDetailInfo() throws Exception {
		FTPFile[] files = ftp.directoryList();
		for (int i = 0; i < files.length; i++) {
			FTPFile oneFTPFile = files[i];
			StringBuffer sb = new StringBuffer();
			sb.append("Group ").append(oneFTPFile.getGroup()).append("\nLinkedName ").append(oneFTPFile.getLinkedName()).append("\nName ").append(
					oneFTPFile.getName()).append("\nOwner ").append(oneFTPFile.getOwner()).append("\nPath ").append(oneFTPFile.getPath()).append(
					"\nPermissions ").append(oneFTPFile.getPermissions()).append("\nRaw ").append(oneFTPFile.getRaw());
			logger.info(sb.toString());
		}
	}

	public static final String PARENT_DIR = ".";

	public static final String ROOT_DIR = "/";

	public void changeDir(String newDir) throws Exception {
		checkConnect();
		String directory = ftp.getRemoteDirectory();
		logger.info("currentRemoteDirection is:" + directory);
		if (newDir.equals(PARENT_DIR)) {
			ftp.changeToParentDirectory();
			logger.info("�л����ϲ�Ŀ¼");
		} else {
			String[] pathGrade = newDir.split("/");
			for (int i = 0; i < pathGrade.length; i++) {
				ftp.changeDirectory(pathGrade[i]);
			}
			// ftp.changeDirectory(newDir);
			logger.info("�л���Ŀ¼" + newDir);
		}
	}

	public void makeDir(String newDir) throws Exception {
		checkConnect();
		logger.info("currentRemoteDirection is:" + ftp.getRemoteDirectory());
		logger.info("����Ҫ������Ŀ¼Ϊ:" + newDir);
		try {
			ftp.createDirectory(newDir);
		} catch (Exception se) {

			logger.error("����FTPĿ¼" + newDir + "ʧ��" + se.getMessage());

		}
	}

	public void deleteDir(String delDir) throws Exception {
		checkConnect();
		logger.info("currentRemoteDirection is:" + ftp.getRemoteDirectory());
		logger.info("����Ҫɾ����Ŀ¼Ϊ:" + delDir);

		try {
			ftp.deleteDirectory(delDir);
		} catch (Exception se) {

			logger.error("ɾ��FTPĿ¼" + delDir + "ʧ��" + se.getMessage());

		}
	}

	public InputStream getInputStream(String uploadFile) {
		try {
			if (this.fis == null) {
				this.fis = new FileInputStream(new File(uploadFile));
			}
		} catch (Exception se) {
			se.printStackTrace();
		}
		return this.fis;
	}

	public FileTransferInputStream getUploadStream(String remoteFile) {
		checkConnect();
		FileTransferInputStream downStream = null;
		try {
			downStream = ftp.downloadStream(remoteFile);
		} catch (Exception se) {

			logger.error("��ȡ�����ļ����׳��쳣" + se.getMessage());

		}
		return downStream;
	}

	/**
	 * @TODO ʵ������������֧�ֽ�������ʾ����
	 * @see com.kingdee.eas.web.IFtp#downloadStream(java.io.InputStream,
	 *      java.lang.String)
	 */
	public boolean downloadStream(FMEFtpInputStream dis, String localFileName) throws Exception {
		checkConnect();
		boolean downloadResult = false;

		FileOutputStream fos = new FileOutputStream(localFileName);
		byte[] buffer = new byte[TransferFile_Cache];
		int readV = -1;
		try {
			while ((readV = dis.read(buffer)) != -1) {
				fos.write(buffer, 0, readV);
			}
			downloadResult = true;
		} catch (InterruptedIOException iio) {
			logger.warn("�������ر���ֹ" + iio.getMessage());
		}
		fos.close();
		dis.close();
		logger.info("����FTP�ļ�������:" + localFileName);

		this.disconnect();
		return downloadResult;
	}

	public boolean downloadStreamByFileName(String remoteFileName, String localFileName) throws Exception {
		checkConnect();

		byte[] buffer = new byte[TransferFile_Cache];
		int readV = -1;

		// �Ż�Ϊ��ȡ���ش�����Ϣ
		String[] blockArry = getFileZipBlockArray(ftp, remoteFileName);

		boolean downloadResult = false;

		FileTransferInputStream ftis = ftp.downloadStream(remoteFileName);

		FileOutputStream fos = new FileOutputStream(localFileName);
		buffer = new byte[TransferFile_Cache];
		readV = -1;
		try {
			if (blockArry != null) {
				for (int i = 0; i < blockArry.length; i++) {
					buffer = new byte[Integer.parseInt(blockArry[i])];
					if (ftis.read(buffer) != -1) {
						byte[] temp = FMEDataZipUtils.unpack(buffer);
						fos.write(temp);
					}
				}
			} else {
				while ((readV = ftis.read(buffer)) != -1) {
					fos.write(buffer, 0, readV);
				}
			}
			downloadResult = true;
		} catch (InterruptedIOException iio) {
			logger.warn("�������ر���ֹ" + iio.getMessage());
		}
		fos.close();
		ftis.close();
		logger.info("����FTP�ļ�������:" + localFileName);

		this.disconnect();
		return downloadResult;
	}

	/**
	 * @TODO ��ȡ�����ļ���ѹ������Ϣ����
	 * @param ftp
	 * @param remoteFileName
	 * @return
	 */
	public String[] getFileZipBlockArray(FileTransferClient ftp, String remoteFileName) {

		String[] blockArry = null;
		int readV = -1;
		byte[] buffer = new byte[TransferFile_Cache];

		// ���ܸ����ļ�·��
		String extFileName = remoteFileName.substring(0, remoteFileName.lastIndexOf(".") + 1) + FMECommonRes.EXT;
		try {
			boolean existZipExtFile = ftp.exists(extFileName);

			// �����������汾�ļ��ܸ����ļ�
			if (existZipExtFile) {
				logger.info("���ڼ��ܸ����ļ������ݵ�һ�ּ��ܷ�ʽ���ܴ���");
				FileTransferInputStream extftis = null;
				try {
					// ���ؼ��ܸ����ļ�
					extftis = ftp.downloadStream(extFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// ������ܸ����ļ���Ϊ�գ�ִ�ж�ȡ���ܸ����ļ�
				if (extftis != null) {
					String[] fileDirNameArray = getUploadSaveDirAndName(extFileName);
					File extFile = new File(FMEUIUtils.getClientTempDir() + fileDirNameArray[1]);
					FileOutputStream extFos = new FileOutputStream(extFile);
					while ((readV = extftis.read(buffer)) != -1) {
						extFos.write(buffer, 0, readV);
					}
					extftis.close();
					extFos.close();
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(extFile)));
					String lineString = "";
					while ((lineString = br.readLine()) != null) {
						blockArry = lineString.split(",");
					}
					br.close();
					try {
						extFile.delete();
					} catch (Exception e) {
						logger.warn("ɾ�����ܸ����ļ�����:" + e.getMessage());
						e.printStackTrace();
					}
				}
			} else {
				// �ж��Ƿ��ǵڶ��ַ�ʽ�ļ���
				logger.info("�����ڼ��ܸ����ļ������ݵڶ��ּ��ܷ�ʽ���ܴ���");
				long fileSize = ftp.getSize(remoteFileName);
				if (fileSize > 20) {

					FileTransferInputStream ftis = ftp.downloadStream(remoteFileName);
					// ��ȡ���20���ֽ�
					byte[] end20Byte = new byte[20];
					ftis.skip(fileSize - 20);
					int readCount = ftis.read(end20Byte, 0, 20);
					ftis.close();

					String lastEnd = new String(end20Byte);

					if (lastEnd.endsWith(EXT_FLAG)) {
						int firstPos = lastEnd.indexOf("#");
						if (firstPos != -1) {
							String extraBlockSize = lastEnd.substring(0, firstPos);
							int blockLength = Integer.parseInt(extraBlockSize);
							byte[] blockByte = new byte[blockLength];

							ftis = ftp.downloadStream(remoteFileName);
							ftis.skip(fileSize - blockLength - 20);
							ftis.read(blockByte, 0, blockLength);
							ftis.close();
							String blockStr = new String(blockByte);
							blockArry = blockStr.split(",");
						} else {
							logger.info("�ļ��ļ���block���ȳ���10G,�����ļ������쳣");
						}
					} else {
						logger.info("û�м����ļ���ʶ");
					}
				} else {
					logger.info("�ļ�û�о������ܴ���");
				}
			}
		} catch (Exception se) {
			logger.warn("��ȡ�����ļ���ѹ��Block�׳��쳣:" + se.getMessage());
		}
		return blockArry;
	}

	/*
	 * �����ļ���֧�ֶ���ļ��������� ���� Javadoc��
	 * 
	 * @see
	 * com.kingdee.eas.base.fme.ftp.IFMEFtp#downloadStream(java.awt.Component,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int downloadStream(Component monitorParentCom, Object monitorMessage, String remoteFileName, String localFileName, String downloadIndex)
			throws Exception {
		int downloadStatus = -1;

		try {
			checkConnect();
			if (!ftp.isConnected()) {
				logger.info("================================��������������ʧ��");
				downloadStatus = -1;
				return downloadStatus;
			}
		} catch (Exception se) {
			logger.warn("================================��������������ʧ��");
			downloadStatus = -1;
			return downloadStatus;
		}

		try {

			byte[] buffer = new byte[TransferFile_Cache];
			int readV = -1;

			String[] blockArry = getFileZipBlockArray(ftp, remoteFileName);

			// ���ؼ����ļ�
			FileOutputStream fos = new FileOutputStream(localFileName);

			long fileSize = ftp.getSize(remoteFileName);
			FileTransferInputStream ftis = ftp.downloadStream(remoteFileName);
			FMEFtpInputStream dis = new FMEFtpInputStream(monitorParentCom, monitorMessage, ftis, (int) fileSize);
			dis.setUpload(false);
			// ����������������
			dis.setProgressIndex(downloadIndex);
			try {
				// ������ܸ����ļ������ݴ��ڣ�������ļ�������ֱ�������ļ�
				if (blockArry != null) {
					for (int i = 0; i < blockArry.length; i++) {
						buffer = new byte[Integer.parseInt(blockArry[i])];
						if (dis.read(buffer) != -1) {
							byte[] temp = FMEDataZipUtils.unpack(buffer);
							fos.write(temp);
						}
					}
					// ��ȡ�����ʱ������100%����ʾ
					dis.updateUnzipPregress();
				} else {
					while ((readV = dis.read(buffer)) != -1) {
						fos.write(buffer, 0, readV);
					}
				}
				downloadStatus = 1;
			} catch (InterruptedIOException iio) {
				logger.warn("---------------------------------�������ر���ֹ" + iio.getMessage());
				downloadStatus = 0;
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (dis != null) {
						dis.close();
					}
					if (ftis != null) {
						ftis.close();
					}
				} catch (Exception se) {
					logger.warn("ȡ�������ļ��׳��쳣:" + se.getMessage());
					se.printStackTrace();
				}

				// ����������ļ��б���ֹ��������ص��ļ�
				if (downloadStatus == 0) {
					try {
						File cancelDownloadFile = new File(localFileName);
						if (cancelDownloadFile.exists()) {
							boolean deleteCancelDownloadFile = cancelDownloadFile.delete();
							logger.info("�����ļ����̱���ֹ��ɾ��ȡ�����ļ�" + localFileName + " ���Ϊ��" + deleteCancelDownloadFile);
						}
					} catch (Exception rcEx) {
						logger.warn("ɾ��ȡ�����ص��ļ��׳��쳣:" + rcEx.getMessage());
					}
				}

			}
		} catch (Exception se) {
			se.printStackTrace();
			logger.warn("�����ļ�ʧ��,ԭ�����Ϊ�����ļ��������ˣ�" + se.getMessage());
			downloadStatus = -2;
		}
		logger.info("����FTP�ļ�������:" + localFileName);
		this.disconnect();
		return downloadStatus;
	}

	/**
	 * @TODO ��ȡFtpָ�����ļ���С����������ʹ��
	 * @param file
	 * @return
	 */
	public long getFileSize(String file) {
		long size = 0;
		checkConnect();
		try {
			size = ftp.getSize(file);
		} catch (Exception se) {
			logger.error("��ȡFtp�ļ���С�׳��쳣" + se.getMessage());
			size = -1;
		}
		return size;
	}

	/*
	 * @TODO ʵ��ֱ�Ӹ���FTP�������ϵ��ļ� (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#copyFile(java.lang.String,
	 * java.lang.String)
	 */
	public void copyFile(String initFileName, String destFileName) throws Exception {
		checkConnect();
		try {
			long beginTime = System.currentTimeMillis();
			if (destFileName == null || destFileName.trim().length() <= 0) {
				destFileName = "temp" + initFileName;
			} else {
				if (destFileName.trim().equalsIgnoreCase(initFileName)) {
					destFileName = "����" + initFileName;
				}
			}
			ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);

			FileTransferInputStream fis = ftp.downloadStream(initFileName);
			try {
				if (this.ftp2 == null) {
					this.createNewFtp();
				}
				ftp2.connect();
				FileTransferOutputStream fos = ftp2.uploadStream(destFileName);

				byte[] buffer = new byte[TransferFile_Cache];
				int readSize = 0;
				while ((readSize = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, readSize);
				}
				fos.close();
				fis.close();
			} catch (Exception se) {
				logger.error("ͨ�������������ļ��׳��쳣" + se.getMessage());

			} finally {
				if (ftp2 != null && ftp2.isConnected()) {
					ftp2.disconnect();
				}
			}

			long endTime = System.currentTimeMillis();
			logger.info("�����ļ�:" + initFileName + "��ʱΪ:" + (endTime - beginTime));
		} catch (Exception se) {
			throw new Exception("�����ļ��׳��쳣" + se.getMessage(), se);
		}
	}

	public void copyFile(String initFileName, String destFileName, FileTransferClient targetFtpClient) throws Exception {
		checkConnect();
		try {
			long beginTime = System.currentTimeMillis();
			if (destFileName == null || destFileName.trim().length() <= 0) {
				destFileName = "temp" + initFileName;
			} else {
				if (destFileName.trim().equalsIgnoreCase(initFileName)) {
					destFileName = "����" + initFileName;
				}
			}
			ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);

			FileTransferInputStream fis = ftp.downloadStream(initFileName);
			try {

				targetFtpClient.connect();
				FileTransferOutputStream fos = targetFtpClient.uploadStream(destFileName);

				byte[] buffer = new byte[TransferFile_Cache];
				int readSize = 0;
				while ((readSize = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, readSize);
				}
				fos.close();
				fis.close();
			} catch (Exception se) {
				logger.error("ͨ�������������ļ��׳��쳣" + se.getMessage());

			} finally {
				if (targetFtpClient != null && targetFtpClient.isConnected()) {
					targetFtpClient.disconnect();
				}
			}

			long endTime = System.currentTimeMillis();
			logger.info("�����ļ�:" + initFileName + "��ʱΪ:" + (endTime - beginTime));
		} catch (Exception se) {
			throw new Exception("�����ļ��׳��쳣" + se.getMessage(), se);
		}
	}

	public List checkFtpLink() {
		List retList = new ArrayList();
		if (this.ftp == null) {
			retList.add(new Boolean(false));
			retList.add("��ȡ��Ӧ��FTP Instance Ϊnull");
		} else {
			if (this.connect() && this.disconnect()) {
				retList.add(new Boolean(true));
				retList.add("������������");
			} else {
				retList.add(new Boolean(false));
				retList.add("�������ӡ��Ͽ�����ʧ��!!!");
			}
		}
		return retList;
	}

	public boolean renameRootPath(String sourceFtpRootPath, String targetFtpRootPath) {
		boolean renameResult = false;
		checkConnect();
		try {
			ftp.rename(sourceFtpRootPath, targetFtpRootPath);
			renameResult = true;
		} catch (Exception se) {
			logger.error("renameRootPath�׳��쳣" + se.getMessage());
			renameResult = false;
		}
		return renameResult;
	}

	public boolean transferOneDocument(String sourceFilePath, String taregetFilePath) throws Exception {
		boolean transResult = false;
		try {
			this.copyFile(sourceFilePath, taregetFilePath);
			transResult = true;
		} catch (Exception se) {
			transResult = false;
			logger.warn("���䵥���ļ�,�׳��쳣:" + se.getMessage());
		}
		return transResult;
	}

	public boolean transferOneDocumentWithMutilFtp(String sourceFilePath, String taregetFilePath, FtpConfig extConfig) throws Exception {
		boolean transResult = false;
		try {
			FileTransferClient extFtp = this.createExtFtp(extConfig);
			this.copyFile(sourceFilePath, taregetFilePath, extFtp);
			extFtp.disconnect();
			transResult = true;
		} catch (Exception se) {
			transResult = false;
			logger.warn("���䵥���ļ�,�׳��쳣:" + se.getMessage());
		}
		return transResult;
	}

	private List getDirectoryFileList(String dir, String parentDir) throws IOException, FTPException {
		List fileList = new ArrayList();
		logger.info("��λ��Ŀ¼Ϊ��" + parentDir);
		logger.info("��λ��ǰĿ¼Ϊ��" + dir);
		String currentParentDir = parentDir;

		if (!FMEUIUtils.isBlankString(currentParentDir)) {
			ftp.changeDirectory("/" + parentDir);
		}
		ftp.changeDirectory(dir);

		String[] filelist = ftp.directoryNameList();
		boolean modifyParentDir = false;
		for (int i = 0; i < filelist.length; i++) {
			String oneDirFile = filelist[i];

			// ����Ŀ¼
			if (oneDirFile.indexOf(".") == -1) {
				if (!modifyParentDir) {
					if (FMEUIUtils.isBlankString(currentParentDir)) {
						currentParentDir = dir;
					} else {
						currentParentDir = currentParentDir + FMECommonRes.FTP_PATH_SEPERATOR + dir;
					}
					modifyParentDir = true;
				}
				List subDirFile = getDirectoryFileList(oneDirFile, currentParentDir);
				logger.info("�������ļ���" + subDirFile.size());
				fileList.addAll(subDirFile);
			} else {
				// �����ļ�
				if (!FMEUIUtils.isBlankString(parentDir)) {
					fileList.add(parentDir + FMECommonRes.FTP_PATH_SEPERATOR + dir + FMECommonRes.FTP_PATH_SEPERATOR + oneDirFile);
				} else {
					fileList.add(dir + FMECommonRes.FTP_PATH_SEPERATOR + oneDirFile);
				}

			}
		}
		return fileList;
	}

	public FtpDocumentInfo getFtpDocumentInfo(String serverID, String rootPath) throws Exception {
		FtpDocumentInfo ftpDocInfo = new FtpDocumentInfo(serverID, rootPath);
		try {
			// �Ƚ�������
			this.ftp.connect();
			List fileList = getDirectoryFileList(rootPath, "");
			for (int i = 0; i < fileList.size(); i++) {
				ftpDocInfo.addDocument((String) fileList.get(i));
			}
		} catch (Exception sex) {
			logger.warn("��ȡָ��FTP�������ĸ�Ŀ¼�µ������ļ���Ϣ���׳��쳣:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return ftpDocInfo;
	}

	public boolean createSavePath(String rootPath, String pathName) throws Exception {
		boolean createResult = false;
		try {
			// �Ƚ�������
			this.ftp.connect();
			changeDirToRootPath(rootPath);

			// �ж���Ӧ��pathName�Ƿ����
			try {
				if (!ftp.exists(pathName)) {
					ftp.createDirectory(pathName);
					logger.info("�����洢·��:" + pathName);
				}
			} catch (Exception se) {
				logger.warn("�洢·��:" + pathName + "�Ѿ�����");
			}
			createResult = true;
		} catch (Exception sex) {
			logger.warn("createSavePath�׳��쳣:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return createResult;
	}

	public boolean updateSavePath(String rootPath, String oldPathName, String newPathName) throws Exception {
		boolean updateResult = false;
		try {
			// �Ƚ�������
			this.ftp.connect();
			changeDirToRootPath(rootPath);

			// �ж���Ӧ��pathName�Ƿ����
			try {
				ftp.changeDirectory(oldPathName);
				// ����ɵ�·�����ڣ�������
				ftp.changeToParentDirectory();
				// �ж��µ��ļ����Ƿ����
				ftp.rename(oldPathName, newPathName);
				logger.info("�洢·����" + oldPathName + "����Ϊ" + newPathName);

			} catch (Exception se) {
				logger.warn("�ɵĴ洢·��:" + oldPathName + "������");

				// �ж���Ӧ��newPathName�Ƿ����
				try {
					if (!ftp.exists(newPathName)) {
						ftp.createDirectory(newPathName);
						logger.info("�����洢·��:" + newPathName);
					}
				} catch (Exception se2) {
					logger.warn("�洢·��:" + newPathName + "�Ѿ�����");
				}
				updateResult = true;
			}
			updateResult = true;
		} catch (Exception sex) {
			logger.warn("updateSavePath�׳��쳣:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return updateResult;
	}

	public void changeDirToRootPath(String rootPath) throws Exception {
		try {
			if (!ftp.exists(rootPath)) {
				ftp.createDirectory(rootPath);
				logger.info("�����洢��·��:" + rootPath);
			}
		} catch (Exception se) {
			logger.warn("�洢��·��:" + rootPath + "�Ѿ�����");
		}
		ftp.changeDirectory(rootPath);
	}

	public boolean deleteSavePath(String rootPath, String pathName) throws Exception {
		boolean delResult = false;
		try {
			// �Ƚ�������
			this.ftp.connect();

			changeDirToRootPath(rootPath);

			try {
				ftp.changeDirectory(pathName);
				// ����ɵ�·�����ڣ�������
				ftp.changeToParentDirectory();
				logger.info("�洢·��:" + pathName + "�Ѿ�����,׼��ɾ��");
				ftp.deleteDirectory(pathName);

				try {
					ftp.changeDirectory(pathName);
					ftp.changeToParentDirectory();
					logger.info("�洢·��:" + pathName + "ɾ�����ɹ�");
					delResult = false;
				} catch (Exception ssse) {
					logger.warn("�洢·��:" + pathName + "�Ѿ�ɾ��");
					delResult = true;
				}
			} catch (Exception se) {
				logger.warn("�洢·��:" + pathName + "�����ڣ�����Ҫɾ��");
				delResult = true;
			}
		} catch (Exception sex) {
			logger.warn("deleteSavePath�׳��쳣:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return delResult;
	}

}
