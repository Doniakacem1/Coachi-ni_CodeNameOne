/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.codename1.uikit.materialscreens;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Compte;
import com.mycompany.myapp.entities.EncapsulationCompte;
import com.mycompany.myapp.guiDonia.AfficherOffre;
import com.mycompany.myapp.services.ServiceCompte;

/**
 * The Login form
 *
 * @author Shai Almog
 */
public class LoginForm extends Form {
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        TextField login = new TextField("", "username", 20, TextField.USERNAME) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        Label erreur= new Label("The password or the username is incorrect");
        erreur.setVisible(false);
        erreur.getAllStyles().setFgColor(0xff0000);
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        erreur.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
       
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               
               Compte result =ServiceCompte.getInstance().login(login.getText(), ServiceCompte.getInstance().encrypt(password.getText())); 
               
                
               erreur.setVisible(true);
               if(result.getType().equals("client"))
               {
                   EncapsulationCompte.setCompte(result);
                    new AfficherOffre(theme).show();
                    
                    
               }
               else if(result.getType().equals("coach"))
               {
                  EncapsulationCompte.setCompte(result);
                   new AfficherOffre(theme).show();
               }
               
                   
            }
        });
        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new signInClient(theme).show();
            }
        });
        
        Button createCoach = new Button("BECOME A COACH!");
        createCoach.setUIID("CreateNewAccountButton");
        createCoach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new signInCoach(theme).show();
            }
        });
        
        Button forgotPassword = new Button("forgot your password ?");
        forgotPassword.setUIID("CreateNewAccountButton");
        forgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new RetrievePassword(theme).show();
            }
        });
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                
                
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                erreur,
                loginButton,
                createNewAccount,
                createCoach,
                forgotPassword
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
