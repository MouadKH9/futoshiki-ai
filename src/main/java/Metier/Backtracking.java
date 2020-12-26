/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Interface.MyFutoshikiGame;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JTextField;

/**
 *
 * @author Hafsa GH
 */
public class Backtracking {

    static JTextField[][] maGrille;
    static int[][] valGrille; // Les valeurs
    static char[][] contraintesHoriz; // Grille des contraintes horizontales : < et >
    static char[][] contraintesVert; // Grille des contraintes verticales : ^ et v
    static ST<String, SET<String>> domain;

    // --- Constructeur ---
    public Backtracking(MyFutoshikiGame game) {
        valGrille = game.getValGrille();
        contraintesHoriz = game.getContraintesHoriz();
        contraintesVert = game.getContraintesVert();
    }

    public static String getVariable(ST<String, String> config) {
        //retrieve a variable based on a heuristic or the next 'unfilled' one if there is no heuristic
        for (String s : config) {
            if (config.get(s).equalsIgnoreCase("")) {
                return s;
            }
        }
        //get variable failed (all variables have been coloured)
        return null;
    }

    public static SET<String> orderDomainValue(String variable, ST<String, SET<String>> domain) {
        //return the SET of domain values for the variable
        return domain.get(variable);
    }

    public static boolean complete(ST<String, String> config) {
        for (String s : config) {
            //if we find a variable in the config with no value, then this means that the config is NOT complete
            if (config.get(s).equalsIgnoreCase("")) {
                return false;
            }
        }
        //ALL variables in config have a value, so the configuration is complete
        return true;
    }

    public static boolean consistent(String value, String variable, ST<String, String> config, Graph g) {
        for (String adj : g.adjacentTo(variable)) {
            if (!adj.contains("s") && !adj.contains("i")) {
                if (config.get(adj).equalsIgnoreCase(value)) {
                    return false;
                }
            } else if (adj.contains("s")) {
                String originalName = adj.replace("s", "x");
                if (!config.get(originalName).equals("")) {
                    int variableNumber = Integer.parseInt(value);
                    int supNumber = Integer.parseInt(config.get(originalName));

                    if (supNumber <= variableNumber) {
                        return false;
                    }
                }
            } else {
                String originalName = adj.replace("i", "x");
                if (!config.get(originalName).equals("")) {
                    int variableNumber = Integer.parseInt(value);
                    int infNumber = Integer.parseInt(config.get(originalName));

                    if (infNumber > variableNumber) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean consistent(String value, String variable, ST<String, String> config,
            ST<String, ST<String, ST<String, SET<String>>>> constraintsTable) {
        //we need to get the constraint list for the variable
        for (String constraints : constraintsTable.get(variable)) {
            //if the adjacency list member's value is equal to the variable's selected value, then consistency fails
            if (!config.get(constraints).equals("") && !(constraintsTable.get(constraints).get(value).contains(config.get(constraints)))) {
                return false;
            }
        }
        //consistency check passed according to the variable's adjacancy list
        return true;
    }

    static void aff(ST<String, String> config) {
        System.out.println("");
        System.out.print(" - ");

        if (config == null) {
            System.out.print("Pas de solution");
        } else {
            for (String s : config) {
                System.out.print("(" + s + ", " + config.get(s) + ")");
            }
        }
    }

    /*---------------------- MVR ------------------------------*/
    public static String MVR(ST<String, SET<String>> domain, ST<String, String> config) {
        // la liste des tailles de domaine correpondant a chaque variable 
        ArrayList<Integer> taille = new ArrayList<Integer>();
        for (String s : config) {
            if (config.get(s).equalsIgnoreCase("")) {
                taille.add(domain.get(s).size());
            }
        }
        // le tri de la liste taille (croissant)
        Collections.sort(taille);
        for (String s : config) {
            if (domain.get(s).size() == taille.get(0) && config.get(s).equalsIgnoreCase("")) {
                return s;
            }
        }
        return null;
    }

    /*---------------------- Forward Checking ------------------------------*/
    public static void forwardChecking(String u, String variable, Graph g, ST<String, String> config, ST<String, SET<String>> domain) {
        for (String adj : g.adjacentTo(variable)) {
            if (config.get(adj) == null) {
                continue;
            }
            if (config.get(adj).equalsIgnoreCase("")) {
                SET<String> domaine = new SET<String>();
                domaine = orderDomainValue(adj, domain);
                if (domaine.contains(u)) {
                    System.out.println("Removing " + u + " from " + adj);
                    domain.get(adj).remove(u);
                }
            }
        }
    }

    /*---------------------- Retour en arriere ------------------------------*/
    public static void retour(String u, String variable, Graph g, ST<String, String> config, ST<String, SET<String>> domain) {

        SET<String> domaine;
        for (String adj : g.adjacentTo(variable)) {
            if (config.get(adj) == null)
                continue;
            if (config.get(adj).equalsIgnoreCase("")) {
                domaine = orderDomainValue(adj, domain);
                if (!domaine.contains(u)) {
                    domain.get(adj).add(u);
                }
            }
        }
    }

    public static ST<String, String> backtracking(ST<String, String> config, ST<String, SET<String>> domain, Graph g, boolean enableMVR, boolean enableForwardChecking) {
        
        // Arrêter s'il s'agit d'une affectation complete
        if (complete(config))
            return config;
            
        ST<String, String> result = null;

        // Variable à affecter
        String v = enableMVR ? MVR(domain, config) : getVariable(config);

        // Domaine de cette variable (liste des valeurs)
        SET<String> vu = orderDomainValue(v, domain);
        
        // Parcourir la liste des valeurs
        for (String u : vu) {
            if (!consistent(u, v, config, g))
                continue;
            
            config.put(v, u);
            if(enableForwardChecking)
                forwardChecking(u, v, g, config, domain);
            aff(config);
            valGrille[Character.getNumericValue(v.charAt(1))][Character.getNumericValue(v.charAt(2))] = Integer.parseInt(config.get(v));
            result = backtracking(config, domain, g, enableMVR, enableForwardChecking);
            if (result != null) {
                return result;
            }
            if(enableForwardChecking)
                retour(u, v, g, config, domain);
            config.put(v, "");
        }
        return null;
    }
}
