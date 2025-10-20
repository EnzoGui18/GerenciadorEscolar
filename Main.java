// Arquivo: Main.java

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1. Instanciando as classes de gerenciamento
        ListaEstudantes listaEstudantes = new ListaEstudantes();
        CadastroDisciplinas cadastroDisciplinas = new CadastroDisciplinas();
        HistoricoNotas historicoNotas = new HistoricoNotas();

        // 2. Carregando os datasets
        List<String> duplicatasDetectadas = carregarDados(listaEstudantes, cadastroDisciplinas, historicoNotas);

        // 3. Exibindo os relatórios conforme solicitado
        System.out.println("== Lista de Estudantes (ordenada) ==");
        listaEstudantes.ordenarEstudantesPorNome();
        for (Estudante e : listaEstudantes.getEstudantes()) {
            System.out.println(e.getId() + " - " + e.getNome());
        }
        System.out.println(); // Linha em branco para separar

        System.out.println("== Disciplinas (ordem de inserção) ==");
        for (Disciplina d : cadastroDisciplinas.obterTodasDisciplinas()) {
            System.out.println(d.getCodigo() + " - " + d.getNome());
        }
        System.out.println();

        System.out.println("== Duplicatas de disciplinas detectadas na importação ==");
        if (duplicatasDetectadas.isEmpty()) {
            System.out.println("(nenhuma)");
        } else {
            for (String dup : duplicatasDetectadas) {
                System.out.println(dup);
            }
        }
        System.out.println();

        System.out.println("== Histórico de Notas dos Alunos ==");
        for (Estudante e : listaEstudantes.getEstudantes()) {
            System.out.printf("%s: Matrículas: %s | Média: %.2f\n",
                    e.getNome(),
                    historicoNotas.obterMatriculas(e.getId()),
                    historicoNotas.mediaDoEstudante(e.getId()));
        }
        System.out.println();

        System.out.println("== Médias por Disciplina ==");
        for (Disciplina d : cadastroDisciplinas.obterTodasDisciplinas()) {
            System.out.printf("%s: Média: %.2f\n",
                    d.getNome(),
                    historicoNotas.mediaDaDisciplina(d.getCodigo()));
        }
        System.out.println();

        System.out.println("== Top 3 Alunos por Média ==");
        List<Estudante> top3 = historicoNotas.topNEstudantesPorMedia(3, listaEstudantes);
        int rank = 1;
        for (Estudante e : top3) {
            System.out.printf("%d) %s - Média: %.2f\n",
                    rank++,
                    e.getNome(),
                    historicoNotas.mediaDoEstudante(e.getId()));
        }
        System.out.println();

        System.out.println("== Alunos com média >= 8.0 ==");
        for (Estudante e : listaEstudantes.getEstudantes()) {
            if (historicoNotas.mediaDoEstudante(e.getId()) >= 8.0) {
                System.out.println(e.getNome());
            }
        }
        System.out.println();

        System.out.println("== Disciplinas com média < 6.0 ==");
        boolean encontrouMediaBaixa = false;
        for (Disciplina d : cadastroDisciplinas.obterTodasDisciplinas()) {
            if (historicoNotas.mediaDaDisciplina(d.getCodigo()) < 6.0) {
                System.out.println(d.getNome());
                encontrouMediaBaixa = true;
            }
        }
        if (!encontrouMediaBaixa) {
            System.out.println("(nenhuma)");
        }
    }

    /**
     * Método auxiliar para popular as coleções com os dados de exemplo.
     * Retorna uma lista com os nomes das disciplinas duplicadas encontradas.
     */
    public static List<String> carregarDados(ListaEstudantes lista, CadastroDisciplinas cadastro, HistoricoNotas historico) {
        // Estudantes (id, nome) [cite: 99-104]
        lista.adicionarEstudante(new Estudante(1, "Ana"));
        lista.adicionarEstudante(new Estudante(2, "Bruno"));
        lista.adicionarEstudante(new Estudante(3, "Carla"));
        lista.adicionarEstudante(new Estudante(4, "Diego"));
        lista.adicionarEstudante(new Estudante(5, "Elisa"));

        // Disciplinas (código, nome) [cite: 105-109]
        List<String> duplicatas = new ArrayList<>();
        Disciplina[] disciplinasParaAdicionar = {
            new Disciplina("MAT101", "Matemática"),
            new Disciplina("PRG201", "Programação"),
            new Disciplina("BD301", "Banco de Dados"),
            new Disciplina("EDF110", "Educação Física"),
            // Adicionando uma duplicata para teste
            new Disciplina("MAT101", "Matemática Duplicada") 
        };

        for (Disciplina d : disciplinasParaAdicionar) {
            if (!cadastro.adicionarDisciplina(d)) {
                duplicatas.add("Código já existente: " + d.getCodigo() + " - " + d.getNome());
            }
        }

        // Matrículas/Notas (estudanteId, disciplinaCodigo, nota) [cite: 110-116]
        historico.adicionarMatricula(1, "MAT101", 8.5);
        historico.adicionarMatricula(1, "PRG201", 9.0);
        historico.adicionarMatricula(2, "PRG201", 7.0);
        // Adicionando uma nota extra para o Bruno, como no exemplo de saída
        historico.adicionarMatricula(2, "MAT101", 5.0); 
        historico.adicionarMatricula(3, "BD301", 6.5);
        // Adicionando uma nota extra para a Carla, como no exemplo de saída
        historico.adicionarMatricula(3, "MAT101", 7.5);
        historico.adicionarMatricula(4, "PRG201", 8.0);
        historico.adicionarMatricula(5, "EDF110", 10.0);

        return duplicatas;
    }
}