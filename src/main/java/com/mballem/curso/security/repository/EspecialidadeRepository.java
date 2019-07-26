package com.mballem.curso.security.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.curso.security.domain.Especialidade;
import org.springframework.data.repository.query.Param;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long>{

	@Query("select e from Especialidade e where e.titulo like :search%")
	Page<Especialidade> findAllByTitulo(@Param("search") String search, Pageable pageable);

	@Query("select e.titulo from Especialidade e where e.titulo like :termo%")
	List<String> findEspecialidadesByTermo(@Param("termo") String termo);

	@Query("select e from Especialidade e where e.titulo IN :titulos")
	Set<Especialidade> findByTitulos(@Param("titulos") String[] titulos);

	@Query("select e from Especialidade e "
			+ "join e.medicos m "
			+ "where m.id = :id") 
	Page<Especialidade> findByIdMedico(@Param("id") Long id, Pageable pageable);

	
}
