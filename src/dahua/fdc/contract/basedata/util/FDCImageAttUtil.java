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
 * ����ͼƬ������
 * <p>
 * ������Ҫʵ�ָ���ҵ�񵥾�ID���ϴ�ͼƬ���������߶�ȡ����ͼƬ����������ʾָ��������ָ���ؼ���<br>
 * 
 * �÷���<br>
 * 1��������ҵ�񵥾�ID�������ӿڹ��칤����<br>
 * 2������Ҫ��ʾ����ͼƬ��ʱ�򣬵���setIMGByAttID(attID,lable)��������Ҫ��ʾ�ĸ�����ʾ��ָ��Lable��<br>
 * 3������Ҫ�ϴ�ͼƬ�ĵط�������uploadIMGAtt()���ᵯ���ļ�ѡ���������ϴ�<br>
 * 
 * ע��:<br>
 * 1. ���ѡ��Ĭ����������ͼƬ�Ĺ��췽�������ڳ�ʼ��ʱ�������и���ͼƬ�����ͼƬ���࣬<br>
 * �������һ��ʱ��ĵȴ�����ʱ�����һ���ȴ���<br>
 * ���ѡ��Ĭ�ϲ�����ͼƬ�Ĺ��췽��������ÿ����ʾ����ʱ���ж���û�����ع������û�У�����ʱ����<br>
 * ��������������ظ�����ɵĲ�����������������Ա����ȡ�ᡣ<br>
 * ����༭������ҪչʾͼƬ�ģ�һ����ȫ��������ɡ���ʱ������ֻҪ��ʾͼƬ�����ģ���һ�������ء�<br>
 * 2. ��ý�ͼƬ������KDPanel�ϣ�������KDPanelΪXYLayout����֤ͼƬ���ᱻ����
 * 
 * �汾��<br>
 * 1.0 ʵ�ֻ�������11.6.27<br>
 * 1.1 ���֧��Ĭ�ϲ�ȫ�����ظ���11.6.27<br>
 * 1.2 ���ˢ�¸��������ڽ���ı��˸���ʱ���ã��������³�ʼ����11.6.27<br>
 * 1.3 ��setIMGByAttID������Ϊ֧��JComponent��������JPanel���ϻ�ͼ���Ա�ʹ��11.6.28<br>
 * 1.4 �޸�һ�����Ӹ������������ˢ��δ�ɹ����� 11.6.28<br>
 * 1.5 ֧��ͼƬ�ϴ� 11.6.28<br>
 * 1.6 ֧�ֽ�����������ͼƬչʾ���KDImagePanel 11.7.7<br>
 * 1.7 ȥ����jdk1.6���� 2011.8.1<br>
 * 
 * @author emanon
 * @version 1.7
 * @createTime 2011.06.27
 * @lastUpdate 2011.8.1
 */
public class FDCImageAttUtil {
	private static final Logger logger = Logger
			.getLogger(FDCImageAttUtil.class);

	/* attPath�����У����ĳ������ID��Ӧ��ͼƬ����·���Ǵ˳�����˵����δ�ӷ��������� */
	private static final String UN_INITIALIZED = "unInitialized";

	/* ҵ�񵥾�ID */
	private String bosID;

	/* ����ͼƬ����ID���ϣ��Դ���ʱ������ */
	private List imgAtts;
	/* ����ID��ͼƬ����·���ļ�ֵ�� */
	private Map attPath = null;

	/* ����ʵ��Զ�̵��ýӿ� */
	private IAttachment ia = null;
	/* �����ϴ����ع��ܷ���ӿ� */
	private IAttachmentFtpFacade iAff = null;
	/* ��������ӿ� */
	private AttachmentClientManager attachmentClientManager = null;
	private AttachmentServerManager attachmentServerManager = null;
	/* ������ҵ�񵥾ݹ�ϵ�ӿ� */
	private IBoAttchAsso iba = null;

	/* ���ø���Ľ��棬������ʾ���صȴ��� */
	private Component waitOwner = null;
	/* �����ģ�������nullʱΪ�ͻ��˵��ã�����˴���ctx */
	private Context ctx = null;

	/* �Ƿ���ͼƬ���� */
	private boolean isHasIMGAtt = false;
	/* �Ƿ��ڹ���ʱ��������ͼƬ���� */
	private boolean isDefualtDown = true;

