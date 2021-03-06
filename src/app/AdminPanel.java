/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import dal.Repository;
import dal.RepositoryFactory;
import hr.algebra.utils.FileUtils;
import hr.algebra.utils.MessageUtils;
import java.io.IOException;
import java.sql.SQLException;
import javax.xml.stream.XMLStreamException;
import parsers.rss.MovieParser;

/**
 *
 * @author RnaBo
 */
public class AdminPanel extends javax.swing.JPanel {

    private Repository repo;
    private static final String DIR = "assets";
    /**
     * Creates new form AdminPanel
     */
    public AdminPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnPurge = new javax.swing.JButton();
        btnUpload = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1025, 927));
        setPreferredSize(new java.awt.Dimension(1025, 950));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Welcome Admin");

        btnPurge.setText("Purge all data");
        btnPurge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPurgeActionPerformed(evt);
            }
        });

        btnUpload.setText("Upload new data");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 413, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPurge, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(387, 387, 387))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(451, 451, 451))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel1)
                .addGap(116, 116, 116)
                .addComponent(btnPurge, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(341, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        
       
        try {
            
            MovieParser.parse().forEach(m ->  {
                try {
                    repo.addMovie(m);
                } catch (SQLException ex) {
                     MessageUtils.showErrorMessage("Nece", "Nece SQL");
                }
            } );
            MessageUtils.showInformationMessage("Upload data", "Finished uploading data!");
           
            
        } catch (IOException ex) {
            MessageUtils.showErrorMessage("Nece", "Nece IO");
        } catch (XMLStreamException ex) {
            MessageUtils.showErrorMessage("Nece", "Nece net");
        }
        
        
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnPurgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPurgeActionPerformed
       
         if (MessageUtils.showConfirmDialog("Purge database", "Are you sure you want to purge the database?") == 0){
             try {
                 repo.purgeDatabase();
                 FileUtils.cleanDirectory(DIR);
                 MessageUtils.showInformationMessage("Purge data", "Finished clearing data!");
             } catch (Exception ex) {
                  MessageUtils.showErrorMessage("Nece", "Nece SQL");
             }
         }
        
        
    }//GEN-LAST:event_btnPurgeActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
       init();
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPurge;
    private javax.swing.JButton btnUpload;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    private void init() {
        repo = RepositoryFactory.getRepository();
    }
}
