import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private static ArrayList<Livro> livros = new ArrayList<>();
    private static AcervoLivro acervo = new AcervoLivro(livros);
    private static ArrayList<EmprestimoLivro> emprestimos = new ArrayList<>();
    private static int proximoCodigoEmprestimo = 1; // Inicializa o próximo código disponível

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            chamaMenuPrincipal();
        });
    }

    private static void chamaMenuPrincipal() {
        JFrame frame = new JFrame("Biblioteca");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opções");
        JMenuItem cadastroItem = new JMenuItem("Cadastro de Livros");
        cadastroItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chamaCadastro(acervo);
            }
        });

        JMenuItem listaItem = new JMenuItem("Exibir Acervo de Livros");
        listaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chamaLista(acervo);
            }
        });

        JMenuItem emprestimoItem = new JMenuItem("Empréstimo de Livros");
        emprestimoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerEmprestimo(acervo);
            }
        });

        JMenuItem devolucaoItem = new JMenuItem("Devolução de Livros");
        devolucaoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerDevolucao(acervo);
            }
        });

        JMenuItem relatorioItem = new JMenuItem("Relatório de Empréstimos");
        relatorioItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirEmprestimos();
            }
        });

        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(cadastroItem);
        menu.add(listaItem);
        menu.add(emprestimoItem);
        menu.add(devolucaoItem);
        menu.add(relatorioItem);
        menu.addSeparator();
        menu.add(sairItem);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setVisible(true);
    }

    private static void chamaCadastro(AcervoLivro acervo) {
        JFrame janelaCadastro = new JFrame("Cadastro de Livros");
        janelaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelCadastro = new JPanel();
        painelCadastro.setLayout(new GridLayout(3, 2));

        JLabel labelNomeLivro = new JLabel("Nome do Livro:");
        JTextField campoNomeLivro = new JTextField();
        JLabel labelNomeAutor = new JLabel("Nome do Autor:");
        JTextField campoNomeAutor = new JTextField();

        JButton botaoCadastrar = new JButton("Cadastrar");

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeLivro = campoNomeLivro.getText();
                String nomeAutor = campoNomeAutor.getText();
                Livro livro = new Livro(nomeLivro, nomeAutor);
                acervo.adicionarLivro(livro);
                JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
                janelaCadastro.dispose();
            }
        });

        painelCadastro.add(labelNomeLivro);
        painelCadastro.add(campoNomeLivro);
        painelCadastro.add(labelNomeAutor);
        painelCadastro.add(campoNomeAutor);
        painelCadastro.add(new JLabel());
        painelCadastro.add(botaoCadastrar);
        janelaCadastro.setPreferredSize(new Dimension(400, 300));
        janelaCadastro.setLocationRelativeTo(null);
        janelaCadastro.add(painelCadastro);
        janelaCadastro.pack();
        janelaCadastro.setVisible(true);
    }

    public static void chamaLista(AcervoLivro acervo) {
        acervo.exibirAcervo();
    }

    private static void fazerEmprestimo(AcervoLivro acervo) {
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

        JFrame janelaEmprestimo = new JFrame("Empréstimo de Livros");
        janelaEmprestimo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelEmprestimo = new JPanel();
        painelEmprestimo.setLayout(new BorderLayout());

        DefaultListModel<String> listaLivrosModel = new DefaultListModel<>();
        JList<String> listaLivros = new JList<>(listaLivrosModel);

        for (Livro livro : livrosDisponiveis) {
            listaLivrosModel.addElement(livro.getNomeLivro());
        }

        JScrollPane scrollPane = new JScrollPane(listaLivros);
        painelEmprestimo.add(scrollPane, BorderLayout.CENTER);

        JButton botaoEmprestimo = new JButton("Empréstimo");
        botaoEmprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIdx = listaLivros.getSelectedIndex();
                if (selectedIdx >= 0) {
                    Livro livroSelecionado = livrosDisponiveis.get(selectedIdx);
                    livroSelecionado.setEmprestado(true);

                    EmprestimoLivro empréstimo = new EmprestimoLivro(proximoCodigoEmprestimo, livroSelecionado, new Date());
                    proximoCodigoEmprestimo++;

                    emprestimos.add(empréstimo);
                    JOptionPane.showMessageDialog(null, "Livro emprestado: " + livroSelecionado.getNomeLivro());
                    janelaEmprestimo.dispose();
                }
            }
        });

        painelEmprestimo.add(botaoEmprestimo, BorderLayout.SOUTH);

        janelaEmprestimo.add(painelEmprestimo);
        janelaEmprestimo.setPreferredSize(new Dimension(400, 300));
        janelaEmprestimo.setLocationRelativeTo(null);
        janelaEmprestimo.pack();
        janelaEmprestimo.setVisible(true);
    }


    private static void fazerDevolucao(AcervoLivro acervo) {
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

        JFrame janelaDevolucao = new JFrame("Devolução de Livros");
        janelaDevolucao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelDevolucao = new JPanel();
        painelDevolucao.setLayout(new BorderLayout());

        DefaultListModel<String> listaEmprestimosModel = new DefaultListModel<>();
        JList<String> listaEmprestimos = new JList<>(listaEmprestimosModel);

        for (EmprestimoLivro emprestimo : emprestimosAtivos) {
            listaEmprestimosModel.addElement("Código: " + emprestimo.getCodigo() + " - Livro: " + emprestimo.getLivro().getNomeLivro());
        }

        JScrollPane scrollPane = new JScrollPane(listaEmprestimos);
        painelDevolucao.add(scrollPane, BorderLayout.CENTER);

        JButton botaoDevolucao = new JButton("Devolução");
        botaoDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIdx = listaEmprestimos.getSelectedIndex();
                if (selectedIdx >= 0) {
                    EmprestimoLivro emprestimoSelecionado = emprestimosAtivos.get(selectedIdx);
                    emprestimoSelecionado.getLivro().setEmprestado(false);
                    emprestimoSelecionado.setDataDevolucao(new Date());
                    JOptionPane.showMessageDialog(null, "Livro devolvido: " + emprestimoSelecionado.getLivro().getNomeLivro());
                    janelaDevolucao.dispose();
                }
            }
        });

        painelDevolucao.add(botaoDevolucao, BorderLayout.SOUTH);
        janelaDevolucao.setPreferredSize(new Dimension(400, 300));
        janelaDevolucao.setLocationRelativeTo(null);
        janelaDevolucao.add(painelDevolucao);
        janelaDevolucao.pack();
        janelaDevolucao.setVisible(true);
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
