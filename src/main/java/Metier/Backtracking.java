/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Interface.MyFutoshikiGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Hafsa GH
 */
public class Backtracking {
    static JTextField [][] maGrille;
    static int [][] valGrille; // Les valeurs
    static char [][] contraintesHoriz; // Grille des contraintes horizontales : < et >
    static char [][] contraintesVert; // Grille des contraintes verticales : ^ et v
    static ST<String, SET<String>> domain;
    
    // --- Constructeur ---
    public Backtracking(MyFutoshikiGame game)
    {
        valGrille = game.getValGrille();
        contraintesHoriz = game.getContraintesHoriz();
        contraintesVert = game.getContraintesVert();
    }
    
    public static String getVariable(ST<String, String> config) {	
	//retrieve a variable based on a heuristic or the next 'unfilled' one if there is no heuristic
        for (String s : config) 
        {
            if(config.get(s).equalsIgnoreCase(""))
                return s;
        }	
        //get variable failed (all variables have been coloured)
        return null;
    }
    
    public static SET<String> orderDomainValue(String variable, ST<String, SET<String>> domain) {
        //return the SET of domain values for the variable
        return domain.get(variable);
    }
	
    public static boolean complete(ST<String, String> config) {
        for(String s: config) {
            //if we find a variable in the config with no value, then this means that the config is NOT complete
            if(config.get(s).equalsIgnoreCase(""))
                return false;
        }
        //ALL variables in config have a value, so the configuration is complete
        return true;
    }
		
    public static boolean consistent(String value, String variable, ST<String, String> config, Graph g) {
        for(String adj: g.adjacentTo(variable)) {
            if(!adj.contains("s") && !adj.contains("i")){
                if(config.get(adj).equalsIgnoreCase(value))
                    return false;
            }else if(adj.contains("s")){
                String originalName = adj.replace("s", "x");
                if(!config.get(originalName).equals("")){
                    int variableNumber = Integer.parseInt(value);
                    int supNumber = Integer.parseInt(config.get(originalName));

                    if(supNumber <= variableNumber)
                        return false;
                }
            }else{
                String originalName = adj.replace("i", "x");
                if(!config.get(originalName).equals("")){
                    int variableNumber = Integer.parseInt(value);
                    int infNumber = Integer.parseInt(config.get(originalName));

                    if(infNumber > variableNumber)
                        return false;
                }
            }
        }
        return true;
    }
	
    public static boolean consistent(String value, String variable, ST<String, String> config,
                                            ST<String, ST<String, ST<String, SET<String>>>> constraintsTable) {
        //we need to get the constraint list for the variable
        for(String constraints: constraintsTable.get(variable)) {
            //if the adjacency list member's value is equal to the variable's selected value, then consistency fails
            if(!config.get(constraints).equals("") && !(constraintsTable.get(constraints).get(value).contains(config.get(constraints)))) {
                return false;
            }
        }
        //consistency check passed according to the variable's adjacancy list
        return true;
    }
    
    static void aff(ST<String, String> config){
        System.out.println("");
        System.out.print(" - ");

        if(config ==null)
            System.out.print("Pas de solution");
        else
        {
            for (String s : config)
            {
               System.out.print("("+s + ", "+ config.get(s)+")");
            }
        }
    }
    /*---------------------- MVR ------------------------------*/
    public static String MVR(ST<String, SET<String>> domain , ST<String, String> config){
        // la liste des tailles de domaine correpondant a chaque variable 
        ArrayList<Integer> taille = new ArrayList<Integer>() ;
        for (String s : config) 
        {
            if(config.get(s).equalsIgnoreCase(""))
            {
                taille.add(domain.get(s).size()) ;
            }  
        }
        // le tri de la liste taille (croissant)
        Collections.sort(taille);
        for(String s : config )   
        {
            if(domain.get(s).size() == taille.get(0)  && config.get(s).equalsIgnoreCase(""))  
               return s;
        }
        return null ;
    }
    
    /*---------------------- Forward Checking ------------------------------*/
    public static void forwardChecking(String u, String variable, Graph g, ST<String, String> config, ST<String, SET<String>> domain )
    {
        for(String adj: g.adjacentTo(variable)) 
        {
            if(config.get(adj).equalsIgnoreCase(""))
            {
                SET<String> domaine = new SET<String>();
                domaine = orderDomainValue(adj, domain) ;
                if (domaine.contains(u)) 
                {
                    domain.get(adj).remove(u);
                }
            }
        }     
    }
    
