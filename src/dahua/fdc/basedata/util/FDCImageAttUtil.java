package com.kingdee.eas.fdc.basedata.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.attachment.AttachmentException;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IAttachmentFtpFacade;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.common.AttachmentServerManager;
import com.kingdee.eas.base.attachment.common.SimpleAttachmentInfo;
import com.kingdee.eas.base.attachment.ftp.AttachmentDownloadClient;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.attachment.util.VariousAttachmentInfoMaker;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.DataZipUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.Uuid;

/**
 * 附件图片工具类
 * <p>
 * 该类主要实现根据业务单据ID，上传图片附件，或者读取所有图片附件，并显示指定附件到指定控件上<br>
 * 
 * 用法：<br>
 * 1、先利用业务单据ID、附件接口构造工具类<br>
 * 2、在需要显示附件图片的时候，调用setIMGByAttID(attID,lable)方法将需要显示的附件显示到指定Lable上<br>
 * 3、在需要上传图片的地方，调用uploadIMGAtt()，会弹出文件选择器进行上传<br>
 * 
 * 注意:<br>
 * 1. 如果选择默认下载所有图片的构造方法，会在初始化时下载所有附件图片，如果图片过多，<br>
 * 可能造成一定时间的等待，此时会出现一个等待框。<br>
 * 如果选择默认不下载图片的构造方法，会在每次显示附件时，判断有没有下载过，如果没有，则临时下载<br>
 * 可能造成由于下载附件造成的操作不流畅。开发人员自行取舍。<br>
 * 建议编辑界面需要展示图片的，一次性全部载入完成。序时簿界面只要显示图片张数的，不一次性下载。<br>
 * 2. 最好将图片绘制于KDPanel上，并设置KDPanel为XYLayout，保证图片不会被拉升
 * 
 * 版本：<br>
 * 1.0 实现基本功能11.6.27<br>
 * 1.1 添加支持默认不全部下载附件11.6.27<br>
 * 1.2 添加刷新附件，用于界面改变了附件时调用，不必重新初始化类11.6.27<br>
 * 1.3 将setIMGByAttID方法改为支持JComponent，即可在JPanel等上绘图，以便使用11.6.28<br>
 * 1.4 修复一个增加附件可能引起的刷新未成功问题 11.6.28<br>
 * 1.5 支持图片上传 11.6.28<br>
 * 1.6 支持进度下新增的图片展示面板KDImagePanel 11.7.7<br>
 * 1.7 去除对jdk1.6依赖 2011.8.1<br>
 * 
 * @author emanon
 * @version 1.7
 * @createTime 2011.06.27
 * @lastUpdate 2011.8.1
 */
public class FDCImageAttUtil {
	private static final Logger logger = Logger
			.getLogger(FDCImageAttUtil.class);

	/* attPath集合中，如果某个附件ID对应的图片保存路径是此常量，说明还未从服务器下载 */
	private static final String UN_INITIALIZED = "unInitialized";

	/* 业务单据ID */
	private String bosID;

	/* 所有图片附件ID集合，以创建时间排序 */
	private List imgAtts;
	/* 附件ID与图片保存路径的键值对 */
	private Map attPath = null;

	/* 附件实体远程调用接口 */
	private IAttachment ia = null;
	/* 附件上传下载功能服务接口 */
	private IAttachmentFtpFacade iAff = null;
	/* 附件管理接口 */
	private AttachmentClientManager attachmentClientManager = null;
	private AttachmentServerManager attachmentServerManager = null;
	/* 附件与业务单据关系接口 */
	private IBoAttchAsso iba = null;

	/* 调用该类的界面，用于显示下载等待框 */
	private Component waitOwner = null;
	/* 上下文，当传入null时为客户端调用，服务端传入ctx */
	private Context ctx = null;

	/* 是否含有图片附件 */
	private boolean isHasIMGAtt = false;
	/* 是否在构造时下载所有图片附件 */
	private boolean isDefualtDown = true;

