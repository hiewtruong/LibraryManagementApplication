/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.view.admin.user;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author hieutruong
 */
public class UserPanel extends JPanel {
     public UserPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Management User"));
        JLabel label = new JLabel("Content Management User", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
