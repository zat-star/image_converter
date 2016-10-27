/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.view;

import autoconverter.controller.AutoConverterUtils;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author yfujita
 */
public class DirectoryChooserDialog extends javax.swing.JDialog {

	private int retval;
	private Logger logger = AutoConverterUtils.getLogger();

	/**
	 * Creates new form DirectoryChooserDialog
	 */
	public DirectoryChooserDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		retval = JFileChooser.ERROR_OPTION;
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                fileChooser = new javax.swing.JFileChooser();
                parentButton = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                fileChooser.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                fileChooserActionPerformed(evt);
                        }
                });
                getContentPane().add(fileChooser, java.awt.BorderLayout.CENTER);

                java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle"); // NOI18N
                parentButton.setText(bundle.getString("parent_directory")); // NOI18N
                parentButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                parentButtonActionPerformed(evt);
                        }
                });
                getContentPane().add(parentButton, java.awt.BorderLayout.PAGE_START);

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
		logger.fine(evt.getActionCommand());
		if(evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
			retval = JFileChooser.APPROVE_OPTION;
			this.setVisible(false);
		}
		if(evt.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)){
			retval = JFileChooser.CANCEL_OPTION;
			this.setVisible(false);
		}
        }//GEN-LAST:event_fileChooserActionPerformed

        private void parentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentButtonActionPerformed
		this.fileChooser.changeToParentDirectory();
        }//GEN-LAST:event_parentButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(DirectoryChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DirectoryChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DirectoryChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DirectoryChooserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				DirectoryChooserDialog dialog = new DirectoryChooserDialog(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	public void setFilePath(String path){
		if(path != null){
			this.fileChooser.setSelectedFile(new File(path));
			this.fileChooser.setCurrentDirectory(new File(path));
		}
		logger.fine(path);
		logger.fine(fileChooser.getSelectedFile().getAbsolutePath());
	}
	public int showDialog(){
		this.setVisible(true);
		return this.retval;
	}
	public File getFile(){
		return this.fileChooser.getSelectedFile();
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JFileChooser fileChooser;
        private javax.swing.JButton parentButton;
        // End of variables declaration//GEN-END:variables
}