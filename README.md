# Projeto de Gerenciamento Acadêmico com Java Collections
Enzo Guimarães Miguel
## Breve Descrição do Projeto

Este programa é um sistema de gerenciamento acadêmico desenvolvido em Java. Ele simula o controle de estudantes, disciplinas e notas de uma escola, realizando todas as operações em memória. O sistema permite cadastrar alunos e disciplinas, registrar suas notas, e gerar relatórios como médias de alunos, médias de disciplinas e um ranking dos estudantes com melhor desempenho.

## Justificativa das Escolhas de Coleções

A seleção de cada `Collection` foi fundamental para a eficiência e corretude do programa:

* **`List` (usando `ArrayList`) para Estudantes:**
    * [cite_start]**Por que `List`?** A lista de estudantes (`ListaEstudantes`) precisava manter uma ordem, que poderia ser a de inserção ou uma ordem alfabética após a ordenação[cite: 8, 31]. A interface `List` é ideal para coleções ordenadas que permitem acesso por índice.
    * **Por que `ArrayList`?** Foi escolhido por sua eficiência em operações de acesso e leitura por índice (`get`), que são comuns ao buscar estudantes ou exibir a lista. Como as operações de inserção e remoção no meio da lista não eram frequentes, suas desvantagens nesses cenários não foram um impeditivo.

* **`Set` (usando `LinkedHashSet`) para Disciplinas:**
    * [cite_start]**Por que `Set`?** O requisito principal era garantir que não houvesse disciplinas duplicadas[cite: 9, 37]. A interface `Set` automaticamente impede a inserção de elementos duplicados, simplificando a lógica de negócio.
    * [cite_start]**Por que `LinkedHashSet`?** O projeto exigia que as disciplinas fossem exibidas na mesma ordem em que foram inseridas[cite: 42, 67]. `LinkedHashSet` oferece a garantia de unicidade do `HashSet` ao mesmo tempo que mantém a ordem de inserção dos elementos.

* **`Map` (usando `HashMap`) para Histórico de Notas:**
    * [cite_start]**Por que `Map`?** A necessidade era associar um estudante (através de seu `id`) a um conjunto de dados complexo (sua lista de matrículas e notas)[cite: 10, 51]. `Map` é a estrutura perfeita para criar essa relação chave-valor, permitindo a busca rápida do histórico de um aluno a partir de seu ID.
    * **Por que `HashMap`?** Foi a implementação escolhida por oferecer o melhor desempenho (em média, tempo constante O(1)) para operações de inserção (`put`) e busca (`get`), que são as ações mais comuns ao adicionar uma nota ou consultar o histórico de um estudante.

## Como Executar e Gerar o `output.txt`

Siga os passos abaixo para compilar o projeto e salvar a saída em um arquivo `output.txt`.

1.  **Pré-requisito:** Certifique-se de ter o **JDK (Java Development Kit)** instalado e configurado no seu sistema.

2.  **Organize os arquivos:** Coloque todos os arquivos (`Estudante.java`, `Disciplina.java`, `Main.java`, etc.) no mesmo diretório.

3.  **Abra o terminal:** Navegue pelo terminal (ou prompt de comando) até a pasta onde os arquivos foram salvos.

4.  **Compile o código:** Execute o seguinte comando para compilar todos os arquivos Java.
    ```bash
    javac *.java
    ```

5.  **Execute e gere o arquivo:** Rode o programa e use o operador `>` para redirecionar a saída padrão (o que seria impresso no console) para o arquivo `output.txt`. O arquivo será criado automaticamente no mesmo diretório.
    ```bash
    java Main > output.txt
    ```
    Após a execução, o arquivo `output.txt` conterá o resultado completo do programa.

## Desafio Encontrado

O principal desafio técnico encontrado durante o desenvolvimento foi a **necessidade de sobrescrever os métodos `equals()` e `hashCode()` na classe `Disciplina`**.

Inicialmente, ao adicionar disciplinas a um `Set`, poderiam ser inseridas disciplinas com o mesmo código, pois o Java, por padrão, compara a referência de memória dos objetos. O requisito de negócio, no entanto, era que a unicidade da disciplina fosse definida pelo seu **código**. Foi crucial entender que, para um `Set` (e outras coleções baseadas em hash) funcionar corretamente com objetos customizados, é obrigatório fornecer uma implementação customizada desses dois métodos. Ao basear o `equals()` na comparação dos códigos e o `hashCode()` no hash do código, o `Set` passou a identificar corretamente as duplicatas, garantindo a integridade dos dados conforme exigido pelo projeto.
