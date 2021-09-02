import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FileDialogTest extends JFrame implements ActionListener {
    Button btnLoad, btnInitial;

    public FileDialogTest() {
        setTitle("FileDiaog");
        InitLayout();
        setBounds(300, 300, 300, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void InitLayout() {
        setLayout(new GridLayout(2, 1));
        btnInitial = new Button("처음 파일 넣기");
        btnLoad = new Button("만들고 싶은 링크 정보 파일");
        btnLoad.addActionListener(this);
        btnInitial.addActionListener(this);
        add(btnLoad);
        add(btnInitial);
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
            new ManageExcel(file, false);
        } else if (e.getSource().equals(btnInitial)) {
            // 1. FileDialog를 열어 불러올 파일 지정
            FileDialog dialog = new FileDialog(this, "열기", FileDialog.LOAD);
            dialog.setDirectory(".");
            dialog.setVisible(true);

            // 2. FileDialog가 비정상 종료
            if(dialog.getFile() == null) return;

            String file = dialog.getDirectory() + dialog.getFile();

            new ManageExcel(file, true);
        }
    }
}
