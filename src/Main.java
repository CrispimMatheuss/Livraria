import javax.swing.*;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Livro> livros = new ArrayList<>();
    private static AcervoLivro acervo = new AcervoLivro(livros);
    public static void main(String[] args) {

        chamaMenuPrincipal();
    }

    public static void chamaMenuPrincipal() {
        String[] opcoesMenu = {"Cadastro de Livros", "Exibir Acervo de Livros", "Empréstimo de Livros", "Devolução de Livros", "Sair"};
        int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Menu Principal",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
        switch (opcao){
            case 0:
                chamaCadastro(acervo);
                chamaMenuPrincipal();
            case 1:
                chamaLista(acervo);
                chamaMenuPrincipal();
            case 2:
                fazerEmprestimo(acervo);
                chamaMenuPrincipal();
            case 3:
                fazerDevolucao(acervo);
                chamaMenuPrincipal();
            case 4:
                System.exit(0);
        }
    }

    public static AcervoLivro chamaCadastro(AcervoLivro acervo) {
        boolean cadastra = true;

        while(cadastra){
            String nomeLivro = JOptionPane.showInputDialog(null, "Digite o nome do livro: ");
            String nomeAutor = JOptionPane.showInputDialog(null, "Digite o nome do autor: ");
            Livro livro = new Livro(nomeLivro, nomeAutor);
            acervo.adicionarLivro(livro);

            int opcaoCadastro = JOptionPane.showConfirmDialog(null, "Livro cadastrado com sucesso! Deseja cadastrar outro livro?", "Cadastro de Livros", JOptionPane.YES_NO_OPTION);
            if (opcaoCadastro != JOptionPane.YES_OPTION) {
                cadastra = false;
            }
        }
        return acervo;

    }

    public static void chamaLista(AcervoLivro acervo) {
        acervo.exibirAcervo();
    }

    public static void fazerEmprestimo(AcervoLivro acervo) {
        // Lista de livros disponíveis para empréstimo
        ArrayList<Livro> livrosDisponiveis = new ArrayList<>();
        for (Livro livro : acervo.getListaLivros()) {
            if (!livro.isEmprestado()) {
                livrosDisponiveis.add(livro);
            }
        }

        if (livrosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum livro disponível para empréstimo.");
            return;
        }

        // Solicita ao usuário o livro a ser emprestado
        String[] opcoesLivros = new String[livrosDisponiveis.size()];
        for (int i = 0; i < livrosDisponiveis.size(); i++) {
            opcoesLivros[i] = livrosDisponiveis.get(i).getNomeLivro();
        }

        int escolhaLivro = JOptionPane.showOptionDialog(null, "Escolha o livro para empréstimo:",
                "Empréstimo de Livros",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesLivros, opcoesLivros[0]);

        // Marca o livro como emprestado
        Livro livroSelecionado = livrosDisponiveis.get(escolhaLivro);
        livroSelecionado.setEmprestado(true);
        JOptionPane.showMessageDialog(null, "Livro emprestado: " + livroSelecionado.getNomeLivro());
    }

    public static void fazerDevolucao(AcervoLivro acervo) {
        // Lista de livros emprestados
        ArrayList<Livro> livrosEmprestados = new ArrayList<>();
        for (Livro livro : acervo.getListaLivros()) {
            if (livro.isEmprestado()) {
                livrosEmprestados.add(livro);
            }
        }

        if (livrosEmprestados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum livro emprestado para devolução.");
            return;
        }

        // Solicita ao usuário o livro a ser devolvido
        String[] opcoesLivros = new String[livrosEmprestados.size()];
        for (int i = 0; i < livrosEmprestados.size(); i++) {
            opcoesLivros[i] = livrosEmprestados.get(i).getNomeLivro();
        }

        int escolhaLivro = JOptionPane.showOptionDialog(null, "Escolha o livro para devolução:",
                "Devolução de Livros",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesLivros, opcoesLivros[0]);

        // Marca o livro como devolvido
        Livro livroSelecionado = livrosEmprestados.get(escolhaLivro);
        livroSelecionado.setEmprestado(false);
        JOptionPane.showMessageDialog(null, "Livro devolvido: " + livroSelecionado.getNomeLivro());
    }

}