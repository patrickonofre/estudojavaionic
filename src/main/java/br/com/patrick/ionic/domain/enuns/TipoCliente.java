package br.com.patrick.ionic.domain.enuns;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer cod;
	private String descricao;
	
	
	private TipoCliente(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}


	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		} 
		
		for(TipoCliente tc: TipoCliente.values()) {
				if(codigo.equals(tc.getCod())) {
					return tc;
				}
		}
		
		throw new IllegalArgumentException("Id inválido - Id :" + codigo);
	}
}
