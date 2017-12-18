/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file IMailUGUI.java
 * @author Ibrahim Thai
 * @date December 17, 2017
 * @brief The main class that does all the email actions
 */
package imailu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ibro-
 */
public class IMailUGUI extends JFrame implements ActionListener{
    
    // Add variables here
    JFrame setupFrame = new JFrame();
    Font myFont = new Font("SansSerif", Font.BOLD, 20);
    
    // Create pop-up Windows
    JFrame successWindow = new JFrame();
    JFrame errorWindow = new JFrame();
    
    JLabel myDragLabel = new JLabel();
    JLabel mySetupDragLabel = new JLabel();
    JButton myExitButton = new JButton();
    JButton myMinimizeButton = new JButton();
    
    // Text Fields for MAIN
    JTextField toTextField = new JTextField();
    JTextField subjectTextField = new JTextField();
    JTextArea textAreaField = new JTextArea(15, 70);
    
    // Text Fields for SETUP
    JTextField hostTextField = new JTextField();
    JTextField portTextField = new JTextField();
    JTextField emailTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();
    
    JButton mySendButton = new JButton();
    JButton mySetupButton = new JButton();
    
    int x1;
    int y1;
    
    // Mouse Drag Action System
    public void FramDragMouseDragged(java.awt.event.MouseEvent evt)
    {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        // Get the coordinates of the mouse drag location
        this.setLocation(x - x1, y - y1);
    }
    
    // Mouse Drag Action System
    public void FormMousePressed(java.awt.event.MouseEvent evt)
    {
        x1 = evt.getX();
        y1 = evt.getY();
    } 
    
    /**
     * @param args the command line arguments
     */
    
