package Notas;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior, Rafael Deps e Gustavo Firme
 */
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Entrada {
    public Scanner input;
    private boolean arquivo;

    /**
     * Construtor da classe Entrada
     * Se houver um arquivo dados.txt, define que o Scanner vai ler deste arquivo.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão
     * (teclado)
     */
    public Entrada() {
        try {
            // Se houver um arquivo input.txt, o Scanner vai ler dele.
            this.input = new Scanner(new FileReader("dados.txt"));
            this.arquivo = true;
        } catch (FileNotFoundException e) {
            // Caso contrário, vai ler do teclado.
            this.input = new Scanner(System.in);
            this.arquivo = false;
        }
    }

    public boolean isArquivo() {
        return arquivo;
    }

    /**
     * Faz a leitura de uma linha inteira
     * Ignora linhas começando com #, que vão indicar comentários no arquivo de
     * entrada:
     *
     * @param msg: Mensagem que será exibida ao usuário
     * @return Uma String contendo a linha que foi lida
     */
    private String lerLinha(String msg) {
        // Imprime uma mensagem ao usuário, lê uma e retorna esta linha
        if (!arquivo)
            System.out.print(msg);

        return this.input.nextLine();
    }

    /**
     * Faz a leitura de um número inteiro
     *
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiro(String msg) {
        // Imprime uma mensagem ao usuário, lê uma linha contendo um inteiro e retorna
        // este inteiro
        String Linha;
        int valor = 0;
        boolean entradacorreta = true;
        while (entradacorreta) {
            String linha = this.lerLinha(msg);
            try {
                valor = Integer.parseInt(linha);
                entradacorreta = false;
            } catch (NumberFormatException e) {
                System.out.print("Erro, não é inteiro. Tente novamente: \n");
            }
        }
        return valor;
    }

    /**
     * Faz a leitura de um double
     *
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDouble(String msg) {
        // Imprime uma mensagem ao usuário, lê uma linha contendo um double e retorna este double
        String linha;
        double valor = 0;

        boolean entradacorreta = true;
        while (entradacorreta) {
            linha = this.lerLinha(msg);
            try {
                valor = Double.parseDouble(linha);
                if (valor < 0) {
                    System.out.print("o numero deve ser positivo, tente novamente: \n");
                } else {
                    entradacorreta = false;
                }
            } catch (NumberFormatException e) {
                System.out.print("valor invalido tente novamente: \n");
            }
        }
        return valor;
    }

    /**
     * Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção
     * selecionada.
     *
     * @return Inteiro contendo a opção escolhida pelo usuário
     */
    public int menu() {
        // Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção
        // selecionada.

        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastrar professor:\n" +
                "2) Cadastrar aluno:\n" +
                "3) Cadastrar turma:\n" +
                "4) Listar turmas:\n" +
                "0) Salvar e Sair\n";

        int op = this.lerInteiro(msg);

        // TEM QUE MUDAR PARA OPCOES DE ARQUIVO
        while (op < 0 || op > 4) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    // METODO PARA LEITURA DE ARQUIVO. SERVE PARA PODER USAR O lerLinha NO MAIN
    public String menuArquivo() {
        return this.lerLinha("");
    }

    /**
     * Lê os dados de um novo Professor e cadastra-o no sistema.
     *
     * @param s: Um objeto da classe Sistema
     */
    public void cadProf(Sistema s) {
        if (!arquivo)
            s.listarProfs();

        String nome = this.lerLinha("Digite o nome do professor: ");
        String cpf = this.lerLinha("Digite o CPF do professor: ");
        boolean continuar = true;

        while (continuar){
            if (s.encontrarProfessor(cpf) == null) { // Garantindo que o CPF não esteja duplicado.
                continuar = false;
            } else {
                cpf = this.lerLinha("Erro CPF duplicado. Tente novamente: ");
            }

        }
        double salario = this.lerDouble("Digite o salário do professor: R$");

        Professor p = new Professor(nome, cpf, salario);
        s.novoProf(p);
    }

    /***************************************************/

    public void cadAluno(Sistema s) {
        if (!arquivo)
            s.listarAlunos();

        String nome = this.lerLinha("Digite o nome do aluno: ");
        String cpf = this.lerLinha("Digite o cpf do aluno: ");
        String mat = this.lerLinha("Digite a matricula do aluno: ");
        boolean continuar = true;

        while (continuar){
            if (s.encontrarAluno(mat) == null) { // Garantindo que a matricula não esteja duplicada.
                Aluno a = new Aluno(nome, cpf, mat);
                s.novoAluno(a);
                continuar = false;
            } else {
                mat = this.lerLinha("Erro matricula duplicada. Tente novamente: ");
            }
        }



    }

    /***************************************************/

    private void cadAvaliacao(Sistema s, int quantAval, Aluno[] alunos, Avaliacao[] avaliacoes) {

        Prova prova;
        Trabalho trabalho;

        for (int j = 0; j < quantAval; j++) {
            String msg = "Escolha um tipo de avaliação:\n" +
                    "1) Prova\n" +
                    "2) Trabalho\n";

            int tipoAval = this.lerInteiro(msg);

            while (tipoAval < 1 || tipoAval > 2) {
                System.out.println("Opção inválida. Tente novamente: ");
                tipoAval = this.lerInteiro(msg);
            }

            if (tipoAval == 1) {
                prova = lerProva(s, alunos);
                avaliacoes[j] = prova;
            } else {
                trabalho = lerTrabalho(s);
                avaliacoes[j] = trabalho;
            }
        }
    }

    /***************************************************/

    private void cadAvaliacaoArq(Sistema s, int quantAval, Aluno[] alunos, Avaliacao[] avaliacoes) {

        Prova prova;
        Trabalho trabalho;

        for (int j = 0; j < quantAval; j++) {
            String tipoAval = this.lerLinha("");

            if (tipoAval.equals("PROV")) {
                prova = lerProva(s, alunos);
                avaliacoes[j] = prova;
            }
            if (tipoAval.equals("TRAB")) {
                trabalho = lerTrabalho(s);
                avaliacoes[j] = trabalho;
            }
        }
    }

    /***************************************************/

    public void cadTurma(Sistema s) {
        if (!arquivo)
            s.listarTurmas();

        String nome = this.lerLinha("Digite o nome da disciplina: ");
        int ano = this.lerInteiro("Digite o ano da disciplina: ");
        int sem = this.lerInteiro("Digite o semestre da disciplina: ");
        Professor professor = null;

        while (professor == null) {
            String cpf = this.lerLinha("Digite o cpf do professor: ");
            professor = s.encontrarProfessor(cpf);

            if (professor == null) { // Garantindo que o professor exista
                System.out.println("Erro professor não existente. Tente novamente: ");

            }
        }

        Aluno[] alunos = lerAlunos(s);

        int quantAvalia = this.lerInteiro("Digite a quantidade de avaliações na disciplina: ");
        Avaliacao[] avaliacoes = new Avaliacao[quantAvalia];

        if (arquivo)
            cadAvaliacaoArq(s, quantAvalia, alunos, avaliacoes);
        else
            cadAvaliacao(s, quantAvalia, alunos, avaliacoes);

        Turma turma = new Turma(nome, ano, sem, professor, alunos, avaliacoes);
        s.novaTurma(turma);

    }

    public Aluno[] lerAlunos(Sistema s) {
        int quantAlunos = lerInteiro("Digite a quantidade de alunos na disciplina: ");
        Aluno[] alunos = new Aluno[quantAlunos];

        for (int i = 0; i < quantAlunos; i++) {
            while (true) {
                String mat = lerLinha("Digite a matrícula do aluno: ");
                Aluno a = s.encontrarAluno(mat);
                if (a != null) {
                    alunos[i] = a;
                    break;
                } else {
                    System.out.println("Erro: matrícula não existe. Tente novamente: ");
                }
            }
        }
        return alunos;
    }

    public Prova lerProva(Sistema s, Aluno[] alunos) {
        String nomeAval = this.lerLinha("Informe o nome desta prova: ");
        int diaAval = this.lerInteiro("Digite o dia da prova: ");
        int mesAval = this.lerInteiro("Digite o mes da prova: ");
        int anoAval = this.lerInteiro("Digite o ano da prova: ");
        double vMax = this.lerDouble("Digite o valor máximo desta avaliação: ");
        int numQuest = this.lerInteiro("Digite o número de questões: ");

        Data data = new Data(diaAval, mesAval, anoAval);

        AlunoProva[] alunoProvas = new AlunoProva[alunos.length];
        int j = 0;

        for (Aluno aluno : alunos) {
            double[] notas = new double[numQuest];

            for (int i = 0; i < numQuest; i++) {
                double notaQuest = this.lerDouble("Nota de " + aluno.getNome() + " na questão " + (i + 1) + ": ");
                notas[i] = notaQuest;
            }

            AlunoProva alunoProva = new AlunoProva(aluno, notas);
            alunoProvas[j] = alunoProva;
            j++;
        }
        Prova prova = new Prova(nomeAval, data, vMax, numQuest, alunoProvas);

        return prova;

    }

    public Trabalho lerTrabalho(Sistema s) {
        String nomeAval = this.lerLinha("Informe o nome desta avaliação: ");
        int diaAval = this.lerInteiro("Digite o dia do trabalho: ");
        int mesAval = this.lerInteiro("Digite o mes do trabalho: ");
        int anoAval = this.lerInteiro("Digite o ano do trabalho: ");
        double vMax = this.lerDouble("Digite o valor máximo desta avaliação: ");
        int maxInte = this.lerInteiro("Digite o número máximo de integrantes: ");
        int numGrupos = this.lerInteiro("Digite o número de grupos: ");

        Data data = new Data(diaAval, mesAval, anoAval);

        GrupoTrabalho[] grupos = new GrupoTrabalho[numGrupos];

        for (int i = 0; i < numGrupos; i++) {
            int numAlunos = this.lerInteiro("Digite o número alunos neste grupo: ");
            Aluno[] alunos = new Aluno[numAlunos];

            for (int j = 0; j < numAlunos; j++) {
                String matriAluno = this.lerLinha("Digite a matrículo do aluno: ");

                Aluno a = s.encontrarAluno(matriAluno);
                if (a == null) { // Verificando se o aluno existe
                    System.out.println("Aluno não encontrado. Tente novamente.");
                    j--;
                } else { // Se existe o aluno entra na lista de alunos
                    alunos[j] = a;
                }
            }

            double notaGrupo = this.lerDouble("Nota do grupo: ");
            grupos[i] = new GrupoTrabalho(alunos, notaGrupo);
        }
        Trabalho trabalho = new Trabalho(nomeAval, data, vMax, maxInte, grupos);

        return trabalho;

    }

    public void fimArquivo() {
        this.input.close();
        this.input = new Scanner(System.in);
        this.arquivo = false;
    }
}