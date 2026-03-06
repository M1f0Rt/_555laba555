package org.example._555laba555.base;

import org.example._555laba555.base.Reagent;
import org.example._555laba555.base.ProgramState.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ReagentTraker {
    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    //Массив который экспорт из файла если файл есть
    //лист1 лист2 лист3

    public void run() throws Exception   {
        while (true){
            String line = in.readLine();
            if (line.equals("reagent_add")){
            System.out.println("Название:");
            String name = in.readLine();
            ProgramState.addReagent(new Reagent(1, name , "d", "k", "e", "r", null, null));
            continue;
            }
            if (line.equals("reag_list")){
            System.out.println(ProgramState.getReagents());
            continue;
            }
        }

        }
    }