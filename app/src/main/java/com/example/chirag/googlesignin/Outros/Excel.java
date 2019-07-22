package com.example.chirag.googlesignin.Outros;


import android.content.Context;
import android.widget.Toast;

import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import java.util.List;

import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy;


public class Excel {

    public static boolean ExportDisciplinaModel(List<RealmObject> disciplinas, Context context, String fileName, String extension) {
        String newLine = System.getProperty("line.separator");

        if (!extension.equals("csv") && !extension.equals("txt"))
            throw new IllegalArgumentException("File extension must be csv or txt.");

        if (disciplinas.size() == 0) {
            Toast.makeText(context, "Não foi selecionada nenhuma disciplina para exportar para "+extension+ ".", Toast.LENGTH_SHORT).show(); //Show shadow text
            return false;
        }

        if (!Useful.isWriteStoragePermissionGranted(context))
            return false;

        StringBuilder txt = new StringBuilder();

        txt.append("Prof,Year,Id,Nome,Curso,Acronimo,Semestre" + newLine);
        for (RealmObject c : disciplinas) {
            DisciplinaModel disc = ((com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy) c);
            txt.append(disc.getProfId() + "," + disc.getYear() + "," + disc.getID() + "," + disc.getNome() + "," + disc.getCurso() + "," + disc.getAcronimo() + "," + disc.getSemestre() + "," + newLine);


        }

        if (Useful.CreatFile(context, fileName, extension, txt.toString().getBytes())) {
            Toast.makeText(context, "Ficheiro exportado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
