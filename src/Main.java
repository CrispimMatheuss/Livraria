import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private static ArrayList<Livro> livros = new ArrayList<>();
    private static AcervoLivro acervo = new AcervoLivro(livros);
    private static ArrayList<EmprestimoLivro> emprestimos = new ArrayList<>();
    private static int proximoCodigoEmprestimo = 1; // Inicializa o próximo código disponível

    public static void main(String[] args) {

        chamaMenuPrincipal();
    }

    public static void chamaMenuPrincipal() {
        String[] opcoesMenu = {"Cadastro de Livros", "Exibir Acervo de Livros", "Empréstimo de Livros", "Devolução de Livros", "Relatório de Empréstimos", "Sair"};
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
                exibirEmprestimos();
                chamaMenuPrincipal();
            case 5:
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

        // Cria o objeto EmprestimoLivro
        EmprestimoLivro emprestimo = new EmprestimoLivro(proximoCodigoEmprestimo, livroSelecionado, new Date());

        proximoCodigoEmprestimo++;

        // Adiciona o empréstimo à lista de empréstimos
        emprestimos.add(emprestimo);

        JOptionPane.showMessageDialog(null, "Livro emprestado: " + livroSelecionado.getNomeLivro());
    }

//    public static void fazerDevolucao(AcervoLivro acervo) {
//        // Lista de livros emprestados
//        ArrayList<Livro> livrosEmprestados = new ArrayList<>();
//        for (Livro livro : acervo.getListaLivros()) {
//            if (livro.isEmprestado()) {
//                livrosEmprestados.add(livro);
//            }
//        }
//
//        if (livrosEmprestados.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Nenhum livro emprestado para devolução.");
//            return;
//        }
//
//        // Solicita ao usuário o livro a ser devolvido
//        String[] opcoesLivros = new String[livrosEmprestados.size()];
//        for (int i = 0; i < livrosEmprestados.size(); i++) {
//            opcoesLivros[i] = livrosEmprestados.get(i).getNomeLivro();
//        }
//
//        int escolhaLivro = JOptionPane.showOptionDialog(null, "Escolha o livro para devolução:",
//                "Devolução de Livros",
//                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesLivros, opcoesLivros[0]);
//
//        // Marca o livro como devolvido
//        Livro livroSelecionado = livrosEmprestados.get(escolhaLivro);
//        livroSelecionado.setEmprestado(false);
//
//        EmprestimoLivro emprestimo = null;
//        for (EmprestimoLivro emp : emprestimos) {
//            if (emp.getLivro() == livroSelecionado) {
//                emprestimo = emp;
//                break;
//            }
//        }
//
//        emprestimo.setDataDevolucao(new Date());
//        JOptionPane.showMessageDialog(null, "Livro devolvido: " + livroSelecionado.getNomeLivro());
//    }

    public static void fazerDevolucao(AcervoLivro acervo) {
        // Lista de empréstimos
        ArrayList<EmprestimoLivro> emprestimosAtivos = new ArrayList<>();

        for (EmprestimoLivro emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                emprestimosAtivos.add(emprestimo);
            }
        }

        if (emprestimosAtivos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum empréstimo ativo para devolução.");
            return;
        }

        // Solicita ao usuário o empréstimo a ser devolvido
        String[] opcoesEmprestimos = new String[emprestimosAtivos.size()];

        for (int i = 0; i < emprestimosAtivos.size(); i++) {
            EmprestimoLivro emprestimo = emprestimosAtivos.get(i);
            opcoesEmprestimos[i] = "Código: " + emprestimo.getCodigo() + " - Livro: " + emprestimo.getLivro().getNomeLivro();
        }

        int escolhaEmprestimo = JOptionPane.showOptionDialog(null, "Escolha o empréstimo para devolução:",
                "Devolução de Livros",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesEmprestimos, opcoesEmprestimos[0]);

        // Obtém o empréstimo selecionado
        EmprestimoLivro emprestimoSelecionado = emprestimosAtivos.get(escolhaEmprestimo);

        // Marca o livro associado ao empréstimo como não emprestado
        emprestimoSelecionado.getLivro().setEmprestado(false);

        // Define a data de devolução como a data atual
        emprestimoSelecionado.setDataDevolucao(new Date());

        JOptionPane.showMessageDialog(null, "Livro devolvido: " + emprestimoSelecionado.getLivro().getNomeLivro());
    }


    public static void exibirEmprestimos() {
        StringBuilder emprestimosTexto = new StringBuilder();
        emprestimosTexto.append("Lista de Empréstimos:\n\n");

        for (EmprestimoLivro emprestimo : emprestimos) {
            emprestimosTexto.append("Livro: ").append(emprestimo.getLivro().getNomeLivro()).append("\n");
            emprestimosTexto.append("Autor: ").append(emprestimo.getLivro().getNomeAutor()).append("\n");
            emprestimosTexto.append("Data de Empréstimo: ").append(emprestimo.getDataEmprestimo()).append("\n");
            emprestimosTexto.append("Data de Devolução: ").append(emprestimo.getDataDevolucao()).append("\n");
            emprestimosTexto.append("\n");
        }

        JOptionPane.showMessageDialog(null, emprestimosTexto.toString(), "Lista de Empréstimos", JOptionPane.INFORMATION_MESSAGE);
    }

}