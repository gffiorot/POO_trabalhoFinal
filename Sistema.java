package Notas;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class Sistema {
    private List<Professor> profs;
    private List<Aluno> alunos;
    private List<Turma> turmas;

    public Sistema() {
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    /***************************************************/

    public void novoProf(Professor p) {
        this.profs.add(p);
    }

    /***************************************************/

    public void novoAluno(Aluno a) {
        this.alunos.add(a);
    }

    /***************************************************/

    public void novaTurma(Turma t) {
        this.turmas.add(t);
    }
    /***************************************************/

    public Professor encontrarProfessor(String cpf) {
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    /***************************************************/

    public Aluno encontrarAluno(String matricula) {
        for (Aluno a : this.alunos) {
            if (a.getMat().equals(matricula)) {
                return a;
            }
        }
        return null;
    }

    /***************************************************/

    public void listarProfs() {
        if (this.profs.size() > 0) {
            System.out.println("Professores cadastrados:");
            for (Professor p : this.profs) {
                System.out.println("* " + p.toString());
            }
        }
        else {
            System.out.println("Nenhum professor cadastrado até o momento.");
        }
    }

    /***************************************************/

    public void listarAlunos() {
        if (this.alunos.size() > 0) {
            System.out.println("Alunos cadastrados:");
            for (Aluno a : this.alunos) {
                System.out.println("* "+a.toString());
            }
        }
        else {
            System.out.println("Nenhum Aluno cadastrado até o momento.");
        }
    }

    /***************************************************/

    public void listarTurmas() {
        if (this.turmas.size() > 0) {
            for (Turma t : this.turmas) {
                t.medias();
            }
        }
        else {
            System.out.println("Nenhuma turma cadastrada até o momento.");
        }
    }

    /***************************************************/

    public void salvarTudo() {
        try {
            FileWriter f = new FileWriter("dados.txt");
            BufferedWriter b = new BufferedWriter(f);

            for (Professor p : this.profs) {
                p.salvarProf(b);
            }
            for (Aluno a : this.alunos) {
                a.salvarAluno(b);
            }
            for (Turma a : this.turmas) {
                a.salvarTurma(b);
            }
            b.write("FIM");
            b.close();
        } catch (IOException e){
            System.out.println("Erro ao gravar arquivo");
        }
    }

}
