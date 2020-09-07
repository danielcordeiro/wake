package com.dgc.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	@Column(name = "nome2")
	private String nome2;

	@Column(name = "apelido")
	private String apelido;

	@Column(name = "mail")
	private String mail;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "telefone2")
	private String telefone2;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "senha")
	private String senha;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "ind_deletado")
	private boolean indDeletado;

	@Column(name = "termo", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private boolean termo;

	@Transient
	private boolean termo2;
	@Transient
	private boolean termo3;
	@Transient
	private boolean termo4;
	@Transient
	private boolean termo5;
	@Transient
	private boolean termo6;
	@Transient
	private boolean termo7;
	@Transient
	private boolean termo8;
	@Transient
	private boolean termo9;
	@Transient
	private boolean termo10;
	@Transient
	private boolean termo11;
	@Transient
	private String dataNascimentoString;

	public void setIndDeletado(boolean indDeletado) {
		super.setIndDeletado(indDeletado);
		this.indDeletado = indDeletado;
	}

	public String getNome() {
		return nome;
	}

	public String getApelidoNome() {
		String apelidoNome = getApelido();
		if (getNome().length() < 13) {
			apelidoNome = apelidoNome + " / " + getNome();
		} else {
			apelidoNome = apelidoNome + " / " + getNome().substring(0, 12);
		}
		return apelidoNome;
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

	public boolean isTermo() {
		return termo;
	}

	public void setTermo(boolean termo) {
		this.termo = termo;
	}

	public boolean isTermo2() {
		return termo2;
	}

	public void setTermo2(boolean termo2) {
		this.termo2 = termo2;
	}

	public boolean isTermo3() {
		return termo3;
	}

	public void setTermo3(boolean termo3) {
		this.termo3 = termo3;
	}

	public boolean isTermo4() {
		return termo4;
	}

	public void setTermo4(boolean termo4) {
		this.termo4 = termo4;
	}

	public boolean isTermo5() {
		return termo5;
	}

	public void setTermo5(boolean termo5) {
		this.termo5 = termo5;
	}

	public boolean isTermo6() {
		return termo6;
	}

	public void setTermo6(boolean termo6) {
		this.termo6 = termo6;
	}

	public boolean isTermo7() {
		return termo7;
	}

	public void setTermo7(boolean termo7) {
		this.termo7 = termo7;
	}

	public boolean isTermo8() {
		return termo8;
	}

	public void setTermo8(boolean termo8) {
		this.termo8 = termo8;
	}

	public boolean isTermo9() {
		return termo9;
	}

	public void setTermo9(boolean termo9) {
		this.termo9 = termo9;
	}

	public boolean isTermo10() {
		return termo10;
	}

	public void setTermo10(boolean termo10) {
		this.termo10 = termo10;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNome2() {
		return nome2;
	}

	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public boolean isTermo11() {
		return termo11;
	}

	public void setTermo11(boolean termo11) {
		this.termo11 = termo11;
	}

	public String getDataNascimentoString() {
		return dataNascimentoString;
	}

	public void setDataNascimentoString(String dataNascimentoString) {
		this.dataNascimentoString = dataNascimentoString;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Usuario(String nome, String mail, String telefone, String cpf) {
		super();
		this.nome = nome;
		this.mail = mail;
		this.telefone = telefone;
		this.cpf = cpf;
	}

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

}
