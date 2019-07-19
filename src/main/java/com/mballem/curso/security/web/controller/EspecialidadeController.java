package com.mballem.curso.security.web.controller;

import com.mballem.curso.security.domain.Especialidade;
import com.mballem.curso.security.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("especialidades")
public class EspecialidadeController {

	@Autowired
	private EspecialidadeService service;

	// abrir pagina cadastro de especialidades
	@GetMapping({"", "/"})
	public String abrirEspecialidades(Especialidade especialidade) {
		return "especialidade/especialidade";
	}

	// listagem de  especialidades
	@GetMapping("/datatables/server")
	public ResponseEntity<?> listarEspecialidades(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarEspecialidades(request));
	}

	// busca especialidade para edição
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modalMap) {
		modalMap.addAttribute("especialidade", service.buscarPorId(id));
		return "especialidade/especialidade";
	}


	// exclui especialidade
	@GetMapping("/excluir/{id}")
	public String excluirEspecialidade(@PathVariable("id") Long id, RedirectAttributes attributes) {
		service.excluir(id);
		attributes.addFlashAttribute("sucesso", "Exclusão realizada com sucesso");
		return "redirect:/especialidades";
	}

	// abrir pagina cadastro de especialidades
	@PostMapping({"/salvar"})
	public String salvarEspecialidades(Especialidade especialidade, RedirectAttributes attributes) {
		service.salvar(especialidade);
		attributes.addFlashAttribute("sucesso", "Operação realizada com sucesso");
		return "redirect:/especialidades";
	}

}
