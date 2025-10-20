import java.util.*;
import java.util.stream.Collectors;

public class HistoricoNotas{

    // O Map principal: a chave é o ID do estudante, o valor é a lista de suas matrículas.
    private Map<Integer, List<Matricula>> historico;

    public HistoricoNotas() {
        this.historico = new HashMap<>();
    }

    /**
     * Adiciona uma nova matrícula para um estudante.
     */
    public void adicionarMatricula(int idEstudante, String codigoDisciplina, double nota) {
        // Cria uma nova instância de Matrícula.
        Matricula novaMatricula = new Matricula(codigoDisciplina, nota);

        // Usa computeIfAbsent para obter a lista do estudante. Se o estudante não estiver no
        // mapa ainda, cria uma nova ArrayList para ele antes de adicionar a matrícula.
        this.historico.computeIfAbsent(idEstudante, k -> new ArrayList<>()).add(novaMatricula);
    }

    /**
     * Retorna a lista de matrículas de um estudante específico.
     */
    public List<Matricula> obterMatriculas(int idEstudante) {
        // Usa getOrDefault para retornar a lista do estudante ou uma lista vazia se ele não tiver matrículas.
        // Isso evita erros de NullPointerException.
        return this.historico.getOrDefault(idEstudante, Collections.emptyList());
    }

    /**
     * Retorna a nota de um estudante em uma disciplina específica.
     * Usa Optional<Double> para tratar casos onde a matrícula não existe.
     */
    public Optional<Double> obterNota(int idEstudante, String codigoDisciplina) {
        List<Matricula> matriculas = obterMatriculas(idEstudante);
        
        return matriculas.stream()
                .filter(m -> m.getCodigoDisciplina().equals(codigoDisciplina))
                .map(Matricula::getNota)
                .findFirst();
    }

    /**
     * Remove uma matrícula de um estudante.
     */
    public void removerMatricula(int idEstudante, String codigoDisciplina) {
        List<Matricula> matriculas = this.historico.get(idEstudante);
        if (matriculas != null) {
            matriculas.removeIf(m -> m.getCodigoDisciplina().equals(codigoDisciplina));
        }
    }

    /**
     * Calcula e retorna a média das notas de um estudante.
     */
    public double mediaDoEstudante(int idEstudante) {
        List<Matricula> matriculas = obterMatriculas(idEstudante);
        
        return matriculas.stream()
                .mapToDouble(Matricula::getNota)
                .average()
                .orElse(0.0); // Retorna 0.0 se o aluno não tiver notas.
    }

    /**
     * Calcula e retorna a média geral de uma disciplina, considerando todos os alunos matriculados nela.
     */
    public double mediaDaDisciplina(String codigoDisciplina) {
        return this.historico.values().stream() // Pega todas as listas de matrículas
                .flatMap(List::stream) // Transforma a lista de listas em um único stream de matrículas
                .filter(m -> m.getCodigoDisciplina().equals(codigoDisciplina)) // Filtra pela disciplina
                .mapToDouble(Matricula::getNota) // Pega apenas as notas
                .average()
                .orElse(0.0); // Retorna 0.0 se ninguém estiver matriculado na disciplina.
    }
    
    /**
     * Retorna uma lista dos N estudantes com as maiores médias.
     * @param N O número de estudantes para o "top".
     * @param listaEstudantes Objeto que contém a lista de todos os estudantes para consulta.
     * @return Uma lista de objetos Estudante.
     */
    public List<Estudante> topNEstudantesPorMedia(int N, ListaEstudantes listaEstudantes) {
        // Mapeia cada ID de estudante para um objeto contendo o ID e sua média
        return this.historico.keySet().stream()
                .map(id -> new AbstractMap.SimpleEntry<>(id, mediaDoEstudante(id)))
                // Ordena as entradas do mapa em ordem decrescente de média
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                // Limita o resultado aos N primeiros
                .limit(N)
                // Para cada ID no top N, busca o objeto Estudante correspondente
                .map(entry -> listaEstudantes.getEstudantes().stream()
                        .filter(e -> e.getId() == entry.getKey())
                        .findFirst()
                        .orElse(null))
                // Remove qualquer resultado nulo (caso um estudante não seja encontrado)
                .filter(Objects::nonNull)
                // Coleta o resultado em uma lista final
                .collect(Collectors.toList());
    }
}