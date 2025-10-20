public class Estudante {
    private int id;
    private String nome;

    // Construtor
    public Estudante(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters para acessar os dados
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

   
    @Override
    public String toString() {
        return "Estudante{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}