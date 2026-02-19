package com.javanauta.bff_agendador_tarefas.controller;



import com.javanauta.bff_agendador_tarefas.business.UsuarioService;
import com.javanauta.bff_agendador_tarefas.business.dto.in.EnderecoDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.LoginDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.TelefoneDTORequest;
import com.javanauta.bff_agendador_tarefas.business.dto.in.UsuarioDTORequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag (name = "Usuário" , description = "Cadastro e login de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation (summary = "Salvar Usuários", description = "Salvar um novo usuário")
    @ApiResponse (responseCode = "200" , description = "Usuário salvo com sucesso")
    @ApiResponse (responseCode = "400" , description = "Usuário já cadastrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity <UsuarioDTORequest> salvaUsuario (@RequestBody UsuarioDTORequest usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario (usuarioDTO));
    }

    @PostMapping("/login")
    @Operation (summary = "Login Usuários", description = "Login do usuário")
    @ApiResponse (responseCode = "200" , description = "Usuário logado com sucesso")
    @ApiResponse (responseCode = "401" , description = "Credenciais inválidas")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public String login(@RequestBody LoginDTORequest usuarioDTO) {
      return usuarioService.loginUsuario(usuarioDTO);
    }

    @GetMapping
    @Operation (summary = "Buscar dados de Usuários por email", description = "Buscar dados do usuário")
    @ApiResponse (responseCode = "200" , description = "Usuário encontrado")
    @ApiResponse (responseCode = "404" , description = "Usuário não cadastrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<UsuarioDTORequest> buscaUsuarioPorEmail(@RequestParam("email") String email,
                                                                  @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation (summary = "Deletar Usuários", description = "Deletar usuário")
    @ApiResponse (responseCode = "200" , description = "Usuário deletado com sucesso")
    @ApiResponse (responseCode = "404" , description = "Usuário não encontrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity<Void> deletaUsuarioPorEmail (@PathVariable String email,
                                                       @RequestHeader ("Authorization") String token){
        usuarioService.deletaUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation (summary = "Atualizar dados de Usuários", description = "Atualizar dados do usuário")
    @ApiResponse (responseCode = "200" , description = "Usuário atualizado com sucesso")
    @ApiResponse (responseCode = "404" , description = "Usuário não encontrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity <UsuarioDTORequest> atualizaDadosUsuario (@RequestBody UsuarioDTORequest dto,
                                                                    @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation (summary = "Atualiza endereço de Usuários", description = "Atualiza endereço do usuário")
    @ApiResponse (responseCode = "200" , description = "Endereço atualizado com sucesso")
    @ApiResponse (responseCode = "404" , description = "Usuário não encontrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity <EnderecoDTORequest> atualizaEndereco(@RequestBody EnderecoDTORequest dto, @RequestParam ("id")Long id, @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto, token));
    }

    @PutMapping("/telefone")
    @Operation (summary = "Atualiza endereço de Usuários", description = "Atualiza endereço do usuário")
    @ApiResponse (responseCode = "200" , description = "Telefone atualizado com sucesso")
    @ApiResponse (responseCode = "404" , description = "Usuário não encontrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity <TelefoneDTORequest> atualizaTelefone (@RequestBody TelefoneDTORequest dto, @RequestParam ("id") Long id, @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto, token));
    }

    @PostMapping ("/endereco")
    @Operation (summary = "Salvar endereços de Usuários", description = "Salvar endereço do usuário")
    @ApiResponse (responseCode = "200" , description = "Endereço salvo com sucesso")
    @ApiResponse (responseCode = "404" , description = "Usuario não encontrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity <EnderecoDTORequest> cadastraEndereco (@RequestBody EnderecoDTORequest dto, @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    @PostMapping ("/telefone")
    @Operation (summary = "Salva telefones de Usuários", description = "Salvar telefone do usuário")
    @ApiResponse (responseCode = "200" , description = "Telefone salvo com sucesso")
    @ApiResponse (responseCode = "404" , description = "Usuario não encontrado")
    @ApiResponse (responseCode = "500" , description = "Erro de servidor")
    public ResponseEntity <TelefoneDTORequest> cadastraTelefone (@RequestBody TelefoneDTORequest dto, @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }
}
