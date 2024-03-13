/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;



import controladores.UsuariosJpaController;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Peralta
 */
public class IncriptacionContraseñas {

    UsuariosJpaController controlVotantes = new UsuariosJpaController();

    public static void main(String[] args) {
        IncriptacionContraseñas as = new IncriptacionContraseñas();
        as.Asiganacion();
    }

    public void Asiganacion() {
        List<Usuarios> listaVotantes = controlVotantes.findUsuariosEntities();
        for (Usuarios listaVotante : listaVotantes) {
            String id = String.valueOf(listaVotante.getClaves());
            String clave = listaVotante.EncryptarClave(id);
            listaVotante.setClaves(clave);
            try {
                controlVotantes.edit(listaVotante);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(IncriptacionContraseñas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
