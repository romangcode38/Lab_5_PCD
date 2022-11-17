import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class MyFrame extends JFrame{
    static int Max_Height = 600;
    static int Max_Width = 800;
    static JLabel label;
    static JLabel label1;
    static ImageIcon icon;
    static Button button1, button2, button3;
    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(Max_Width, Max_Height));
        this.getContentPane().setBackground(Color.darkGray);
        this.pack();
        this.setLayout(null);
        label = new JLabel();
        label1 = new JLabel();
        button1 = new Button("Start");
        button1.setBounds(10, (Max_Height-80),80, 30 );
        button1.setBackground(Color.GREEN);
        button2 = new Button("Rand_Pos");
        button2.setBounds(350, (Max_Height-80),100, 30 );
        button2.setBackground(Color.ORANGE);
        button3 = new Button("Restart");
        button3.setBounds((Max_Width-100), (Max_Height-80),80, 30 );
        button3.setBackground(Color.red);
        this.add(label);
        this.add(label1);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.setVisible(true);
    }
    public void setOrigin_Imag(int pos_x_Img, int pos_y_Img){
        icon = new ImageIcon("C:\\Users\\romag\\IdeaProjects\\Lab_5_PCD\\Lab_5_PCD\\src\\main\\java\\M.png");
        label.setBounds(pos_x_Img, pos_y_Img, 140, 140);
        label.setIcon(icon);
    }
    public void setOrigin_label(int pos_x_label, int pos_y_label) {
        label1.setBounds(pos_x_label, pos_y_label, 500, 15);
        label1.setForeground(Color.LIGHT_GRAY);
        label1.setText("Departamentul de Informatică și Ingineria Sistemelor;");
    }
}
class Control extends Thread {
    private final MyFrame myframe;
    static int position_x_img;
    static int position_y_img;
    static int position_x_label;
    static int position_y_label;
    Control(MyFrame myFrame1) {
        this.myframe = myFrame1;
    }
    int size_x = MyFrame.Max_Width;
    Thread Image = new Thread() {
        public void run() {
            boolean vall = true;
            try {
                while (vall) {
                    for (int i = 0; i <= size_x - 140; i = i + 10) {
                        position_x_img = position_x_img + 10;
                        myframe.setOrigin_Imag(position_x_img, position_y_img);
                        Thread.sleep(25);
                        if (position_x_img == size_x - 140) {
                            break;
                        }
                    }
                    vall = false;
                    while (!vall) {
                        if (position_x_img >= size_x - 140) {
                            for (int j = size_x - 140; j >= 10; j = j - 10) {
                                position_x_img = position_x_img - 10;
                                myframe.setOrigin_Imag(position_x_img, position_y_img);
                                Thread.sleep(40);
                            }
                        }
                        vall = true;
                    }
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };
    Thread Writing = new Thread() {
        public void run() {
            boolean vall = true;
            try {
                while (vall) {
                    for (int i = 0; i <= size_x - 320; i = i + 10) {
                        position_x_label = position_x_label + 10;
                        myframe.setOrigin_label(position_x_label, position_y_label);
                        Thread.sleep(50);
                        if (position_x_label == size_x - 320) {
                            break;
                        }
                    }
                    vall = false;
                    while (!vall) {
                        if (position_x_label >= size_x - 320) {
                            for (int j = size_x - 320; j >= 10; j = j - 10) {
                                position_x_label = position_x_label - 10;
                                myframe.setOrigin_label(position_x_label, position_y_label);
                                Thread.sleep(30);
                            }
                        }
                        vall = true;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };
    Thread Rand_Pos = new Thread() {
        public void run() {
            int size_y = MyFrame.Max_Height;
            int max_y = size_y - 140, min_y = 100;
            int range = max_y - min_y + 1;
            int rand_img, rand_label;
            while (true){
                rand_img = (int) (Math.random() * range);
                position_y_img = rand_img;
                myframe.setOrigin_Imag(position_x_img, position_y_img);
                rand_label = (int) (Math.random() * range);
                position_y_label = rand_label;
                myframe.setOrigin_label(position_x_label, position_y_label);
                System.out.println(rand_img + "  " + rand_label);
                 Thread.currentThread().suspend();
            }
        }
    };
    Thread Initial_coord = new Thread(){
        public void run(){
            while(true) {
                int coord_x = MyFrame.Max_Width - MyFrame.Max_Width;
                int coord_y = MyFrame.Max_Height - MyFrame.Max_Height;
                myframe.setOrigin_Imag(coord_x, coord_y);
                myframe.setOrigin_label(coord_x, coord_y);
                Thread.currentThread().suspend();
            }
        }
    };
}
public class Lab_5_PCD {
    public static void main(String[] str) {
        new MyFrame();
        Thread th1 = new Control(new MyFrame()).Image;
        Thread th2 = new Control(new MyFrame()).Writing;
        Thread th3 = new Control(new MyFrame()).Rand_Pos;
        Thread th4 = new Control(new MyFrame()).Initial_coord;
        th1.start();
        th1.suspend();
        th2.start();
        th2.suspend();
        th3.start();
        th3.resume();
        th4.start();
        th4.suspend();
        MyFrame.button1.setActionCommand("Start");
        MyFrame.button1.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Start") {
                    th1.resume();
                    th2.resume();
                    if (th1.isAlive() && th2.isAlive()) {
                        th1.resume();
                        th2.resume();
                        th3.suspend();
                    }
                }
            }
        });
        MyFrame.button2.setActionCommand("Rand_Pos");
        MyFrame.button2.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Rand_Pos") {
                    th3.resume();
                    try {
                        Thread.currentThread().sleep(100);
                        if (th1.isAlive() && th2.isAlive()) {
                            th1.suspend();
                            th2.suspend();
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        MyFrame.button3.setActionCommand("Restart");
        MyFrame.button3.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Restart") {
                    th4.resume();
                    if(th4.isAlive()){
                       th1.suspend();
                       th2.suspend();
                       th3.suspend();
                    }
                }
            }
        });
    }
}
