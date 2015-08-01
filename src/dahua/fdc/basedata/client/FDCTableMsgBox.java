/* Copyright (c) 2002-2003 Kingdee公司研发中心
 * 创建人: EAS基础系统部 赵进
 * 日  期: 2004-1-13
 * 描  述:
 * 版  本: EASV4.0
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.ctrl.swing.KDTextAreaCtrl;
import com.kingdee.eas.base.uiframe.client.MainFrame;
import com.kingdee.eas.base.uiframe.client.SystemEntry;
import com.kingdee.eas.common.client.LocaleUtil;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.util.ExceptionUtil;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExitSystemHelper;
import com.kingdee.eas.util.client.MailUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @author xiha
 * @version EASv5.0 modified by zhangfan
 */
public class FDCTableMsgBox extends KDDialog
{
    static protected final Logger logger = Logger.getLogger(FDCTableMsgBox.class);
    protected boolean isDetail = false;
    protected String msg;
    protected String detail;
    protected Map detailMap;
    protected int messageType;
    protected int option;
    protected String[] options;
    protected int result = CANCEL_OPTION;
    protected static Image imgInfoCue;
    protected static Image imgInfoAsk;
    protected static Image imgInfoError;
    protected static Image imgInfoAdvise;
    protected static final String CUE = "imgInfo_cue";
    protected static final String ASK = "imgInfo_ask";
    protected static final String ERROR = "imgInfo_error";
    protected static final String ADVISE = "imgInfo_advise";
    protected static final Color bottomBgColor = new Color(230, 230, 230);
    protected static final Color separatorColor = new Color(180, 180, 180);
    
    
    public String getMsg() 
    {
        return msg;
    }
    
    

    public String getDetail()
	{
		return detail;
	}



	public void setDetail(String detail)
	{
		this.detail = detail;
	}



	public static Image getImgInfoCue()
    {
        if(imgInfoCue == null)
        {
            imgInfoCue = EASResource.getImage(CUE);
        }

        return imgInfoCue;
    }

    public static Image getImgInfoAsk()
    {
        if(imgInfoAsk == null)
        {
            imgInfoAsk = EASResource.getImage(ASK);
        }

        return imgInfoAsk;
    }

    public static Image getImgInfoError()
    {
        if(imgInfoError == null)
        {
            imgInfoError = EASResource.getImage(ERROR);
        }

        return imgInfoError;
    }

    public static Image getImgInfoAdvise()
    {
        if(imgInfoAdvise == null)
        {
            imgInfoAdvise = EASResource.getImage(ADVISE);
        }

        return imgInfoAdvise;
    }

    public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    public static final int INFORMATION_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
    public static final int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    public static final int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;
    public static final int YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION; //1
    public static final int OK_CANCEL_OPTION = JOptionPane.OK_CANCEL_OPTION; //2
    public static final int OK_ONLY_OPTION = 3;
    public static final int OK_CANCEL_ADV_OPTION = 4;
    public static final int OK_ADV_OPTION = 5;
    public static final int DETAIL_OK_OPTION = 8188;
    public static final int YES_NO_OPTION=8189;
    public static final int OK_RETRY_OPTION = 6;
    public static final int YES_OPTION = JOptionPane.YES_OPTION;
    public static final int NO_OPTION = JOptionPane.NO_OPTION;
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    public static final int OK_OPTION = JOptionPane.OK_OPTION;

