/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.CredentialManager;

/**
 *
 * @author VITHANH
 */
import java.util.prefs.Preferences;

public class CredentialManager {
    private static final Preferences prefs = Preferences.userRoot().node("login_app");

    public static void saveLogin(String username, String password) {
        prefs.put("username", username);
        prefs.put("password", password); // lưu plaintext tạm, nên mã hóa nếu cần
    }

    public static String[] loadLogin() {
        String username = prefs.get("username", "");
        String password = prefs.get("password", "");
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }
        return new String[]{username, password};
    }

    public static void clearLogin() {
        prefs.remove("username");
        prefs.remove("password");
    }
}

