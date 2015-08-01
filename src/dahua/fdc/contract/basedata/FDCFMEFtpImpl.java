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
 * 这是一个坑爹的类，他的所有代码完全复制自他的父类
 * <p>
 * 因为他的父类定义的变量全是坑爹的private<br>
 * 当然，青出于蓝而胜于蓝<br>
 * 这个坑爹的类与父类唯一的不同是，上传时不会加密<br>
 * 是的，uploadStream(InputStream is, String remoteFileName)坑爹的加密
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
			// 设置二进制方式传输
			ftp.setContentType(FTPTransferType.BINARY);
			ftp.getAdvancedFTPSettings().setConnectMode(FTPConnectMode.PASV);
			logger.info("----------------------------------------------------");
			logger.info("---	Ftp host is:" + config.getHost() + "			 -----");
			logger.info("---	Ftp port is:" + config.getPort() + "             -----");
			logger.info("----------------------------------------------------");

		} catch (FTPException ftpe) {
			logger.info("建立连接抛出异常:" + ftpe.getMessage());
			ftpe.printStackTrace();
			logger.info("实例化FileTransferClient 抛出异常" + ftpe.getMessage());
			throw new Exception("实例化FileTransferClient 抛出异常", ftpe);
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
			// 设置二进制方式传输
			ftp.setContentType(FTPTransferType.BINARY);

		} catch (FTPException ftpe) {
			logger.warn("建立连接抛出异常:" + ftpe.getMessage());
			ftpe.printStackTrace();

			throw new Exception("实例化FileTransferClient 抛出异常", ftpe);
		}
	}

	private void createNewFtp() throws Exception {
		try {
			ftp2 = new FileTransferClient();

			ftp2.setRemoteHost(this.config.getHost());

			ftp2.setUserName(this.config.getUsername());

			ftp2.setPassword(this.config.getPassword());

		} catch (FTPException ftpe) {
			logger.error("实例化FileTransferClient 抛出异常" + ftpe.getMessage());
			throw new Exception("实例化FileTransferClient 抛出异常", ftpe);
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
			logger.error("实例化createExtFtp FileTransferClient 抛出异常" + ftpe.getMessage());
			throw new Exception("实例化createExtFtp FileTransferClient 抛出异常", ftpe);
		}
		return ftp3;
	}

	/*
	 * @TODO 联结FTP服务器，执行预先检查是否联结已经开发
	 * 
	 * @see com.kingdee.eas.web.IFtp#connect()
	 */
	public boolean connect() {
		boolean conResult = false;
		try {
			logger.info("建立联结");
			if (ftp == null) {
				logger.error("FMEEdFtpImpl实例没有创建，无法建立跟服务器的联结");
				conResult = false;
			} else {
				if (!ftp.isConnected()) {
					ftp.connect();
					logger.info("<<<<<<<<<<<<<<<<<<<建立跟FTP服务器的联结");
					conResult = true;
				} else {
					logger.info("已经建立跟FTP服务器的联结，无需重新建立联结");
					conResult = true;
				}
			}
		} catch (Exception se) {
			logger.warn("建立连接抛出异常:" + se.getMessage());
			se.printStackTrace();
			conResult = false;
		}
		return conResult;
	}

	/*
	 * @TODO 断开联结 (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.web.IFtp#disconnect()
	 */
	public boolean disconnect() {
		boolean discResult = false;
		try {
			if (ftp.isConnected()) {
				ftp.disconnect();
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>断开跟FTP服务器的联结");
				discResult = true;
			} else {
				logger.info("文件服务器没有建立联结，不需要关闭");
				discResult = true;
			}
		} catch (Exception se) {
			logger.warn("建立连接抛出异常:" + se.getMessage());
			se.printStackTrace();
			discResult = false;
		}
		return discResult;
	}

	public void checkConnect() {
		if (!ftp.isConnected()) {
			logger.info("跟Ftp服务器没有建立联结");
			connect();
		} else {
			logger.info("跟Ftp服务器已经建立联结");
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
			logger.info("要删除的文件" + remoteFileName + "不存在 不需要删除");
			delResult = true;
		}
		logger.info("删除FTP服务文件:" + remoteFileName);
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

		logger.info("从FTP服务器上下载文件:" + remoteFileName);
		logger.info("下载文件本地保存为:" + localFileName);

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
			logger.error("重命名文件" + ftpName + "为" + newName + "抛出异常" + se.getMessage());
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
			logger.info("上传本地文件:" + localFileName);
			logger.info("上传到FTP路径:" + remoteFileName);

		} catch (Exception se) {
			logger.error("FMEEdFtpImpl上传文件失败，可能上传文件不存在" + se.getMessage());
			throw new Exception("FMEEdFtpImpl上传文件失败，可能上传文件不存在", se);
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

		logger.info("上传到FTP路径:" + remoteFileName);

	}

	/**
	 * @TODO 获取需要上传的文件的路径目录和实际文件，组成的数组
	 * @param uploadFileName
	 * @return 指定上传的文件的路径、文件名组成的数组
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
			logger.info("输入流可用的为:" + isAV);
			logger.info("预上传文件为:" + remoteFileName);

			String[] fileDirNameArray = getUploadSaveDirAndName(remoteFileName);// FtpUtils
			// .
			// getUploadSaveDirAndName
			// (
			// remoteFileName
			// )
			// ;

			logger.info("获取上传的文件路径:" + fileDirNameArray[0] + "---" + fileDirNameArray[1]);

			// 判定相应的目录是否存在
			String fileDirs = fileDirNameArray[0];
			String[] dirArray = fileDirs.split("/");
			StringBuffer filePathBuffer = new StringBuffer();
			for (int di = 0; di < dirArray.length; di++) {
				String oneDir = dirArray[di];
				try {
					filePathBuffer.append(oneDir);
					ftp.createDirectory(filePathBuffer.toString());
					logger.info("创建目录" + oneDir);

				} catch (Exception se) {
					logger.info("目录" + filePathBuffer.toString() + "已经存在，不需要创建");
				} finally {
					filePathBuffer.append("/");
				}
			}

			try {
				if (!ftp.exists(fileDirs)) {
					ftp.createDirectory(fileDirs);
					logger.info("创建目录" + fileDirs);
				}
			} catch (Exception se) {
				logger.info("目录" + fileDirs + "已经存在");
			}

			changeDir(fileDirNameArray[0]);

			logger.info("执行文件上传");

			FileTransferOutputStream ftos = ftp.uploadStream(fileDirNameArray[1]);
			byte[] buffer = new byte[TransferFile_Cache];
			int readV = -1;
			String blockArray = "";
			// 判定是否被中止

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
				logger.warn("附件上传被中止" + iio.getMessage());
				isCancel = true;
			} finally {
				ftos.close();
				is.close();
			}

			// 生成加密附加文件
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
				logger.warn("附件上传被中止" + iio.getMessage());
				isCancel = true;
			} finally {
				ftosExt.close();
				extIs.close();
			}

			try {
				extFile.delete();
			} catch (Exception e) {
				logger.warn("删除加密附加文件出错!" + e.getMessage());
			}

			// 如果中止了，该文件需要删除
			if (isCancel) {
				logger.info("正在上传的文件被中止，需要清除垃圾文件");
				changeDir(PARENT_DIR);
				changeDir(PARENT_DIR);
				boolean deleteCancelResult = this.delete(remoteFileName);
				logger.info("清除被中止的文件结果为:" + deleteCancelResult);
				uploadResult = 1;
			} else {
				logger.info("文件上传完毕");
				logger.info("上传到FTP路径:" + remoteFileName);
			}
		} catch (Exception se) {
			se.printStackTrace();
			logger.warn("上传文件抛出异常:" + se.getMessage());
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
	 * @TODO 生成加密文件扩展，将加密信息组合到一个文件中
	 * 
	 * @see com.kingdee.eas.web.IFtp#upload(java.lang.String, java.lang.String)
	 */
	public int uploadStream(InputStream is, String remoteFileName) throws Exception {
		int uploadResult = 2;
		try {
			checkConnect();

			int isAV = is.available();
			logger.info("输入流可用的为:" + isAV);
			logger.info("预上传文件为:" + remoteFileName);

			String[] fileDirNameArray = getUploadSaveDirAndName(remoteFileName);

			logger.info("获取上传的文件路径:" + fileDirNameArray[0] + "---" + fileDirNameArray[1]);

			// 判定相应的目录是否存在
			String fileDirs = fileDirNameArray[0];
			String[] dirArray = fileDirs.split("/");
			StringBuffer filePathBuffer = new StringBuffer();
			for (int di = 0; di < dirArray.length; di++) {
				String oneDir = dirArray[di];
				try {
					filePathBuffer.append(oneDir);
					ftp.createDirectory(filePathBuffer.toString());
					logger.info("创建目录" + oneDir);

				} catch (Exception se) {
					logger.warn("目录" + filePathBuffer.toString() + "已经存在，不需要创建");
				} finally {
					filePathBuffer.append("/");
				}
			}

			try {
				if (!ftp.exists(fileDirs)) {
					ftp.createDirectory(fileDirs);
					logger.info("创建目录" + fileDirs);
				}
			} catch (Exception se) {
				logger.warn("目录" + fileDirs + "已经存在");
			}

			changeDir(fileDirNameArray[0]);

			logger.info("执行文件上传");

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
				logger.warn("附件上传被中止" + iio.getMessage());
				isCancel = true;
			} finally {
				ftos.close();
				is.close();
			}

			// 如果中止了，该文件需要删除
			if (isCancel) {
				logger.info("正在上传的文件被中止，需要清除垃圾文件");
				changeDir(PARENT_DIR);
				changeDir(PARENT_DIR);
				boolean deleteCancelResult = this.delete(remoteFileName);
				logger.info("清除被中止的文件结果为:" + deleteCancelResult);
				uploadResult = 1;
			} else {
				logger.info("文件上传完毕");
				logger.info("上传到FTP路径:" + remoteFileName);
			}
		} catch (Exception se) {
			se.printStackTrace();
			logger.warn("上传文件抛出异常:" + se.getMessage());
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
			logger.info("切换到上层目录");
		} else {
			String[] pathGrade = newDir.split("/");
			for (int i = 0; i < pathGrade.length; i++) {
				ftp.changeDirectory(pathGrade[i]);
			}
			// ftp.changeDirectory(newDir);
			logger.info("切换到目录" + newDir);
		}
	}

	public void makeDir(String newDir) throws Exception {
		checkConnect();
		logger.info("currentRemoteDirection is:" + ftp.getRemoteDirectory());
		logger.info("你需要创建的目录为:" + newDir);
		try {
			ftp.createDirectory(newDir);
		} catch (Exception se) {

			logger.error("创建FTP目录" + newDir + "失败" + se.getMessage());

		}
	}

	public void deleteDir(String delDir) throws Exception {
		checkConnect();
		logger.info("currentRemoteDirection is:" + ftp.getRemoteDirectory());
		logger.info("你需要删除的目录为:" + delDir);

		try {
			ftp.deleteDirectory(delDir);
		} catch (Exception se) {

			logger.error("删除FTP目录" + delDir + "失败" + se.getMessage());

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

			logger.error("获取下载文件流抛出异常" + se.getMessage());

		}
		return downStream;
	}

	/**
	 * @TODO 实现下载流处理，支持进度条显示处理
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
			logger.warn("附件下载被中止" + iio.getMessage());
		}
		fos.close();
		dis.close();
		logger.info("下载FTP文件到本地:" + localFileName);

		this.disconnect();
		return downloadResult;
	}

	public boolean downloadStreamByFileName(String remoteFileName, String localFileName) throws Exception {
		checkConnect();

		byte[] buffer = new byte[TransferFile_Cache];
		int readV = -1;

		// 优化为获取下载次数信息
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
			logger.warn("附件下载被中止" + iio.getMessage());
		}
		fos.close();
		ftis.close();
		logger.info("下载FTP文件到本地:" + localFileName);

		this.disconnect();
		return downloadResult;
	}

	/**
	 * @TODO 获取加密文件的压缩块信息数字
	 * @param ftp
	 * @param remoteFileName
	 * @return
	 */
	public String[] getFileZipBlockArray(FileTransferClient ftp, String remoteFileName) {

		String[] blockArry = null;
		int readV = -1;
		byte[] buffer = new byte[TransferFile_Cache];

		// 加密附加文件路径
		String extFileName = remoteFileName.substring(0, remoteFileName.lastIndexOf(".") + 1) + FMECommonRes.EXT;
		try {
			boolean existZipExtFile = ftp.exists(extFileName);

			// 如果存在最初版本的加密辅助文件
			if (existZipExtFile) {
				logger.info("存在加密辅助文件，根据第一种加密方式解密处理");
				FileTransferInputStream extftis = null;
				try {
					// 下载加密附加文件
					extftis = ftp.downloadStream(extFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 如果加密附加文件不为空，执行读取加密附加文件
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
						logger.warn("删除加密附加文件出错:" + e.getMessage());
						e.printStackTrace();
					}
				}
			} else {
				// 判定是否是第二种方式的加密
				logger.info("不存在加密辅助文件，根据第二种加密方式解密处理");
				long fileSize = ftp.getSize(remoteFileName);
				if (fileSize > 20) {

					FileTransferInputStream ftis = ftp.downloadStream(remoteFileName);
					// 读取最后20个字节
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
							logger.info("文件的加密block长度超过10G,解密文件出现异常");
						}
					} else {
						logger.info("没有加密文件标识");
					}
				} else {
					logger.info("文件没有经过加密处理");
				}
			}
		} catch (Exception se) {
			logger.warn("获取加密文件的压缩Block抛出异常:" + se.getMessage());
		}
		return blockArry;
	}

	/*
	 * 下载文件，支持多个文件并发下载 （非 Javadoc）
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
				logger.info("================================服务器建立联结失败");
				downloadStatus = -1;
				return downloadStatus;
			}
		} catch (Exception se) {
			logger.warn("================================服务器建立联结失败");
			downloadStatus = -1;
			return downloadStatus;
		}

		try {

			byte[] buffer = new byte[TransferFile_Cache];
			int readV = -1;

			String[] blockArry = getFileZipBlockArray(ftp, remoteFileName);

			// 下载加密文件
			FileOutputStream fos = new FileOutputStream(localFileName);

			long fileSize = ftp.getSize(remoteFileName);
			FileTransferInputStream ftis = ftp.downloadStream(remoteFileName);
			FMEFtpInputStream dis = new FMEFtpInputStream(monitorParentCom, monitorMessage, ftis, (int) fileSize);
			dis.setUpload(false);
			// 下载增加下载索引
			dis.setProgressIndex(downloadIndex);
			try {
				// 如果加密附加文件的内容存在，则解密文件，否则直接下载文件
				if (blockArry != null) {
					for (int i = 0; i < blockArry.length; i++) {
						buffer = new byte[Integer.parseInt(blockArry[i])];
						if (dis.read(buffer) != -1) {
							byte[] temp = FMEDataZipUtils.unpack(buffer);
							fos.write(temp);
						}
					}
					// 读取到最后时，更新100%比显示
					dis.updateUnzipPregress();
				} else {
					while ((readV = dis.read(buffer)) != -1) {
						fos.write(buffer, 0, readV);
					}
				}
				downloadStatus = 1;
			} catch (InterruptedIOException iio) {
				logger.warn("---------------------------------附件下载被中止" + iio.getMessage());
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
					logger.warn("取消下载文件抛出异常:" + se.getMessage());
					se.printStackTrace();
				}

				// 如果是下载文件中被终止，清除下载的文件
				if (downloadStatus == 0) {
					try {
						File cancelDownloadFile = new File(localFileName);
						if (cancelDownloadFile.exists()) {
							boolean deleteCancelDownloadFile = cancelDownloadFile.delete();
							logger.info("下载文件过程被中止，删除取消的文件" + localFileName + " 结果为：" + deleteCancelDownloadFile);
						}
					} catch (Exception rcEx) {
						logger.warn("删除取消下载的文件抛出异常:" + rcEx.getMessage());
					}
				}

			}
		} catch (Exception se) {
			se.printStackTrace();
			logger.warn("下载文件失败,原因可能为下载文件不存在了：" + se.getMessage());
			downloadStatus = -2;
		}
		logger.info("下载FTP文件到本地:" + localFileName);
		this.disconnect();
		return downloadStatus;
	}

	/**
	 * @TODO 获取Ftp指定的文件大小，用于下载使用
	 * @param file
	 * @return
	 */
	public long getFileSize(String file) {
		long size = 0;
		checkConnect();
		try {
			size = ftp.getSize(file);
		} catch (Exception se) {
			logger.error("获取Ftp文件大小抛出异常" + se.getMessage());
			size = -1;
		}
		return size;
	}

	/*
	 * @TODO 实现直接复制FTP服务器上的文件 (non-Javadoc)
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
					destFileName = "复件" + initFileName;
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
				logger.error("通过两个流复制文件抛出异常" + se.getMessage());

			} finally {
				if (ftp2 != null && ftp2.isConnected()) {
					ftp2.disconnect();
				}
			}

			long endTime = System.currentTimeMillis();
			logger.info("复制文件:" + initFileName + "费时为:" + (endTime - beginTime));
		} catch (Exception se) {
			throw new Exception("复制文件抛出异常" + se.getMessage(), se);
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
					destFileName = "复件" + initFileName;
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
				logger.error("通过两个流复制文件抛出异常" + se.getMessage());

			} finally {
				if (targetFtpClient != null && targetFtpClient.isConnected()) {
					targetFtpClient.disconnect();
				}
			}

			long endTime = System.currentTimeMillis();
			logger.info("复制文件:" + initFileName + "费时为:" + (endTime - beginTime));
		} catch (Exception se) {
			throw new Exception("复制文件抛出异常" + se.getMessage(), se);
		}
	}

	public List checkFtpLink() {
		List retList = new ArrayList();
		if (this.ftp == null) {
			retList.add(new Boolean(false));
			retList.add("获取对应的FTP Instance 为null");
		} else {
			if (this.connect() && this.disconnect()) {
				retList.add(new Boolean(true));
				retList.add("测试连接正常");
			} else {
				retList.add(new Boolean(false));
				retList.add("测试连接、断开连接失败!!!");
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
			logger.error("renameRootPath抛出异常" + se.getMessage());
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
			logger.warn("传输单个文件,抛出异常:" + se.getMessage());
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
			logger.warn("传输单个文件,抛出异常:" + se.getMessage());
		}
		return transResult;
	}

	private List getDirectoryFileList(String dir, String parentDir) throws IOException, FTPException {
		List fileList = new ArrayList();
		logger.info("定位父目录为：" + parentDir);
		logger.info("定位当前目录为：" + dir);
		String currentParentDir = parentDir;

		if (!FMEUIUtils.isBlankString(currentParentDir)) {
			ftp.changeDirectory("/" + parentDir);
		}
		ftp.changeDirectory(dir);

		String[] filelist = ftp.directoryNameList();
		boolean modifyParentDir = false;
		for (int i = 0; i < filelist.length; i++) {
			String oneDirFile = filelist[i];

			// 属于目录
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
				logger.info("增加子文件：" + subDirFile.size());
				fileList.addAll(subDirFile);
			} else {
				// 属于文件
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
			// 先建立连接
			this.ftp.connect();
			List fileList = getDirectoryFileList(rootPath, "");
			for (int i = 0; i < fileList.size(); i++) {
				ftpDocInfo.addDocument((String) fileList.get(i));
			}
		} catch (Exception sex) {
			logger.warn("获取指定FTP服务器的根目录下的所有文件信息，抛出异常:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return ftpDocInfo;
	}

	public boolean createSavePath(String rootPath, String pathName) throws Exception {
		boolean createResult = false;
		try {
			// 先建立连接
			this.ftp.connect();
			changeDirToRootPath(rootPath);

			// 判定相应的pathName是否存在
			try {
				if (!ftp.exists(pathName)) {
					ftp.createDirectory(pathName);
					logger.info("创建存储路径:" + pathName);
				}
			} catch (Exception se) {
				logger.warn("存储路径:" + pathName + "已经存在");
			}
			createResult = true;
		} catch (Exception sex) {
			logger.warn("createSavePath抛出异常:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return createResult;
	}

	public boolean updateSavePath(String rootPath, String oldPathName, String newPathName) throws Exception {
		boolean updateResult = false;
		try {
			// 先建立连接
			this.ftp.connect();
			changeDirToRootPath(rootPath);

			// 判定相应的pathName是否存在
			try {
				ftp.changeDirectory(oldPathName);
				// 如果旧的路径存在，重命名
				ftp.changeToParentDirectory();
				// 判定新的文件名是否存在
				ftp.rename(oldPathName, newPathName);
				logger.info("存储路径有" + oldPathName + "改名为" + newPathName);

			} catch (Exception se) {
				logger.warn("旧的存储路径:" + oldPathName + "不存在");

				// 判定相应的newPathName是否存在
				try {
					if (!ftp.exists(newPathName)) {
						ftp.createDirectory(newPathName);
						logger.info("创建存储路径:" + newPathName);
					}
				} catch (Exception se2) {
					logger.warn("存储路径:" + newPathName + "已经存在");
				}
				updateResult = true;
			}
			updateResult = true;
		} catch (Exception sex) {
			logger.warn("updateSavePath抛出异常:" + sex.getMessage());
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
				logger.info("创建存储根路径:" + rootPath);
			}
		} catch (Exception se) {
			logger.warn("存储根路径:" + rootPath + "已经存在");
		}
		ftp.changeDirectory(rootPath);
	}

	public boolean deleteSavePath(String rootPath, String pathName) throws Exception {
		boolean delResult = false;
		try {
			// 先建立连接
			this.ftp.connect();

			changeDirToRootPath(rootPath);

			try {
				ftp.changeDirectory(pathName);
				// 如果旧的路径存在，重命名
				ftp.changeToParentDirectory();
				logger.info("存储路径:" + pathName + "已经存在,准备删除");
				ftp.deleteDirectory(pathName);

				try {
					ftp.changeDirectory(pathName);
					ftp.changeToParentDirectory();
					logger.info("存储路径:" + pathName + "删除不成功");
					delResult = false;
				} catch (Exception ssse) {
					logger.warn("存储路径:" + pathName + "已经删除");
					delResult = true;
				}
			} catch (Exception se) {
				logger.warn("存储路径:" + pathName + "不存在，不需要删除");
				delResult = true;
			}
		} catch (Exception sex) {
			logger.warn("deleteSavePath抛出异常:" + sex.getMessage());
			sex.printStackTrace();
		} finally {
			ftp.disconnect();
		}
		return delResult;
	}

}