    public static FDCTableMsgBox createAdvMsgBox(Component owner, String title,
        String msg, String detail, int messageType, int option)
    {
        //如果弹出该对话框的界面是coreUI，则将该界面切换到前面，2004-12-13，modified by hx
        //为了满足增加多线程处理界面操作，提供进度条需求中的细节处理
//        if(owner instanceof CoreUI&&!(owner instanceof MessageCenterUI)){
//            if(((CoreUI)owner).getUIWindow()!=null)
//                try{
//                    ((CoreUI)owner).getUIWindow().show();
//                }catch(Exception e){
//                    logger.error("AdvMsgBox createAdvMsgBox occur :"+e);
//                }
//        }
            
        Window ownerWindow = ComponentUtil.getOwnerWindow(owner);
       
        if(ownerWindow instanceof Frame)
        {
            return new FDCTableMsgBox((Frame)ownerWindow, title, msg, detail,
                messageType, option);
        }
        else if(ownerWindow instanceof Dialog)
        {
            return new FDCTableMsgBox((Dialog)ownerWindow, title, msg, detail,
                messageType, option);
        }
        else
        {
    		if(SystemEntry.instance.isWebStart())
            {
            	
    			Frame myOwner = JOptionPane
    			.getFrameForComponent((JApplet) SystemEntry.instance.getApplet());
    			return new FDCTableMsgBox(myOwner, title, msg, detail, messageType,
    			                option);
            }	
            return new FDCTableMsgBox((Frame)null, title, msg, detail, messageType,
                option);
        }
    }
    
    public static FDCTableMsgBox createAdvMsgBox(Component owner, String title,
            String msg, Map detailMap, int messageType, int option)
        {
            //如果弹出该对话框的界面是coreUI，则将该界面切换到前面，2004-12-13，modified by hx
            //为了满足增加多线程处理界面操作，提供进度条需求中的细节处理
//            if(owner instanceof CoreUI&&!(owner instanceof MessageCenterUI)){
//                if(((CoreUI)owner).getUIWindow()!=null)
//                    try{
//                        ((CoreUI)owner).getUIWindow().show();
//                    }catch(Exception e){
//                        logger.error("AdvMsgBox createAdvMsgBox occur :"+e);
//                    }
//            }
                
            Window ownerWindow = ComponentUtil.getOwnerWindow(owner);
           
            if(ownerWindow instanceof Frame)
            {
                return new FDCTableMsgBox((Frame)ownerWindow, title, msg, detailMap,
                    messageType, option);
            }
            else if(ownerWindow instanceof Dialog)
            {
                return new FDCTableMsgBox((Dialog)ownerWindow, title, msg, detailMap,
                    messageType, option);
            }
            else
            {
        		if(SystemEntry.instance.isWebStart())
                {
                	
        			Frame myOwner = JOptionPane
        			.getFrameForComponent((JApplet) SystemEntry.instance.getApplet());
        			return new FDCTableMsgBox(myOwner, title, msg, detailMap, messageType,
        			                option);
                }	
                return new FDCTableMsgBox((Frame)null, title, msg, detailMap, messageType,
                    option);
            }
        }

    /**
     * @deprecated 不推荐使用，仅为了与以前的接口兼容
     * @param owner
     * @param title
     * @param msg
     * @param detail
     */
    public FDCTableMsgBox(Dialog owner, String title, String msg, String detail)
    {
        this(owner, title, msg, detail, ERROR_MESSAGE, OK_ADV_OPTION);
    }

    public FDCTableMsgBox(Dialog owner, String title, String msg, String detail,
        int messageType, int option)
    {
        super(owner, title, true);
        init(msg, detail, messageType, option);
    }

    /**
     * @deprecated 不推荐使用，仅为了与以前的接口兼容
     * @param owner
     * @param title
     * @param msg
     * @param detail
     */
    public FDCTableMsgBox(Frame owner, String title, String msg, String detail)
    {
        this(owner, title, msg, detail, ERROR_MESSAGE, OK_ADV_OPTION);
    }

    public FDCTableMsgBox(Frame owner, String title, String msg, String detail,
        int messageType, int option)
    {
        super(owner, title, true);
        init(msg, detail, messageType, option);
    }
    
    public FDCTableMsgBox(Frame owner, String title, String msg, String detail,
            String[] options,int messageType, int option)
        {
            super(owner, title, true);
            init(msg, detail, options,messageType, option);
            
        }
    
    
    public FDCTableMsgBox(Dialog owner, String title, String msg, String detail,
    	            String[] options,int messageType, int option)
    	        {
    	            super(owner, title, true);
    	            init(msg, detail, options,messageType, option);
    	            
    	        }

    public FDCTableMsgBox(Frame owner, String title, boolean b) {
    	 super(owner, title, true);
	}
    public FDCTableMsgBox(Dialog owner, String title, boolean b) {
   	 super(owner, title, true);
	}



