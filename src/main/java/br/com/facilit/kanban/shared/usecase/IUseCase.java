package br.com.facilit.kanban.shared.usecase;

public interface IUseCase <T, R>{
    public R execute(T t);
}