	/* 支持的图片类型（为了规范上传，建议仅支持常用4种jpg、bmp、gif、png） */
	private Set types = new HashSet();
	/* 支持的图片大小（建议限定1M或更小，太大了加载耗时，特别当图片较多时，影响操作流畅度） 
	 * 应客户要注，此处修改为最大限制5M -modify by yuanjun_lan
	 * */
	private int size = 1024*5;

	/**
	 * * 初始化工具类
	 * <p>
	 * 默认下载所有图片附件
	 * 
	 * @param owner
	 *            所属界面
	 * @param bosID
	 *            业务单据ID
	 * @param ia
	 *            附件实体接口
	 * @param iAff
	 *            附件下载功能接口
	 * @throws BOSException
	 */
	public FDCImageAttUtil(Component owner, String bosID, Context ctx)
			throws BOSException {
		this.waitOwner = owner;
		this.bosID = bosID;
		this.ctx = ctx;
		if (ctx == null) {
			this.ia = AttachmentFactory.getRemoteInstance();
			this.iAff = AttachmentFtpFacadeFactory.getRemoteInstance();
			this.attachmentClientManager = AttachmentManagerFactory
					.getClientManager();
			this.attachmentServerManager = null;
			this.iba = BoAttchAssoFactory.getRemoteInstance();
		} else {
			this.ia = AttachmentFactory.getLocalInstance(ctx);
			this.iAff = AttachmentFtpFacadeFactory.getLocalInstance(ctx);
			this.attachmentClientManager = null;
			this.attachmentServerManager = AttachmentManagerFactory
					.getServerManager(ctx);
			this.iba = BoAttchAssoFactory.getLocalInstance(ctx);
		}
		this.isDefualtDown = true;
		initDefaultTypes();
		waitFordown();
	}

	public String getBosID() {
		return bosID;
	}

	public void setBosID(String bosID) {
		this.bosID = bosID;
	}
	/**
	 * 初始化工具类
	 * 
	 * @param owner
	 *            所属界面
	 * @param bosID
	 *            业务单据ID
	 * @param ctx
	 *            上下文，客户端传入空
	 * @param isDown
	 *            是否在构建时下载所有图片附件
	 * @throws BOSException
	 */
	public FDCImageAttUtil(Component owner, String bosID, Context ctx,
			boolean isDown) throws BOSException {
		this.waitOwner = owner;
		this.bosID = bosID;
		this.ctx = ctx;
		if (ctx == null) {
			this.ia = AttachmentFactory.getRemoteInstance();
			this.iAff = AttachmentFtpFacadeFactory.getRemoteInstance();
			this.attachmentClientManager = AttachmentManagerFactory
					.getClientManager();
			this.attachmentServerManager = null;
			this.iba = BoAttchAssoFactory.getRemoteInstance();
		} else {
			this.ia = AttachmentFactory.getLocalInstance(ctx);
			this.iAff = AttachmentFtpFacadeFactory.getLocalInstance(ctx);
			this.attachmentClientManager = null;
			this.attachmentServerManager = AttachmentManagerFactory
					.getServerManager(ctx);
			this.iba = BoAttchAssoFactory.getLocalInstance(ctx);
		}
		this.isDefualtDown = isDown;
		initDefaultTypes();
		if (isDown) {
			waitFordown();
		} else {
			initEmptyAttPath();
		}
	}

	/**
	 * 刷新附件
	 * <p>
	 * 当附件可能改变时调用，刷新当前保存的附件列表<br>
	 * 如果有新附件，将根据isDefualtDown判断是否下载
	 */
	public void refreshAtt() {
		List curAtt = getIMGAtt();
		Map curPath = new HashMap();
		// 当前附件为空，则置空
		if (curAtt == null || curAtt.isEmpty()) {
			imgAtts = null;
			attPath = null;
		}
		// 之前附件为空，则用构造方法下载
		else if (imgAtts == null || imgAtts.isEmpty()) {
			imgAtts = curAtt;
			if (isDefualtDown) {
				waitFordown();
			} else {
				initEmptyAttPath();
			}
		}
		// 之前附件和当前附件都不为空，则修改更新的附件
		else {
			imgAtts = curAtt;
			// 当前存在附件ID集合
			AttachmentInfo att;
			for (int i = 0; i < curAtt.size(); i++) {
				att = (AttachmentInfo) curAtt.get(i);
				curPath.put(att.getId().toString(), UN_INITIALIZED);
			}
			Map.Entry details;
			String key;
			String value;
			Iterator it = attPath.entrySet().iterator();
			while (it.hasNext()) {
				details = (Map.Entry) it.next();
				key = (String) details.getKey();
				if (curPath.containsKey(key)) {
					value = (String) details.getValue();
					curPath.put(key, value);
				}
			}
			attPath = curPath;
		}
	}

