package vocaboli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
class Gui extends JFrame implements ActionListener, KeyListener {

    private static final String TITOLO = "Vocabulary";

    private JPanel centro;
    private JLabel richiesta;
    private JTextField digitato;
    private JButton soluzioni;
    private JLabel corretto;
    private JButton verifica;
    private JLabel azzeccato;
    private JLabel riprova;

    private LinkedList<String> elencoVocaboli;

    private String soluzione;

    private int giuste;
    private int sbagliate;

    private boolean voto;
    
    private static final String CORRETTE = "CORRECT: ";
    private static final String SBAGLIATE = "WRONG: ";

    public Gui() {
        super(TITOLO);

        elencoVocaboli = new LinkedList<>();
        voto = true;

        setLayout(new BorderLayout());
        
        
        Image icon = Toolkit.getDefaultToolkit().getImage("iconaInglese.png");
        setIconImage(icon);

        //setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        centro = new JPanel();
        centro.setLayout(new BorderLayout());

        JPanel centroCentro = new JPanel();

        centro.add(centroCentro, BorderLayout.CENTER);

        digitato = new JTextField();
        digitato.setEditable(false);
        digitato.addKeyListener(this);
        digitato.setCaretColor(Color.red);
        digitato.getCaret().setVisible(true);
        digitato.setPreferredSize(new Dimension(200, 50));
        richiesta = new JLabel();

        centroCentro.add(digitato, BorderLayout.CENTER);
        centroCentro.add(richiesta, BorderLayout.CENTER);

        JPanel sotto = new JPanel();
        centro.add(sotto, BorderLayout.SOUTH);

        verifica = new JButton("Check");
        verifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlla();
            }

        });
        verifica.setEnabled(false);
        sotto.add(verifica, BorderLayout.SOUTH);

        soluzioni = new JButton("Solution");
        soluzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soluzione();
            }
        });
        soluzioni.setEnabled(false);
        sotto.add(soluzioni, BorderLayout.SOUTH);

        JPanel nord = new JPanel();
        nord.setLayout(new BorderLayout());
        //nord.setPreferredSize(new Dimension(1920, 500));
        centro.add(nord, BorderLayout.NORTH);

        JPanel nordOvest = new JPanel();
        nord.add(nordOvest, BorderLayout.WEST);
        
        azzeccato = new JLabel(CORRETTE);
        azzeccato.setForeground(Color.GREEN);
        nordOvest.add(azzeccato);
        
        riprova = new JLabel(SBAGLIATE);
        riprova.setForeground(Color.RED);
        nordOvest.add(riprova);

        JPanel nordSudCentro = new JPanel();    //CORRETTO O NO

        nord.add(nordSudCentro, BorderLayout.SOUTH);
        //nordSudCentro.setLayout(new BorderLayout());
        corretto = new JLabel();
        nordSudCentro.add(corretto);   //FINE CORRETTO O NO
        nordSudCentro.setPreferredSize(new Dimension(200, 25));

        add(centro, BorderLayout.CENTER);

        JPanel sottoo = new JPanel();   //VERIFICA SOLUZIONI E NUOVO
        JButton nuovoRandom = new JButton("New Word");
        nuovoRandom.addActionListener(this);
        sottoo.add(nuovoRandom);
        add(sottoo, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(466, 215));

        pack();

        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 233), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - 107);

        setVisible(true);

        System.out.println("" + getWidth() + " " + getHeight());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!voto) {
            sbagliate++;
            azzeccato.setText(CORRETTE+ giuste);
            riprova.setText(SBAGLIATE + sbagliate);
        }
        if (elencoVocaboli.isEmpty()) {
            if ((giuste != 0 || sbagliate != 0)) {
                JOptionPane.showMessageDialog(this, CORRETTE + giuste + "\n"+SBAGLIATE + sbagliate);
            }
            try {
                FileReader fr = new FileReader("vocaboli.txt");
                BufferedReader br = new BufferedReader(fr);

                String temp = "";
                while ((temp = br.readLine()) != null) {
                    if (!temp.equals(" ") || temp.split("=").length == 2) {
                        elencoVocaboli.push(temp);
                    }
                }
                Collections.shuffle(elencoVocaboli);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
            giuste = 0;
            sbagliate = 0;
            
            azzeccato.setText(CORRETTE + giuste);
            riprova.setText(SBAGLIATE + sbagliate);
            
        }
        String pop = elencoVocaboli.removeFirst();
        String[] scomposto = pop.split("=");
        Random casuale = new Random();
        int caso = casuale.nextInt(2);

        if (caso == 0) {
            richiesta.setText(scomposto[0] + " [Inglese]");
            soluzione = scomposto[1];
        } else {
            richiesta.setText(scomposto[1] + " [Italiano]");
            soluzione = scomposto[0];
        }
        digitato.setEditable(true);
        digitato.setCaretColor(Color.BLACK);
        digitato.getCaret().setVisible(true);
        digitato.setText("");
        soluzioni.setEnabled(true);
        verifica.setEnabled(true);
        corretto.setText("");
        voto = false;
        repaint();
    }

    private void controlla() {
        if (digitato.getText().equals(soluzione)) {
            soluzioni.setEnabled(false);
            corretto.setForeground(Color.GREEN);
            corretto.setText("CORRECT");
            giuste++;
        } else {
            corretto.setText("WRONG");
            corretto.setForeground(Color.RED);
            sbagliate++;
        }
        voto = true;
        digitato.setEditable(false);
        verifica.setEnabled(false);
        azzeccato.setText(CORRETTE + giuste);
        riprova.setText(SBAGLIATE + sbagliate);
        repaint();
    }

    private void soluzione() {
        corretto.setForeground(Color.RED);
        corretto.setText(soluzione);
        digitato.setEditable(false);
        soluzioni.setEnabled(false);
        verifica.setEnabled(false);
        if (!voto) {
            sbagliate++;
            voto = true;
            azzeccato.setText(CORRETTE + giuste);
            riprova.setText(SBAGLIATE + sbagliate);
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (digitato.isEditable()) {
                    controlla();
                }
                break;
            case KeyEvent.VK_CONTROL:
                actionPerformed(null);
                break;
            case KeyEvent.VK_ESCAPE:
                if (soluzioni.isEnabled()) {
                    soluzione();
                }
                break;
        }
    }

}