	public FDCTableMsgBox(Frame owner, String title, String msg, Map detailMap, int messageType, int option) {
		 super(owner, title, true);
		 init(msg, detailMap, messageType, option);
	}



	public FDCTableMsgBox(Dialog owner, String title, String msg, Map detailMap, int messageType, int option) {
		super(owner, title, true);
		init(msg, detailMap, messageType, option);
	}



	/**
     * 返回结果YES_OPTION/NO_OPTION/OK_OPTION/CANCEL_OPTION
     * @return
     */
    public int getResult()
    {
        return result;
    }

    protected void init(String msg, String detail, int messageType, int option)
    {
        this.msg = msg;
        this.detail = detail;
        this.messageType = messageType;
        this.option = option;
        jbInit();
    }
    
    protected void init(String msg, Map detailMap, int messageType, int option)
    {
        this.msg = msg;
        this.detailMap = detailMap;
        this.messageType = messageType;
        this.option = option;
        jbInit();
    }
    
    protected void init(String msg, String detail,String[] options, int messageType, int option)
    {
        this.msg = msg;
        this.detail = detail;
        this.messageType = messageType;
        this.option = option;
        this.options = options;
        jbInit();
    }
    
    protected void jbInit()
    {
    	this.jbInit(null);
    }

    protected void jbInit(JPanel bgPanel)
    {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(null);

        if(bgPanel == null)
        {
            if(messageType == INFORMATION_MESSAGE)
            {
                bgPanel = new BgPanel(getImgInfoCue(), msg);
            }
            else if(messageType == WARNING_MESSAGE)
            {
                bgPanel = new BgPanel(getImgInfoAdvise(), msg);
            }
            else if(messageType == QUESTION_MESSAGE)
            {
                bgPanel = new BgPanel(getImgInfoAsk(), msg);
            }
            else
            {
                //ERROR_MESSAGE
                bgPanel = new BgPanel(getImgInfoError(), msg);
            }
        }
        
        final Dimension bgPanelDimension = bgPanel.getPreferredSize();
        final int normalBottomHeight = 42;
        final int advanceBottomHeight = 202;
        contentPane.setPreferredSize(new Dimension(bgPanelDimension.width,
                bgPanelDimension.height + normalBottomHeight));
        bgPanel.setBounds(0, 0, bgPanelDimension.width, bgPanelDimension.height);
        contentPane.add(bgPanel);

        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(null);
        pnlBottom.setBounds(0, bgPanelDimension.height, bgPanelDimension.width,
            advanceBottomHeight);
        contentPane.add(pnlBottom);
        pnlBottom.setBackground(bottomBgColor);

        KDSeparator sp = new KDSeparator();
        sp.setBounds(0, 0, bgPanelDimension.width, 2);
        pnlBottom.add(sp);

        KDButton btnSendMail = new KDButton(EASResource.getString("sendMail"));
        btnSendMail.setMargin(new Insets(2,1,2,1));
        btnSendMail.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                	dispose();
                    MailUtil.sendMailToKingdee(FDCTableMsgBox.this.msg,
                        FDCTableMsgBox.this.detail);
                }
            });

        KDButton btnRetry = new KDButton(EASResource.getString("relogin"));
        btnRetry.setMargin(new Insets(2,1,2,1));
        btnRetry.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    dispose();

                    try
                    {
                    	SystemEntry.instance.relogin();
                    }
                    catch(Exception exc)
                    {
                    	// @AbortException
                    	logger.error(ExceptionUtil.getExcLinkStackTrace(exc));
                        MsgBox.showConnectionError();
                    }
                }
            });

        KDButton btnAdv = new KDButton(EASResource.getString("advance"));
        btnAdv.setMargin(new Insets(2,1,2,1));
        btnAdv.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JPanel contentPane = (JPanel)getContentPane();

                    if(isDetail)
                    {
                        contentPane.setPreferredSize(new Dimension(
                                bgPanelDimension.width,
                                bgPanelDimension.height + normalBottomHeight));
                        pack();
                        isDetail = false;
                    }
                    else
                    {
                        contentPane.setPreferredSize(new Dimension(
                                bgPanelDimension.width,
                                bgPanelDimension.height + advanceBottomHeight));
                        pack();
                        isDetail = true;
                    }
                }
            });

        KDButton btnOK = new KDButton(EASResource.getString("ok"));
        btnOK.setMargin(new Insets(2,1,2,1));
        btnOK.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    result = OK_OPTION;
                    dispose();
                }
            });

        KDButton btnCancel = null;
        if(this.options == null)
        {
            btnCancel = new KDButton(EASResource.getString("cancel"));
        }
        else
        {
            btnCancel = new KDButton(this.options[2]);
        }
        btnCancel.setMargin(new Insets(2,1,2,1));
        btnCancel.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    result = CANCEL_OPTION;
                    dispose();
                }
            });
        
        KDButton btnReConnection = null;
        if(this.options == null)
        {
        	btnReConnection = new KDButton(EASResource.getString("reConnection"));
        }
        else
        {
        	btnReConnection = new KDButton(this.options[2]);
        }
        btnReConnection.setMargin(new Insets(2,1,2,1));
        btnReConnection.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    result = CANCEL_OPTION;
                    dispose();
                }
            });

        KDButton btnYes = null;
        if(this.options == null)
        {
            btnYes = new KDButton(EASResource.getString("yes"));
        }
        else
        {
            btnYes = new KDButton(this.options[0]);
        }
        btnYes.setMargin(new Insets(2,1,2,1));
        btnYes.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    result = YES_OPTION;
                    dispose();
                }
            });

        KDButton btnNo = null;
        if(this.options == null)
        {
            btnNo = new KDButton(EASResource.getString("no"));
        }
        else
        {
            btnNo = new KDButton(this.options[1]);
        }
        btnNo.setMargin(new Insets(2,1,2,1));
        btnNo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    result = NO_OPTION;
                    dispose();
                }
            });
        
        KDButton btnQuitSystem=new KDButton(EASResource.getString("quitSystem"));
        btnQuitSystem.setMargin(new Insets(2,1,2,1));
        btnQuitSystem.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
                try
        		{
                	ExitSystemHelper.preparedExit();
	        		MainFrame.closeApplicationWhenSessionTimeOut();
	                dispose();
        		}
        		catch(Throwable ee)
        		{
        			// @AbortException
        			logger.error("close windows occur error!",ee);
        			System.exit(-1);
        		}
        	}
        });
        
        if(option==DETAIL_OK_OPTION) {
            btnOK.setBounds(342,11,77,21);
            btnAdv.setBounds(262,11,77,21);
            pnlBottom.add(btnAdv);
            pnlBottom.add(btnOK);
            setDefaultFocus(btnOK);
        } 
        else if(option==YES_NO_OPTION) 
        {
            btnYes.setBounds(262,11,77,21);
            btnNo.setBounds(342,11,77,21);
            pnlBottom.add(btnYes);
            pnlBottom.add(btnNo);
            setDefaultFocus(btnYes);
        }
        else if(messageType == INFORMATION_MESSAGE)
        {
            btnOK.setBounds(342, 11, 77, 21);
            pnlBottom.add(btnOK);
        }
        else if(messageType == WARNING_MESSAGE)
        {
            btnOK.setBounds(342, 11, 77, 21);
            pnlBottom.add(btnOK);
        }
        else if(messageType == QUESTION_MESSAGE)
        {
            if(option == OK_CANCEL_OPTION)
            {
                btnOK.setBounds(257, 11, 77, 21);
                btnCancel.setBounds(342, 11, 77, 21);
                pnlBottom.add(btnOK);
                pnlBottom.add(btnCancel);
            }
            else if(option == YES_NO_CANCEL_OPTION)
            {
                btnYes.setBounds(172, 11, 77, 21);
                btnNo.setBounds(257, 11, 77, 21);
                btnCancel.setBounds(342, 11, 77, 21);
                pnlBottom.add(btnYes);
                pnlBottom.add(btnNo);
                pnlBottom.add(btnCancel);
            }
            else
            {
                //OK_CANCEL_ADV_OPTION
                btnOK.setBounds(257, 11, 77, 21);
                btnCancel.setBounds(342, 11, 77, 21);
                btnAdv.setBounds(10, 11, 77, 21);
                pnlBottom.add(btnOK);
                pnlBottom.add(btnCancel);
                pnlBottom.add(btnAdv);
                setDefaultFocus(btnOK);
            }
        }
        else if(messageType == ERROR_MESSAGE)
        {
            if(option == OK_RETRY_OPTION)
            {
                //网络连接失败，重新连接  btnRetry
            	
            	btnReConnection.setBounds(172,11,77,21);
            	btnRetry.setBounds(257, 11, 77, 21);
            	btnQuitSystem.setBounds(342, 11, 77, 21);
            	
                pnlBottom.add(btnRetry);
                pnlBottom.add(btnReConnection);
                pnlBottom.add(btnQuitSystem);
                setDefaultFocus(btnRetry);
                addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent we) {
                        if(MsgBox.showConfirm2(EASResource.getString("cnFailWillExit"))==MsgBox.OK) {
                            System.exit(0);
                        } else {
                            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        }
                    }
                });
            }
            else if(option == OK_ADV_OPTION)
            {
                //OK_ADV_OPTION
//                btnOK.setText(EASResource.getString("notSend"));
//                btnOK.setBounds(342, 11, 77, 21);
//                btnSendMail.setBounds(262, 11, 77, 21);
//                btnRelogin.setBounds(262, 11, 77, 21);
//                btnRelogin.addKeyListener(new KeyAdapter(){
//                    public void keyPressed(KeyEvent e) {
//                        respondToKeyPressing(e,bgPanelDimension,normalBottomHeight,advanceBottomHeight);
//                    }
//                });
//                btnAdv.setBounds(10, 11, 77, 21);
                btnCancel.setBounds(342,11,77,21);
                btnCancel.setText(EASResource.getString("ignore"));
                btnCancel.addKeyListener(new KeyAdapter(){
                    public void keyPressed(KeyEvent e) {
                        respondToKeyPressing(e,bgPanelDimension,normalBottomHeight,advanceBottomHeight);
                    }    
                });
                btnQuitSystem.setBounds(262,11,77,21);
                btnQuitSystem.addKeyListener(new KeyAdapter(){
                    public void keyPressed(KeyEvent e) {
                        respondToKeyPressing(e,bgPanelDimension,normalBottomHeight,advanceBottomHeight);
                    }
                });
//                pnlBottom.add(btnOK);
//                pnlBottom.add(btnSendMail);
//                pnlBottom.add(btnRelogin);
//				pnlBottom.add(btnAdv);
				pnlBottom.add(btnQuitSystem);
				pnlBottom.add(btnCancel);
                setDefaultFocus(btnCancel);
            }
            else
            {
                btnOK.setBounds(342, 11, 77, 21);
                pnlBottom.add(btnOK);
            }
        }
        if(detailMap!=null&&detailMap.size()>0){
        	getTable().addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent e) {
                    respondToKeyPressing(e,bgPanelDimension,normalBottomHeight,advanceBottomHeight);
                }
            });
        }else{
	        
	        KDTextAreaCtrl txtDetail = new KDTextAreaCtrl();
	        txtDetail.getTextComponent().setEditable(false);
	        txtDetail.setText(detail);
	        txtDetail.setSelectionStart(0);
	        txtDetail.setSelectionEnd(0);
	        txtDetail.setBounds(10, 43, 409, 150);
	        txtDetail.getTextComponent().addKeyListener(new KeyAdapter(){
	            public void keyPressed(KeyEvent e) {
	                respondToKeyPressing(e,bgPanelDimension,normalBottomHeight,advanceBottomHeight);
	            }
	        });
	        pnlBottom.add(txtDetail);
        }
        pnlBottom.add(table);
        this.pack();
        this.setLocationRelativeTo(null);
        
    }
    

