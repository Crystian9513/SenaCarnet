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

    UsuariosJpaController controlUsuario = new UsuariosJpaController();

    public static void main(String[] args) {
        IncriptacionContraseñas as = new IncriptacionContraseñas();
        as.Asiganacion();
    }

    public void Asiganacion() {
        List<Usuarios> listaUsuario = controlUsuario.findUsuariosEntities();
        for (Usuarios listauser : listaUsuario) {
            String clave = listauser.getClaves();
            // Verifica si la clave ya está encriptada
            if (!esClaveEncriptada(clave)) {
                // Si la clave no está encriptada, entonces la encripta
                String id = String.valueOf(listauser.getClaves());
                clave = listauser.EncryptarClave(id);
                listauser.setClaves(clave);
                try {
                    controlUsuario.edit(listauser);
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(IncriptacionContraseñas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean esClaveEncriptada(String clave) {
        // Verifica si la clave comienza con el prefijo "ENC:"
        return clave.startsWith("$1008");
    }

}
