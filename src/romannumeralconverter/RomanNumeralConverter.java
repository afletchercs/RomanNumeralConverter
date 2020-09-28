package romannumeralconverter;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class RomanNumeralConverter {   
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MainWindow();
            }
        });        
    }   
}

class MainWindow{
    JFrame frm;
    RomanNumeral rn;
    MainWindow(){
        rn = new RomanNumeral();
        frm = new JFrame("Roman Numeral Converter");
        frm.setSize(200, 200);
        frm.setLayout(new GridLayout(3,1));
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        
        Font font = new Font(Font.SERIF, Font.BOLD, 20);
        
        JTextField txt = new JTextField();
        txt.setFont(font);
        txt.setText("VII");
        frm.add(txt);
        
        JLabel lbl = new JLabel("Enter a value");
        lbl.setFont(font);
        frm.add(lbl);
        
        JButton btn = new JButton("Convert");
        btn.setFont(font);
        btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                
                lbl.setText("" + rn.Convert(txt.getText()));
            }
        });
        frm.add(btn);        
    }
}
class RomanNumeral{
    private LinkedHashMap<Character, Integer> rnValues = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> rnCorrectValues = new LinkedHashMap<>();
    
    public RomanNumeral(){
        rnValues.put('I', 1);
        rnValues.put('V', 5);
        rnValues.put('X', 10);
        rnValues.put('L', 50);
        rnValues.put('C', 100);
        rnValues.put('D', 500);
        rnValues.put('M', 1000);
        
        rnCorrectValues.put("I", 1);
        rnCorrectValues.put("II", 2);
        rnCorrectValues.put("III", 3);
        rnCorrectValues.put("IV", 4);
        rnCorrectValues.put("V", 5);
        rnCorrectValues.put("VI", 6);
        rnCorrectValues.put("VII", 7);
        rnCorrectValues.put("VIII", 8);
        rnCorrectValues.put("IX", 9);
        rnCorrectValues.put("X", 10);
        rnCorrectValues.put("XX", 20);
        rnCorrectValues.put("XXX", 30);
        rnCorrectValues.put("XL", 40);
        rnCorrectValues.put("L", 50);
        rnCorrectValues.put("LX", 60);
        rnCorrectValues.put("LXX", 70);
        rnCorrectValues.put("LXXX", 80);
        rnCorrectValues.put("XC", 90);
        rnCorrectValues.put("C", 100);
        rnCorrectValues.put("CC", 200);
        rnCorrectValues.put("CCC", 300);
        rnCorrectValues.put("CD", 400);
        rnCorrectValues.put("D", 500);
        rnCorrectValues.put("DC", 600);
        rnCorrectValues.put("DCC", 700);
        rnCorrectValues.put("DCCC", 800);
        rnCorrectValues.put("CM", 900);
        rnCorrectValues.put("M", 1000);
        rnCorrectValues.put("MM", 2000);
        rnCorrectValues.put("MMM", 3000);
    }
    public String Convert(String input){
        Pattern numerals = Pattern.compile("[IVXLCDM]+");
        Pattern number = Pattern.compile("[0-9]+");
        Matcher numeralMatch = numerals.matcher(input);
        Matcher numberMatch = number.matcher(input);
        
        if(numeralMatch.matches() == true){
            int n = ConvertFromRomanNumeral(input);
            return Integer.toString(n);
        }
        else if(numberMatch.matches() == true){
            int n = Integer.parseInt(input);
            return ConvertToRomanNumeral(n);
        }
        return "Error";        
    }
    private int ConvertFromRomanNumeral(String input){
        if(input.isEmpty()) return -1;
        Pattern pat = Pattern.compile("[^IVXLCDM]");
        Matcher mat = pat.matcher(input);
        boolean found = mat.matches();
        if(found == true) return -1;       
                
        char[] inArray = input.toCharArray();
        int value = 0;
        
        for(int i=inArray.length-1; i>=0; i--){
            char current = inArray[i];            
            boolean largerValueToRight = false;
            for(int j=i; j<=inArray.length-1; j++){
                if(rnValues.get(inArray[j]) > rnValues.get(current)) largerValueToRight = true;
            }
            if(largerValueToRight) value -= rnValues.get(current);
            else value += rnValues.get(current);
        }
        return value;
    }
    private String ConvertToRomanNumeral(int input){
        String output = "";
        if(input < 0){
            output = "Invalid input";
            return output;
        }
        ArrayList<Integer> values = new ArrayList<Integer>(rnCorrectValues.values());
        int currentIndex = values.size()-1;
        while(input > 0){                       
            if(input>=values.get(currentIndex)) {
                output += getKey(rnCorrectValues, values.get(currentIndex));
                input -= values.get(currentIndex);            
            }
            else {
                currentIndex--;
            }
        }
        
        return output;
    }
    private Character getKey(Integer value){
        for(Map.Entry<Character,Integer> entry: rnValues.entrySet()){
            if(value.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }
    private String getKey(Map<String, Integer> map, Integer value){
        for(Map.Entry<String, Integer> entry: rnCorrectValues.entrySet()){
            if(value.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }
    
    

}