//    protected void fireRelogin() {
//        dispose();
//		MainFrame.reLogin();
//    }
    
    protected void respondToKeyPressing(KeyEvent e,Dimension bgPanelDimension,int normalBottomHeight,int advanceBottomHeight) {
        if(e.getKeyCode()==KeyEvent.VK_E && e.isControlDown()) {
	        JPanel contentPane = (JPanel)getContentPane();
	        if(isDetail)
	        {
	            contentPane.setPreferredSize(new Dimension(
	                    bgPanelDimension.width,
	                    bgPanelDimension.height + normalBottomHeight));
	            pack();
	            isDetail = false;
	        }
	        else
	        {
	            contentPane.setPreferredSize(new Dimension(
	                    bgPanelDimension.width,
	                    bgPanelDimension.height + advanceBottomHeight));
	            pack();
	            isDetail = true;
	        }
        }
    }

    protected void setDefaultFocus(final Component comp)
    {
        addWindowListener(new WindowAdapter()
            {
                public void windowOpened(WindowEvent e)
                {
                    comp.requestFocusInWindow();
                }
            });
    }
    
    private KDTable table=null;
    private KDTable getTable() {
		if(table==null){
			Map detailMap=this.detailMap;
			String[]head=(String[])detailMap.get("head");
			Object[][]data=(Object[][])detailMap.get("data");
			Object format[]=(Object[])detailMap.get("format");
			if(head.length>0){
				int cols=head.length;
				table=new KDTable(cols,1,0);
				IRow headRow=table.getHeadRow(0);
				for(int i=0;i<head.length;i++){
					headRow.getCell(i).setValue(head[i]);
				}
				if(format.length>0){
					for(int i=0;i<format.length;i++){
						if(format[i]!=null&&format[i].equals("amount")){
							FDCHelper.formatTableNumber(table, i, FDCHelper.strDataFormat);
						}
					}
				}
				if(data!=null){
					KDTableUtil.fillTableRow(data, null, 0, table);
				}
				table.setBounds(10, 43, 409, 150);
				table.getStyleAttributes().setLocked(true);
			}
//			table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		}
		return table;
	}

    public static void main(String[] args) throws Exception
    {
//        UIManager.setLookAndFeel("com.kingdee.bos.plaf.TeemsLookAndFeel");

//        JFrame frame = new JFrame();
//        AdvMsgBox advMsgBox = new AdvMsgBox(frame, "title",
//                "类似K3模式，通过异步取ID的方式解决第一次取数慢的问题，通过Query指定LogicKey解决右关联问题，用(FAID = ? and FBID = ?) or (FAID = ? and FBID = ?)代替in操作来解决多主键时的in操作的性能问题。待确定项：a.	单纯移动游标的性能(next的性能)。b.	移动游标后取数时多列与单列（只取ID）的性能差异。以上两者的测试，需要保证有表连接、有过滤条件、列数超过20、数据量不小于10万。",
//                "detail", INFORMATION_MESSAGE, OK_ONLY_OPTION);
//        advMsgBox.show();
//
//        AdvMsgBox advMsgBox2 = new AdvMsgBox(frame, "title", null, "detail",
//                WARNING_MESSAGE, OK_ONLY_OPTION);
//        advMsgBox2.show();
//
//        AdvMsgBox advMsgBox3 = new AdvMsgBox(frame, "title", "msg", "detail",
//                QUESTION_MESSAGE, OK_CANCEL_OPTION);
//        advMsgBox3.show();
//
//        AdvMsgBox advMsgBox4 = new AdvMsgBox(frame, "title", "msg", "detail",
//                QUESTION_MESSAGE, YES_NO_CANCEL_OPTION);
//        advMsgBox4.show();
//
//        AdvMsgBox advMsgBox5 = new AdvMsgBox(frame, "title",
//                "类似K3模式，通过异步取ID的方式解决第一次取数慢的问题，通过Query指定LogicKey解决右关联问题，用(FAID = ? and FBID = ?) or (FAID = ? and FBID = ?)代替in操作来解决多主键时的in操作的性能问题。待确定项：a.	单纯移动游标的性能(next的性能)。b.	移动游标后取数时多列与单列（只取ID）的性能差异。以上两者的测试，需要保证有表连接、有过滤条件、列数超过20、数据量不小于10万。",
//                "detail", QUESTION_MESSAGE, OK_CANCEL_ADV_OPTION);
//        advMsgBox5.show();
//
//        AdvMsgBox advMsgBox6 = new AdvMsgBox(frame, "title", "msg", "detail",
//                ERROR_MESSAGE, OK_ONLY_OPTION);
//        advMsgBox6.show();
//
//        AdvMsgBox advMsgBox7 = new AdvMsgBox(frame, "title", "msg", "detail",
//                ERROR_MESSAGE, OK_ADV_OPTION);
//        advMsgBox7.show();
        
//        int r=MsgBox.showConfirm2New(null,"java is good");
//        assert r==MsgBox.YES || r==MsgBox.NO;
//        System.exit(0);
        
        String detail="dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk" +
        		"dkdkdkdkdkdkddkdkdkdk";
        MsgBox.showError(null,"dkdkdkdkd",detail);
        System.exit(0);
    }
}