	/**
	 * 将附件ID对应的图片显示到compt上
	 * <p>
	 * 图片按实际大小展示，如果图片过大，请在compt外添加滚动条控件<br>
	 * 程序会改变compt大小<br>
	 * 如果compt为Panel，则会在Panel上绘制一个Label<br>
	 * 如果compt为Label，则直接设置图标<br>
	 * 推荐传入Panel，防止界面大小改变时锚定了的滚动条中的Label自动改变大小导致图片拉伸<br>
	 * 
	 * @param attID
	 *            附件ID
	 * @param compt
	 *            指定控件
	 * @throws IOException
	 *             读取文件错误
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void setIMGByAttID(String attID, JComponent compt)
			throws IOException, EASBizException, BOSException {
		if (compt == null
				|| !((compt instanceof JPanel) || (compt instanceof JLabel))) {
			logger.warn("Can't set IMG on this compt!");
			return;
		}
		if (attID == null || compt == null || !isHasIMGAtt) {
			logger.warn("Can't set IMG by null");
			return;
		}
		if (!attPath.containsKey(attID)) {
			logger.warn("Unknow attID");
			return;
		}
		File imgFile = downIMGAtt(attID);
		ImageIcon icon = getImageFromFile(imgFile);
		Dimension size = new Dimension(icon.getIconWidth(), icon
				.getIconHeight());
//		if (compt instanceof KDImagePanel) {
//			((KDImagePanel) compt).setImageFile(imgFile);
//		} else 
			if (compt instanceof JLabel) {
			compt.setSize(size);
			compt.setMaximumSize(size);
			compt.setPreferredSize(size);
			((JLabel) compt).setText(null);
			((JLabel) compt).setIcon(icon);
		} else if (compt instanceof JPanel) {
			compt.removeAll();
			JLabel lable = new JLabel();
			if(compt.getWidth() == 0 && compt.getHeight() == 0){
				lable.setSize(new Dimension(364, 290));
				if(compt.getName().equalsIgnoreCase("kDPanel1TaskGuide")){
					lable.setSize(new Dimension(426,351));
				}
			}else{
				lable.setSize(compt.getSize());
			}
			lable.setHorizontalAlignment(SwingConstants.CENTER);
			double hwRate = (icon.getIconHeight()*1.0)/ icon.getIconWidth() ;
			int width = icon.getIconWidth();
			int height = icon.getIconHeight();
			
			if(width>lable.getWidth()){
				width = lable.getWidth();
				height = new Integer((int) (lable.getHeight()*hwRate)).intValue();
			}
			icon.setImage(icon.getImage().getScaledInstance(width,height, Image.SCALE_DEFAULT));
			lable.setIcon(icon);
			((KDPanel) compt).add(lable);
			compt.updateUI();
		}
	}

	/**
	 * 下载指定ID的图片附件并返回该图片在本机的File
	 * 
	 * @param attID
	 *            附件ID
	 * @return
	 * @throws IOException
	 * @throws IOException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public File downIMGAtt(String attID) throws IOException, EASBizException, BOSException {
		if (attID == null || !isHasIMGAtt) {
			logger.warn("Can't set IMG by null");
			return null;
		}
		if (!attPath.containsKey(attID)) {
			logger.warn("Unknow attID");
			return null;
		}
		String path = (String) attPath.get(attID);
		if (isDefualtDown && path.equals(UN_INITIALIZED)) {
			path = downloadAtt(attID);
		}
		if (path == null) {
			logger.warn("No Img at path");
			return null;
		}
		logger.info("set image");
		return new File(path);
	}

	/**
	 * 根据一个图片文件地址，返回一张图片
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public ImageIcon getImageFromFile(File file) throws IOException {
		BufferedImage materialImage = ImageIO.read(file);
		ImageIcon icon = new ImageIcon(materialImage);
		return icon;
	}

	/**
	 * 上传一个图片附件
	 * 
	 * @throws Exception
	 */
	public String uploadIMGAtt() throws Exception {
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"jpg,bmp,gif,png", getTypesArray());
		fc.setFileFilter(filter);
		int retVal = fc.showOpenDialog(waitOwner);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return null;
		}
		File upFile = fc.getSelectedFile();
		if (upFile.length() > size * 1024) {
			//
			FDCMsgBox.showWarning(waitOwner, "上传的文件不允许超5M，不允许新增");
			SysUtil.abort();
		}
		String attID = uploadAtt(upFile);
		refreshAtt();
		return attID;
	}

	public List getImgAtts() {
		return imgAtts;
	}

	public boolean isHasIMGAtt() {
		return isHasIMGAtt;
	}

	private void initDefaultTypes() {
		getTypes().add("jpg");
		getTypes().add("bmp");
		getTypes().add("gif");
		getTypes().add("png");
	}

	/**
	 * 下载等待界面
	 * <p>
	 * 默认初始化时会下载所有附件
	 */
	private void waitFordown() {
		logger.info("show waiting dialog");
		KDDialog dialog = null;
		Window win = null;
		if (this.waitOwner != null) {
			win = SwingUtilities.getWindowAncestor(this.waitOwner);
		}
		if (win != null && win instanceof KDDialog) {
			dialog = (KDDialog) win;
		} else {
			dialog = new KDDialog();
		}
		LongTimeDialog ltd = new LongTimeDialog(dialog);
		ltd.setTitle("下载图片中...");
		ltd.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				try {
					initAttPath();
					return Boolean.TRUE;
				} catch (Exception e) {
					logger.info("", e);
					throw e;
				}
			}

			public void afterExec(Object arg0) throws Exception {
			}
		});
		ltd.show();
	}

	/**
	 * 初始化附件对应路径的集合
	 * <p>
	 * 会先循环下载所有附件
	 * 
	 * @throws IOException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void initAttPath() throws IOException, EASBizException, BOSException {
		imgAtts = getIMGAtt();
		if (!imgAtts.isEmpty()) {
			AttachmentInfo att;
			String path;
			attPath = new HashMap();
			for (int i = 0; i < imgAtts.size(); i++) {
				att = (AttachmentInfo) imgAtts.get(i);
				path = downloadAtt(att);
				attPath.put(att.getId().toString(), path);
			}
		}
	}

	/**
	 * 初始化一个空的附件对应路径集合，默认不下载图片时调用
	 */
	private void initEmptyAttPath() {
		imgAtts = getIMGAtt();
		if (!imgAtts.isEmpty()) {
			AttachmentInfo att;
			attPath = new HashMap();
			for (int i = 0; i < imgAtts.size(); i++) {
				att = (AttachmentInfo) imgAtts.get(i);
				attPath.put(att.getId().toString(), UN_INITIALIZED);
			}
		}
	}

	/**
	 * 下载附件到临时目录
	 * <p>
	 * 如果文件以压缩方式储存，先解压缩
	 * 
	 * @param attID
	 *            附件ID
	 * @return 临时存放目录
	 * @throws IOException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private String downloadAtt(String attID) throws IOException, EASBizException, BOSException {
		if (imgAtts.isEmpty()) {
			return null;
		} else {
			AttachmentInfo att;
			String id;
			for (int i = 0; i < imgAtts.size(); i++) {
				att = (AttachmentInfo) imgAtts.get(i);
				id = att.getId().toString();
				if (id.equals(attID)) {
					String path = downloadAtt(att);
					attPath.put(attID, path);
					return path;
				}
			}
		}
		return null;
	}

	/**
	 * 下载附件到临时目录
	 * <p>
	 * 如果文件以压缩方式储存，先解压缩
	 * 
	 * @param att
	 *            附件对象
	 * @return 临时存放目录
	 * @throws IOException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private String downloadAtt(AttachmentInfo att) throws IOException, EASBizException, BOSException {
		String name = att.getName();
		String type = att.getType();
		String path = null;
		File file = File.createTempFile("KDTF-" + name, "."
				+ att.getSimpleName());
		path = file.getPath();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] down = getFileFromFtp(att.getId().toString());
		if (down.length == att.getSizeInByte()) {
			logger.info(name + ": no unpack! ");
			fos.write(down);
		} else {
			logger.info(name + ": unpack! ");
			fos.write(DataZipUtils.unpack(down));
		}
		fos.close();
		return path;
	}

	/**
	 * 真实上传过程
	 * 
	 * @param upAtt
	 * @throws Exception
	 */
	public String uploadAtt(File upAtt) throws Exception {
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		SimpleAttachmentInfo sai = this.getSimpleFile(upAtt, info);
		AttachmentInfo attachmentInfo = VariousAttachmentInfoMaker.makeAttachmentInfo(ia, bosID, sai);
		if (this.attachmentClientManager != null) {
			return attachmentClientManager.submitAttachment(attachmentInfo);
		} else if (this.attachmentServerManager != null) {
			return attachmentServerManager.submitAttachment(attachmentInfo);
		}
		return null;
	}

	/**
	 * 从服务器取得指定ID的附件
	 * 
	 * @param attID
	 *            附件ID
	 * @return 文件流
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private byte[] getFileFromFtp(String attID) throws EASBizException, BOSException {
		ByteArrayOutputStream dataByte = new ByteArrayOutputStream();
		OutputStream out = dataByte;
		AttachmentDownloadClient downloadClient = new AttachmentDownloadClient(
				this.iAff);
		downloadClient.setOutputStream(out);
		downloadClient.downLoad(attID);
		return dataByte.toByteArray();
	}

	/**
	 * 读取所有当前图片附件
	 * 
	 * @return
	 */
	private List getIMGAtt() {
		List imgs = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("attachment.id"));
		sic.add(new SelectorItemInfo("attachment.name"));
		sic.add(new SelectorItemInfo("attachment.type"));
		sic.add(new SelectorItemInfo("attachment.sizeInByte"));
		sic.add(new SelectorItemInfo("attachment.simpleName"));
		view.getSelector().addObjectCollection(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", bosID));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("attachment.createTime"));
		try {
			BoAttchAssoCollection boAttchColl = iba
					.getBoAttchAssoCollection(view);
			if (boAttchColl != null && boAttchColl.size() > 0) {
				AttachmentInfo attachment;
				String type;
				for (int i = 0; i < boAttchColl.size(); i++) {
					attachment = (AttachmentInfo) boAttchColl.get(i)
							.getAttachment();
					type = attachment.getSimpleName().toLowerCase();
					if (type != null && !type.trim().equals("")) {
						if (types.contains(type)) {
							imgs.add(attachment);
							isHasIMGAtt = true;
						}
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e);
		}
		return imgs;
	}

	private SimpleAttachmentInfo getSimpleFile(File file,
			AttachmentUIContextInfo info) throws Exception {
		try {
			String fullname = file.getName();
			String mainname = StringUtil4File.getMainFileName(Uuid.randomUUID().toString());
			// String mainname =
			// StringUtil4File.getMainFileName(BOSUuid.create("0E9C4124"
			// ).toString());
//			String mainname = StringUtil4File.getMainFileName(fullname);
			String extname = StringUtil4File.getExtendedFileName(fullname);
			// 防止上传文件因类型后缀中有大写字符而导致系统将其设置为未知类型文件得BUG
			extname = extname.toLowerCase();
			byte[] content = FileGetter.getBytesFromFile(file);
			SimpleAttachmentInfo sai = new SimpleAttachmentInfo();
			sai.setMainName(mainname);
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

	public void setTypes(Set types) {
		this.types = types;
	}

	public Set getTypes() {
		return types;
	}

	public String[] getTypesArray() {
		if (types == null) {
			return null;
		}
		String[] rt = new String[types.size()];
		Iterator it = types.iterator();
		int i = 0;
		while (it.hasNext()) {
			rt[i] = (String) it.next();
			i++;
		}
		return rt;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

}
