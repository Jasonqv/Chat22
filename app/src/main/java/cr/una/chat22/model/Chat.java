package cr.una.chat22.model;

public class Chat {

    private String emisor;
    private String Receptor;
    private String Mensaje;

    public Chat(String emisor, String receptor, String mensaje) {
        this.emisor = emisor;
        Receptor = receptor;
        Mensaje = mensaje;
    }

    public Chat() {
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String receptor) {
        Receptor = receptor;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