/**
 * 带背景图片的Panel
 * @author xiha
 * 创建日期 2004-5-17
 */
class BgPanel extends KDPanel
{
    protected Image bgImg;
    protected String msg;
    protected Vector vecLines ;
    protected static final int lineHeight = 15;
    protected static final int x = 150;
    protected static final int y = 28;
    protected static final int maxWidth = 255;
    protected static final int normalLine = 8;
    protected static final int maxLine = 26;
    protected static final int imageWidth = 429;
    protected static final int imageHeight = 138;
    protected static final Color extMsgBgColor = new Color(242, 242, 242);

    public BgPanel(Image bgImg, String msg)
    {
        super();
        this.bgImg = bgImg;
        this.msg = msg;
        this.setBackground(extMsgBgColor);
        this.setPreferredSize(new Dimension(imageWidth, 165));
        if(msg!=null&&!msg.equals(""))
            initStringArray();
    }

    protected void initStringArray()
    {
        FontMetrics fontMetrics = this.getFontMetrics(this.getFont());
        
        StringTokenizer st = new StringTokenizer(msg,"\n");
        String paragraphString=null;
        boolean isChinese = false;
        
        String language=null;
        
        try{
            language = LocaleUtil.getOriginalLocale(SysContext.getSysContext().getLocale()).getLanguage();
        }catch(Throwable t){
        	// @AbortException
            language = Locale.getDefault().getLanguage();
        }
        if(language!=null&&language.equalsIgnoreCase("zh")){
            isChinese = true;
        }
        
        vecLines = new Vector(26);
        StringBuffer lineString = null;
        char word ;
        while(st.hasMoreTokens()&&vecLines.size()<26){
            paragraphString = st.nextToken().trim();
            if(isChinese){
                paragraphString="　　"+paragraphString;
            }else{
                paragraphString="  "+paragraphString;
            }
            lineString = new StringBuffer();
            int lineLength = 0;
            for(int i=0;i<paragraphString.length()&&vecLines.size()<26;i++){
                word = paragraphString.charAt(i);
                lineLength +=fontMetrics.charWidth(word);
                if(lineLength<maxWidth){
                    lineString.append(word);
                }else{
                    lineString.append(word);
                    vecLines.add(lineString);
                    lineString = new StringBuffer();
                    lineLength=0;
                }
            }
            vecLines.add(lineString);
        }
        
        if(vecLines.size()>9&&vecLines.size()<27){//默认9行，最多显示26行文本
            int actHeight = vecLines.size()*lineHeight;
            setPreferredSize(new Dimension(imageWidth, actHeight));
        }else if(vecLines.size()>26){
            setPreferredSize(new Dimension(imageWidth, 26*lineHeight));
        }
    }

    /**
     * @param g
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this.getSize().width, imageHeight, this);

        if(vecLines!=null) {
            for(int i=0;i<vecLines.size();i++){
                g.drawString(((StringBuffer)vecLines.elementAt(i)).toString(),x,y+i*lineHeight);
            }
        }
    }

}