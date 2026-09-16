package br.com.iniflex.infra.repositorio;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Consulta;
import br.com.iniflex.dominio.Direcao;
import br.com.iniflex.dominio.Funcionario;
import br.com.iniflex.dominio.Ordenacao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FuncionarioMemoriaRepositorio implements FuncionarioRepositorio {
    private final List<Funcionario> funcionarios = new ArrayList<>();
    private final Map<String, List<Funcionario>> funcionariosPorFuncao = new LinkedHashMap<>();

    @Override
    public void salvar(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        funcionarios.stream()
                .filter(existente -> existente.getNome().equals(funcionario.getNome()))
                .findFirst()
                .ifPresentOrElse(
                        existente -> {
                            funcionarios.set(funcionarios.indexOf(existente), funcionario);
                            removerDoMapa(existente);
                            adicionarNoMapa(funcionario);
                        },
                        () -> {
                            funcionarios.add(funcionario);
                            adicionarNoMapa(funcionario);
                        }
                );
    }

    @Override
    public Funcionario remover(String nome) {
        Funcionario funcionario = buscarPorNome(nome);
        funcionarios.remove(funcionario);
        removerDoMapa(funcionario);
        return funcionario;
    }

    @Override
    public List<Funcionario> listar() {
        return listar(null);
    }

    @Override
    public List<Funcionario> listar(Consulta consulta) {
        Stream<Funcionario> stream = funcionarios.stream();
        if (consulta != null) {
            if (consulta.getOrdenacao() != null) {
                stream = stream.sorted(comparador(consulta.getOrdenacao()));
            }
            if (consulta.getLimite() != null) {
                stream = stream.limit(consulta.getLimite());
            }
        }
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }

    private Comparator<Funcionario> comparador(Ordenacao ordenacao) {
        Comparator<Funcionario> comparador = switch (ordenacao.getCampo()) {
            case NOME -> Comparator.comparing(Funcionario::getNome);
            case IDADE -> Comparator.comparingInt(Funcionario::getIdade);
        };
        if (Direcao.DECRESCENTE.equals(ordenacao.getDirecao())) {
            return comparador.reversed();
        }
        return comparador;
    }

    @Override
    public Map<String, List<Funcionario>> listarPorFuncao() {
        Map<String, List<Funcionario>> copia = new LinkedHashMap<>();
        funcionariosPorFuncao.forEach((funcao, lista) -> copia.put(funcao, new ArrayList<>(lista)));
        return copia;
    }

    private Funcionario buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        String nomeBusca = nome.trim();
        return funcionarios.stream()
                .filter(funcionario -> funcionario.getNome().equals(nomeBusca))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
    }

    private void adicionarNoMapa(Funcionario funcionario) {
        funcionariosPorFuncao
                .computeIfAbsent(funcionario.getFuncao(), chave -> new ArrayList<>())
                .add(funcionario);
    }

    private void removerDoMapa(Funcionario funcionario) {
        List<Funcionario> daFuncao = funcionariosPorFuncao.get(funcionario.getFuncao());
        if (daFuncao == null) {
            return;
        }
        daFuncao.remove(funcionario);
        if (daFuncao.isEmpty()) {
            funcionariosPorFuncao.remove(funcionario.getFuncao());
        }
    }
}
