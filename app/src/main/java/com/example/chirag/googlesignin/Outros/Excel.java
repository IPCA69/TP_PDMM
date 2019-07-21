package com.example.chirag.googlesignin.Outros;


import android.content.Context;
import android.widget.Toast;

import com.example.chirag.googlesignin.model.AulaModel;
import com.example.chirag.googlesignin.model.DisciplinaModel;

import java.util.List;

import io.realm.RealmObject;
import io.realm.com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy;


public class Excel {

    public static void ExportDisciplinaModel(List<RealmObject> disciplinas, Context context, String fileName, String extension) {
        String newLine = System.getProperty("line.separator");

        if (!extension.equals("csv") && !extension.equals("txt"))
            throw new IllegalArgumentException("File extension must be csv or txt.");

        if (disciplinas.size() == 0) {
            Toast.makeText(context, "Não foi selecionada nenhuma disciplina para exportar para csv.", Toast.LENGTH_SHORT).show(); //Show shadow text
            return;
        }

        StringBuilder txt = new StringBuilder();
        if (!Useful.isWriteStoragePermissionGranted(context))
            return;
        txt.append("Prof,Year,Id,Curso,Acronimo,Semestre,Aulas" + newLine);
        for (RealmObject c : disciplinas) {
            DisciplinaModel disc = ((com_example_chirag_googlesignin_model_DisciplinaModelRealmProxy) c);
            txt.append(disc.getProfId() + "," + disc.getYear() + "," + disc.getID() + "," + disc.getCurso() + "," + disc.getAcronimo() + "," + disc.getSemestre() + "," + newLine);

            if (disc.getAulaModels().size() != 0)
                txt.append(",,,,,,Prof,Year,Id,DataDeOcorrencia,Duracao,Sala,Tipo,Sumario" + newLine);
            for (AulaModel aula : disc.getAulaModels()) {
                txt.append(",,,,,," + aula.getProfId() + "," + aula.getProfId() + "," + aula.getID() + "," + Useful.GetDateAndHourFromDate(aula.getDataDeOcorrencia()) + "," + aula.getDuracao() + "," + aula.getSala() + "," + aula.getTipo() + "," + aula.getSumario() + newLine);
                txt.append(",,,,,,");

                txt.append(newLine);
            }

        }

        Useful.CreatFile(context, fileName, extension, txt.toString().getBytes());
    }

}
