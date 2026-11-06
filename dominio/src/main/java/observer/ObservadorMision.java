package observer;

import modelo.mision.InformeMision;

import java.util.ArrayList;

public interface ObservadorMision {
    void actualizarInformes(ArrayList<InformeMision> historial);
}