    /*---------------------- Retour en arriere ------------------------------*/
    public static void retour(String variable, Graph g, ST<String, String> config, ST<String, SET<String>> domain, String val) 
    {
        SET<String> intervalle = new SET<String>();

        for(String adj: g.adjacentTo(variable)) {
            if(config.get(adj).equalsIgnoreCase(""))
            {
                intervalle = orderDomainValue(adj, domain);
                if( !intervalle.contains(val)){
                    orderDomainValue(adj, domain).add(val);
                }
            }
        }
    }
    
    public static ST<String, SET<String>> check(ST<String, SET<String>> domain)
    {
        // --- Vérification ---
        for(int i = 0; i < valGrille.length ; i++)
        {
            for(int j = 0; j < valGrille.length - 1; j++)
            {
                if(contraintesHoriz[i][j] != ' ')
                {
                    switch(contraintesHoriz[i][j])
                    {
                        case '>':
                            if(valGrille[i][j] - valGrille[i][j+1] < 0)
                            {
                                System.out.println("\n à changer : " + i + ", " + j);
                                if(valGrille[i][j+1] < valGrille.length)
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i][j] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = valGrille[i][j+1] + 1; k <= valGrille.length; k++)
                                    {
                                        ((SET<String>)dom[i][j]).add(""+k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.getST().replace("x"+i+""+j, (SET<String>)dom[i][j]);
                                    return domain;
                                }
                                else
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i][j+1] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = 1; k < valGrille[i][j]; k++)
                                    {
                                        ((SET<String>)dom[i][j+1]).add(""+k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.getST().replace("x"+i+""+(j+1), (SET<String>)dom[i][j+1]);
                                    return domain;
                                }
                                //System.out.println("\n à changer : " + i + ", " + j);
                                //config.getST().replace("x"+i+""+j, "");
                                //config.getST().replace("x"+i+""+(j+1), "");
                            }
                            break;
                        case '<':
                            if(valGrille[i][j] - valGrille[i][j+1] > 0)
                            {
                                System.out.println("\n à changer : " + i + ", " + j);
                                if(valGrille[i][j] < valGrille.length)
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i][j+1] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = valGrille[i][j] + 1; k <= valGrille.length; k++)
                                    {
                                        ((SET<String>)dom[i][j+1]).add(""+k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.getST().replace("x"+i+""+(j+1), (SET<String>)dom[i][j+1]);
                                    return domain;
                                }
                                else
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i][j] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = 1; k < valGrille[i][j+1]; k++)
                                    {
                                        ((SET<String>)dom[i][j]).add(""+k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.getST().replace("x"+i+""+j, (SET<String>)dom[i][j]);
                                    return domain;
                                }
                            }
                            break;
                    }
                }
            }
        }
        for(int i = 0; i < valGrille.length - 1; i++)
        {
            for(int j = 0; j < valGrille.length; j++)
            {
                //System.out.println("Verticale : " + i);
                if(contraintesVert[i][j] != ' ')
                {
                    //System.out.println("_______________" + i + ", " + j + " : contient une contrainte qui est : " + contraintesVert[i][j]);
                    switch(contraintesVert[i][j])
                    {
                        case '^':
                            if(valGrille[i][j] - valGrille[i+1][j] < 0)
                            {
//                                System.out.println("\n à changer ^ : " + i + ", " + j);
                                if(valGrille[i][j] < valGrille.length)
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i+1][j] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = valGrille[i][j] + 1; k <= valGrille.length; k++)
                                    {
                                        ((SET<String>)dom[i+1][j]).add(""+k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.getST().replace("x"+(i+1)+""+j, (SET<String>)dom[i+1][j]);
                                    return domain;
                                }
                                else
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i][j] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = 1; k < valGrille[i+1][j]; k++)
                                    {
                                        ((SET<String>)dom[i][j]).add(""+k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.getST().replace("x"+i+""+j, (SET<String>)dom[i][j]);
                                    return domain;
                                }
                            }
                            break;
                        case 'v':
                            if(valGrille[i][j] - valGrille[i+1][j] > 0)
                            {
                                if(valGrille[i+1][j] < valGrille.length)
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i][j] = new SET<String>(); // Initialisation du domaine temporel
//                                    if(valGrille[i][j] > 0)
//                                    {
//                                        System.out.println("\n------------------- 1º: Val > 0 ------------------- " + i + ", " + j);
                                        for(int k = valGrille[i+1][j] + 1; k <= valGrille.length; k++)
                                        {
                                            ((SET<String>)dom[i][j]).add(""+k);
                                            System.out.print("k = " + k + " - ");
                                        }
//                                        System.out.println("");
//                                    }
//                                    else
//                                    {
//                                        System.out.println("\n------------------- 2º: Val < 0 ------------------- " + i + ", " + j);
//                                        for(int k = valGrille[i+1][j] + 2; k <= valGrille.length; k++)
//                                        {
//                                            ((SET<String>)dom[i][j]).add(""+k);
//                                            System.out.print("k = " + k + " - ");
//                                        }
//                                        System.out.println("");
//                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.put("x"+i+""+j, (SET<String>)dom[i][j]);

                                    dom[i+1][j] = new SET<String>();
                                    ((SET<String>)dom[i+1][j]).add(new String(String.valueOf(valGrille[i+1][j])));
                                    domain.put("x"+(i+1)+""+j, (SET<String>)dom[i+1][j]);
                                    System.out.println("Domaines : \n" + domain.getST());
                                    return domain;
//                                    System.out.println("---------------------------- " + i + "" + j + " : " + domain.get("x"+i+""+j));
//                                    System.out.println("---------------------------- " + (i+1) + "" + j + " : "  + domain.get("x"+(i+1)+""+j));
                                }
                                else
                                {
                                    Object[][] dom = new Object[valGrille.length][valGrille.length]; // Déclaration d'un domaine temporel
                                    dom[i+1][j] = new SET<String>(); // Initialisation du domaine temporel
                                    for(int k = 1; k < valGrille[i][j]; k++)
                                    {
                                        ((SET<String>)dom[i+1][j]).add(""+k);
//                                        System.out.println("------------------------- k = " + k);
                                    }
                                    //SET<String> domains = domain.get("x"+i+""+j);
                                    domain.put("x"+(i+1)+""+j, (SET<String>)dom[i+1][j]);

                                    dom[i][j] = new SET<String>();
                                    ((SET<String>)dom[i][j]).add(new String(String.valueOf(valGrille[i][j])));
                                    domain.put("x"+i+""+j, (SET<String>)dom[i][j]);
                                    return domain;
//                                    System.out.println("---------------------------- " + i + "" + j + " : " + domain.get("x"+i+""+j));
//                                    System.out.println("---------------------------- " + (i+1) + "" + j + " : " + domain.get("x"+(i+1)+""+j));
                                }
                            }
                            break;
                    }
                }
            }
        }
        return domain;
    }
    
    public static ST<String, String> backtracking(ST<String, String> config, ST<String, SET<String>> domain, Graph g){
        // -------------------------------------- Backtracking simple --------------------------------------       
        //domain = domain;
        // Arrêter s'il s'agit d'une affectation complete
        if(complete(config))
                return config;
        
        ST<String, String> result = null;
        // Variable à affecter
        String v = getVariable(config); // Backtracking simple
        //String v = MVR(domain, config); // En utilisant MVR

        // Domaine de cette variable (liste des valeurs)
        SET<String> vu = orderDomainValue(v, domain);
        // Parcourir la liste des valeurs
        for(String u: vu) {
            if(consistent(u, v, config, g)) { // 
                config.put(v, u); //
                aff(config);
                // --- Changement de la valeur dans la grille des valeurs ---
                valGrille[Character.getNumericValue(v.charAt(1))][Character.getNumericValue(v.charAt(2))] = Integer.parseInt(config.get(v));
//                ST<String, SET<String>> dm = check(domain);
                // --- Affichage des domaines de chaque cellule ---
//                System.out.println("\nLa table des domaines est : ");
//                Set<String> keys = (Set<String>) domain.getST().keySet();
//                for(String key : keys)
//                {
//                    System.out.println("Le domaine de " + key + " est: " + domain.getST().get(key));
//                }
                //--------------------------------------------------------
                result = backtracking(config, domain, g);
                if(result != null)
                    return result;

                config.put(v,""); // X config.remove(v)
            }
        }
        return null;
        /*// -------------------------------------- Backtracking + ForwardChecking + MRV --------------------------------------
        //recursion base case - check configuration completeness
        if(complete(config))
                return config;

        ST<String, String> result = null;

        //get a variable
        //String v = getVariable(config);
        String v = MVR(domain,config);
        //get a SET of all the variable's values
        SET<String> vu = orderDomainValue(v, domain);

        //loop through all the variable's values
        for(String u: vu) 
        {
            //if(consistent(u, v, config, g)) {
            if(consistent(u,v, config, g)) 
            {
                config.put(v, u);
                forwardChecking(u,v,g,config, domain);
                aff(config);
                result = backtracking(config, domain, g);
                if(result != null)
                        return result;
                retour(v, g, config, domain, u);
                config.put(v,"");
            }
        }
        return null;*/
    }
}