    // Main starts here
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        IMailUGUI email = new IMailUGUI();
        email.setVisible(true);
    } // End of Main
    
    public IMailUGUI()
    {
        super("iMail U");
        setSize(865, 700);
        
        // Windows Frame (MAIN Template)
        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("EmailTemplate.jpg"))) ) );
        } catch (IOException e) {
            System.out.println("Email template cannot be opened!");
        }
        
        // Remove everything including the Exit, Maximize, and Minimize buttons
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Mouse Dragging System for MAIN Windows Frame
        myDragLabel = new JLabel();
        myDragLabel.setBounds(0, 0, 698, 41);
        myDragLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() 
        {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                FramDragMouseDragged(evt);
            }
        });
        
        // Windows Frame (SETUP Template)
        setupFrame = new JFrame("Setup");
        setupFrame.setSize(720, 390);
        setupFrame.setLocationRelativeTo(null);

        // SETUP Windows Template
        try {
            setupFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("EmailSetupTemplate.jpg"))) ) );
        } catch (IOException exception) {
            System.out.println("Email template cannot be opened!");
        }
        
        // Remove everything including the Exit, Maximize, and Minimize buttons
        setupFrame.setUndecorated(true);
        setupFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupFrame.setVisible(false);
        
        // Mouse Dragging System for SETUP Windows Frame
        mySetupDragLabel = new JLabel();
        mySetupDragLabel.setBounds(0, 0, 626, 35);
        mySetupDragLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() 
        {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                FramDragMouseDragged(evt);
            }
        });
        
        // Exit Button
        myExitButton = new JButton();
        myExitButton.setBounds(813, 0, 51, 39);
        myExitButton.setOpaque(false);
        myExitButton.setContentAreaFilled(false);
        myExitButton.setBorderPainted(false);
        myExitButton.addActionListener(new EndingListener());
        
        // Minimize Button
        myMinimizeButton = new JButton();
        myMinimizeButton.setBounds(755, 0, 54, 39);
        myMinimizeButton.setOpaque(false);
        myMinimizeButton.setContentAreaFilled(false);
        myMinimizeButton.setBorderPainted(false);
        myMinimizeButton.addActionListener(new MinimizeListener());
        
        // Send Button
        mySendButton = new JButton();
        mySendButton.setBounds(735, 645, 100, 36);
        mySendButton.setOpaque(false);
        mySendButton.setContentAreaFilled(false);
        mySendButton.setBorderPainted(false);
        mySendButton.addActionListener(new SendEmailListener());
        
        // Setup Button
        mySetupButton = new JButton();
        mySetupButton.setBounds(38, 645, 100, 36);
        mySetupButton.setOpaque(false);
        mySetupButton.setContentAreaFilled(false);
        mySetupButton.setBorderPainted(false);
        mySetupButton.addActionListener(new OpenSetupListener());
        
        // Send To
        toTextField = new JTextField();
        toTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        toTextField.setOpaque(false);
        toTextField.setBounds(180, 146, 650, 40);
        toTextField.setFont(myFont);
        
        // Subject
        subjectTextField = new JTextField();
        subjectTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        subjectTextField.setOpaque(false);
        subjectTextField.setBounds(180, 214, 650, 40);
        subjectTextField.setFont(myFont);
        
        // Email Text Area
        textAreaField = new JTextArea(15, 70);
        textAreaField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textAreaField.setOpaque(false);
        textAreaField.setBounds(40, 305, 787, 313);
        textAreaField.setFont(myFont);
        textAreaField.setLineWrap(true);
        
        // Add everything here
        add(myDragLabel);
        add(myExitButton);
        add(myMinimizeButton);
        add(toTextField);
        add(subjectTextField);
        add(textAreaField);
        add(mySendButton);
        add(mySetupButton);
        
    } // End of MAIN Frame

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Action used for exiting MAIN Window
    public class EndingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    // Action used for minimizing MAIN Window
    public class MinimizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setState(Frame.ICONIFIED);
        }
    }
    
    // Action used for minimizing SETUP Window
    public class MinimizeSetupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setupFrame.setState(Frame.ICONIFIED);
        }
    }
    
    // Action used for making SETUP Window invisible
    public class InvisibleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setupFrame.setVisible(false);
        }
    }
    
    // Action used for opening the SETUP Window
    public class OpenSetupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Open SETUP Menu visible
            setupFrame.setVisible(true);
            
            // Exit Button (SETUP Menu)
            myExitButton = new JButton();
            myExitButton.setBounds(674, 0, 46, 35);
            myExitButton.setOpaque(false);
            myExitButton.setContentAreaFilled(false);
            myExitButton.setBorderPainted(false);
            myExitButton.addActionListener(new InvisibleListener());
            
            // Minimize Button (SETUP Menu)
            myMinimizeButton = new JButton();
            myMinimizeButton.setBounds(628, 0, 46, 35);
            myMinimizeButton.setOpaque(false);
            myMinimizeButton.setContentAreaFilled(false);
            myMinimizeButton.setBorderPainted(false);
            myMinimizeButton.addActionListener(new MinimizeSetupListener());
            
            // Host
            hostTextField = new JTextField();
            hostTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            hostTextField.setOpaque(false);
            hostTextField.setBounds(148, 123, 546, 35);
            hostTextField.setFont(myFont);
            
            // Port Number (NOTE: Port Numbers may vary!)
            portTextField = new JTextField("587");
            portTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            portTextField.setOpaque(false);
            portTextField.setBounds(148, 178, 546, 35);
            portTextField.setFont(myFont);
            portTextField.setEditable(false);
            
            // Email Address
            emailTextField = new JTextField();
            emailTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            emailTextField.setOpaque(false);
            emailTextField.setBounds(148, 233, 546, 35);
            emailTextField.setFont(myFont);
            
            // Password (for Email Address)
            passwordTextField = new JPasswordField();
            passwordTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            passwordTextField.setOpaque(false);
            passwordTextField.setBounds(148, 286, 546, 35);
            passwordTextField.setFont(myFont);
            
            // Add everything to SETUP Windows Frame
            setupFrame.add(mySetupDragLabel);
            setupFrame.add(myExitButton);
            setupFrame.add(myMinimizeButton);
            setupFrame.add(hostTextField);
            setupFrame.add(portTextField);
            setupFrame.add(emailTextField);
            setupFrame.add(passwordTextField);
            
        }
        
    } // End of SETUP Frame
    
    // Send Email Action
    public class SendEmailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            Properties properties = new Properties();
            
            properties.put( "mail.smtp.ss.trust", hostTextField.getText() );
            properties.put( "mail.smtp.auth", true );
            properties.put( "mail.smtp.starttls.enable", true );
            properties.put( "mail.smtp.host", hostTextField.getText() );
            properties.put( "mail.smtp.ss.port", portTextField.getText() );
            
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                
                // Login to your Email
                protected javax.mail.PasswordAuthentication getPasswordAuthentication()
                {
                    return new javax.mail.PasswordAuthentication( emailTextField.getText(), passwordTextField.getText() );
                }
            });
            
            try
            {
                Message message = new MimeMessage(session);
                
                // Set a temp Email Address
                message.setFrom( new InternetAddress("no-reply@gmail.com") );
                
                // Set the person's (The person you want to email) Email Address
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toTextField.getText()));
                message.setSubject(subjectTextField.getText());
                message.setText(textAreaField.getText());
                
                // Send the message
                Transport.send(message);
                
                JOptionPane.showMessageDialog(successWindow, "Message successfully sent!");
                
            }
            catch (Exception exception)
            {
                JOptionPane.showMessageDialog(errorWindow, "Couldn't send message! Please try again!");
            }
            
        }
    }   
    
} // End of IMailUGUI Class
