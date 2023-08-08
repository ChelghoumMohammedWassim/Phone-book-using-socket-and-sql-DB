package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class UserFrame extends JFrame implements ActionListener {
    Socket socket;
    JButton button = new JButton();

    JButton clearButton = new JButton();
    JTextField input = new JTextField();
    ChatTable chatTable = new ChatTable();
    JTable table = new JTable(chatTable);

    public UserFrame() {
        try {
            this.socket = new Socket("172.23.6.220", 9980);
            System.out.println("connected");
            chatTable.addCommand("   Console:     Server Connected");
        } catch (IOException e) {
            chatTable.addCommand("   Console:     Server filed to Connect");
        }
        JPanel frame = new JPanel();
        frame.setSize(new Dimension(900, 500));
        setSize(new Dimension(600, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(61, 61, 61));
        setTitle("Machine Annuaire");

        JPanel buttomPanel = new JPanel();
        buttomPanel.setSize(new Dimension(600, 60));
        buttomPanel.setLayout(new BorderLayout());
        input.setPreferredSize(new Dimension(330, 35));
        Action enterbutton = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addCommand();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 5));
        buttons.setMinimumSize(new Dimension(250, 30));
        input.addActionListener(enterbutton);
        clearButton.setText("Reload");
        clearButton.setSize(new Dimension(125, 30));
        clearButton.addActionListener(this);
        button.setText("Confirm");
        button.setSize(new Dimension(125, 30));
        button.addActionListener(this);
        buttons.add(button);
        buttons.add(clearButton);
        buttomPanel.add(input, BorderLayout.CENTER);
        buttomPanel.add(buttons, BorderLayout.EAST);
        setLayout(new BorderLayout());
        frame.setLayout(new BorderLayout());
        frame.add(buttomPanel, BorderLayout.SOUTH);
        frame.setBackground(new Color(73, 73, 74));
        table.setRowHeight(table.getRowHeight() + 10);
        table.setShowGrid(false);
        table.setFont(new Font("Monospace", 0, 14));
        table.setSelectionBackground(new Color(0f, 0f, 0f, 0f));
        table.setTableHeader(null);
        table.setBackground(new Color(238, 238, 238, 255));
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        add(frame);
        pack();
        input.requestFocusInWindow();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                addCommand();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == clearButton) {
            chatTable.clear();
            table.repaint();
            try {
                this.socket = new Socket("localhost", 8080);
                chatTable.addCommand("   Console:     Server Connected");
            } catch (IOException ex) {
                chatTable.addCommand("   Console:     Server filed to Connect");
            }
            input.setEnabled(true);
            button.setEnabled(true);

        }
    }

    void addCommand() throws IOException {
        String command = input.getText().trim();
        if (!command.isEmpty()) {
            chatTable.addCommand("  User :     " + command);
            input.setText("");
            PrintWriter out;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException ex) {
                chatTable.addCommand("   Console:     Connection lost");
                throw new RuntimeException(ex);
            }
            out.println(command);
            //read from server
            BufferedReader buffer = null;
            try {
                buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String line = null;
            try {
                line = buffer.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            chatTable.addCommand("  Server :     " + line);
            table.changeSelection(table.getRowCount() - 1, 0, false, false);
            if(line.toUpperCase().equals("STOP")){
                input.setEnabled(false);
                button.setEnabled(false);
            }

            if (command.toUpperCase().equals("/QUIT")){
                socket.close();
            }

        }
    }
}
