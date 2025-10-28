package com.smartcalc.main;

import com.smartcalc.ui.SmartCalculatorUI;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SmartCalcLauncher extends JFrame {

    public SmartCalcLauncher() {
        // 🧠 Futuristic Frame Setup
        setTitle("SmartCalc");
        setSize(520, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 520, 400, 25, 25));

        // ⚡ Neon Background Panel
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(15, 15, 25),
                        getWidth(), getHeight(), new Color(10, 10, 50)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Neon border glow
                g2d.setColor(new Color(0, 255, 255, 80));
                g2d.setStroke(new BasicStroke(4f));
                g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 25, 25);
            }
        };
        background.setLayout(new BorderLayout());
        add(background);

        // 🌟 Title
        JLabel title = new JLabel("⚡ SmartCalc ⚡", SwingConstants.CENTER);
        title.setForeground(new Color(0, 255, 255));
        title.setFont(new Font("Orbitron", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        background.add(title, BorderLayout.NORTH);

        // 🎛 Button Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JButton calcBtn = createNeonButton("🧮  Launch SmartCalc");
        JButton aboutBtn = createNeonButton("ℹ️  About SmartCalc");
        JButton exitBtn = createNeonButton("❌  Exit");

        calcBtn.addActionListener(e -> new SmartCalculatorUI());
        aboutBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "🚀 SmartCalc v2.0\nDeveloped by Piyush Lomte\nJava Swing Neon Edition",
                        "About SmartCalc",
                        JOptionPane.INFORMATION_MESSAGE));
        exitBtn.addActionListener(e -> System.exit(0));

        centerPanel.add(calcBtn);
        centerPanel.add(aboutBtn);
        centerPanel.add(exitBtn);
        background.add(centerPanel, BorderLayout.CENTER);

        // 🧊 Footer
        JLabel footer = new JLabel("© 2025 SmartCalc Labs", SwingConstants.CENTER);
        footer.setForeground(new Color(120, 255, 255));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        background.add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 🎨 Neon Button Creator
    private JButton createNeonButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 30, 50));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover glow
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 60, 90));
                btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 3, true));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 30, 50));
                btn.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2, true));
            }
        });
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartCalcLauncher::new);
    }
}
