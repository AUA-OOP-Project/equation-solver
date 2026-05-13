package com.equationsolver.app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.equationsolver.model.Equation;
import com.equationsolver.model.EquationType;
import com.equationsolver.model.Solution;
import com.equationsolver.parser.EquationParser;
import com.equationsolver.parser.ParserFactory;
import com.equationsolver.solver.EquationSolver;
import com.equationsolver.solver.SolverFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class EquationSolverGUI {

    // Catppuccin Mocha palette
    private static final Color BG_BASE    = new Color(0x1E1E2E);
    private static final Color BG_MANTLE  = new Color(0x181825);
    private static final Color BG_SURFACE = new Color(0x313244);
    private static final Color LAVENDER   = new Color(0xB4BEFE);
    private static final Color MAUVE      = new Color(0xCBA6F7);
    private static final Color GREEN      = new Color(0xA6E3A1);
    private static final Color YELLOW     = new Color(0xF9E2AF);
    private static final Color RED        = new Color(0xF38BA8);
    private static final Color CYAN       = new Color(0x89DCEB);
    private static final Color TEXT       = new Color(0xCDD6F4);
    private static final Color SUBTEXT    = new Color(0xBAC2DE);
    private static final Color OVERLAY    = new Color(0x6C7086);

    // Each entry: { display name, example equation }
    private static final String[][] TYPES = {
        { "Linear",         "2x + 4 = 0"                },
        { "Quadratic",      "x^2 - 5x + 6 = 0"          },
        { "Cubic",          "x^3 - 6x^2 + 11x - 6 = 0"  },
        { "Quartic",        "x^4 - 5x^2 + 4 = 0"        },
        { "Exponential",    "2^x = 8"                    },
        { "Logarithmic",    "log2(x) = 3"                },
        { "Rational",       "(x^2 - 1) / (x - 2) = 0"   },
        { "Absolute Value", "|2x + 3| = 7"               },
        { "Sine",           "sin(x) = 0.5"               },
        { "Cosine",         "cos(x) = 1"                 },
        { "Tangent",        "tan(x) = 2"                 },
    };

    private JTextField  inputField;
    private JLabel      typeBadge;
    private JEditorPane outputPane;
    private JButton     copyBtn;
    private String      lastSolutionText = "";

    private DefaultListModel<String> historyModel;
    private JList<String>            historyList;

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        UIManager.put("@accentColor",       "#CBA6F7");
        UIManager.put("Component.arc",       8);
        UIManager.put("Button.arc",          8);
        UIManager.put("TextComponent.arc",   8);
        UIManager.put("ScrollBar.width",     6);
        UIManager.put("ScrollBar.thumbArc",  999);
        SwingUtilities.invokeLater(() -> new EquationSolverGUI().show());
    }

    private void show() {
        JFrame frame = new JFrame("Equation Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1060, 680);
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null);
        frame.setBackground(BG_BASE);
        frame.setContentPane(buildRoot());
        frame.setVisible(true);
        inputField.requestFocusInWindow();
    }

    // ── Root ──────────────────────────────────────────────────────────────────

    private JPanel buildRoot() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_BASE);
        root.add(buildHeader(),    BorderLayout.NORTH);
        root.add(buildBody(),      BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);
        return root;
    }

    // ── Header ─────────────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_BASE);
        wrapper.add(buildAccentStrip(), BorderLayout.NORTH);
        wrapper.add(buildInputRow(),    BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildAccentStrip() {
        JPanel strip = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setPaint(new GradientPaint(0, 0, new Color(0x7C3AED), getWidth(), 0, new Color(0x06B6D4)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        strip.setPreferredSize(new Dimension(0, 3));
        return strip;
    }

    private JPanel buildInputRow() {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(BG_BASE);
        row.setBorder(new EmptyBorder(26, 24, 24, 24));

        JLabel title = new JLabel("∑  Equation Solver");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        title.setForeground(LAVENDER);
        title.setBorder(new EmptyBorder(0, 0, 0, 20));

        inputField = new JTextField();
        inputField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
        inputField.setForeground(TEXT);
        inputField.setBackground(BG_SURFACE);
        inputField.setCaretColor(MAUVE);
        inputField.putClientProperty("JTextField.placeholderText",
                "Select a type on the left, or type an equation here");
        inputField.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)  solve();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) clearInput();
            }
        });
        // live type detection
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { updateBadge(); }
            public void removeUpdate(DocumentEvent e)  { updateBadge(); }
            public void changedUpdate(DocumentEvent e) { updateBadge(); }
        });

        typeBadge = buildTypeBadge();

        // wrap input + badge together so badge sits flush to the right of the field
        JPanel inputWrapper = new JPanel(new BorderLayout(8, 0));
        inputWrapper.setBackground(BG_BASE);
        inputWrapper.add(inputField, BorderLayout.CENTER);
        inputWrapper.add(typeBadge,  BorderLayout.EAST);

        row.add(title,          BorderLayout.WEST);
        row.add(inputWrapper,   BorderLayout.CENTER);
        row.add(buildSolveButton(), BorderLayout.EAST);
        return row;
    }

    private JLabel buildTypeBadge() {
        JLabel badge = new JLabel("", SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                if (getText().isBlank()) return;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0x45475A));
                g2.fillRoundRect(0, (getHeight() - 24) / 2, getWidth(), 24, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        badge.setForeground(MAUVE);
        badge.setPreferredSize(new Dimension(130, 36));
        badge.setVisible(false);
        return badge;
    }

    private void updateBadge() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            typeBadge.setVisible(false);
            return;
        }
        try {
            EquationType type = ParserFactory.detectType(text);
            typeBadge.setText(typeName(type));
            typeBadge.setVisible(true);
        } catch (Exception ex) {
            typeBadge.setVisible(false);
        }
    }

    private String typeName(EquationType type) {
        return switch (type) {
            case LINEAR        -> "Linear";
            case QUADRATIC     -> "Quadratic";
            case CUBIC         -> "Cubic";
            case QUARTIC       -> "Quartic";
            case EXPONENTIAL   -> "Exponential";
            case LOGARITHMIC   -> "Logarithmic";
            case RATIONAL      -> "Rational";
            case ABSOLUTE_VALUE -> "Absolute Value";
            case SINE          -> "Sine";
            case COSINE        -> "Cosine";
            case TANGENT       -> "Tangent";
            default            -> type.name();
        };
    }

    private void clearInput() {
        inputField.setText("");
        typeBadge.setVisible(false);
    }

    private JButton buildSolveButton() {
        JButton btn = new JButton("Solve  →") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = getModel().isPressed()
                    ? new GradientPaint(0, 0, new Color(0x6D28D9), getWidth(), 0, new Color(0x0891B2))
                    : new GradientPaint(0, 0, new Color(0x7C3AED), getWidth(), 0, new Color(0x06B6D4));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(130, 44));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> solve());
        return btn;
    }

    // ── Body ──────────────────────────────────────────────────────────────────

    private JSplitPane buildBody() {
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(),
                buildOutputPanel()
        );
        split.setDividerLocation(240);
        split.setResizeWeight(0.0);
        split.setBorder(null);
        split.setBackground(BG_BASE);
        return split;
    }

    // ── Left panel ────────────────────────────────────────────────────────────

    private JSplitPane buildLeftPanel() {
        JSplitPane vertical = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                buildTypesPanel(),
                buildHistoryPanel()
        );
        vertical.setDividerLocation(390);
        vertical.setResizeWeight(0.7);
        vertical.setBorder(null);
        vertical.setBackground(BG_MANTLE);
        return vertical;
    }

    private JPanel buildTypesPanel() {
        DefaultListModel<Integer> model = new DefaultListModel<>();
        for (int i = 0; i < TYPES.length; i++) model.addElement(i);

        JList<Integer> typesList = new JList<>(model);
        typesList.setBackground(BG_MANTLE);
        typesList.setSelectionBackground(BG_SURFACE);
        typesList.setFixedCellHeight(52);
        typesList.setCellRenderer(typesCellRenderer());
        typesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = typesList.getSelectedIndex();
                if (idx >= 0) {
                    inputField.setText(TYPES[idx][1]);
                    inputField.requestFocusInWindow();
                    inputField.selectAll();
                    SwingUtilities.invokeLater(() -> typesList.clearSelection());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(typesList);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_MANTLE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MANTLE);
        panel.add(sectionHeader("Equation Types", null), BorderLayout.NORTH);
        panel.add(scroll,                                BorderLayout.CENTER);
        return panel;
    }

    private ListCellRenderer<Integer> typesCellRenderer() {
        return new ListCellRenderer<>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends Integer> list, Integer idx,
                    int row, boolean selected, boolean focused) {
                JLabel nameLbl = new JLabel(TYPES[idx][0]);
                nameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
                nameLbl.setForeground(selected ? MAUVE : LAVENDER);

                JLabel exLbl = new JLabel(TYPES[idx][1]);
                exLbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
                exLbl.setForeground(selected ? CYAN : OVERLAY);

                JPanel cell = new JPanel();
                cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
                cell.setBorder(new EmptyBorder(8, 14, 8, 14));
                cell.setBackground(selected ? BG_SURFACE : (row % 2 == 0 ? BG_MANTLE : BG_BASE));
                cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                cell.add(nameLbl);
                cell.add(exLbl);
                return cell;
            }
        };
    }

    private JPanel buildHistoryPanel() {
        historyModel = new DefaultListModel<>();
        historyList  = new JList<>(historyModel);
        historyList.setBackground(BG_MANTLE);
        historyList.setForeground(SUBTEXT);
        historyList.setSelectionBackground(BG_SURFACE);
        historyList.setSelectionForeground(MAUVE);
        historyList.setFixedCellHeight(34);
        historyList.setCellRenderer(historyCellRenderer());
        historyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = historyList.getSelectedValue();
                if (sel != null) {
                    inputField.setText(sel);
                    solve();
                    SwingUtilities.invokeLater(() -> historyList.clearSelection());
                }
            }
        });

        JButton clear = new JButton("Clear");
        clear.setFont(clear.getFont().deriveFont(11f));
        clear.setForeground(OVERLAY);
        clear.putClientProperty("JButton.buttonType", "borderless");
        clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clear.addActionListener(e -> historyModel.clear());

        JScrollPane scroll = new JScrollPane(historyList);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_MANTLE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MANTLE);
        panel.add(sectionHeader("History", clear), BorderLayout.NORTH);
        panel.add(scroll,                          BorderLayout.CENTER);
        return panel;
    }

    @SuppressWarnings("unchecked")
    private ListCellRenderer<String> historyCellRenderer() {
        return (ListCellRenderer<String>) (ListCellRenderer<?>) new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean sel, boolean focused) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, sel, focused);
                label.setBorder(new EmptyBorder(0, 14, 0, 14));
                label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                if (!sel) label.setBackground(index % 2 == 0 ? BG_MANTLE : BG_BASE);
                return label;
            }
        };
    }

    // ── Output panel ──────────────────────────────────────────────────────────

    private JPanel buildOutputPanel() {
        outputPane = new JEditorPane("text/html", welcomeHtml());
        outputPane.setEditable(false);
        outputPane.setBackground(BG_BASE);
        outputPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        JScrollPane scroll = new JScrollPane(outputPane);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_BASE);

        copyBtn = new JButton("⎘  Copy");
        copyBtn.setFont(copyBtn.getFont().deriveFont(11f));
        copyBtn.setForeground(OVERLAY);
        copyBtn.putClientProperty("JButton.buttonType", "borderless");
        copyBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copyBtn.setEnabled(false);
        copyBtn.addActionListener(e -> copyToClipboard());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_BASE);
        panel.add(sectionHeader("Solution", copyBtn), BorderLayout.NORTH);
        panel.add(scroll,                             BorderLayout.CENTER);
        return panel;
    }

    // ── Status bar ────────────────────────────────────────────────────────────

    private JLabel buildStatusBar() {
        JLabel bar = new JLabel(
            "  Click a type on the left to load an example · Press Enter to solve · ESC to clear"
        );
        bar.setFont(bar.getFont().deriveFont(11f));
        bar.setForeground(OVERLAY);
        bar.setBackground(BG_MANTLE);
        bar.setOpaque(true);
        bar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, BG_SURFACE),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return bar;
    }

    // ── Section header util ───────────────────────────────────────────────────

    private JPanel sectionHeader(String text, JComponent trailing) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setFont(label.getFont().deriveFont(Font.BOLD, 11f));
        label.setForeground(OVERLAY);
        label.setBorder(new EmptyBorder(10, 14, 10, 14));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_MANTLE);
        header.setBorder(new MatteBorder(0, 0, 1, 0, BG_SURFACE));
        header.add(label, BorderLayout.CENTER);
        if (trailing != null) header.add(trailing, BorderLayout.EAST);
        return header;
    }

    // ── Solve ─────────────────────────────────────────────────────────────────

    private void solve() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) return;

        try {
            EquationType   type   = ParserFactory.detectType(input);
            EquationParser parser = ParserFactory.getParser(type);
            Equation       eq     = parser.parse(input);
            EquationSolver solver = SolverFactory.getSolver(type);
            Solution       sol    = solver.solve(eq);

            outputPane.setText(solutionHtml(input, sol));
            outputPane.setCaretPosition(0);

            lastSolutionText = sol.displayWithSteps();
            copyBtn.setEnabled(true);

            if (historyModel.isEmpty() || !historyModel.get(0).equals(input)) {
                historyModel.add(0, input);
            }
        } catch (Exception e) {
            outputPane.setText(errorHtml(e.getMessage()));
            lastSolutionText = "";
            copyBtn.setEnabled(false);
        }
    }

    private void copyToClipboard() {
        Toolkit.getDefaultToolkit()
               .getSystemClipboard()
               .setContents(new StringSelection(lastSolutionText), null);

        copyBtn.setText("✓  Copied!");
        copyBtn.setForeground(GREEN);

        Timer reset = new Timer(1800, e -> {
            copyBtn.setText("⎘  Copy");
            copyBtn.setForeground(OVERLAY);
        });
        reset.setRepeats(false);
        reset.start();
    }

    // ── HTML rendering ────────────────────────────────────────────────────────

    private String solutionHtml(String input, Solution sol) {
        StringBuilder sb = new StringBuilder(htmlHead());

        sb.append("<p class='equation'>").append(esc(input)).append("</p>");

        List<String> steps = sol.getSteps();
        if (!steps.isEmpty()) {
            sb.append("<div class='steps-box'>");
            for (int i = 0; i < steps.size(); i++) {
                sb.append("<p class='step'>")
                  .append("<span class='step-num'>Step ").append(i + 1).append("</span>")
                  .append("&nbsp;&nbsp;").append(esc(steps.get(i)))
                  .append("</p>");
            }
            sb.append("</div>");
        }

        String cls = sol.isInfiniteSolutions() ? "result-inf"
                   : sol.hasSolution()         ? "result-ok"
                   :                             "result-none";
        sb.append("<p class='").append(cls).append("'>")
          .append(esc(sol.display())).append("</p>");

        sb.append("</body></html>");
        return sb.toString();
    }

    private String errorHtml(String msg) {
        return htmlHead() +
               "<p class='equation'>Error</p>" +
               "<p class='error-msg'>" + esc(msg) + "</p>" +
               "</body></html>";
    }

    private String welcomeHtml() {
        return htmlHead() +
               "<p class='welcome-title'>∑ Equation Solver</p>" +
               "<p class='welcome-sub'>Select an equation type on the left to load an example,<br/>" +
               "or type any equation and press <b>Enter</b>.</p>" +
               "</body></html>";
    }

    private String htmlHead() {
        return """
            <html><head><style>
              body {
                font-family: 'JetBrains Mono', 'Courier New', monospace;
                font-size: 14px; background-color: #1E1E2E; color: #CDD6F4;
                margin: 24px 28px; line-height: 1.8;
              }
              .equation {
                font-size: 18px; font-weight: bold; color: #B4BEFE;
                margin-bottom: 16px; padding-bottom: 10px;
                border-bottom: 1px solid #313244;
              }
              .steps-box {
                background: #181825; border-left: 3px solid #45475A;
                padding: 10px 16px; margin: 0 0 16px 0;
              }
              .step     { margin: 3px 0; font-size: 13px; color: #BAC2DE; }
              .step-num { color: #89DCEB; font-weight: bold; }
              .result-ok   { font-size: 20px; font-weight: bold; color: #A6E3A1; margin-top: 8px; }
              .result-none { font-size: 20px; font-weight: bold; color: #F38BA8; margin-top: 8px; }
              .result-inf  { font-size: 20px; font-weight: bold; color: #F9E2AF; margin-top: 8px; }
              .error-msg   { color: #F38BA8; font-size: 14px; margin-top: 8px; }
              .welcome-title { font-size: 22px; font-weight: bold; color: #CBA6F7; margin-bottom: 8px; }
              .welcome-sub   { color: #6C7086; font-size: 13px; line-height: 2; }
            </style></head><body>
            """;
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}