package Notas;

import java.io.BufferedWriter;
import java.io.IOException;

public class Turma {
    private String nome;
    private int ano;
    private int semestre;
    private Professor prof;
    private Aluno[] alunos;
    private Avaliacao[] avals;

    public Turma(String nome, int ano, int semestre, Professor prof, Aluno[] alunos, Avaliacao[] aval) {
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.prof = prof;
        this.alunos = alunos;
        this.avals = aval;
    }

    public void medias(){
        System.out.println("Média da turma "+nome+" ("+ano+"/"+semestre+")");
        String alunoStr;
        double totalAluno = 0;
        double total_turma = 0;
        double media_turma;

        for (Aluno aluno : this.alunos)
        {
            alunoStr = aluno.toString();
            System.out.print(alunoStr+": ");

            for (Avaliacao aval : this.avals){
                System.out.print(aval.nota(aluno.getCpf())+" ");
                totalAluno += aval.nota(aluno.getCpf());
            }
            if (totalAluno>100){
                totalAluno = 100;
            }
            System.out.println("= "+totalAluno);
            total_turma += totalAluno;
            totalAluno = 0;

        }
        media_turma = total_turma / this.alunos.length;
        System.out.println("Média da turma: "+ media_turma);
        System.out.println();
    }

    public void salvarTurma(BufferedWriter b) throws IOException {
        b.write("TUR"+System.lineSeparator());
        b.write(this.nome+System.lineSeparator());
        b.write(this.ano+System.lineSeparator());
        b.write(semestre+System.lineSeparator());
        b.write(this.prof.getCpf()+System.lineSeparator());
        b.write(this.alunos.length+System.lineSeparator());

        for (Aluno aluno : this.alunos)
            b.write(aluno.getMat()+System.lineSeparator());

        b.write(this.avals.length+System.lineSeparator());

        for (Avaliacao aval : this.avals){
            aval.salvarAval(b);
        }


    }

}