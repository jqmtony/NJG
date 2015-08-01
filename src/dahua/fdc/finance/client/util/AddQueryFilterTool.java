package com.kingdee.eas.fdc.finance.client.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.resource.KDResourceManager;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.client.ScheduleTaskF7UI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
/**
 * ��������f7��ӿ��ٹ���
 * @author keyan_zhao
 *
 */


public class AddQueryFilterTool {
	
	//���ٲ�ѯ�ؼ�
    protected  com.kingdee.bos.ctrl.swing.KDTextField txtQuickQueryInput;
    
    //���ٲ�ѯ��ť
    protected  KDWorkButton quickQueryButton;
    
    protected  ScheduleTaskF7UI schUI;
    
    protected  Image icon_off;

	protected  Image icon_disable;
	protected final  Color BGCOLOR = new Color(0x74a8db);
	

    //���ٹ����¼�������
    private  QuickQueryHandler quickQueryHandler = null; 
	
	public AddQueryFilterTool() throws Exception {
	}
	
	public AddQueryFilterTool(ScheduleTaskF7UI scheduleTaskF7UI) {
		schUI = scheduleTaskF7UI;
		//��ӿ��ٲ�ѯ�ؼ�
	    addQuickQuery();
	}

	
	
	
	/**
     * ���ٲ�ѯ�ؼ��� 
     * */
    private  void addQuickQuery() {
        txtQuickQueryInput = new com.kingdee.bos.ctrl.swing.KDTextField();
        txtQuickQueryInput.setName("txtQuickQueryInput");
        txtQuickQueryInput.setText("��������������");		
        txtQuickQueryInput.setMaxLength(60);
        txtQuickQueryInput.setBounds(new Rectangle(13, 6, 170, 19));
        schUI.add(txtQuickQueryInput, new KDLayout.Constraints(10, 10, 200, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

    	icon_off = KDResourceManager.getImageOfRapid("prompt_off.gif");
    	icon_disable = KDResourceManager.getImageOfRapid("prompt_disable.gif");

        quickQueryButton = new KDWorkButton();
        quickQueryButton.setName("simpleQuickQuery"); 
//        button.setText("��ѯ");
        quickQueryButton.setBounds(new Rectangle(183, 6, 19, 19));
        quickQueryButton.setIcon(new ImageIcon(icon_off));
//		button.setBorder(KingdeeBorders.createF7ButtonBorder());
        quickQueryButton.setBackground(BGCOLOR);
        quickQueryButton.setDisabledIcon(new ImageIcon(icon_disable));
//        button.setFocusable(false);
        quickQueryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String text = txtQuickQueryInput.getText();
				FilterInfo filter = new FilterInfo();
				
				if(text != null && !"��������������".equals(text.trim())){
					filter.getFilterItems().add(new FilterItemInfo("name","%"+text+"%",CompareType.LIKE));
				}
				try {
					schUI.queryData(filter);
				} catch (BOSException e1) {
					schUI.handUIExceptionAndAbort(e1);
				} catch (SQLException e1) {
					schUI.handUIExceptionAndAbort(e1);
				}
			}});
        
        schUI.add(quickQueryButton, new KDLayout.Constraints(210, 10, 19, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        
        
		
        quickQueryHandler = new QuickQueryHandler();
        txtQuickQueryInput.addKeyListener(quickQueryHandler);
        txtQuickQueryInput.addMouseListener(quickQueryHandler);
        txtQuickQueryInput.addFocusListener(quickQueryHandler);

        CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(
        		txtQuickQueryInput,KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0));
    }
    
    
    
    /* 
	 * ����������Assistor������Listener������<p>
	 * ��ϸ������ <p>
	 *
	 */
	private  class QuickQueryHandler extends MouseAdapter implements KeyListener,FocusListener{
		public void mouseClicked(MouseEvent e){
			String inputText = txtQuickQueryInput.getText();
			if(inputText != null && "��������������".equals(inputText.trim())){
				txtQuickQueryInput.setText("");
			}
			

			
		}




		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void focusGained(FocusEvent e) {
			String inputText = txtQuickQueryInput.getText();
			if(inputText != null && "��������������".equals(inputText.trim())){
				txtQuickQueryInput.setText("");
			}
			
		}

		public void focusLost(FocusEvent e) {
			String inputText = txtQuickQueryInput.getText();
			if(inputText == null || inputText.trim().length() <= 0){
				txtQuickQueryInput.setText("��������������");
				return;
			}
		}




		public void keyPressed(KeyEvent e) {
			
		}




		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				String text = txtQuickQueryInput.getText();
				FilterInfo filter = new FilterInfo();
				
				if(text != null && !"��������������".equals(text.trim())){
					filter.getFilterItems().add(new FilterItemInfo("name","%"+text+"%",CompareType.LIKE));
				}
				try {
					schUI.queryData(filter);
				} catch (BOSException e1) {
					schUI.handUIExceptionAndAbort(e1);
				} catch (SQLException e1) {
					schUI.handUIExceptionAndAbort(e1);
				}
			}
		}		
		 
	 }
	
	
}
