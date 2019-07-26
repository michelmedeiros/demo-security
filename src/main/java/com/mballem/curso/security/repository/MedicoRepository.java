package com.mballem.curso.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.curso.security.domain.Medico;
import org.springframework.data.repository.query.Param;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	@Query("select m from Medico m where m.usuario.id = :id")
	Optional<Medico> findByUsuarioId(@Param("id") Long id);

	@Query("select m from Medico m where m.usuario.email like :email")
	Optional<Medico> findByUsuarioEmail(@Param("email") String email);

	@Query("select distinct m from Medico m "
			+ "join m.especialidades e "
			+ "where e.titulo like :titulo "
			+ "and m.usuario.ativo = true")
	List<Medico> findByMedicosPorEspecialidade(@Param("titulo") String titulo);

	@Query("select m.id "
			+ "from Medico m "
			+ "join m.especialidades e "
			+ "join m.agendamentos a "
			+ "where "
			+ "a.especialidade.id = :idEsp AND a.medico.id = :idMed")
	Optional<Long> hasEspecialidadeAgendada(@Param("idMed") Long idMed, @Param("idEsp") Long idEsp);
}
