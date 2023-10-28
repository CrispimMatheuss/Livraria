import javax.swing.*;
import java.util.ArrayList;

public class AcervoLivro {

    private ArrayList<Livro> listaLivros;

    public AcervoLivro(ArrayList<Livro> livros) {
        this.listaLivros = livros;
    }

    public void adicionarLivro(Livro livro) {
        listaLivros.add(livro);
        System.out.println("Livro inserido: " + livro.getNomeLivro());
    }

    public void exibirAcervo() {
        StringBuilder acervoTexto = new StringBuilder();
        acervoTexto.append("Acervo de Livros:\n");

        for (Livro livro : listaLivros) {
            String emprestado = livro.isEmprestado() ? "Livro Emprestado" : "Livro Disponível";
            acervoTexto.append("- ").append(livro.getNomeLivro()).append(" do autor: ").append(livro.getNomeAutor()).append(" - ").append(emprestado).append("\n");
        }

        JOptionPane.showMessageDialog(null, acervoTexto.toString(), "Acervo de Livros", JOptionPane.INFORMATION_MESSAGE);
    }

    public ArrayList<Livro> getListaLivros() {
        return listaLivros;
    }
}

