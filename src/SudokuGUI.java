import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class SudokuGUI extends JFrame {
    private JPanel buttonSelectionPanel;
    private Point p1 = new Point();

    // метод для создания первого frame, где будет выбираться сложность игры
    public void start() {
        JFrame frame = new JFrame("Игра Судоку");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Игра Судоку");
        Font font = new Font("Times New Roman", Font.BOLD, 30);
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JButton button1 = new JButton("Легкое");
        JButton button2 = new JButton("Среднее");
        JButton button3 = new JButton("Сложное");
        button1.setBackground(Color.CYAN);
        button2.setBackground(Color.CYAN);
        button3.setBackground(Color.CYAN);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Пространство между компонентами

        panel.add(button1);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(button2);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(button3);

        frame.add(panel);
        frame.pack();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Закрытие текущего окна
                createNewGameWindow(5);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Закрытие текущего окна
                createNewGameWindow(50);
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Закрытие текущего окна
                createNewGameWindow(60);
            }
        });
    }

    //метод для создания основного окна,где будет заполнятся матрица с (клавиатуры)
    private void createNewGameWindow(int k) {
        Font FONT = new Font("Times New Roman", Font.CENTER_BASELINE, 20);
        final int dimension = 9;
        final JTextField[][] grid = new JTextField[dimension][dimension];
        final Map<JTextField, Point> mapFieldToCoordinates = new HashMap<>();
        final Map<JButton, Point> coordinatesButtton = new HashMap<>();
        final JPanel gridPanel = new JPanel();
        final JButton[][] sudButtons = new JButton[3][3];
        JFrame frame = new JFrame("Судоку");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        gridPanel.setPreferredSize(new Dimension(300, 200));
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = new JTextField();
                mapFieldToCoordinates.put(field, new Point(x, y));
                grid[y][x] = field;
            }
        }
        SudokuGenerator g = new SudokuGenerator();
        JTextField[][] generatedSudoku = g.generateRandomSudoku(grid, k);
        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                grid[y][x].setText(generatedSudoku[y][x].getText());
            }
        }
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(30, 30);
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = grid[y][x];
                field.setBorder(border);
                field.setFont(FONT);
                field.setPreferredSize(fieldDimension);
            }
        }
        int miniDimension = (int) Math.sqrt(dimension);
        gridPanel.setLayout(new GridLayout(miniDimension, miniDimension));
        JPanel[][] minisquarePanels = new JPanel[miniDimension][miniDimension];
        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = 0; y < miniDimension; ++y) {
            for (int x = 0; x < miniDimension; ++x) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(miniDimension, miniDimension));
                panel.setBorder(minisquareBorder);
                minisquarePanels[y][x] = panel;
                gridPanel.add(panel);
            }
        }
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                int minisquareX = x / miniDimension;
                int minisquareY = y / miniDimension;
                minisquarePanels[minisquareY][minisquareX].add(grid[y][x]);
            }
        }
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel buttonPanel = new JPanel();
        int i = 1;
        buttonPanel.setLayout(new GridLayout(3, 1));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                JButton but = new JButton(String.valueOf(i));
                coordinatesButtton.put(but, new Point(x, y));
                sudButtons[y][x] = but;
                buttonPanel.add(sudButtons[y][x]);
                i++;
            }
        }
        JButton button1 = new JButton("Новая игра");
        JButton button2 = new JButton("Подсказка");
        JButton button3 = new JButton("Проверка");
        button1.setBackground(Color.CYAN);
        button2.setBackground(Color.CYAN);
        button3.setBackground(Color.CYAN);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        Box topPanel = Box.createHorizontalBox();
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(button1);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(button2);
        topPanel.add(Box.createHorizontalGlue());
        Box bottomPanel = Box.createHorizontalBox();
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(button3);
        bottomPanel.add(Box.createHorizontalGlue());
        mainPanel.add(topPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(bottomPanel);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(gridPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(mainPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.pack();
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        getAllActiveTextFields(grid);
        JTextField field3 = new JTextField();
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudButtons[i][j].addActionListener(new ActionListener() { //Обрабока действий кнопок
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();
                        getAllActiveTextFields(grid);//Получение координат курсора
                        String text = clickedButton.getText(); // Получение значения кнопки
                        grid[p1.x][p1.y].setText(text);
                    }
                });
            }
        }

        button1.addActionListener(new ActionListener() { // Обработка действия конпеи "Новая игра"
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Закрытие текущего окна
                start();//Запуск игры новой игры
            }
        });
        JTextField field2 = new JTextField();
        button2.addActionListener(new ActionListener() {// Обработка действия кнопки "Подсказка"
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllActiveTextFields(grid);//получение координат курсора
                String s = g.numberHint(grid, p1.x, p1.y);// Получение числа для подсказки
                grid[p1.x][p1.y].setText(s);
                mapFieldToCoordinates.put(field2, new Point(p1.x, p1.y));
            }
        });


        button3.addActionListener(new ActionListener() {//Обработка действия кнопки "Проверка"
            public void actionPerformed(ActionEvent e) {
                Point p;
                if (g.checkSudoku(grid)) { // Проверка заполнения
                    createNewGame(); // Открытия окна с результатом
                } else {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            p = new Point(g.coorJTextF(grid, i, j)); // Получение координат где ошибка
                            grid[(int) p.getX()][(int) p.getY()].setBackground(Color.RED); // Выделение ячейк где ошибки
                        }
                    }
                }
            }
        });
    }

    // Метод для получения координат курсора
    public void getAllActiveTextFields(JTextField[][] grid) {
        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                int finalY = y;
                int finalX = x;
                grid[y][x].addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        System.out.println("Фокус установлен на текстовом поле.");
                        p1 = new Point(finalY, finalX);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        System.out.println("Фокус убран с текстового поля.");
                    }
                });
            }
        }
    }


    // Создание окна с выводом результата игры
    private void createNewGame() {
        Font FONT = new Font("Times New Roman", Font.CENTER_BASELINE, 20);
        JFrame frame = new JFrame("Результат");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titleLabel = new JLabel("Игра успешно решена!");
        Font font = new Font("Times New Roman", Font.BOLD, 20);
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(titleLabel, BorderLayout.CENTER);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}