	/* ֧�ֵ�ͼƬ���ͣ�Ϊ�˹淶�ϴ��������֧�ֳ���4��jpg��bmp��gif��png�� */
	private Set types = new HashSet();
	/* ֧�ֵ�ͼƬ��С�������޶�1M���С��̫���˼��غ�ʱ���ر�ͼƬ�϶�ʱ��Ӱ����������ȣ� 
	 * Ӧ�ͻ�Ҫע���˴��޸�Ϊ�������5M -modify by yuanjun_lan
	 * */
	private int size = 1024*5;

	/**
	 * * ��ʼ��������
	 * <p>
	 * Ĭ����������ͼƬ����
	 * 
	 * @param owner
	 *            ��������
	 * @param bosID
	 *            ҵ�񵥾�ID
	 * @param ia
	 *            ����ʵ��ӿ�
	 * @param iAff
	 *            �������ع��ܽӿ�
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
	 * ��ʼ��������
	 * 
	 * @param owner
	 *            ��������
	 * @param bosID
	 *            ҵ�񵥾�ID
	 * @param ctx
	 *            �����ģ��ͻ��˴����
	 * @param isDown
	 *            �Ƿ��ڹ���ʱ��������ͼƬ����
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
	 * ˢ�¸���
	 * <p>
	 * ���������ܸı�ʱ���ã�ˢ�µ�ǰ����ĸ����б�<br>
	 * ������¸�����������isDefualtDown�ж��Ƿ�����
	 */
	public void refreshAtt() {
		List curAtt = getIMGAtt();
		Map curPath = new HashMap();
		// ��ǰ����Ϊ�գ����ÿ�
		if (curAtt == null || curAtt.isEmpty()) {
			imgAtts = null;
			attPath = null;
		}
		// ֮ǰ����Ϊ�գ����ù��췽������
		else if (imgAtts == null || imgAtts.isEmpty()) {
			imgAtts = curAtt;
			if (isDefualtDown) {
				waitFordown();
			} else {
				initEmptyAttPath();
			}
		}
		// ֮ǰ�����͵�ǰ��������Ϊ�գ����޸ĸ��µĸ���
		else {
			imgAtts = curAtt;
			// ��ǰ���ڸ���ID����
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
	 * ������ID��Ӧ��ͼƬ��ʾ��compt��
	 * <p>
	 * ͼƬ��ʵ�ʴ�Сչʾ�����ͼƬ��������compt����ӹ������ؼ�<br>
	 * �����ı�compt��С<br>
	 * ���comptΪPanel�������Panel�ϻ���һ��Label<br>
	 * ���comptΪLabel����ֱ������ͼ��<br>
	 * �Ƽ�����Panel����ֹ�����С�ı�ʱê���˵Ĺ������е�Label�Զ��ı��С����ͼƬ����<br>
	 * 
	 * @param attID
	 *            ����ID
	 * @param compt
	 *            ָ���ؼ�
	 * @throws IOException
	 *             ��ȡ�ļ�����
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
	 * ����ָ��ID��ͼƬ���������ظ�ͼƬ�ڱ�����File
	 * 
	 * @param attID
	 *            ����ID
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
	 * ����һ��ͼƬ�ļ���ַ������һ��ͼƬ
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
	 * �ϴ�һ��ͼƬ����
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
			FDCMsgBox.showWarning(waitOwner, "�ϴ����ļ�������5M������������");
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
	 * ���صȴ�����
	 * <p>
	 * Ĭ�ϳ�ʼ��ʱ���������и���
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
		ltd.setTitle("����ͼƬ��...");
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
	 * ��ʼ��������Ӧ·���ļ���
	 * <p>
	 * ����ѭ���������и���
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
	 * ��ʼ��һ���յĸ�����Ӧ·�����ϣ�Ĭ�ϲ�����ͼƬʱ����
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
	 * ���ظ�������ʱĿ¼
	 * <p>
	 * ����ļ���ѹ����ʽ���棬�Ƚ�ѹ��
	 * 
	 * @param attID
	 *            ����ID
	 * @return ��ʱ���Ŀ¼
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
	 * ���ظ�������ʱĿ¼
	 * <p>
	 * ����ļ���ѹ����ʽ���棬�Ƚ�ѹ��
	 * 
	 * @param att
	 *            ��������
	 * @return ��ʱ���Ŀ¼
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
	 * ��ʵ�ϴ�����
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
	 * �ӷ�����ȡ��ָ��ID�ĸ���
	 * 
	 * @param attID
	 *            ����ID
	 * @return �ļ���
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
	 * ��ȡ���е�ǰͼƬ����
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
			// ��ֹ�ϴ��ļ������ͺ�׺���д�д�ַ�������ϵͳ��������Ϊδ֪�����ļ���BUG
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
