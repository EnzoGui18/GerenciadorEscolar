// Arquivo: CadastroDisciplinas.java

import java.util.LinkedHashSet;
import java.util.Set;

public class CadastroDisciplinas {

    private Set<Disciplina> disciplinas;

    // Construtor: inicializa a coleção com LinkedHashSet
    public CadastroDisciplinas() {
        this.disciplinas = new LinkedHashSet<>();
    }

    /**
     * Adiciona uma nova disciplina. Se a disciplina ja existir,
     * a adição é ignorada pelo Set.
     * @return 
     */
    public boolean adicionarDisciplina(Disciplina disciplina) {
        return this.disciplinas.add(disciplina);
    }

    public boolean verificarDisciplina(String codigo) {
        return this.disciplinas.stream()
                .anyMatch(disciplina -> disciplina.getCodigo().equals(codigo));
    }

    public void removerDisciplina(String codigo) {
        this.disciplinas.removeIf(disciplina -> disciplina.getCodigo().equals(codigo));
    }

    public Set<Disciplina> obterTodasDisciplinas() {
        // Retorna uma cópia para proteger a lista original de modificações externas
        return new LinkedHashSet<>(this.disciplinas);
    }
}