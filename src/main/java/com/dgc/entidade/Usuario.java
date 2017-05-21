package com.dgc.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usuario", schema = "public")
public class Usuario extends Entidade {

	private static final long serialVersionUID = -142718630823260613L;

	@Id
	@SequenceGenerator(name = "usuario_id", sequenceName = "seq_usuario", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_id")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "apelido")
	private String apelido;

	@Column(name = "mail")
	private String mail;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "senha")
	private String senha;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "ind_deletado")
	private boolean indDeletado;

	public void setIndDeletado(boolean indDeletado) {
		super.setIndDeletado(indDeletado);
		this.indDeletado = indDeletado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isIndDeletado() {
		return indDeletado;
	}

}
