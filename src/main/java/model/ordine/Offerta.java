package model.ordine;

public interface Offerta {
    public int getCodice();
    public void setCodice(int codice);

    public int getSconto();
    public void setSconto(int sconto);

    public boolean isValido();
    public void setValido(boolean valido);

    public String getNome();
    public void setNome(String nome);

    public float getPrezzo();
    public void setPrezzo(float prezzo);
}
