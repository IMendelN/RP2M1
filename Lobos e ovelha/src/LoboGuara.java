import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class LoboGuara
{
    private static final int IDADE_PROCRIACAO = 10;
    private static final int IDADE_MAXIMA = 150;
    private static final double PROBABILIDADE_PROCRIACAO = 0.75;
    private static final int TAMANHO_MAXIMO_NINHADA = 5;
    private static final int VALOR_FOME_OVELHA = 7;
    private static final Random rand = Randomizador.getRandom();
    
    private int idade;
    private boolean vivo;
    private Localizacao localizacao;
    private Campo campo;
    private int nivelFome;
//ok
    public LoboGuara(boolean idadeRandomica, Campo campo, Localizacao localizacao)
    {
        idade = 0;
        vivo = true;
        this.campo = campo;
        setLocalizacao(localizacao);
        if(idadeRandomica) {
            idade = rand.nextInt(IDADE_MAXIMA);
            nivelFome = rand.nextInt(VALOR_FOME_OVELHA);
        }
        else {
            nivelFome = VALOR_FOME_OVELHA;
        }
    }
//ok
    public void caca(List<LoboGuara> novosLobos)
    {
        incrementaIdade();
        if(vivo) {
            daALuz(novosLobos);            
            Localizacao newLocalizacao = procuraComida(localizacao);
            if(newLocalizacao == null) { 
                newLocalizacao = campo.localizacaoAdjacenteLivre(localizacao);
            }
            if(newLocalizacao != null) {
                setLocalizacao(newLocalizacao);
            }
            else {
                setMorte();
            }
        }
    }
//ok
    public boolean estaVivo()
    {
        return vivo;
    }
//ok
    public Localizacao getLocalizacao()
    {
        return localizacao;
    }
//ok
    private void setLocalizacao(Localizacao newLocalizacao)
    {
        if(localizacao != null) {
            campo.limpa(localizacao);
        }
        localizacao = newLocalizacao;
        campo.lugar(this, newLocalizacao);
    }
//ok     
    private void incrementaIdade()
    {
        idade++;
        if(idade >= IDADE_MAXIMA) {
            setMorte();
        }
    }
//Duvidas sobre onde utilizar, atualmente add em procura comida, talvez devesse ser usado em caca;
    private void incrementaFome()
    {
        nivelFome--;
        if(nivelFome == 0) {
            setMorte();
        }
    }
//ok (teoricamente)    
    // Add ifs para testar se o animal é uma ovelha e se a ovelha está viva;
    //Add incrementaFome();
    private Localizacao procuraComida(Localizacao localizacao)
    {
        List<Localizacao> adjacente = campo.localizacoesAdjacentes(localizacao);
        Iterator<Localizacao> it = adjacente.iterator();
        while(it.hasNext()) {
            Localizacao onde = it.next();
            Object animal = campo.getObjectAt(onde);
            if(animal instanceof Ovelha){
        	Ovelha ovelha = (Ovelha)animal;
        	if(ovelha.estaViva()) {
            ovelha.setMorte();
            nivelFome = VALOR_FOME_OVELHA;
            return onde;
        	}else{incrementaFome();}
            }
            
        }
        return null;
    }
//ok 
    private void daALuz(List<LoboGuara> novosLobos)
    {
        List<Localizacao> livre = campo.localizacoesAdjacentesLivres(localizacao);
        int nascimentos = procria();
        for(int b = 0; b < nascimentos; b++) {
            Localizacao loc = livre.remove(0);
            LoboGuara jovem = new LoboGuara(false, campo, loc);
            novosLobos.add(jovem);
        }
    }
//ok    
    private int procria()
    {
        int nascimentos = 0;
        if(podeProcriar() && rand.nextDouble() < PROBABILIDADE_PROCRIACAO) {
            nascimentos = rand.nextInt(TAMANHO_MAXIMO_NINHADA) + 1;
        }
        return nascimentos;
    }
//ok
    private boolean podeProcriar()
    {
        return idade > IDADE_PROCRIACAO;
    }
//ok
    private void setMorte()
    {
        vivo = false;
        if(localizacao != null) {
            campo.limpa(localizacao);
            localizacao = null;
            campo = null;
        }
    }
}
