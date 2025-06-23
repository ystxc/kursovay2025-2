package org.example;

import org.example.views.CompanyView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CompanyView().setVisible(true);
        });
    }
}
