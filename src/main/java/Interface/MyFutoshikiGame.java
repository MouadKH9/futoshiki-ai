package Interface;
import Metier.*;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import javax.swing.*;

public class MyFutoshikiGame extends javax.swing.JFrame { 
    // --- Les variables ---
    private int dimension = 0; // Dimension choisie par le joueur (4, 5, 6, 7, 8, 9)
    private int dimGrille = 0; // Dimension de maGrille (= 2 * dimension - 1)
    private JTextField [][] maGrille; // La grille en totalité (valeurs et contraintes)
    private int [][] valGrille; // Les valeurs
    private char [][] contraintesHoriz; // Grille des contraintes horizontales : < et >
    private char [][] contraintesVert; // Grille des contraintes verticales : ^ et v
    private Graph G; // Le graphe du jeu 
    private String configPath = "/Users/macbookpro/Documents/futo_configs/"; // Le repertoire des configurations
    
    
    public MyFutoshikiGame() {
        initComponents();
    }
    
    public int [][] getValGrille()
    {
        return this.valGrille;
    }
    public char [][] getContraintesHoriz()
    {
        return this.contraintesHoriz;
    }
    public char [][] getContraintesVert()
    {
        return this.contraintesVert;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        dimensionGameCB = new javax.swing.JComboBox<>();
        Choose = new javax.swing.JButton();
        grille = new javax.swing.JPanel();
        solutionBtn = new javax.swing.JButton();
        verifyBtn = new javax.swing.JButton();
        calculLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dimensionGameCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4x4", "5x5", "6x6", "7x7", "8x8", "9x9" }));

        Choose.setText("Jouer");
        Choose.setActionCommand("Choose");
        Choose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout grilleLayout = new javax.swing.GroupLayout(grille);
        grille.setLayout(grilleLayout);
        grilleLayout.setHorizontalGroup(
            grilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        grilleLayout.setVerticalGroup(
            grilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 503, Short.MAX_VALUE)
        );

        solutionBtn.setText("Solution");
        solutionBtn.setEnabled(false);
        solutionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solutionBtnActionPerformed(evt);
            }
        });

        verifyBtn.setText("Vérifier ma solution");
        verifyBtn.setEnabled(false);
        verifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(calculLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(grille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                        .addComponent(dimensionGameCB, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Choose)
                        .addGap(78, 78, 78)
                        .addComponent(verifyBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(solutionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dimensionGameCB)
                    .addComponent(Choose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(solutionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verifyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(grille, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(calculLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseActionPerformed
        int index = dimensionGameCB.getSelectedIndex();
        dimension = index + 4;
        dimGrille = 2 * dimension - 1;
        maGrille = new JTextField [dimGrille][dimGrille];

        grille.removeAll();
        grille.repaint();
        
        grille.setBounds(grille.getX(), grille.getY(), 75 * dimension, 75 * dimension);
        
        for(int i = 0; i < dimGrille; i++) {
            for(int j = 0; j < dimGrille; j++) {
                maGrille[i][j] = new JTextField();
                maGrille[i][j].setHorizontalAlignment(JTextField.CENTER);
                grille.add(maGrille[i][j]);
                if(i % 2 == 0 && j % 2 == 0) {
                    maGrille[i][j].setBounds(j * 40, i * 40, 50, 50);
                    maGrille[i][j].setFont(new Font("Bookman Old Style", Font.BOLD, 12));
                } else {
                    maGrille[i][j].setBounds(j * 40 + 10, i * 40 + 10, 30, 30);
                    maGrille[i][j].setFont(new Font("", Font.PLAIN, 24));
                    maGrille[i][j].setEditable(false);
                    maGrille[i][j].setBorder(null);
                    maGrille[i][j].setBackground(new Color(0, 0, 0, 0));
                }
                if (i % 2 != 0 && j % 2 != 0)
                    maGrille[i][j].setVisible(false);
            }
        }
        // Remplissage des contraintes
        this.generateBoard("hard", dimension);
        
    }//GEN-LAST:event_ChooseActionPerformed
    // --- Méthode de récupération des valeurs et contraintes de la grille ---
    
    public boolean setElements(JTextField [][] grille)
    {
        // --- Initialisation des matrices des valeurs et des contraintes horizontales et verticales ---
        valGrille = new int[dimension][dimension];
        contraintesHoriz = new char[dimension][dimension - 1];
        contraintesVert = new char[dimension - 1][dimension];
        // --- On récupère et on vérifie ce qui est inséré dans la case ---
        for(int i = 0; i < dimGrille; i++) {
            for(int j = 0; j < dimGrille; j++) {
                if(!grille[i][j].getText().equals("")) {
                    if(i % 2 == 0 && j % 2 == 0) {
                        try {
                            int val = Integer.parseInt(grille[i][j].getText());
                            /*if(val < 1 || val > dimension)
                            {
                                JOptionPane.showMessageDialog(null, "La valeur " + val + " insérée dans la cellule [" + i + ", " + j + "] n'appartient pas au domaine des valeurs possibles !");
                                return false;
                            }*/
                            valGrille[i/2][j/2] = val;
                        }
                        catch(NumberFormatException ex) {
                            //JOptionPane.showMessageDialog(null, "Vous devez saisir un nombre dans la cellule [" + i + "," + j + "] !");
                            ex.printStackTrace();
                            return false;
                        }
                    }
                    // --- Les contraintes horizontales : < et > ---
                    else if(i % 2 == 0 && j % 2 != 0)
                    {
                        char contrHoriz = grille[i][j].getText().charAt(0);
                        /*if(!(contrHoriz == '<' || contrHoriz == '>'))
                        {
                            JOptionPane.showMessageDialog(null, "Le signe insérée dans la cellule [" + (i+1) + ", " + (j+1) + "] n'est pas une contrainte (doit être '<' ou '>') !");
                            return false;
                        }*/
                        contraintesHoriz[i/2][(j-1)/2] = contrHoriz;
                    }
                    // ---- Les contraintes verticales : ^ et v ---
                    else if(i % 2 != 0 && j % 2 == 0)
                    {
//                        int v = ((i-2)/2);
//                        int u = j/2;
//                        System.out.println("i = " + v);
//                        System.out.println("j = " + u);
//                        System.out.println(i + ", " + j + " -> " + ((i-2)/2) + ", " + j/2 + " : Verticale trouvé : " + grille[i][j].getText().charAt(0));
                        char contrVert = grille[i][j].getText().charAt(0);
                        /*if(!(contrVert == '^' || contrVert == 'v'))
                        {
                            JOptionPane.showMessageDialog(null, "Le signe insérée dans la cellule [" + (i+1) + ", " + (j+1) + "] n'est pas une contrainte (doit être '^' ou 'v') !");
                            return false;
                        }*/
                        contraintesVert[(i-1)/2][j/2] = contrVert;
                    }
                }
            }
        }
        return true;
    }
    // --- Méthode de vérification des contraintes ---
    private boolean verifyContraintes()
    {
        // --- Vérification des contraintes entre colonnes et lignes ---
        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j < dimension; j++)
            {
                int val = valGrille[i][j];
                if(val != 0) // Si la cellule contient un nombre
                {
                    // --- Vérification des valeurs insérées (ne doivent pas être < à 1 ou > dimension) ---
                    if(val < 1 || val > dimension)
                    {
                        JOptionPane.showMessageDialog(null, "La valeur " + val + " insérée dans la cellule [" + i + ", " + j + "] n'appartient pas au domaine des valeurs possibles !");
                        return false;
                    }
                    // --- Comparaison de la valeur avec la colonne ---
                    for(int row = 0; row < dimension; row++)
                    {
                        if(row != i) // Si on n'est pas dans la même ligne, pour ne pas comparer avec la même cellule
                        {
                            if(val == valGrille[row][j]) // Si on trouve une cellule contenant la même valeur
                            {
                                JOptionPane.showMessageDialog(null, "La valeur de la cellule [" + (i+1) + ", " + (j+1) + "] est doublé dans cette colonne (la cellule [" + (row+1) + ", " + (j+1) + "]) !");
                                return false;
                            }
                        }
                    }
                    // --- Comparaison de la valeur avec la ligne ---
                    for(int col = 0; col < dimension; col++)
                    {
                        if(col != j) // Si on n'est pas dans la même colonne, pour ne pas comparer avec la même cellule
                        {
                            if(val == valGrille[i][col]) // Si on trouve un cellule contenant la même valeur
                            {
                                JOptionPane.showMessageDialog(null, "La valeur de la cellule [" + (i+1) + ", " + (j+1) + "] est doublé dans cette ligne (la cellule [" + (i+1) + ", " + (col+1) + "]) !");
                                return false;
                            }
                        }
                    }
                    // --- Vérification des signes entre les cellules horrizontes : > et < ---
                    /* --- Comparaison de la cellule avec la cellule à gauche ---*/
                    if(j != 0) // Puisque la grille des contraintes horizontales est de nbre de colonne = dimension - 1, j doit être >= 1
                    {
                        if(contraintesHoriz[i][j - 1] != ' ') // Si la case contient un signe
                        {
                            if(valGrille[i][j - 1] != 0) // Si la case à gauche contient un nombre
                            {
                                switch(contraintesHoriz[i][j - 1]) // Deux cas : '<' et '>'
                                {
                                    case '>':
                                        if(valGrille[i][j - 1] < val) // Si la valeur est inférieure à la cellule à gauche
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est > à la valeur " + valGrille[i][j-1] + "\n (cellule [" + (i*2+1) + ", " + (j*2+2) + "] et [" + (i*2+1) + ", " + (j*2+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                    case '<':
                                        if(valGrille[i][j - 1] > val) // Si la valeur est supérieure à la cellule à gauche
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est < à la valeur " + valGrille[i][j-1] + "\n (cellule [" + (i*2+1) + ", " + (j*2+2) + "] et [" + (i*2+1) + ", " + (j*2+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    /* --- Comparaison de la cellule avec la cellule à droite ---*/
                    if(j != dimension - 1) // Puisque la grille des contraintes horizontales est de nbre de colonne = dimension - 1
                    {
                        if(contraintesHoriz[i][j] != ' ') // Si la case contient un signe
                        {
                            if(valGrille[i][j + 1] != 0) // Si la case à droite contient un nombre
                            {
                                switch(contraintesHoriz[i][j]) // Deux cas : '<' et '>'
                                {
                                    case '>':
                                        if(valGrille[i][j + 1] > val) // Si la valeur est inférieure à la cellule à droite
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est < à la valeur " + valGrille[i*2][j*2+1] + "\n (cellule [" + (i*2+1) + ", " + (j*2+2) + "] et [" + (i*2+1) + ", " + (j*2+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                    case '<':
                                        if(valGrille[i][j + 1] < val) // Si la valeur est supérieure à la cellule à droite
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est > à la valeur " + valGrille[i][j+1] + "\n (cellule [" + (i+1) + ", " + (j+1) + "] et [" + (i+1) + ", " + (j) + "]) !");
                                            return false;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    // --- Vérification des signes entre les cellules verticales : ⋀ et ⋁ ---
                    /* --- Comparaison de la cellule avec la cellule en haut ---*/
                    if(i != 0) // Puisque la grille des contraintes verticales est de nbre de ligne = dimension - 1, i doit être >= 1
                    {
                        if(contraintesVert[i - 1][j] != ' ') // Si la case contient un signe
                        {
                            if(valGrille[i - 1][j] != 0) // Si la case en haut contient un nombre
                            {
                                switch(contraintesVert[i - 1][j]) // Deux cas : '^' et 'v'
                                {
                                    case '^':
                                        if(valGrille[i - 1][j] > val) // Si la valeur est inférieure à la cellule en haut
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est < à la valeur " + valGrille[i - 1][j] + "\n (cellule [" + (i+1) + ", " + (j+1) + "] et [" + (i) + ", " + (j+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                    case 'v':
                                        if(valGrille[i - 1][j] < val) // Si la valeur est supérieure à la cellule en haut
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est > à la valeur " + valGrille[i - 1][j] + "\n (cellule [" + (i+1) + ", " + (j+1) + "] et [" + (i) + ", " + (j+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    /* --- Comparaison de la cellule avec la cellule en bas ---*/
                    if(i != dimension - 1) // Puisque la grille des contraintes verticales est de nbre de ligne = dimension - 1
                    {
                        if(contraintesVert[i][j] != ' ') // Si la case contient un signe
                        {
                            if(valGrille[i + 1][j] != 0) // Si la case en bas contient un nombre
                            {
                                switch(contraintesVert[i][j]) // Deux cas : '⋀' et '⋁'
                                {
                                    case '^':
                                        if(valGrille[i + 1][j] < val) // Si la valeur est supérieure à la cellule en bas
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est > à la valeur " + valGrille[i + 1][j] + "\n (cellule [" + (i+1) + ", " + (j+1) + "] et [" + (i) + ", " + (j+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                    case 'v':
                                        if(valGrille[i + 1][j] > val) // Si la valeur est inférieure à la cellule en bas
                                        {
                                            JOptionPane.showMessageDialog(null, "La valeur " + val + " est < à la valeur " + valGrille[i + 1][j] + "\n (cellule [" + (i+1) + ", " + (j+1) + "] et [" + (i) + ", " + (j+1) + "]) !");
                                            return false;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private void solutionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solutionBtnActionPerformed
        G = new Graph();
        // --- On appelle la fonction de récupération des éléments de la grille ---
        if(setElements(maGrille))
        {
            // --- Contraintes des lignes ---
            for(int i = 0; i < dimension; i++) // Ligne
            {
                for(int j = 0; j < dimension - 1; j++) // Colonne
                {
                    for(int k = j + 1; k < dimension; k++)
                    {
                        //System.out.println("i = " + i + ", j = " + j + ", k = " + k);
                        String val1 = "x" + i + "" + j;//String.valueOf(valGrille[i][j]);
                        String val2 = "x" + i + "" + k;//String.valueOf(valGrille[i][k]);
                        G.addEdge(val1, val2);
                    }
                }
            }
            // --- Contraintes des colonnes ---
            for(int i = 0; i < dimension; i++){ // Colonne
                for(int j = 0; j < dimension; j++){ // Ligne
                    for(int k = j + 1; k < dimension; k++){
                        //System.out.println("i = " + i + ", j = " + j + ", k = " + k);
                        String val1 = "x" + j + "" + i;//String.valueOf(valGrille[j][i]);
                        String val2 = "x" + k + "" + i;//String.valueOf(valGrille[k][i]);
                        G.addEdge(val2, val1);
                    }
                                        
                    if(i > 0 && (contraintesVert[i - 1][j] == '^' || contraintesVert[i - 1][j] == 'v')){
                        System.out.println("Found contraites vert1 at " + i + "," + j + " = " + contraintesVert[i - 1][j]);
                        
                        boolean cond = contraintesVert[i - 1][j] != '^';
                        
                        String val1 = cond ? "s" + j + "" + (i-1) : "s" + j + "" + i;
                        String val2 = cond ? "x" + j + "" + i : "x" + j + "" + (i-1);
                        
                        G.addEdge(val2, val1);
                        
                        val1 = val1.replace("s", "x");
                        val2 = val2.replace("x","i");
                        
                        G.addEdge(val1, val2);

                    }
                    
                    if(i < dimension - 1 && (contraintesVert[i][j] == '^' || contraintesVert[i][j] == 'v')){
                        System.out.println("Found contraites vert2 at " + i + "," + j + " = " + contraintesVert[i][j]);
                        
                        boolean cond = contraintesVert[i][j] != '^';
                        
                        String val1 = cond ? "s" + j + "" + i : "s" + j + "" + (i+1);
                        String val2 = cond ? "x" + j + "" + (i+1) : "x" + j + "" + i;
                        
                        G.addEdge(val2, val1);
                        
                        val1 = val1.replace("s", "x");
                        val2 = val2.replace("x","i");
                        
                        G.addEdge(val1, val2);
                    }
                    
                    if(j < dimension - 1 && (contraintesHoriz[i][j] == '<' || contraintesHoriz[i][j] == '>')){
                        System.out.println("Found contraites at " + i + "," + j + " = " + contraintesHoriz[i][j]);

                        boolean cond = contraintesHoriz[i][j] == '<';
                        
                        String val1 = cond ? "s" + (j+1) + "" + i : "s" + j + "" + i;
                        String val2 = cond ? "x" + j + "" + i : "x" + (j+1) + "" + i;
                        
                        G.addEdge(val2, val1);
                        
                        val1 = val1.replace("s", "x");
                        val2 = val2.replace("x","i");
                        
                        G.addEdge(val1, val2);
                    }
                    
                    if(j > 0 && (contraintesHoriz[i][j - 1] == '<' || contraintesHoriz[i][j - 1] == '>')){
                        System.out.println("Found horiz contraites at " + i + "," + j + " = " + contraintesHoriz[i][j - 1]);
                        
                        boolean cond = contraintesHoriz[i][j-1] == '<';
                        
                        String val1 = cond ? "s" + j + "" + i  : "s" + (j-1) + "" + i;
                        String val2 = cond ? "x" + (j-1) + "" + i : "x" + j + "" + i;
                        
                        G.addEdge(val2, val1);
                        
                        val1 = val1.replace("s", "x");
                        val2 = val2.replace("x","i");
                        
                        G.addEdge(val1, val2);
                    }
                }
            }
            
            // --- Table des domaines ---
            ST<String, SET<String>> domainTable = new ST<String, SET<String>>();
            // --- Remplissage des domaines ---
            Object[][] domains = new Object[dimension][dimension];
            // --- Initialisation des domaines ---
            for(int i = 0; i < dimension; i++) // Colonne
            {
                for(int j = 0; j < dimension; j++) // Ligne
                {
                    domains[i][j] = new SET<String>();
                }
            }
            // --- Attribuer les domaines aux valeurs de la grille (1) : sans considérer les contraintes de signes ---
            for(int i = 0; i < dimension; i++) // Colonne
            {
                for(int j = 0; j < dimension; j++) // Ligne
                {
                    if(valGrille[i][j] != 0)
                    {
                        ((SET<String>)domains[i][j]).add(new String(String.valueOf(valGrille[i][j]))); // Domaine avec une seule valeur (case remplie)
                    }
                    else
                    {
                        for(int k = 1; k <= dimension; k++)
                        {
                            ((SET<String>)domains[i][j]).add(""+k);
                        }
                    }
                }
            }
            // --- Ajout des domaines à la table ---
            for(int i = 0; i < dimension; i++) 
            {
                for(int j = 0; j < dimension; j++)
                {
                    domainTable.put("x"+j+""+i, ((SET<String>)domains[i][j]));
                }
            }
            // --- Affichage des domaines de chaque cellule ---
            System.out.println("\nLa table des domaines est : ");
            Set<String> keys = (Set<String>) domainTable.getST().keySet();
            for(String key : keys)
            {
                System.out.println("Le domaine de " + key + " est: " + domainTable.getST().get(key));
            }
            // --- Configuration initiale ---
            ST<String, String> config = new ST<String, String>();
            for(int i = 0; i < dimension; i++) // Ligne 
            {
                for(int j = 0; j < dimension; j++) // Colonne
                {
                    config.put("x"+i+""+j,""); // Variables non affectées
                }
            }
            calculLbl.setText("Calcul de la solution en cours ...");
            // --- Appliquer l'algorithme du Backtracking pour calculer le solution ---
            Backtracking backtracking = new Backtracking(this);
            System.out.println("Constraints: ");
            System.out.println(G);
            long start = System.currentTimeMillis();
            ST<String, String> result = backtracking.backtracking(config, domainTable, G);
            long end = System.currentTimeMillis();
            System.out.println("Result:");
            System.out.println(result);
            // --- Affichage de la solution ---
            { 
                System.out.println("");    
            for(int i = 0; i < dimension; i++) // Ligne
                for(int j = 0; j < dimension; j++) // Colonne
                {
                    if(i % 2 == 0 && j % 2 == 0) {
                        //System.out.println("2 pairs : i = " + i + " et j = " + j + "\n ---- val = " + config.get("x"+i+""+j));
                        maGrille[i*2][j*2].setText(config.get("x"+j+""+i));
                        maGrille[i*2][j*2].setEditable(false);
                    }
                    else if(i % 2 == 0 && j % 2 != 0) {
                        //System.out.println("1 pair, 1 impair : i = " + i + " et j = " + j + "\n ---- val = " + config.get("x"+i+""+j));
                        maGrille[i*2][j*2].setText(config.get("x"+j+""+i));
                        maGrille[i*2][j*2].setEditable(false);
                    }
                    else if (i % 2 != 0 && j % 2 == 0) {
                        //System.out.println("1 impair, 1 pair : i = " + i + " et j = " + j + "\n ---- val = " + config.get("x"+i+""+j));
                        maGrille[i*2][j*2].setText(config.get("x"+j+""+i));
                        maGrille[i*2][j*2].setEditable(false);
                    }
                    else if(i % 2 != 0 && j % 2 != 0) {
                        maGrille[i*2][j*2].setText(config.get("x"+j+""+i));
                        maGrille[i*2][j*2].setEditable(false);
                    }
                    System.out.print(i + "," + j + ": "+ config.get("x"+i+""+j)+" | ");
                }
            }
            grille.repaint();
            calculLbl.setText("Terminé: " + (end - start)/100 + "s");
        }
        else
            System.out.println("Erreur lors de la récupération des éléments de la grille !");
    }//GEN-LAST:event_solutionBtnActionPerformed

    private void verifyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyBtnActionPerformed
//        setElements(maGrille);
//        System.out.println("\n------ Contraintes verticales ---------");
//        for(int i = 0; i < dimension - 1; i++)
//        {
//            for(int j = 0; j < dimension; j++)
//            {
//                System.out.println("- " + i + ", " + j + " : " + contraintesVert[i][j]);
//            }
//        }
//        System.out.println("\n------ Contraintes Horizontales ---------");
//        for(int i = 0; i < dimension; i++)
//        {
//            for(int j = 0; j < dimension - 1; j++)
//            {
//                System.out.println("- " + i + ", " + j + " : " + contraintesHoriz[i][j]);
//            }
//        }
//        System.out.println("\n------ Valeurs ---------");
//        for(int i = 0; i < dimension; i++)
//        {
//            for(int j = 0; j < dimension; j++)
//            {
//                System.out.println("- " + i + ", " + j + " : " + valGrille[i][j]);
//            }
//        }
        if(setElements(maGrille))
        {
            if(verifyContraintes())
            {
                JOptionPane.showMessageDialog(null, "Aucune erreur \nFélicitations :)");
                System.out.println("Pas d'erreur :)");
            }
            else
            {
                System.out.println("Errors !");
            }
        }
        else
            System.out.println("Erreur lors de la récupération des éléments de la grille !");
    }//GEN-LAST:event_verifyBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyFutoshikiGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyFutoshikiGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyFutoshikiGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyFutoshikiGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyFutoshikiGame().setVisible(true);
            }
        });
    }
    
    private JTextField createField(JTextField textField, String value){
        textField.setText(value);
        textField.setEditable(false);
        textField.setForeground(Color.GRAY);
        return textField;
    }
    
    private void generateBoard(String diff, int dim){
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(new File("src/main/resources/config_" + dim + "_" + diff + ".csv")));
            String row;
            while ((row = csvReader.readLine()) != null) {
                System.out.println("row " + row);
                String[] data = row.split(",");
                int i  = Integer.parseInt(data[0]);
                int j  = Integer.parseInt(data[1]);
                
                System.out.println("Adding field " + i + " " + j + " " + data[2] );

                this.maGrille[i][j] = this.createField(maGrille[i][j], data[2]);
                
                solutionBtn.setEnabled(true);
                verifyBtn.setEnabled(true);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Choose;
    private javax.swing.JLabel calculLbl;
    private javax.swing.JPanel contentPane;
    private javax.swing.JComboBox<String> dimensionGameCB;
    private javax.swing.JPanel grille;
    private javax.swing.JButton solutionBtn;
    private javax.swing.JButton verifyBtn;
    // End of variables declaration//GEN-END:variables
}