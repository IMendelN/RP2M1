import java.util.List;

public class Ovelha extends Animais {
	private static final int IDADE_PROCRIACAO = 5;
	private static final int IDADE_MAXIMA = 40;
	private static final double PROBABILIDADE_PROCRIACAO = 0.15;
	private static final int TAMANHO_MAXIMO_NINHADA = 4;

	private Localizacao localizacao;

	public Ovelha(boolean idadeRandomica, Campo campo, Localizacao localizacao) {
		super(idadeRandomica, campo, IDADE_MAXIMA);
		setLocalizacao(localizacao);
	}

	public void corre(List<Ovelha> novasOvelhas) {
		localizacao = incrementaIdade(IDADE_MAXIMA, localizacao);
		if (estaVivo()) {
			daALuz(novasOvelhas);
			Localizacao newLocalizacao = getCampo().localizacaoAdjacenteLivre(localizacao);
			if (newLocalizacao != null) {
				setLocalizacao(newLocalizacao);
			} else {
				setMorte();
			}
		}
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	private void setLocalizacao(Localizacao newLocalizacao) {
		if (localizacao != null) {
			getCampo().limpa(localizacao);
		}
		localizacao = newLocalizacao;
		getCampo().lugar(this, newLocalizacao);
	}

	private void daALuz(List<Ovelha> novasOvelhas) {
		List<Localizacao> livre = getCampo().localizacoesAdjacentesLivres(localizacao);
		int nascimentos = procria(IDADE_PROCRIACAO, PROBABILIDADE_PROCRIACAO, TAMANHO_MAXIMO_NINHADA);
		for (int b = 0; (b < nascimentos) && (livre.size() > 0); b++) {
			Localizacao loc = livre.remove(0);
			Ovelha jovem = new Ovelha(false, getCampo(), loc);
			novasOvelhas.add(jovem);
		}
	}

	public boolean estaViva() {
		return estaVivo();
	}

	public void setMorte() {

		super.setMorte(localizacao);
		if (localizacao != null)
			localizacao = null;
	}

	public Object getClasse() {
		return this;
	}
}
