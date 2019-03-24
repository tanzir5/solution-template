package com.tigerit.exam;


import static com.tigerit.exam.IO.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    public static Comparator<ArrayList<Integer>> cmp = new Comparator<ArrayList<Integer>>() {

	public int compare(ArrayList<Integer> s1, ArrayList<Integer> s2) {
            for(int i = 0; i<s1.size(); i++)
                if(!s1.get(i).equals(s2.get(i)))
                    return s1.get(i) - s2.get(i);
            return 0;
        }
    };
    
    int T, numberOfTable;
    Map<String,Integer> mapName =new HashMap<String, Integer>();  
    class Pair{
        int first, second;
        public Pair(int a, int b)
        {
            first = a;
            second = b;
        }
    }
    class Table{
        int numberOfColumn, numberOfRow;
        String myName;
        ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
        Map<String,Integer> mapColumn =new HashMap<String, Integer>();  
    
        void takeInput(int idx)
        {
            myName = readLine();
            mapName.put(myName, idx);
            String line = readLine();
            Scanner sc = new Scanner(line);
            
            numberOfColumn = sc.nextInt();
            numberOfRow = sc.nextInt();
            line = readLine();
            sc = new Scanner(line);
            for(int i = 0; i<numberOfColumn; i++)
            {
                String tmp = sc.next();
                columnNames.add(tmp);
                mapColumn.put(tmp, i);
            }
            for(int i = 0; i<numberOfRow; i++)
            {
                ArrayList<Integer>tmp = new ArrayList<Integer>();
                line = readLine();
                sc = new Scanner(line);
                for(int j = 0; j<numberOfColumn; j++)
                    tmp.add(sc.nextInt());
                data.add(tmp);
            }
        }
        
        void showAll()
        {
            System.out.println("My name is: " + myName);
            System.out.println("col: " + numberOfColumn);
            System.out.println("row: " + numberOfRow);
            for(int i = 0; i<columnNames.size(); i++)
                System.out.print(columnNames.get(i)+ " ");
            System.out.println();
            for(int i = 0; i<data.size(); i++)
            {
                for(int j = 0; j<data.get(i).size(); j++)
                    System.out.print(data.get(i).get(j)+" ");
                System.out.println();
            }
            System.out.println("Print all done.\n");
        }
        
    }
    
    
    ArrayList<Table>tables = new ArrayList<Table>();
    
    class querySolver{
        int table1, table2;
        Map<String,Integer> shortNameMap=new HashMap<String,Integer>();  
        String []line = new String[4];
        String arg1, arg2;
        String []arg1Parts;
        String []arg2Parts;
        int col1, col2;
        ArrayList<ArrayList<Integer>>result = new ArrayList<ArrayList<Integer>>();
        ArrayList<Pair>imp = new ArrayList<Pair>();
        ArrayList<String> colName = new ArrayList<String>();
        
        void format0()
        {
            Scanner sc;
            sc = new Scanner(line[0]);
            sc.next();
            String s = sc.next();
            if(s.charAt(0) == '*')
            {
                for(int i = 0; i<tables.get(table1).numberOfColumn; i++)
                {
                    colName.add(tables.get(table1).columnNames.get(i));
                    Pair tmp = new Pair(table1, i);
                    imp.add(tmp);
                }
                for(int i = 0; i<tables.get(table2).numberOfColumn; i++)
                {
                    colName.add(tables.get(table2).columnNames.get(i));
                    Pair tmp = new Pair(table2, i);
                    imp.add(tmp);
                }
                
            }
            else
            {
                sc = new Scanner(line[0]);
                sc.next();
                while(sc.hasNext())
                {
                    s = sc.next();
                    String []nw = s.split("\\.");
                    nw[1] = nw[1].replace(",", "");
                    colName.add(nw[1]);
                    if(shortNameMap.get(nw[0]).equals(new Integer(table1)))
                    {
                        Pair tmp = new Pair(table1, tables.get(table1).mapColumn.get(nw[1]));
                        imp.add(tmp);
                    }
                    else
                    {
                        Pair tmp = new Pair(table2, tables.get(table2).mapColumn.get(nw[1]));
                        imp.add(tmp);
                    }
                }
            }
            
        }
        
        void format1()
        {
            Scanner sc;
            sc = new Scanner(line[1]);
            sc.next();
            table1 = mapName.get(sc.next());
            if(sc.hasNext())
                shortNameMap.put(sc.next(), table1);
            else
                shortNameMap.put(tables.get(table1).myName, table1);
            
        }
        
        void format2()
        {
            Scanner sc;
            sc = new Scanner(line[2]);
            sc.next();
            table2 = mapName.get(sc.next());
            if(sc.hasNext())
                shortNameMap.put(sc.next(), table2);
            else
                shortNameMap.put(tables.get(table2).myName, table2);
            
            
        }
        
        void format3()
        {
            Scanner sc;
            sc = new Scanner(line[3]);
            sc.next();
            arg1 = sc.next();
            sc.next();
            arg2 = sc.next();
            arg1Parts = arg1.split("\\.");
            arg2Parts = arg2.split("\\.");
            col1 = tables.get(table1).mapColumn.get(arg1Parts[1]);
            col2 = tables.get(table2).mapColumn.get(arg2Parts[1]);

        }
        
        void takeInput()
        {
            for(int i = 0; i<4; i++)
                line[i] = readLine();
            readLine();
            format1();
            format2();
            format3();
            format0();
        }
        
        ArrayList<Integer> build(int idx1, int idx2)
        {
            ArrayList<Integer>ret = new ArrayList<Integer>();
            for(int i = 0; i<imp.size(); i++)
            {
                if(imp.get(i).first == table1)
                    ret.add(tables.get(table1).data.get(idx1).get(imp.get(i).second));
                else
                    ret.add(tables.get(table2).data.get(idx2).get(imp.get(i).second));
            }
            return ret;
        }
        
        void printResult()
        {
            String out = "";
            for(int i = 0; i < colName.size(); i++)
            {
                if(i > 0) out += " ";
                out += colName.get(i);
            }
            printLine(out);
            for(int i = 0; i<result.size(); i++)
            {
                out = "";
                for(int j = 0; j<result.get(i).size(); j++)
                {
                    if(j > 0) out += " ";
                    out += result.get(i).get(j);
                }
                printLine(out);
            }
            printLine("");
        }
        
        void solveQuery()
        {
            takeInput();
            for(int i = 0; i<tables.get(table1).numberOfRow; i++)
            {
                for(int j = 0; j<tables.get(table2).numberOfRow; j++)
                {
                    if(tables.get(table1).data.get(i).get(col1).equals(tables.get(table2).data.get(j).get(col2)))
                        result.add(build(i,j));
                }
            }
            Collections.sort(result, cmp);
            printResult();
        }
    }
    
    
    
    
    
    
    void init(int cs)
    {
        tables.clear();
        mapName.clear();
        printLine("Test: " + cs);
    }
    
    void takeInput()
    {
        numberOfTable = readLineAsInteger();
        for(int i = 0; i<numberOfTable; i++)
        {
            Table curTable = new Table();
            curTable.takeInput(i);
            //curTable.showAll();
            tables.add(curTable);
        }
    }
    
    
    @Override
    public void run() {
        // your application entry point
        
        T = readLineAsInteger();
        for(int cs = 1; cs<=T; cs++)
        {
            init(cs);
            takeInput();
            int q = readLineAsInteger();
            for(int i = 0; i<q; i++)
            {
                querySolver cur = new querySolver();
                cur.solveQuery();
            }
            
        }
        /*// sample input process
        String string = readLine();

        Integer integer = readLineAsInteger();
        
        // sample output process
        printLine(string);
        printLine(integer);
        */
    }
}
