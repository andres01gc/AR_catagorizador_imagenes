package gc.comas;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Inicio {
    private List<String> pathImagenes = new ArrayList<>();
    private List<String[]> datosLinea = new ArrayList<>();
    private String[] partes_linea;

    Inicio() {
        cargarPathsTodasImagenes(); //done!
        cargarCsv("E:\\Gc\\Trabajo\\Arce\\Botellas\\code.csv");
        separarImagenesPorCategoria("E:\\Gc\\Trabajo\\Arce\\Botellas\\code.csv");

    }

    void cargarPathsTodasImagenes() {
        File principalFile = new File("E:\\Gc\\Trabajo\\Arce\\Botellas\\Imagenes_Finales\\Todas");

        for (File f : principalFile.listFiles()) {
            if (f.getName().toUpperCase().contains(".JPG")) {
                System.out.println(f.getAbsolutePath());
                pathImagenes.add(f.getAbsolutePath());
            }
        }
        System.out.println("Se Cargan los path de las imagenes");
    }

    public void cargarCsv(String path) {
        BufferedReader in = null;
        String line = null;
        boolean ttl = true;

        // Cargo el archivo csv
        try {
            in = new BufferedReader(new FileReader(path));
            line = in.readLine();//leo la primera linea de los titlos
            partes_linea = line.split(",");
            //elimino los espacios de los titulos
            for (int i = 0; i < partes_linea.length; i++) {
                partes_linea[i] = partes_linea[i].trim();
//                System.out.println(partes_linea[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean act = true;
        while (act) {
            try {

                if ((line = in.readLine()) != null) {
//                    System.out.println(line);
                    datosLinea.add(line.split(","));
                } else {
                    act = false;
                    System.out.println("Se finaliza de leer la ultima linea");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("se cargan los datos csv");
    }
}
