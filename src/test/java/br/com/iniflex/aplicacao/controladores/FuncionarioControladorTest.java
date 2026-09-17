package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.AumentarSalarioFuncionario;
import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.casodeuso.ListarQuantidadeSalariosMinimos;
import br.com.iniflex.aplicacao.views.FuncionarioViewFake;
import br.com.iniflex.aplicacao.views.FuncionarioViewFake.Chamada;
import br.com.iniflex.dominio.Campo;
import br.com.iniflex.dominio.Direcao;
import br.com.iniflex.dominio.Funcionario;
import br.com.iniflex.dominio.Ordenacao;
import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuncionarioControladorTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private FuncionarioViewFake view;
    private FuncionarioControlador controlador;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;
    private CadastrarFuncionario.Input caio;
    private CadastrarFuncionario.Input miguel;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        view = new FuncionarioViewFake();
        controlador = controladorComRepositorio(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        caio = new CadastrarFuncionario.Input("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
        miguel = new CadastrarFuncionario.Input("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor");
    }

    @Test
    void cadastrandoValido() {
        controlador.cadastrarFuncionario(maria);
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(1, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).getNome());
        assertEquals(maria.dataNascimento(), funcionarios.get(0).getDataNascimento());
        assertEquals(maria.salario(), funcionarios.get(0).getSalario());
        assertEquals(maria.funcao(), funcionarios.get(0).getFuncao());
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionário cadastrado"),
                        new Chamada("exibir", new CadastrarFuncionario.Output(
                                maria.nome(),
                                maria.dataNascimento(),
                                maria.salario(),
                                maria.funcao()
                        ))
                ),
                view.chamadas()
        );
    }

    @Test
    void cadastrandoInvalido() {
        controlador.cadastrarFuncionario(null);
        assertEquals(0, repositorio.listar().size());
        assertEquals(
                List.of(new Chamada("toast", false, "Entrada não pode ser nula")),
                view.chamadas()
        );
    }

    @Test
    void excluindoValido() {
        controlador.cadastrarFuncionario(maria);
        view.limpar();
        controlador.excluirFuncionario(new ExcluirFuncionario.Input("Maria"));
        assertEquals(0, repositorio.listar().size());
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionário excluído"),
                        new Chamada("exibir", new ExcluirFuncionario.Output(
                                maria.nome(),
                                maria.dataNascimento(),
                                maria.salario(),
                                maria.funcao()
                        ))
                ),
                view.chamadas()
        );
    }

    @Test
    void excluindoInvalido() {
        controlador.excluirFuncionario(null);
        assertEquals(0, repositorio.listar().size());
        assertEquals(
                List.of(new Chamada("toast", false, "Entrada não pode ser nula")),
                view.chamadas()
        );
    }

    @Test
    void listandoValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(joao);
        view.limpar();
        controlador.listarFuncionarios();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados"),
                        new Chamada("exibir", new ListarFuncionarios.Output(List.of(
                                new ListarFuncionarios.Output.Funcionario(
                                        maria.nome(),
                                        maria.dataNascimento(),
                                        maria.salario(),
                                        maria.funcao()
                                ),
                                new ListarFuncionarios.Output.Funcionario(
                                        joao.nome(),
                                        joao.dataNascimento(),
                                        joao.salario(),
                                        joao.funcao()
                                )
                        )))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoVazio() {
        controlador.listarFuncionarios();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados"),
                        new Chamada("exibir", new ListarFuncionarios.Output(List.of()))
                ),
                view.chamadas()
        );
    }

    @Test
    void aumentandoSalarioValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(joao);
        view.limpar();
        controlador.aumentarSalarioFuncionario(new AumentarSalarioFuncionario.Input(10));
        assertEquals(new BigDecimal("2210.38"), repositorio.listar().get(0).getSalario());
        assertEquals(new BigDecimal("2512.82"), repositorio.listar().get(1).getSalario());
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Salário de funcionários aumentado"),
                        new Chamada("exibir", new AumentarSalarioFuncionario.Output(List.of(
                                new AumentarSalarioFuncionario.Output.Funcionario(
                                        maria.nome(),
                                        maria.dataNascimento(),
                                        new BigDecimal("2210.38"),
                                        maria.funcao()
                                ),
                                new AumentarSalarioFuncionario.Output.Funcionario(
                                        joao.nome(),
                                        joao.dataNascimento(),
                                        new BigDecimal("2512.82"),
                                        joao.funcao()
                                )
                        )))
                ),
                view.chamadas()
        );
    }

    @Test
    void aumentandoSalarioInvalido() {
        controlador.cadastrarFuncionario(maria);
        view.limpar();
        controlador.aumentarSalarioFuncionario(null);
        assertEquals(maria.salario(), repositorio.listar().get(0).getSalario());
        assertEquals(
                List.of(new Chamada("toast", false, "Entrada não pode ser nula")),
                view.chamadas()
        );
    }

    @Test
    void listandoPorFuncaoValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(joao);
        controlador.cadastrarFuncionario(caio);
        view.limpar();
        controlador.listarFuncionariosPorFuncao();
        Map<String, List<ListarFuncionariosPorFuncao.Output.Funcionario>> funcionariosPorFuncao = new LinkedHashMap<>();
        funcionariosPorFuncao.put("Operador", List.of(
                new ListarFuncionariosPorFuncao.Output.Funcionario(
                        maria.nome(),
                        maria.dataNascimento(),
                        maria.salario(),
                        maria.funcao()
                ),
                new ListarFuncionariosPorFuncao.Output.Funcionario(
                        joao.nome(),
                        joao.dataNascimento(),
                        joao.salario(),
                        joao.funcao()
                )
        ));
        funcionariosPorFuncao.put("Coordenador", List.of(
                new ListarFuncionariosPorFuncao.Output.Funcionario(
                        caio.nome(),
                        caio.dataNascimento(),
                        caio.salario(),
                        caio.funcao()
                )
        ));
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados por função"),
                        new Chamada("exibir", new ListarFuncionariosPorFuncao.Output(funcionariosPorFuncao))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoPorFuncaoVazio() {
        controlador.listarFuncionariosPorFuncao();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados por função"),
                        new Chamada("exibir", new ListarFuncionariosPorFuncao.Output(Map.of()))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoPorAniversarioValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(joao);
        controlador.cadastrarFuncionario(miguel);
        view.limpar();
        controlador.listarFuncionariosPorAniversario(new ListarFuncionariosPorAniversario.Input(List.of(10, 12)));
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados por aniversário"),
                        new Chamada("exibir", new ListarFuncionariosPorAniversario.Output(List.of(
                                new ListarFuncionariosPorAniversario.Output.Funcionario(
                                        maria.nome(),
                                        maria.dataNascimento(),
                                        maria.salario(),
                                        maria.funcao()
                                ),
                                new ListarFuncionariosPorAniversario.Output.Funcionario(
                                        miguel.nome(),
                                        miguel.dataNascimento(),
                                        miguel.salario(),
                                        miguel.funcao()
                                )
                        )))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoPorAniversarioVazio() {
        controlador.listarFuncionariosPorAniversario(new ListarFuncionariosPorAniversario.Input(List.of(10, 12)));
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados por aniversário"),
                        new Chamada("exibir", new ListarFuncionariosPorAniversario.Output(List.of()))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoPorAniversarioInvalido() {
        controlador.listarFuncionariosPorAniversario(null);
        assertEquals(
                List.of(new Chamada("toast", false, "Entrada não pode ser nula")),
                view.chamadas()
        );
    }

    @Test
    void listandoMaisVelhoValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(caio);
        view.limpar();
        controlador.listarFuncionarioMaisVelho();
        int idade = Period.between(caio.dataNascimento(), LocalDate.now()).getYears();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionário mais velho listado"),
                        new Chamada("exibir", new ListarFuncionarioMaisVelho.Output(List.of(
                                new ListarFuncionarioMaisVelho.Output.Funcionario(caio.nome(), idade)
                        )))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoMaisVelhoVazio() {
        controlador.listarFuncionarioMaisVelho();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionário mais velho listado"),
                        new Chamada("exibir", new ListarFuncionarioMaisVelho.Output(List.of()))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoQuantidadeSalariosMinimosValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(joao);
        view.limpar();
        controlador.listarQuantidadeSalariosMinimos();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Quantidade de salários mínimos listada"),
                        new Chamada("exibir", new ListarQuantidadeSalariosMinimos.Output(List.of(
                                new ListarQuantidadeSalariosMinimos.Output.Funcionario(maria.nome(), new BigDecimal("1.66")),
                                new ListarQuantidadeSalariosMinimos.Output.Funcionario(joao.nome(), new BigDecimal("1.88"))
                        )))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoQuantidadeSalariosMinimosVazio() {
        controlador.listarQuantidadeSalariosMinimos();
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Quantidade de salários mínimos listada"),
                        new Chamada("exibir", new ListarQuantidadeSalariosMinimos.Output(List.of()))
                ),
                view.chamadas()
        );
    }

    @Test
    void listandoOrdenadoPorNome() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(joao);
        controlador.cadastrarFuncionario(caio);
        view.limpar();
        controlador.listarFuncionarios(new Ordenacao(Campo.NOME, Direcao.CRESCENTE));
        assertEquals(
                List.of(
                        new Chamada("toast", true, "Funcionários listados"),
                        new Chamada("exibir", new ListarFuncionarios.Output(List.of(
                                new ListarFuncionarios.Output.Funcionario(
                                        caio.nome(),
                                        caio.dataNascimento(),
                                        caio.salario(),
                                        caio.funcao()
                                ),
                                new ListarFuncionarios.Output.Funcionario(
                                        joao.nome(),
                                        joao.dataNascimento(),
                                        joao.salario(),
                                        joao.funcao()
                                ),
                                new ListarFuncionarios.Output.Funcionario(
                                        maria.nome(),
                                        maria.dataNascimento(),
                                        maria.salario(),
                                        maria.funcao()
                                )
                        )))
                ),
                view.chamadas()
        );
    }

    @Test
    void tratandoErroSemMensagem() {
        controlador = controladorComRepositorio(new FuncionarioMemoriaRepositorio() {
            @Override
            public void salvar(Funcionario funcionario) {
                throw new RuntimeException();
            }
        });
        controlador.cadastrarFuncionario(maria);
        assertEquals(
                List.of(new Chamada("toast", false, "Erro inesperado")),
                view.chamadas()
        );
    }

    @Test
    void tratandoErroComMensagemEmBranco() {
        controlador = controladorComRepositorio(new FuncionarioMemoriaRepositorio() {
            @Override
            public void salvar(Funcionario funcionario) {
                throw new RuntimeException("   ");
            }
        });
        controlador.cadastrarFuncionario(maria);
        assertEquals(
                List.of(new Chamada("toast", false, "Erro inesperado")),
                view.chamadas()
        );
    }

    private FuncionarioControlador controladorComRepositorio(FuncionarioMemoriaRepositorio repositorio) {
        return new FuncionarioControlador(
                new CadastrarFuncionario(repositorio),
                new ExcluirFuncionario(repositorio),
                new ListarFuncionarios(repositorio),
                new AumentarSalarioFuncionario(repositorio),
                new ListarFuncionariosPorFuncao(repositorio),
                new ListarFuncionariosPorAniversario(repositorio),
                new ListarFuncionarioMaisVelho(repositorio),
                new ListarQuantidadeSalariosMinimos(repositorio),
                view
        );
    }
}
