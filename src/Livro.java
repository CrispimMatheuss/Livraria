public class Livro {

    private String nomeLivro;
    private String nomeAutor;
    private boolean emprestado; // Adicione o atributo emprestado

    @Override
    public String toString() {
        return "Livro{" +
                "nomeLivro='" + nomeLivro + '\'' +
                ", nomeAutor='" + nomeAutor + '\'' +
                '}';
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    public Livro(String nomeLivro, String nomeAutor) {
        this.nomeLivro = nomeLivro;
        this.nomeAutor = nomeAutor;
        this.emprestado = false; // Inicialmente, o livro não está emprestado

    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
}