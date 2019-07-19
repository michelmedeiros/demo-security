package com.mballem.curso.security.web.controller;

import com.mballem.curso.security.domain.AbstractEntity;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("u")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	// abrir pagina cadastro de usuários
	@GetMapping({"/novo/cadastro/usuario"})
	public String cadastroUsuario(Usuario usuario) {
		return "usuario/cadastro";
	}

	// listar pagina cadastro de usuários
	@GetMapping({"/lista"})
	public String listarUsuarios() {
		return "usuario/lista";
	}

	// listar pagina cadastro de usuários
	@GetMapping({"/datatables/server/usuarios"})
	public ResponseEntity<?> buscarUsuarios(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarUsuarios(request));
	}


	// editar cadastro de usuários
	@GetMapping({"/editar/credenciais/usuario/{id}"})
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {
		return new ModelAndView("usuario/cadastro", "usuario", service.buscarUsuarioPorId(id));
	}

	// editar cadastro de dados pessoais usuários
	@GetMapping({"/editar/dados/usuario/{id}/perfis/{perfis}"})
	public ModelAndView preEditarDadosPessoais(@PathVariable("id") Long id, @PathVariable("perfis") Long[] perfisId) {
		return new ModelAndView("redirect:/u/lista");
	}

	// cadastrar usuários
	@PostMapping(value = "/cadastro/salvar")
	public String salvarUsuarios(Usuario usuario, RedirectAttributes redirectAttributes) {
		List<Long> perfilList = usuario.getPerfis().stream().map(AbstractEntity::getId)
				.collect(Collectors.toList());

		boolean isInvalidPerfil = perfilList.size() > 2 ||
				perfilList.containsAll(Arrays.asList(1L, 3L)) ||
						perfilList.containsAll(Arrays.asList(2L, 3L));
		if(isInvalidPerfil) {
			redirectAttributes.addFlashAttribute("falha", "Usuário/Perfil Inválidos");
		} else {
			try {
				service.salvarUsuario(usuario);
				redirectAttributes.addFlashAttribute("sucesso", "Sucesso ao salvar usuário");
			} catch (DataIntegrityViolationException ex) {
				redirectAttributes.addFlashAttribute("falha", "Cadastro não realizado, já existe um usuário cadastrado com esse email");
			}

		}
		return "redirect:/u/novo/cadastro/usuario";
	}

}
