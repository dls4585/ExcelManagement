import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class FileDialogTest extends JFrame implements ActionListener {
    JButton btnLoad, btnInitial, btnText;
    JPanel buttons, textPanel;
    JTextArea textArea;
    public FileDialogTest() {
        setTitle("FileDiaog");
        InitLayout();

    }

    public void InitLayout() {
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttons = new JPanel();
        buttons.setLayout(null);
        buttons.setBounds(0, 0, 600, 100);

        btnLoad = new JButton("만들고 싶은 링크 정보 파일");
        btnLoad.addActionListener(this);
        btnLoad.setBounds(300, 25, 250, 50);
        buttons.add(btnLoad);

        btnInitial = new JButton("처음 파일 넣기");
        btnInitial.addActionListener(this);
        btnInitial.setBounds(50, 25, 250, 50);
        buttons.add(btnInitial);

        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBounds(0, 100, 600, 300);
        textArea = new JTextArea();
        textArea.setBounds(50, 100, 500, 200);
        textPanel.add(textArea);

        btnText = new JButton("확인");
        btnText.addActionListener(this);
        btnText.setBounds(475, 310, 75, 50);
        textPanel.add(btnText);

        add(buttons);
        add(textPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnLoad)) {
            // 1. FileDialog를 열어 불러올 파일 지정
            FileDialog dialog = new FileDialog(this, "열기", FileDialog.LOAD);
            dialog.setDirectory(".");   // .은 지금폴더
            dialog.setVisible(true);

            // 2. FileDialog가 비정상 종료되었을때
            if (dialog.getFile() == null) return;

            // 3. 불러올 파일의 경로명 저장
            String file = dialog.getDirectory() + dialog.getFile();
            new ExcelManager(file, false);
        } else if (e.getSource().equals(btnInitial)) {
            // 1. FileDialog를 열어 불러올 파일 지정
            FileDialog dialog = new FileDialog(this, "열기", FileDialog.LOAD);
            dialog.setDirectory(".");
            dialog.setVisible(true);

            // 2. FileDialog가 비정상 종료
            if(dialog.getFile() == null) return;

            String file = dialog.getDirectory() + dialog.getFile();

            new ExcelManager(file, true);
        } else if (e.getSource().equals(btnText)) {
            String text = textArea.getText();
            String[] lines = text.split("\n");
            System.out.println(Arrays.toString(lines));
        }
    }
}
