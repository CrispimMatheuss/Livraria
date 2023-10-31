import java.util.Date;

public class EmprestimoLivro {
    private int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    private Livro livro;
    private Date dataEmprestimo;
    private Date dataDevolucao;

    public EmprestimoLivro(int codigo, Livro livro, Date dataEmprestimo) {
        this.codigo = codigo;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
    }

    public EmprestimoLivro(Livro livro, Date dataEmprestimo) {
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
