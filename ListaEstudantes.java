import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListaEstudantes {

    private List<Estudante> estudantes;

    // Construtor: inicializa a lista de estudantes
    public ListaEstudantes() {
        this.estudantes = new ArrayList<>();
    }

    /**
     * Adiciona um novo estudante à lista. 
     */
    public void adicionarEstudante(Estudante estudante) {
        this.estudantes.add(estudante);
    }

    /**
     * Remove um estudante da lista com base no seu ID. 
     */
    public void removerEstudantePorId(int id) {
        // Usa o método removeIf com uma expressão lambda para encontrar e remover o estudante
        this.estudantes.removeIf(estudante -> estudante.getId() == id);
    }

    /**
     * Retorna o estudante em uma posição específica (índice) da lista.
     */
    public Estudante obterEstudantePorIndice(int indice) {
        if (indice >= 0 && indice < estudantes.size()) {
            return this.estudantes.get(indice);
        }
        return null; 
    }

    /**
     * Busca todos os estudantes cujo nome contém uma determinada substring (ignorando maiúsculas/minúsculas). 
     * @return 
     */
    public List<Estudante> buscarEstudantesPorNome(String substring) {
        // Converte a substring de busca para minúsculas para a comparação case-insensitive
        String substringMinuscula = substring.toLowerCase();
        
        // Usa Streams para filtrar a lista de forma mais declarativa
        return this.estudantes.stream()
                .filter(estudante -> estudante.getNome().toLowerCase().contains(substringMinuscula))
                .collect(Collectors.toList());
    }

    /**
     * Ordena a lista de estudantes em ordem alfabética pelo nome.
     */
    public void ordenarEstudantesPorNome() {
        // Usa o método sort da lista com um Comparator para ordenar pelo nome
        this.estudantes.sort(Comparator.comparing(Estudante::getNome));
    }
    
    /**
     * Retorna a lista completa de estudantes.
     */
    public List<Estudante> getEstudantes() {
        return this.estudantes;
    }
}